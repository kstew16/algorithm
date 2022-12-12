import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main( )=with(BufferedReader(InputStreamReader(System.`in`))){
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    bw.write(readLine().split(" ").filter { it != "" }.size.toString())
    bw.flush()
    bw.close()
    close()
}