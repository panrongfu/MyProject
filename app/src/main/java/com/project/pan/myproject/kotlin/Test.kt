package com.project.pan.myproject.kotlin

import android.net.wifi.p2p.WifiP2pManager
import com.project.pan.myproject.kotlin.generics.Box
import java.util.*
import kotlin.collections.ArrayList
typealias Predicate<T> = (T) -> Boolean
class Test {
    var str: String = "sdfdf"
    // 其中幕后字段field指的就是当前的这个属性

        set(value) {
        field = value + "222"
    }

//   例子一
//    class Person {
//        var name:String = ""
//            get() = field
//            set(value) {
//                field = value
//            }
//    }
//    例子二
//     class Person {
//        var name:String = ""
//    }
//上面两个属性的声明是等价的，例子一中的getter和setter 就是默认的getter和setter。
// 其中幕后字段field指的就是当前的这个属性，
// 它不是一个关键字，只是在setter和getter的这个两个特殊作用域中有着特殊的含义，就像一个类中的this,代表当前这个类。

  companion object{

      @JvmStatic
      fun main(arg: Array<String>){
          val numbers = listOf("one", "two", "three", "four")
          numbers.filter { it.length > 3 }
            numbers.associateWith {  }
            var test = Test()
          println("get: ${test.str}")
          test.str = "77777"
          println("set And get Str: ${test.str}")

            var wang = Wang("oooo","2222")
          println("firstName: ${wang.firstName}  lasstName: ${wang.lastName}")

          val fruits = listOf("banana", "avocado", "apple", "kiwifruit", "abkiwifruit")
          fruits .filter { it.startsWith("a") }
                  .sortedBy { it }
                  .map { it.toUpperCase() }
                  .forEach { println(it) }

           var user = User("pan",29)

          println("my name is ${user.name} age: ${user.age}")

          val box = Box<Int>(23)

          var result = fun1(1) {
              it > 2
              // it > 2 == x -> x > 2
          }
          println("the result: $result")

          var result2 = fun1(false) {
              it
              // it -> if(it) true else false
          }
          println("the result: $result")
          println("the result2: $result2")



      }

//      private fun<T> fun1(sum: T, p:Predicate<T>): () -> Boolean = {
//           p(sum)
//      }

      private fun<T> fun1(sum: T, p:Predicate<T>) = p(sum)

  }
}