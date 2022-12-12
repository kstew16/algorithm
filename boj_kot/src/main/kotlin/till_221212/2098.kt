import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
// 낮에 한참 고민해도 안 돼서 봤는데 비트마스킹 써있길래.. 아.. 하면서 또 한참 고민하다가.. 후.. 10시간쯤 했나 그래도 포기 안 하고 해서 풀긴 함
// 유명한 문제라 풀고 넘어가고 싶었어 ㅋㅋ....
// 모든 곳을 방문해야 한다 싶은 조건이 있고 노드가 32개 안 되면 (64개도 되긴 하겠지) 비트마스킹 생각해보기
// bottom up 을 채우는 방식이 반복문이 항상 좋은 게 아니란 것 기억하자 dfs (visited off 없는 1인칭 dfs) 로 쓸데없는 반복 줄이니까 훨씬빠르네
// ㄴ 이거 빠른 줄 알았는데 특정 최악에 대해서 같은 곳을 반복해서 돌게 되는 끔찍한 시간저하가 발생할 수 있음
// + 순회는 시작지점 아무데나 잡아도 상관 없는거임 그걸 신경써야 하는게 아니라 이거 생각난게 포인트였던듯
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