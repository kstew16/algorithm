import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.PriorityQueue
import java.util.StringTokenizer

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    val n = st.getInt(); val k = st.getInt()
    data class Edge(val destination:Int, val cost:Int)
    val map = Array(n+1){ mutableListOf<Edge>() }
    repeat(k){
        st = StringTokenizer(readLine())
        val s = st.getInt(); val e = st.getInt(); val cost = st.getInt()
        map[e].add(Edge(s,cost))
        map[s].add(Edge(e,cost))
    }
    fun dijkstra(from:Int,to:Int):Int{
        var distance = IntArray(n+1){Int.MAX_VALUE}.apply { this[from] = 0 }
        val pq = PriorityQueue<Edge>{a,b-> a.cost - b.cost}.apply{this.add(Edge(from,0))}
        while(pq.isNotEmpty()){
            val cur = pq.poll()
            if(distance[cur.destination]<cur.cost) continue
            map[cur.destination].forEach {
                val newCost = it.cost+cur.cost
                if(newCost<distance[it.destination]) {
                    distance[it.destination] = newCost
                    pq.add(Edge(it.destination,newCost))
                }
            }
        }
        return if (distance[to] == Int.MAX_VALUE) -1 else distance[to]
    }
    st = StringTokenizer(readLine())
    val v1 = st.getInt(); val v2=st.getInt()
    val dBetweenV12 = dijkstra(v1,v2)
    var minDistance = Int.MAX_VALUE
    if(dBetweenV12==-1) print(-1)
    else{
        val d1tov1 = dijkstra(1,v1)
        val d1tov2 = dijkstra(1,v2)
        val dv1toN = dijkstra(v1,n)
        val dv2toN = dijkstra(v2,n)
        if(d1tov1!=-1 && dv2toN!=-1) minDistance = minDistance.coerceAtMost(d1tov1+dv2toN+dBetweenV12)
        if(d1tov2!=-1 && dv1toN!=-1) minDistance = minDistance.coerceAtMost(d1tov2+dv1toN+dBetweenV12)
        print(if(minDistance== Int.MAX_VALUE) -1 else minDistance)
    }

}