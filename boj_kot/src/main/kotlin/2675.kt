import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main( )=with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    repeat(n){
        val (times,str) = readLine().split(" ")
        str.map {
            char->
            repeat(times.toInt()){
                bw.write(char.toString())
            }
        }
        bw.write("\n")
    }
    bw.flush()
    bw.close()
    close()
}