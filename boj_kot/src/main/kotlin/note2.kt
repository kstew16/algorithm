import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
import kotlin.math.abs

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    println(5000)
    for(i in 0 until 4999){
        print((-1000000000L + (2000000000/5000).toLong()*i))
        print(" ")
    }
    println(1000000000)
}
