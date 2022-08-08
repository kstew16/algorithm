import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    println(32)
    repeat(32){
        repeat(31){
            print("0 ")
        }
        println("0")
    }
}