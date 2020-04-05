package com.project.pan.myproject.kotlin.coroutines

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Coroutines {

    companion object{

        @JvmStatic
        fun main(arg: Array<String>) {
            GlobalScope.launch { // 在后台启动一个新的协程并继续
                delay(1000L) // 非阻塞的等待 1 秒钟（默认时间单位是毫秒）
                println("World!") // 在延迟后打印输出
            }
            println("Hello,") // 协程已在等待时主线程还在继续
            runBlocking {     // 但是这个表达式阻塞了主线程
                delay(2000L)  // ……我们延迟 2 秒来保证 JVM 的存活
            }

            var job = GlobalScope.launch {
                
            }
        }
    }
}