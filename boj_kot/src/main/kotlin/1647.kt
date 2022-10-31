import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() : Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    val n = st.getInt(); val m = st.getInt()
    data class Edge(val source:Int, val destination:Int, val cost:Int,val originalCost:Int)
    val originalMap = Array<MutableList<Edge>>(n+1){ mutableListOf() }
    repeat(m){
        st = StringTokenizer(readLine())
        val from = st.getInt()
        val to = st.getInt()
        val cost = st.getInt()
        originalMap[from].add(Edge(from,to,cost,cost))
        originalMap[to].add(Edge(to,from,cost,cost))
    }

    // Prim MST
    val newMap = Array<MutableList<Edge>>(n+1){ mutableListOf() }
    val distance = IntArray(n+1){Int.MAX_VALUE}.apply{this[1] = 0}
    val pq = PriorityQueue<Edge>{
            a,b ->
        when{
            a.cost<b.cost -> -1
            else -> {
                when{
                    a.originalCost<b.originalCost -> -1
                    else -> 1
                }
            }
        }
    }.apply {
        originalMap[1].forEach {
            this.add(it)
        }
    }
    var expensiveWay = Edge(0,0,0,0)
    while(pq.isNotEmpty()){
        val cur = pq.poll()
        if(distance[cur.destination]!= Int.MAX_VALUE) continue
        distance[cur.destination] = cur.cost
        newMap[cur.destination].add(Edge(cur.destination,cur.source,cur.cost,cur.originalCost))
        newMap[cur.source].add(Edge(cur.source,cur.destination,cur.cost,cur.originalCost))
        if(expensiveWay.originalCost < cur.originalCost)
            expensiveWay = cur
        originalMap[cur.destination].forEach {
            pq.add(Edge(cur.destination,it.destination,cur.cost+it.cost,it.originalCost))
        }
    }
    val villageA = expensiveWay.source
    val villageB = expensiveWay.destination
    newMap[villageA].remove(Edge(expensiveWay.source,expensiveWay.destination,expensiveWay.cost,expensiveWay.originalCost))
    newMap[villageB].remove(Edge(expensiveWay.destination,expensiveWay.source,expensiveWay.cost,expensiveWay.originalCost))

    var sum = 0
    val queue = LinkedList<Int>().apply { this.add(villageA) }
    val visited = BooleanArray(n+1).apply { this[villageA] = true }
    while (queue.isNotEmpty()){
        val cur = queue.pollFirst()
        newMap[cur].forEach {
            if(!visited[it.destination]){
                visited[it.destination] = true
                sum += it.originalCost
                queue.add(it.destination)
            }
        }
    }
    queue.add(villageB)
    visited[villageB] = true
    while (queue.isNotEmpty()){
        val cur = queue.pollFirst()
        newMap[cur].forEach {
            if(!visited[it.destination]){
                visited[it.destination] = true
                sum += it.originalCost
                queue.add(it.destination)
            }
        }
    }
    print(sum)
}