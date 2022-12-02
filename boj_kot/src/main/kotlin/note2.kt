import java.io.BufferedReader
import java.io.InputStreamReader

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    println(1)
    println(100000)
    for(i in 1 until 100000)print("${i+1} ")
    print(99995)

}