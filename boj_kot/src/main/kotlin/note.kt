import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.absoluteValue
import kotlin.math.sqrt

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    println(16)
    val map = Array(16){IntArray(16)}
    for(s in 0 until 16){
        for(e in 0 until 16){
            if(s==e) map[s][e] = 0
            else map[s][e] = (1000000-20000*(s+e))
        }
    }
    for(i in 0 until 16){
        println(map[i].joinToString(" "))
    }
}


