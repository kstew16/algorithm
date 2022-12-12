import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.PriorityQueue
import java.util.StringTokenizer

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val e = readLine().toInt()
    data class Edge(val destination:Int,val distance:Int)
    val map = Array(n+1){ mutableListOf<Edge>() }
    var st:StringTokenizer
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    repeat(e){
        st = StringTokenizer(readLine())
        map[st.getInt()].add(Edge(st.getInt(),st.getInt()))
    }
    st = StringTokenizer(readLine())
    val from = st.getInt(); val to = st.getInt()
    val distance = IntArray(n+1){Int.MAX_VALUE}
    val pq = PriorityQueue<Edge>(compareBy { it.distance }).apply { this.add(Edge(from,0)) }
    while(pq.isNotEmpty()){
        val cur = pq.poll()
        else if(cur.distance>distance[cur.destination]) continue
        map[cur.destination].forEach {
            val newCost = cur.distance + it.distance
            if(distance[it.destination]>newCost){
                distance[it.destination] = newCost
                pq.add(Edge(it.destination,newCost))
            }
        }
    }
    print(distance[to])
}