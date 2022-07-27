import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.StringTokenizer
// Int 범위에 안 들어가는 입력은 애초에 toInt() 또는 parseInt()가 런타임에러 뱉음
// Int 범위는 4 * 10^9 레벨이라는걸 다시 한 번 상기 -> 이거때문에 런타임에러 2번 냄
fun main(){
    var maxPow = 0
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    val n = br.readLine().toInt()
    val st = StringTokenizer(br.readLine())
    var arr = Array(n){
        val num =st.nextToken().toLong()
        var tmp = num
        var pow = 0
        while(tmp%3==0L){
            tmp/=3
            pow+=1
        }
        maxPow = pow.coerceAtLeast(maxPow)
        longArrayOf(num, pow.toLong())
    }

    for(i in maxPow downTo 0){
        (arr.filter{it[1]==i.toLong()}.sortedBy { it[0] }).forEach {
            bw.write("${it[0]} ")
        }
    }
    bw.flush()
    bw.close()
    br.close()
}