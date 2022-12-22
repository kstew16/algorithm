import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
import kotlin.math.max

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))) {
    var st = StringTokenizer(readLine())
    val n = st.nextToken().toInt()
    val limit = st.nextToken().toLong()
    st = StringTokenizer(readLine())
    val energy = LongArray(n+1) {
        if(it==0) 0
        else st.nextToken().toLong()
    }
    var sum = 0L
    val accEnergy = LongArray(n+1){
        sum+= energy[it]
        sum
    }
    // dp[i] -> i 까지의 최대 잉여 에너지
    val dp = LongArray(n+1){0L}
    var s = 1
    var e = 1
    // e 까지의 최대 에너지는 최소 e-1 까지의 최대 에너지이며
    // 섭취중인 구간에서 잉여 에너지가 발생한다면 그 구간 이전까지의 최대에너지 + 구간에서 발생하는 잉여에너지
    while(s<=n && e<=n && s<=e){
        // [s,e] 구간으로 섭취한 먹이
        val inStomach = accEnergy[e] - accEnergy[s-1]
        dp[e] = max(dp[e],dp[e-1])
        if(inStomach>=limit) {
            dp[e] = max(dp[s-1] + inStomach-limit,dp[e])
        }
        // 섭취중인 구간이 limit 보다 같거나 크다면
        // 1. 먹이 하나로만으로도 limit 을 넘어가는 경우 s,e 가 동시에 증가해야 하고
        // 2. 그게 아니라면 s 만 증가시켜서 구간에 포함되는 먹이를 줄이면 됨
        if(inStomach>=limit){
            if(s==e){
                s++
                e++
            }else s++
        }
        // 섭취중인 구간이 limit 보다 작다면 update 되는 구간이 아니므로 e++
        else e++
    }
    print(dp[n])
}