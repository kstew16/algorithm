import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val st = StringTokenizer(readLine())
    val n = st.nextToken().toInt()
    val m = st.nextToken().toInt()
    val dp = Array(n+1){IntArray(m+1){Int.MAX_VALUE} }.apply {
        for(i in 0..1)
            for(j in 0..1) this[i][j] = 0
    }

    // 이미 작성된 조각들을 최소로 이어서 n*m 짜리 조각을 만드는 법 탐구
    for(j in 1 .. n){
        for(i in 1 .. m){
            for(vertical in 1 .. n-j){
                dp[vertical + j][i] = (1+dp[vertical][i]+dp[j][i]).coerceAtMost(dp[vertical + j][i])
            }
            for(horizontal in 1 .. m-i){
                dp[j][i+horizontal] = (1+dp[j][i]+dp[j][horizontal]).coerceAtMost(dp[j][i+horizontal])
            }
        }
    }
    println(dp[n][m])
}