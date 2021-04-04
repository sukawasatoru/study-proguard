package jp.tinyport.example.proguard

import android.app.Activity
import android.os.Bundle
import android.util.Log
import kotlin.random.Random

private data class Foo(val myNum: Int)

class MainActivity: Activity() {
    companion object {
        const val TAG = "MainActivity"
    }

    private var classVal: String? = "classVal"
    private var fooNonNull = Foo(999)
    private var fooNullable: Foo? = Foo(999)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val localVal = "localVal"

        println("localVal")
        Log.i(TAG, "localVal: $localVal")

        println("classVal")
        Log.i(TAG, "classVal: $classVal")

        println("Random")
        Log.i(TAG, "Random: ${Random(0).nextInt()}")

        println("joinString")
        val joinString = listOf("aa", "bb")
        Log.i(TAG, "joinString: ${joinString.joinToString { it }}")

        println("fooLocalNonNull.myNum")
        val fooLocalNonNull = Foo(999)
        Log.i(TAG, "fooLocalNonNull.myNum: ${fooLocalNonNull.myNum}")

        println("fooNonNull.myNum")
        Log.i(TAG, "fooNonNull.myNum: ${fooNonNull.myNum}")
        println("fooNullable.myNum")
        Log.i(TAG, "fooNullable.myNum: ${fooNullable?.myNum}")

        println("logString(\"ABC\")")
        logString("ABC")
        println("logInt(999)")
        logInt(999)
    }

    private fun logString(msg: String) {
        Log.i(TAG, "log: $msg")
    }

    private fun logInt(msg: Int) {
        Log.i(TAG, "log: $msg")
    }
}
