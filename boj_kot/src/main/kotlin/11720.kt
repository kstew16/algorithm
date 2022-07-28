import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main( )=with(BufferedReader(InputStreamReader(System.`in`))){
    readLine()
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    bw.write(readLine().chunked(1).sumOf{it.toInt()}.toString())
    bw.flush()
    bw.close()
    close()
}