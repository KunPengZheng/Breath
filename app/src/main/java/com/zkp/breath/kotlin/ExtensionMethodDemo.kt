package com.zkp.breath.kotlin

import android.content.res.Resources
import android.util.TypedValue

/**
 * 扩展：给已有的类去额外添加函数和属性，而且既不需要改源码也不需要写子类，虽然是定义在其他地方，但是在使用该类的时候
 *      扩展函数/属性也会和普通函数/属性那样进行提示，好处在于能避免相同的工具方法的书写。
 *
 * 扩展函数：
 * 1.顶层扩展函数：并不属于任何一个类，只属于所在的package，只是限制只能通过某个类的对象才能调用。
 * 2.成员扩展函数：既是所在类的成员函数，又是前缀类的扩展函数，调用的时候需要使用分发函数进行桥接调用。
 * 3.顶层扩展函数和成员扩展函数区别在于可见性。
 * 4.顶层扩展函数允许使用函数引用的调用方式，而成员扩展函数不允许。
 *
 *
 * 一般的扩展函数或扩展属性我们都定义在顶层（扩展可以视为一种工具方法/属性）
 */

open class H {
    open fun p() = println("H.p")
}

class J : H() {
    override fun p() = println("J.p")
}

// =====================================================
// =====================================================

open class Particle

class Electron : Particle()

/**
 * 分发接收者类型是虚拟的（如果子类重写父类的方法，那么会调用子类的重写扩展函数），
 * 扩展接收者静态解析（方法参数声明类型是什么类型就是什么类型，与实际接收的实例类型无关）
 */
open class Element(val name: String = "") {

    //  三个方法的名字都是一致的，但是三个方法隶属的类是不同的，所以不会冲突

    open fun Particle.react(name: String) {
        println("$name 与粒子发生反应")
    }

    open fun Electron.react(name: String) {
        println("$name 与电子发生反应")
    }

    fun react(p: Particle) {
        p.react(name)   // 静态解析的，和接收的参数的实例的类型无关
    }
}

open class NobleGas(name: String) : Element(name) {

    override fun Particle.react(name: String) {
        println("$name 与粒子发生反应")
    }

    override fun Electron.react(name: String) {
        println("$name 与电子发生反应")
    }

    // 重载，因为父类已经有了一个同名方法
    fun react(p: Electron) {
        p.react(name)
    }
}

open class M
class N : M()

fun M.p() = println("M.p")
fun N.p() = println("N.p")


// =====================================================
// =====================================================

class Host(val hostname: String) {
    fun printHostname() {
        print(hostname)
    }
}

/**
 * 类内声明其他类的扩展函数。
 * 扩展声明所在的类的实例称为分发接收者（Connection），调用扩展方法的类的实例称为扩展接收者（Host）
 */
class Connection(val host: Host, val port: Int) {

    fun printPort() {
        print(port)
    }

    fun printHostname() {}

    fun Host.printConnectionString() {
        // 调用 Host.printHostname()，因为这里相当于this.printHostname()，而this指代调用者即Host对象
        printHostname()
        // 调用 Connection.printPort()，因为这里相当于this@Connection.printPort（）
        printPort()
        // 分发接收者和扩展接收者同名函数情况下，调用分发接收者的方法加上： this@类名
        this@Connection.printHostname()
    }

    fun connect() {
        host.printConnectionString()   // 调用扩展函数
    }
}

// =====================================================
// =====================================================

open class D3 {
}

class D1 : D3() {
}

open class C3 {
    open fun D3.foo() {
        println("D.foo in C")
    }

    open fun D1.foo() {
        println("D1.foo in C")
    }

    fun caller(d: D3) {
        d.foo()
    }
}

class C4 : C3() {
    override fun D3.foo() {
        println("D.foo in C1")
    }

    override fun D1.foo() {
        println("D1.foo in C1")
    }
}

// =====================================================
// =====================================================

class MyClass {

    /**
     * 1.因为伴生对象是在外部类实例化的时候才去实例化，所以伴生对象是不能调用外部类的成员方法或者属性的。
     * 2.伴生对象可以有扩展函数
     */
    companion object {

        val myClassField1: Int = 1
        var myClassField2 = "this is myClassField2"

        fun companionFun1() {
            println("this is 1st companion function.")
            foo()
        }

        // 伴生对象的成员函数
        fun companionFun2() {
            println("this is 2st companion function.")
            companionFun1()
        }
    }

    // 主构函数方法体
    init {
        test2()
    }

    // 成员函数
    fun test2() {
        /**
         * 注意：
         * 1.类内的其它函数优先引用类内扩展的伴随对象函数，即对于类内其它成员函数来说，类内扩展屏蔽类外扩展.
         * 2.类内的伴随对象扩展函数只能被类内的其他函数调用。
         */
        // 下面两种写法都可以
        foo()
        MyClass.foo()   // 类名 + 伴生对象扩展函数名
        Companion.foo()     // Companion关键字 + 伴生对象扩展函数名
    }

    // 类内伴生对象扩展函数
    // 基于伴生对象依赖外部类，伴生对象的扩展函数也只能外部类（内部）才能调用。
    fun Companion.foo() {
        println("伴随对象的扩展函数（内部）")
    }
}

// 顶层伴生对象的扩展函数
fun MyClass.Companion.foo() {
    println("伴随对象的扩展函数")
}

// 顶层伴生对现象的扩展变量
val MyClass.Companion.no: Int
    get() = 10


// =====================================================
// =====================================================

