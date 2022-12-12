import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val (n,m) = readLine().split(" ").map{it.toInt()}
    // dp[i] i 의 재실행 비용을 통해 확보할 수 있는 최대 메모리
    val dp = IntArray(10001){0}
    data class Process(val memory:Int,val removeCost:Int):Comparable<Process>{
        override fun compareTo(other: Process): Int {
            return when{
                this.removeCost==0 && other.removeCost==0 ->{
                    when{
                        this.memory>other.memory -> -1
                        else -> 1
                    }
                }
                this.removeCost == 0 && other.removeCost!=0 -> -1
                this.removeCost!= 0 && other.removeCost==0 -> 1
                else ->{
                    val a = this.memory/this.removeCost
                    val b = other.memory/other.removeCost
                    when{
                        a>b -> -1
                        else -> 1
                    }
                }
            }
        }
    }

    var st = StringTokenizer(readLine())
    val memoryArr = IntArray(n){st.nextToken().toInt()}
    st = StringTokenizer(readLine())
    val costArr = IntArray(n){st.nextToken().toInt()}
    val processes = Array(n){
        Process(memoryArr[it],costArr[it])
    }.sorted()
    val visited = BooleanArray(n){false}
    for(i in 0 until n){
        if(processes[i].removeCost==0) {
            dp[0] += processes[i].memory
            visited[i] = true
        }
    }
    var minCost = Int.MAX_VALUE
    fun killProcess(i:Int,memoryGot:Int,cost:Int){
        dp[cost] = memoryGot
        if(memoryGot>=m){
            minCost = cost.coerceAtMost(minCost)
            return
        }
        for(next in 0 until n){
            if(!visited[next]){
                visited[next] = true
                val nextCost = cost + processes[next].removeCost
                if(nextCost<minCost && dp[nextCost]==0)
                    killProcess(next,memoryGot+processes[next].memory,nextCost)
                visited[next] = false
            }
        }
    }
    killProcess(-1,dp[0],0)
    print(minCost)
}