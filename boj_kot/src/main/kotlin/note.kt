import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.sqrt

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    println("32000 10000")
    for(j in 0 until 10){
        for(i in 0 until 1000){
            println("${32000-1000*j} ${(32000-1000*j)-(i+1)*2}")
        }
    }
}