fun String?.toString(): String {
    if (this == null) return "null"
    // 空检测之后，“this”会自动转换为非空类型，调用不需要添加?.
    return toString()
}

// ======================================================
// ======================================================

/**
 * 扩展属性:
 * 1. 扩展属性允许定义在类或者kotlin文件中，不允许定义在函数中
 * 2. 扩展属性不能有初始化器，所以var的set访问器没有实际作用意义，所以一般用val修饰符
 * 3. 其实扩展变量和扩展方法作用一样，因为变量的get访问器其实就是一个方法。有些东西写成扩展属性比扩展函数更加直观。
 */
val <T> List<T>.cusLastIndex: Int
    get() = 1

var List<String>.xshh: Int
    get() = 1
    set(value) {
        // 没有初始化器，没有后台字段field，不能进行实际操作
    }

//  dp转px
val Float.dp
    get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            Resources.getSystem().displayMetrics
    )

// 把扩展函数的引用赋值给函数类型变量。
val a1: String.(Int) -> Unit = String::method1      // 1
val a11: (String, Int) -> Unit = String::method1    // 2,等同于1。扩展函数的扩展接受者可以作为一个参数定义
val a1x1: String.(Int) -> Unit = ::method2
val a1x11: (String, Int) -> Unit = ::method2

val a2: String.(Int) -> Unit = {
    println("调用者:$this")    // this表示扩展接收者
    println("参数:$it")   // 只有一个参数的时候可以忽略不写，使用it代替。
}

val a3: String.(Int) -> Unit = { i: Int ->
    plus("调用者:$this")
    println("参数:$i")
}

// ======================================================
// ======================================================

class Extensions {

    private fun String.method1(i: Int) {

    }

    fun test(ss: String) {
        "".method1(1)
        /**
         * 函数引用方式的调用只能用于顶层扩展函数，而成员扩展函数不允许。
         * 原因：因为不知用谁的类名，是接收者还是方法所在类，所以kotlin干脆就不允许这种调用方式了。
         */
//        (String::method1)(1)  // 该扩展函数的定义区域不允许此方式调用
    }
}

fun String.method1(i: Int) {
    println(i)
}

fun method2(s: String, i: Int) {

}

fun main(args: Array<String>) {
    val ele: H = J()
    ele.p()     // 输出"J.p",动态解析就是和java的多态一样
    println()

    // =====================================================
    // =====================================================

    val al = Element("铝")
    al.react(Particle())    // 铝和粒子发生反应
    al.react(Electron()) // 铝和粒子发生反应
    println()

    val a2 = NobleGas("氢")
    a2.react(Particle())    // 调用的是父类的方法
    a2.react(Electron())  // 调用的是重载了父类方法的重载方法
    println()

    val n = N()
    n.p()   // n的类型为N

    val m: M = N()  // 多态
    m.p()   // 扩展函数静态解析

    // =====================================================
    // =====================================================

    C3().caller(D3())   // 输出 "D.foo in C"
    C4().caller(D3())  // 输出 "D.foo in C1" —— 分发接收者虚拟解析
    C3().caller(D1())  // 输出 "D.foo in C" —— 扩展接收者静态解析
    println()

    // =====================================================
    // =====================================================

    // 调用的是扩展函数，因为我们声明为可null类型。（和重载的含义相似）
    val t: String? = ""
    println(t.toString())

    // =====================================================
    // =====================================================

    println("no:${MyClass.no}")
    // 类内的伴随对象扩展函数只能被类内的函数引用，不能被类外的函数和伴随对象内的函数引用；
    // 所以这里调用的是顶层伴生对象扩展函数
    MyClass.foo()
    MyClass.companionFun2()


    val cusLastIndex = arrayListOf<String>("1", "2").cusLastIndex

    /**
     * method1属于顶层函数，所以扩展函数用函数引用的方式创建并使用。
     *  (String::method1)("", 1) 理解：创建了一个属于String类的method1（）函数的引用，并且方法的首位参数也是
     *  String类型。其实转换成java代码后还是一个FunctionX类，一想到函数引用就要想到FunctionX类。
     */
    // 以下都调用方式都相互等价
    "".method1(1)   // 最常见的写法
    (String::method1)("", 1)  // 顶层扩展函数的函数引用，类名的调用方式，首位是扩展函数的接收者对象
    String::method1.invoke("", 1)
    (""::method1)(1)  // 没用使用invoke调用，所以前面的函数引用要用（）包裹
    (""::method1).invoke(1)
    ""::method1.invoke(1)

    "rengwuxian".a1(1)
    a1("rengwuxian", 1)
    a1.invoke("rengwuxian", 1)  // 声明的时候已经可以知道是一个函数引用了，所以可以这样调用

    /**
     * 使用函数的引用去调用的时候，不管是一个普通的成员函数还是扩展函数，你都需要把调用者作为第一个参数填进去。
     */
    (Extensions::test)(Extensions(), "")

    // 无接受者的函数引用赋值给有接受者的lambda表达式
    //    "rengwuxian".method2(1) // 不允许调用，报错
    val f: String.(Int) -> Unit = ::method2
    "rengwuxian".f(1) // 可以调用
    val f1: (String, Int) -> Unit = ::method2
    f1("", 1)   // 1
    f1.invoke("", 1)    // 2和1的作用相同

    // 有接受者的函数引用赋值给无接受者的lambda表达式
    "rengwuxian".method1(1) // 可以调用
    val b: (String, Int) -> Unit = String::method1
    b("rengwuxian", 1)
//    "rengwuxian".b(1) // 不允许调用，报错
}