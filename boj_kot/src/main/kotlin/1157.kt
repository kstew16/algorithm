import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

fun main( )=with(BufferedReader(InputStreamReader(System.`in`))){
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    var maxVal = 0
    var maxChar = ""
    with(readLine().uppercase(Locale.getDefault())){
        this.chunked(1).toSet().forEach {
                alphabet->
            val count =  this.count {
                it.toString() == alphabet
            }
            if(count>maxVal){
                maxVal = count
                maxChar = alphabet
            }
            else if(count==maxVal){
                maxChar = "?"
            }
        }
    }
    bw.write(maxChar)
    bw.flush()
    bw.close()
    close()
}