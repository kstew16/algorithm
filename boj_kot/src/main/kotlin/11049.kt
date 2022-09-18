import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
// 점화식이 완벽하지 않았음. 답코드 보고서 점화식 틀린 거 알아챘음. 수업까지 들었는데 ㅜ
fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val mat = Array(n){
        val st = StringTokenizer(readLine())
        IntArray(2){st.nextToken().toInt()}
    }
    // dp[a][b] ->  a 번째 행렬부터 b 번째 행렬까지 곱하는 최소 연산횟수
    val dp = Array(n){IntArray(n){0}}
    // dp[a][b] = for(k in 0 until i) min(dp[a][a+k]+dp[a+k+1][b]+mat[a][0]*mat[a+k][1]*mat[b][1]
    for(i in 1 until n){
        for(j in 0 until n-i){
            var optimum = Int.MAX_VALUE
            for(k in 0 until i){
                optimum = (dp[j][j+k] + dp[j+k+1][j+i] + mat[j][0]*mat[j+k][1]*mat[j+i][1] ).coerceAtMost(optimum)
            }
            dp[j][j+i] = optimum
        }
    }
    print(dp[0][n-1])
}