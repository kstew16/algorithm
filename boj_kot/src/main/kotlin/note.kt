import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val mat = Array(n){
        val st = StringTokenizer(readLine())
        IntArray(2){st.nextToken().toInt()}
    }
    // dp[a][b] ->  a 번째 행렬부터 b 번째 행렬까지 곱하는 최소 연산횟수
    //val dp = Array(n){IntArray(n){Int.MAX_VALUE}}.apply{for(i in 0 until n)this[i][i]=0}
    val dp = Array(n){IntArray(n){0}}
    // dp[a][b] = min(dp[a][b-1] + mat[a][0]*mat[b-1][1]*mat[b][1] ,dp[a+1][b] + mat[a][0]*mat[a+1][0]*mat[b][1])
    for(i in 1 until n){
        for(j in 0 until n-i){
            dp[j][j+i] =  kotlin.math.min(
                dp[j][j+i-1] + mat[j][0]*mat[j+i-1][1]*mat[j+i][1],
                dp[j+1][j+i] + mat[j][0]*mat[j+1][0]*mat[j+i][1]
            )
        }
    }
    print(dp[0][n-1])
}