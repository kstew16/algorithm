//1157 Memory, String 은 Char 에 비해 굉장히 큰 메모리를 사용함
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

fun main( )=with(BufferedReader(InputStreamReader(System.`in`))){
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    var maxVal = 0
    var maxChar = ' '
    with(readLine().uppercase(Locale.getDefault())){
        this.toSet().forEach {
            alphabet ->
            val count =  this.count {
                it == alphabet
            }
            if(count>maxVal){
                maxVal = count
                maxChar = alphabet
            }
            else if(count==maxVal){
                maxChar = '?'
            }
        }
    }
    bw.write(maxChar.toString())
    bw.flush()
    bw.close()
    close()
}