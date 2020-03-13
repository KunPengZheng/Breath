package com.zkp.breath.kotlin


// 数据类，提供了一些常用的方法。对应java的Bean类
// 自建函数componentN()，析构函数，copy函数
data class DataClass(val s: String, var i: Int) {
}

fun main() {

    val dataClass = DataClass("数据类", 22)
    println("toString(): ${dataClass.toString()}")
    println("componentN()组件函数: ${dataClass.component1()}")
    val copy = dataClass.copy("数据类copy", 33)
    val copy1 = dataClass.copy()
    println("copy类: ${copy.toString()}")
    println("copy内存地址比较: ${dataClass === copy1}")
    // 使用解构函数解构对象，其实内部调用的就是组件函数
    val (s, i) = copy1
    // 直接调用数据类对象其实内部调用是toString()方法
    println("copy1的toString(): ${copy1}")
    // 标准库提供的数据类
    val pair = Pair<String, String>("a", "b")
    val triple = Triple<String, String, String>("a", "b", "c")
    // 下划线表示没有使用到的变量
    val (_, s2) = pair
    println("打印参数2：${s2}")

    // 解构方法
    val (i1, i2, i3) = resolve()
    println("i1: ${i1}, i2: ${i2}, i3: ${i3}")

    // Map优雅的遍历方式
    // 接收参数接收的是一个Pair，Pair本身也是Data类
    val hashMapOf = hashMapOf("a" to 1, "b" to 2, "c" to 3)
    for ((key, value) in hashMapOf) {
        println("获取到的key:${key}，value是：${value}")
    }

}

fun resolve() = arrayOf(1, 2, 3)

