import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val st = StringTokenizer(readLine())
    val type = IntArray(n){st.nextToken().toInt()}
    // dp[from][k] from 번 사람부터 k명의 사람이 지을 수 있는 짝의 최대 수 (from 번 사람 포함)
    val dp = Array(n){IntArray(n+1){0}}
    for(k in 2..n)
        for(from in 0 .. n-k){
            val myType = type[from]
            // 자신은 짝 안 짓고 뒤에걸로만 짓는걸로 초기화
            var ans = dp[from+1][k-1]
            // from 과 from+i를 짝짓고 그 사이의 짝 최대수 불러오기
            // (from 과 from+i의 짝) + (from+1 이상 from+i-1 이하 i-1 개) + (from+i+1 이상 from+k-1 이하 k-i-1 개)
            for(i in 1 until k)
                ans =  ( (if(myType == type[from+i]) 1 else 0) + (if(i>=3) dp[from+1][i-1] else 0)+(if(k-i>=3) dp[from+i+1][k-i-1] else 0)).coerceAtLeast(ans)
            dp[from][k] = ans
        }
    print(dp[0][n])
    close()
}