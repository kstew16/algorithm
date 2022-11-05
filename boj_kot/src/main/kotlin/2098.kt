import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))) {
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    val n = st.getInt()
    data class Edge(val e:Int, val cost:Int)
    val INF = 1000000007
    val graph = Array(n){
        st = StringTokenizer(readLine())
        val edges = Array(n){
            var cost = st.getInt()
            if(cost == 0) cost = INF
            Edge(it,cost)
        }
        edges.sortedBy { it.cost }
    }
    var minCost = Int.MAX_VALUE
    val visited = BooleanArray(n)
    fun dfs(source:Int,visiting:Int,depth:Int,cost:Int){
        if(depth==n){
            val returnCost = graph[visiting].find { it.e == source }!!.cost
            if(returnCost == INF) return
            else minCost = (cost + returnCost).coerceAtMost(minCost)
        }
        if(cost >= minCost) return

        val g = graph[visiting]
        for(i in 0 until n){
            val next = g[i].e
            if(!visited[next]){
                visited[next] = true
                dfs(source,next,depth+1,cost + graph[visiting][i].cost)
                visited[next] = false
            }
        }
    }
    for(i in 0 until n){
        visited[i] = true
        dfs(i,i,1,0)
        visited[i] = false
    }
    print(minCost)
}