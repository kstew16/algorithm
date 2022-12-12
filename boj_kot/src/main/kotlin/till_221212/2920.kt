import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main( )=with(BufferedReader(InputStreamReader(System.`in`))){
    val arr = readLine().split(" ")
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    var sample = listOf("1","2","3","4","5","6","7","8")
    bw.write(
        if(arr == sample) "ascending"
        else if(arr == sample.reversed())"descending"
        else "mixed"
    )
    bw.flush()
    bw.close()
    close()
}