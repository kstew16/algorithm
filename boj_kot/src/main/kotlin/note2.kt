import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
// 12872 제출용
fun main() {
    for(n in 1 .. 100){
        for(coolDown in 0..n){
            for(length in n..100){
                val nom = 1000000007L

                fun fact(k:Int):Long{
                    var ans = 1L
                    for(i in 2..k) ans = (ans*i.toLong())%nom
                    return ans
                }
                val singularity = coolDown+1
                val dp = Array(n+1){LongArray(length+1)}.apply {
                    if(singularity<=n)for(i in singularity..length)this[singularity][i] = fact(singularity)
                }
                for(i in (singularity+1)..n){
                    for(j in i..length){
                        dp[i][j] = (((i-coolDown).toLong()*dp[i][j-1])%nom + (i.toLong()*dp[i-1][j-1])%nom)%nom
                    }
                }
                println(dp[n][length])
            }
        }

    }
}