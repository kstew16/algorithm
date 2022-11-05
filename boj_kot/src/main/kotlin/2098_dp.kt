import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.math.pow

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    infix fun Int.on(i:Int) = this or (1 shl i)
    infix fun Int.off(i:Int) = this and (1 shl i).inv()
    infix fun Int.chk(i:Int) = this and (1 shl i)>=1
    fun Int.countOff(n:Int):Int{
        var count = 0
        for(i in 0 until n ){
            if(!this.chk(i)) count+=1
        }
        return count
    }

    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    val INF = 1000000007
    val n = st.getInt()
    val graph = Array(n){
        IntArray(n){
            st.getInt()
        }
    }
    // 남은 방문해야 할 곳이 [notVisited] 인 상황이고 다음 방문해야 하는 곳이 [next] 일 때
    // t턴에 [visiting]을 방문하면서 얻을 수 있는 순회의 최소값
    // [t][visiting][next][notVisited]
    val kinds = 2.0.pow(n).toInt()
    val dp = Array(n){
        Array(n){
            Array(n){
                IntArray(kinds)
            }
        }
    }

    for(t in n-2 downTo 0){
        for(visiting in 0 until n){
            for(next in 0 until n){
                for(notVisited in 0 until kinds){
                    for(whatever in 0 until n){
                        var tmp =notVisited on visiting
                        dp[t][visiting][next][tmp] = dp[t][next][whatever][tmp on next] + graph[visiting][next]
                    }
                }
            }
        }
    }


}