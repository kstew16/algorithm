import java.io.BufferedReader
import java.io.InputStreamReader

fun main(){
    infix fun Int.on(i:Int) = this or (1 shl i)
    val br = BufferedReader(InputStreamReader(System.`in`))
    val n = br.readLine().toInt()
    // dp[l][j][b] 길이가 l 인, j로 끝나는, 고유값이 b 인
    val dp = Array(n+1){Array(10){LongArray(1024) }}.apply {
        for(i in 1..9){
            this[1][i][0 on i] = 1
        }
    }
    val mod = 1000000000L
    for(l in 1 until  n){
        for(j in 0..9){
            for(b in 0 until 1024){
                val cur = dp[l][j][b]
                // 맨 뒷자리에 j+1 추가하기
                if(j in 0..8) dp[l+1][j+1][b on j+1]  = (dp[l+1][j+1][b on j+1] + cur)%mod
                // 맨 뒷자리에 j-1 추가하기
                if(j in 1..9) dp[l+1][j-1][b on j-1] = (dp[l+1][j-1][b on j-1] + cur)%mod
            }
        }
    }
    var sum = 0L
    for(i in 0..9) sum = (sum + dp[n][i][1023])%mod
    println(sum)
}