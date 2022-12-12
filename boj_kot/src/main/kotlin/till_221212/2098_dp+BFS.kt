import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.math.pow
// 노드가 유일하지 않아서 같은 경로를 다시 밟게 되는 게 문제같은데..
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
    data class Node(val depth:Int, val e:Int,val visited:Int,val cost:Int)
    val queue = LinkedList<Node>().apply{this.add(Node(0,0,1,0))}
    while(queue.isNotEmpty()){
        val (depth,e,visited,cost) = queue.pollFirst()
        // 그새 다른 최적의 길이 발견되었을 경우 그 노드는 스킵
        if(dp[depth][e][visited]<cost) continue
        for(next in 1 until n){
            if(!(visited chk next) && graph[e][next]!=0){
                val newCost = graph[e][next] + cost
                val newVisited = visited on next
                if(dp[depth+1][next][newVisited]>newCost){
                    dp[depth+1][next][newVisited] = newCost
                    queue.add(Node(depth+1,next,newVisited,newCost))
                }
            }
        }
    }
    var minCycleCost = Int.MAX_VALUE
    // 마지막으로 0을 다시 붙여줌
    for(e in 1 until n){
        val cur = dp[n-1][e][kinds-1]
        val newCost = graph[e][0]
        if(newCost != 0 && cur != INF) minCycleCost = (cur + newCost).coerceAtMost(minCycleCost)
    }

    print(minCycleCost)


}