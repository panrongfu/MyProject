package com.project.pan.myproject.kotlin.enums

import android.os.Build
import android.support.annotation.RequiresApi
import java.util.function.BinaryOperator
import java.util.function.IntBinaryOperator

@RequiresApi(Build.VERSION_CODES.N)
enum class IntArithmetics: BinaryOperator<Int>, IntBinaryOperator {
    PLUS {
        override fun apply(p0: Int, p1: Int): Int {
            return  p0 + p1
        }
    },

    TIMES {
        override fun apply(p0: Int, p1: Int): Int {
            return  p0 *  p1
        }
    };
    override fun applyAsInt(p0: Int, p1: Int): Int {
        return apply(p0, p1)
    }
}