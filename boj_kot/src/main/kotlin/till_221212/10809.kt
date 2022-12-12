import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main( )=with(BufferedReader(InputStreamReader(System.`in`))){
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    val word = readLine()
    for(alphabet in 'a'..'z'){
        when{
            alphabet.toString()=="z" -> bw.write("${word.indexOf(alphabet)}")
            else -> bw.write("${word.indexOf(alphabet)} ")
        }
    }
    bw.flush()
    bw.close()
    close()
    // 거.. 음.. 공백 하나만 봐주면 안됨? 귀찮은데
}