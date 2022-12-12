import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.PriorityQueue
import java.util.StringTokenizer

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    var st = StringTokenizer(readLine())
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    val n = st.getInt(); val k = st.getInt()
    val start = readLine().toInt()
    val distance = IntArray(n+1){Int.MAX_VALUE}.apply{this[start] = 0}
    data class Edge(val destination:Int,val cost:Int)
    val pq = PriorityQueue<Edge>{a,b -> a.cost - b.cost}.apply{
        this.add(Edge(start,0))
    }
    val map = Array(n+1){mutableListOf<Edge>()}
    repeat(k){
        st = StringTokenizer(readLine())
        val s = st.getInt();val e = st.getInt(); val cost = st.getInt()
        map[s].add(Edge(e,cost))
    }
    while(pq.isNotEmpty()){
        val cur = pq.poll()
        if(distance[cur.destination]<cur.cost) continue
        map[cur.destination].forEach{
            val newCost = cur.cost + it.cost
            if(distance[it.destination]>newCost){
                distance[it.destination] = newCost
                pq.add(Edge(it.destination,newCost))
            }
        }
    }
    val sb = StringBuilder("")
    for(i in 1..n){
        with(distance[i]){
            sb.append(if(this==Int.MAX_VALUE) "INF\n" else "$this\n")
        }
    }
    print(sb)
}