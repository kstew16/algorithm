import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val n= readLine().toInt()
    val tmp = Array(51){
        mutableSetOf<String>()
    }
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    repeat(n){
        val word = readLine()
        tmp[word.length].add(word)
    }
    for(i in tmp.indices){
        tmp[i].sorted().forEach{
            bw.write(it+"\n")
        }
    }

    bw.flush()
    bw.close()
    close()
}