import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.PriorityQueue
import java.util.StringTokenizer
// MST 알고리즘 - Prim MST : O(ElogV), 희소 간선 그래프의 경우 O(ElogE) 인 크루스칼(간선정렬) 알고리즘 사용 바람
// 한 번에 맞았는데 프림을 보면서 구현해서 별넣어둔거야 풀어봐
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    var st:StringTokenizer
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    st = StringTokenizer(readLine())
    data class Edge(val destination:Int, val cost:Int)
    data class Node(val num:Int, val cost:Int)
    val v = st.getInt(); val e = st.getInt()
    val graph = Array(v+1){ mutableListOf<Edge>() }
    repeat(e){
        st = StringTokenizer(readLine())
        val a = st.getInt(); val b = st.getInt(); val cost = st.getInt()
        graph[a].add(Edge(destination = b, cost = cost))
        graph[b].add(Edge(destination = a, cost = cost))

    }
    // 출발 간선은 1로 설정
    val visited = BooleanArray(v+1)
    var treeCost = 0
    val pq = PriorityQueue<Node>{a,b -> a.cost-b.cost}.apply { this.add(Node(1,0)) }
    while(pq.isNotEmpty()){
        val cur = pq.poll()
        if(visited[cur.num]) continue
        else {
            visited[cur.num] = true
            treeCost += cur.cost
        }
        graph[cur.num].forEach {
            if(!visited[it.destination]){
                pq.add(Node(it.destination,it.cost))
            }
        }
    }
    print(treeCost)
}