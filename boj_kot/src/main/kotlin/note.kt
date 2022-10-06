import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer


fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    val c = readLine().toInt()
    repeat(c){
        var st = StringTokenizer(readLine())
        val start = st.getInt()
        val nScore = st.getInt()
        st = StringTokenizer(readLine())
        val scores = IntArray(nScore){st.getInt()}
        val minStep = scores.minOf { it }
        // dp[i] 남은 팔굽혀펴기 횟수가 i 회일때, 득점할 수 있는 총점의 최댓값
        val dp = Array(start+1){IntArray(start+1){-1}.apply { this[0] = 0 }}
        fun fillDP(current:Int, distance:Int):Int{
            if(dp[distance][current]!=-1) return dp[distance][current]
            else if(current<(distance+minStep)) {
                dp[distance][current] = 0
                return 0
            }
            val value = IntArray(nScore){
                val newDistance = distance+scores[it]
                if(current>=newDistance) fillDP(current-newDistance,newDistance)+scores[it]
                else 0
            }.maxOf { it }
            dp[distance][current] = value
            return value
        }
        println(fillDP(start,0))
    }
}