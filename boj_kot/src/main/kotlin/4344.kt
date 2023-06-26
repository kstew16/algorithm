import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

fun StringTokenizer.getInt() = this.nextToken().toInt()
fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val tc = readLine().toInt()
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    repeat(tc){
        val st = StringTokenizer(this.readLine())
        val n = st.getInt()
        var sum = 0
        val scores = IntArray(n){ st.getInt().also { sum+=it }}
        bw.write(String.format("%.3f",(100f*scores.filter { it>sum.toFloat()/n.toFloat() }.size.toFloat()/n))+"%\n")
        // format 은 사사오입, round 는 오사오입 방식 사용함. 오사오입이 통계적으로 유리한 관계로 정확도가 필요할 때는 round 로 별개 작업을 수행하기 바람
    }
    bw.flush()
    bw.close()
    this.close()
}