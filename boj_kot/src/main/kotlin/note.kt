import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.absoluteValue
import kotlin.math.sqrt

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    println(100)
    for(i in 0 until 100){
        println("${(i*10).toDouble()} ${(i*10).toDouble()}")
    }
}


