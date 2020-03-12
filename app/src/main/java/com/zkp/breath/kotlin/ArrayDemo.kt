package com.zkp.breath.kotlin

/**
 * 数组，即便有自动推断类型，最好还是指定其泛型类型或者使用指定类型的包装函数（intArrayOf）进行创建。这样才能
 * 避免类型强转出错。
 */
fun main() {
    // 可空类型
    val arrayOfNulls = arrayOfNulls<String>(3)
    // val arrayOfNulls: Array<String?> = arrayOfNulls(3) // 另外一种写法
    // 设置对应角标的值的不同写法
    arrayOfNulls.set(0, "a")
    arrayOfNulls[1] = "b"   // 重置了get/set方法
    arrayOfNulls[2] = "c"
    arrayOfNulls.forEach { println("字符串数组内容: " + it) }
    println()

    // 自动推断类型,不安全
    val arrayOf = arrayOf(1, 2, 3)
    arrayOf.forEach { println("int数组内容: " + it) }
    // 指定类型
    val intArrayOf = intArrayOf(1, 2, 3)
    intArrayOf.forEach { println("无封箱int数组内容: " + it) }
    println()
    // 指定类型，通过lambda表达式赋值
    val intArray = IntArray(5) { index -> index * 2 }
    intArray.forEach { print("无封箱int数组内容创建方式2：$it") }

    // 创建一个 Array<String> 初始化为 ["0", "1", "4", "9", "16"]
    // index表示角标
    val asc = Array(5, { index -> (index * index).toString() })
    asc.forEach { println(it) }
    println()

}


