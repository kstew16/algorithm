import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main( )=with(BufferedReader(InputStreamReader(System.`in`))){
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    bw.write(readLine().split(" ").map{it.reversed().toInt()}.maxOf { it }.toString())
    bw.flush()
    bw.close()
    close()
}