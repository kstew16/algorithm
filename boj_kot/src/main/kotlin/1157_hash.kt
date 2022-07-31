//1157 Hash
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

fun main( )=with(BufferedReader(InputStreamReader(System.`in`))){
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    var maxVal = 0
    var maxChar = ' '
    var history = hashMapOf<Char,Int>()
    with(readLine().uppercase(Locale.getDefault())){
        this.forEach {
                alphabet ->
            history[alphabet] = history[alphabet]?.plus(1)?:1
        }
    }
    history.forEach{
        (alphabet,count)->
        if(count>maxVal){
            maxVal = count
            maxChar = alphabet
        }
        else if(count==maxVal){
            maxChar = '?'
        }
    }

    bw.write(maxChar.toString())
    bw.flush()
    bw.close()
    close()
}