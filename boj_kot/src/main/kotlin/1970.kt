import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

// 선 하나를 긋는 것은 쪼개진 두 개의 문제를 낳는다. 모든 악수 대상에 대해서 차례대로 부분 문제의 최댓값을 취하는 방식으로 하면 될듯 일단 T-B
fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val st = StringTokenizer(readLine())
    val type = IntArray(n){st.nextToken().toInt()}
    // dp[i][j] i번 사람부터 j명의 사람이 지을 수 있는 짝의 최대 수
    val dp = Array(n){IntArray(n+1){-1} }.apply {
        for(i in 0 until n) {
            this[i][0] = 0
            this[i][1] = 0
        }
    }
    fun cheers(from:Int,m:Int):Int{
        if(dp[from][m]!=-1) return dp[from][m]
        var ans = 0
        ans =  (cheers((from+1)%n,m-1)).coerceAtLeast(ans)
        val myType = type[from]
        for(i in 2..m){
            val target = (from+i-1)%n
            val next = (from+1)%n
            if(myType == type[target]) {
                // 짝을 짓는 경우
                val clockwise = cheers(next,i-2)
                val cClockwise = if(m-i>1) cheers((target+1)%n, m-i) else 0
                ans = (1 + clockwise + cClockwise ).coerceAtLeast(ans)
            }
        }
        dp[from][m] = ans
        println("made dp[$from][$m] ")
        return ans
    }
    print(cheers(0,n))
}