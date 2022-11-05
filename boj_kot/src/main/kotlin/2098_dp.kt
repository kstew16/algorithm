import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.math.pow

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    infix fun Int.on(i:Int) = this or (1 shl i)
    infix fun Int.chk(i:Int) = this and (1 shl i)>=1
    fun StringTokenizer.getInt() = this.nextToken().toInt()

    var st = StringTokenizer(readLine())
    val n = st.getInt()
    val graph = Array(n){
        st = StringTokenizer(readLine())
        IntArray(n){
            st.getInt()
        }
    }

    val INF = 1000000007
    val kinds = 2.0.pow(n).toInt()
    val dp = Array(n){
        Array(n){
            IntArray(kinds){INF}
        }
    }
    // 0 에서 출발하는 해밀턴 링크
    dp[0][0][0] = 0
    for(depth in 0 until n-1){
        for(e in 0 until n){
            for(b in 0 until kinds){
                for(v in 1 until n){
                    val cur = dp[depth][e][b]
                    val newCost = graph[e][v]
                    // v 를 방문한 적 없고, 길이 존재하고, 링크도 존재할 때 링크 뒤에 잇기
                    if(!(b chk v) && newCost != 0 && cur != INF) dp[depth+1][v][b on v] = (cur + newCost).coerceAtMost(dp[depth+1][v][b on v])
                }
            }
        }
    }

    var minCycleCost = Int.MAX_VALUE
    // 마지막으로 0을 다시 붙여줌
    for(e in 1 until n){
        val cur = dp[n-1][e][kinds-2]
        val newCost = graph[e][0]
        if(newCost != 0 && cur != INF) minCycleCost = (cur + newCost).coerceAtMost(minCycleCost)
    }

    print(minCycleCost)


}