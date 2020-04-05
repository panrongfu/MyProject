package com.project.pan.myproject.kotlin

interface Person: Name {
    val firstName:String
    val lastName:String
    override var name: String
        get() = "$firstName + $lastName"
        //使用默认构造方法 就filed
         set(value){
         }

}