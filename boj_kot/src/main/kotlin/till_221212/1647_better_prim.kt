import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
// dijkstra 와 prim 을 구분하자 prim 은 visited로 충분하다
fun main() : Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    val n = st.getInt(); val m = st.getInt()
    data class Edge(val destination:Int, val cost:Int)
    val originalMap = Array<MutableList<Edge>>(n+1){ mutableListOf() }
    repeat(m){
        st = StringTokenizer(readLine())
        val from = st.getInt()
        val to = st.getInt()
        val cost = st.getInt()
        originalMap[from].add(Edge(to,cost))
        originalMap[to].add(Edge(from,cost))
    }

    // Prim MST
    val visited = BooleanArray(n+1)
    //val mst = Array<MutableList<Edge>>(n+1){ mutableListOf() }
    val pq = PriorityQueue<Edge>{
            a,b ->
        when{
            a.cost<b.cost -> -1
            else -> 1
        }
    }.apply {
        this.add(Edge(1,0))
    }
    var mostExpensive = 0
    var expandingCost = 0
    while(pq.isNotEmpty()){
        val cur = pq.poll()
        if(visited[cur.destination])continue
        visited[cur.destination] = true
        expandingCost += cur.cost
        mostExpensive = cur.cost.coerceAtLeast(mostExpensive)
        originalMap[cur.destination].forEach {
            pq.add(Edge(it.destination,it.cost))
        }
    }
    print(expandingCost - mostExpensive)

}