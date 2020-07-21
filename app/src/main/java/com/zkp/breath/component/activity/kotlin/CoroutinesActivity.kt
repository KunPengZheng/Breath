package com.zkp.breath.component.activity.kotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityCoroutinesBinding
import kotlinx.coroutines.*
import kotlin.concurrent.thread

/**
 * 什么是协程（启动一个协程可以使用 launch 或者 async 函数，协程其实就是这两个函数中闭包的代码块）：
 * 协程就是 Kotlin 提供的线程框架，但并不是说协程就是为线程而生的，协程最常用的功能是并发，而并发的典型场景就是多线程，
 * 能够在同一个代码块进行多次线程切换而不会导致多级嵌套，只形成上下级关系。用同步的方式写异步的代码
 *
 * 为什么使用协程：
 * java的线程池Executor是对线程复用和生命周期（创建，销毁）的管理，android的AsyncTask或者Handler是用于解决线
 * 程间通信（子线程切到主线程），但是一旦遇到多个api调用且需要合并结果的时候，我们一般会嵌套串行调用，明明可以并发
 * 执行由于使用了串行导致效率降低了一倍，而且也会陷入“地狱式回调”也就是嵌套太多层回调导致可读性降低。
 *
 * 挂起本质：
 * 代码执行到 suspend 函数的时候会从当前线程挂起协程，就是这个协程从正在执行它的线程上脱离（launch函数指定的线程中脱离），
 * 挂起后的协程会在suspend函数指定的线程中继续执行，在 suspend 函数执行完成之后，协程会自动帮我们把线程再切回来（切回launch函数指定的线程）。
 * suspend的意义在于提醒使用者要在协程中调用，真正实现挂起的是withContext（）这个kotlin提供的方法。
 *
 * kotlin提供的suspend函数，注意都需要在协程中调用：
 * 1.withContext（）
 * 2.delay()   等待一段时间后再继续往下执行代码,使用它就可以实现刚才提到的等待类型的耗时操作，该操作发生在launch函数指定的线程。
 *
 * 非阻塞式挂起:
 * 阻塞是发生在单线程中，挂起已经是一种切到另外的线程执行了，所以挂起一定是非阻塞的。
 */
class CoroutinesActivity : BaseActivity() {
    private lateinit var binding: ActivityCoroutinesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutinesBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        init()
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        // kotlin提供的函数简化了对Thread的使用
        thread {
            Log.i(TAG, "thread: ${Thread.currentThread().name}")
        }

        // 如果没有ui操作就没必要使用Dispatchers.Main，否则会报错。
        // 消除了嵌套关系，内部的withContext形成了上下级关系
        // 挂起函数后并不会往下继续执行，只有等挂起函数执行完毕才能接着往下执行，但这个挂起不是暂停，而是脱离的意思，脱离到其他线程执行完毕再切换原有线程继续往下执行。
        GlobalScope.launch(Dispatchers.Main) {
            // 1
            withContext(Dispatchers.IO) {
                Thread.sleep(2000)
                Log.i(TAG, "launch_IO1: ${Thread.currentThread().name}")
            }
            // 2
            withContext(Dispatchers.IO) {
                Thread.sleep(2000)
                Log.i(TAG, "launch_IO2: ${Thread.currentThread().name}")
            }

            // 3
            extractWithContext()

            // 4
            extractDelay()

            // 5
            binding.tvCoroutines.text = "tttt"
            Log.i(TAG, "launch_Main: ${Thread.currentThread().name}")
        }

    }

    // 可以把withContext放进单独的一个函数内部，但函数需要添加suspend关键字（因为withContext 是一个 suspend 函数，它需要在协程或者是另一个 suspend 函数中调用）
    private suspend fun extractWithContext() = withContext(Dispatchers.IO) {
        Log.i(TAG, "extractWithContext_IO3: ${Thread.currentThread().name}")
    }

    private suspend fun extractDelay() {
        // 等待一段时间后再继续往下执行代码,使用它就可以实现刚才提到的等待类型的耗时操作
        delay(1000)
        Log.i(TAG, "extractDelay: ${Thread.currentThread().name}")
    }

}