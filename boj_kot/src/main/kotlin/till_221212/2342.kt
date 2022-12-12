import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val st = StringTokenizer(readLine())
    // 턴별 발 위치와 비용
    val inf = 1000000007
    val dp = Array(100000){
        Array(5){
            IntArray(5){inf}
        }
    }
    fun getCost(last:Int,current:Int):Int{
        if(last==current) return 1
        else return when(last){
            0 -> 2
            1,3 -> {
                when (current) {
                    0 -> 2
                    2, 4 -> 3
                    else -> 4
                }
            }
            2,4 ->{
                when (current) {
                    0 -> 2
                    1, 3 -> 3
                    else -> 4
                }
            }
            else -> 100
        }
    }
    var turn = 0
    var command = st.nextToken().toInt()
    if(command!=0){
        dp[0][command][0] = 2
        dp[0][0][command] = 2
        command = st.nextToken().toInt()
        while(command!=0){
            turn+=1
            // 이번 턴에 왼 발로 누름
            for(right in 0 until 5){
                // 오른 발이 있는 곳으로 왼 발은 올 수 없음
                if(command == right) continue
                for(left in 0 until 5){
                    val lastCost = dp[turn-1][left][right]
                    if(lastCost==inf) continue
                    dp[turn][command][right] = (lastCost + getCost(left,command)).coerceAtMost(dp[turn][command][right])
                }
            }
            // 이번 턴에 오른 발로 누름
            for(left in 0 until 5){
                if(command == left) continue
                for(right in 0 until 5){
                    val lastCost = dp[turn-1][left][right]
                    if(lastCost==inf) continue
                    dp[turn][left][command] = (lastCost + getCost(right,command)).coerceAtMost(dp[turn][left][command])
                }
            }
            command = st.nextToken().toInt()
        }
        var ans = inf
        for(left in 0 until 5){
            for(right in 0 until 5){
                ans = (dp[turn][left][right]).coerceAtMost(ans)
            }
        }
        print(ans)
    } else print(0)
}