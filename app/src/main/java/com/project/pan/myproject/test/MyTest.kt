package com.project.pan.myproject.test

import io.reactivex.Observable
import io.reactivex.Observer


/**
 * @Author: panrongfu
 * @CreateDate: 2020/4/1 20:44
 * @Description: java类作用描述
 */
class MyTest {

    companion object{

        @JvmStatic
        fun main(args: Array<String>) {
            val result1 = resultByOpt(2) {
                it -1
            }
            val result2 = resultByOpt2(2,2) {
                num1,num2 -> num2 + num1
            }
            println(">>> $result1")
            println(">>> $result2")
        }

        private fun resultByOpt(num : Int, funSum : (Int) -> Int) :Int {
            return funSum(num)
        }
        private fun resultByOpt2(num1 : Int, num2:Int, funSum : (Int,Int) -> Int) :Int {
            return funSum(num1,num2)
        }
    }
}