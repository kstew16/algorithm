import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

private const val N_ALPHABETS = 26
private const val N_DIGITS = 10
fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val condition = br.readLine().chunked(1)
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    var count = 0
    for(i in condition.indices){
        var cur = if(condition[i]=="c")N_ALPHABETS else N_DIGITS
        if(i==0){
            when(condition[i]){
                "c" -> count=cur
                "d" -> count=cur
            }
        }
        else{
            if(condition[i]==condition[i-1]){
                cur-=1
            }
            count *= cur
        }
    }
    bw.write("$count")
    bw.flush()
    bw.close()
    br.close()
}