import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))

    repeat(st.getInt()) {
        st = StringTokenizer(readLine())
        val n = st.getInt() ; val k = st.getInt()

        val requiredTime = IntArray(n+1)
        st = StringTokenizer(readLine())
        for(i in 1..n) requiredTime[i] = st.getInt()

        val preCondition = Array(n+1){mutableListOf<Int>()}
        repeat(k){
            st = StringTokenizer(readLine())
            val a = st.getInt(); val b = st.getInt()
            preCondition[b].add(a)
        }
        // dp[i]는  i 번 건물을 완성할 수 있는 최소 시간
        val dp = IntArray(n+1){-1}
        fun fillDP(i:Int){
            var fastest = 0
            if(preCondition[i].isEmpty()) fastest = requiredTime[i]
            else preCondition[i].forEach {
                if(dp[it]==-1) fillDP(it)
                fastest = (dp[it]+requiredTime[i]).coerceAtLeast(fastest)
            }
            dp[i] = fastest
        }
        val target = StringTokenizer(readLine()).getInt()
        fillDP(target)
        bw.write("${dp[target]}\n")
    }
    bw.flush()
    bw.close()
    close()
}