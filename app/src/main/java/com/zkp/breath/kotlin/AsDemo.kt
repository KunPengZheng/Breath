package com.zkp.breath.kotlin

/**
 * 强转Demo
 * as：强转关键字
 * as？：空安全的强转，强转失败会返回null，接收类型需要声明为可空类型
 *
 * is：如果是类的话则是类型判断（判断成功自动转为该类型，即可调用该类型的方法或者属性）；
 *     如果是int，float，long，boolean则是数值判断。相当于java的 "instance + 强转"操作
 */

open class Person {
    fun p1() {
        println("person的p1方法")
    }
}

class Man : Person() {
    fun s1() {
        println("Man的s1方法")
    }
}

fun classCheck(p: Any) {
    when (p) {
        is Person -> {
            p.p1()
            println()
        }
        is Man -> p.s1()
        else -> println("哈哈")
    }
}


fun main() {

//    // 因为类型指定为父类，所以无法执行子类的方法
//    val pp: Person = Man()
//    // 强转为子类，就能执行子类特有的方法
//    val pp2 = pp as Man
//    pp2.s1()
//
//    // 可空类型（装箱类型）
//    val ppNull: Person? = Man()
//    // 强转为指定类型（拆箱）
//    val ppNull2 = ppNull as Man
//    ppNull2.s1()
//
//    val nullP: Person? = null
//    val nullPP2 = nullP as Man  // null 不能强转为任何类型
//    nullPP2.s1()        // 报异常，程序奔溃


    // 最安全的写法
    val man: Person? = null
    // 强转失败则会返回null，所以一定要指定类型则类型还要可空。
    // 下面三种写法都可以，推荐第一种
    val man2: Man? = man as? Man    //1
    val man3 = man as? Man? //2
    val man4 = man as Man?  //3
    // 防止null异常写法，推荐
    man2?.s1()
    println("dasdasdas")
}
