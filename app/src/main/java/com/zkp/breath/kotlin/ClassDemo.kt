package com.zkp.breath.kotlin

// 编译期常量,相当于java的静态常量
// 没有自定义 getter (即默认隐式get访问器)
const val CONST = 22

class Demo {

    // var 是 variable 的缩写， val 是 value 的缩写。
    var i: Int = 2
        set(value) {
            // field幕后字段，代表该属性
            field = field + value
        }
        get() = field + 1
    val s
        get() = i == 3

    // 虽然 val 修饰的变量不能二次赋值，但可以通过自定义变量的 getter 函数，让变量每次被访问时，返回动态获取的值。
    val ss
        // 幕后属性，指向别的属性的值来作为自身的初始值
        get() = s

    // 延迟初始化属性
    // 关键字lateinit,用于类体中的属性，顶层属性与局部变量
    // 并且仅当该属性没有自定义get和set时，必须是非空类型，不能是原生类型(你声明为Int是不被允许的)。
    lateinit var lateinitStr: String

    fun test1() {
        //重要，this::前缀是必须的。判断是否初始化
        if (this::lateinitStr.isInitialized) {

        }
    }
}

class ClassDemo {

    val s: String = ""

    // 1.要么声明的时候赋初始值
    // 2.要么可以放在init()中赋值，但as提示还是直接赋值
    var i: Int = 1

    // 方法
    // 方法声明：fun关键字 方法名（方法参数）{}
    fun f() {

    }

    // kotlin的方法的参数默认是val，所以该参数不能重新赋值，也不能添加val/var修饰。
    // 而java的的参数可以添加final修饰，一般在局部匿名内部类使用外部局部变量的时候会添加。
    // Kotlin 里这样设计的原因是保证了参数不会被修改，而 Java 的参数可修改（默认没 final 修饰）会增加出错的概率。
    fun f2(s: String) {

    }
}

// 无类体省略了花括号'{}',类体即java中的成员函数和成员变量，而在kotlin中成员变量也叫做属性
class ClassDemo2


// ‘constructor’关键字加构造参数表示主构造函数
// 方法参数表示‘（参数名 : 参数类型）’
class ClassDemo3 constructor(s: String) {

}

// 没有任何注解或者可见性修饰符可以省略关键字‘constructor’
class ClassDemo4(s: String) {

}

// 可见修饰符放在关键字‘constructor’前面
class ClassDemo5 private constructor(s: String) {

}

// 多个次构造函数（类似于java的方法重构）
// 没有主构函数，不需要在次构函数后面加this()去表示调用主构函数
class ClassDemo6 {
    constructor(s: String)
    constructor(i: Int)
    constructor(s: String, i: Int)
}

class ClassDemo7 constructor(s: String) {
    // 存在主构的话则次构函数在参数后‘: this（主构函数参数）’表示间接调用主构函数
    constructor(i: Int, s: String) : this(s) {

    }
}

// 主构函数参数存在默认值
class ClassDemo8 constructor(s: String = "默认值") {
    // this()括号中可以不传值
    constructor(i: Int, s: String) : this() {

    }
}


class ClassDemo9 constructor(s: String) {

    // 主构函数的参数可以赋值给属性
    val n: String = s
    var n1: String = s

    // val和var修饰的属性必须声明的时候就初始化或者在init()中初始化（且在init()初始化的属性必须指明数据类型）
    val n2: String
    var n3 = ""
    val n4: String? = ""
    var n5: String?

    // 初始化代码块（相当于主构函数的方法体）
    // 主构函数的参数可以在此代码块中出现
    init {
        n2 = ""
        n5 = ""

        println("初始化代码块")
        // (知识点)字符串模板 : $符号后面加上属性/变量
        // 字符串模板相当于java中的打印信息引用变量的形式。即： println("调用主构函数的参数s:" + s)
        println("调用主构函数的参数s:$s")
    }

    constructor(i: Int) : this("主构函数参数值") {
        println("次构造函数")
    }
}


class Demo13 {

    var s: String = "哈哈"
    var s1: String

    // lateinit 只能修饰var的非空数据类型的属性，必须指定类型（因为lateinit的作用只是延迟初始化）
    lateinit var s2: String
    val s3: String = ""

    // 提供get/set方法必须马上初始化，不允许在init（）中初始化。因为get/set方法都需要知道该属性的类型
    var s4: String = ""
        // field 关键词只能用于属性的get/set访问器
        // 在kotlin中，属性名=value会被编译器翻译成调用setter方法进而形成递归死循环,所以在get/set中kotlin提供了field关键字用于解决这个问题
        get() = field.toUpperCase()
        set(value) {
            if (value == "哈哈") {
                field = "大于"
            } else {
                field = "小于"
            }
        }

    val s5: String = "wo"
        get() = field.toUpperCase()


    init {
        s1 = "你好s2"
    }

    fun setup() {
        s2 = ""
    }

}


class User1 {

    init {
        // 初始化代码块，先于下面的构造器执行
        println("我是init")
    }

    // 相当于一个init{}代码块，按照声明顺序，上面的init{}先执行。
    // 只能显示声明一个主构造器，但构造代码块可以有多个。
    constructor() {
        println("我是constructor")
    }

}


fun main(args: Array<String>) {
    // 知识点
    // kotlin创建对象：val/var修饰符  变量名 = 类名（主构或者次构参数值）。
    // java创建对象： private/public修饰符 变量名 = new关键字 类名（构造函数参数值）。会java的同学一看就感觉和kotlin创建对象的方式其实很像
    val classDemo9 = ClassDemo9(1)

    val demo13 = Demo13()
    println("demo13的属性s4：${demo13.s4}")
}
