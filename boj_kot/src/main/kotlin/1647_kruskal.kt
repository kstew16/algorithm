import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
// Kruskal
fun main() : Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    val n = st.getInt(); val m = st.getInt()
    data class Edge(val from:Int, val to:Int, val cost:Int)
    val parent = IntArray(n+1){it}
    val pq = PriorityQueue<Edge>{
            a,b ->
        when{
            a.cost<b.cost -> -1
            else -> 1
        }
    }
    repeat(m){
        st = StringTokenizer(readLine())
        val from = st.getInt()
        val to = st.getInt()
        val cost = st.getInt()
        pq.add(Edge(from,to,cost))
    }

    fun find(x:Int):Int{
        if(parent[x]==x) return x
        parent[x] = find(parent[x])
        return  parent[x]
    }

    fun union(x:Int,y:Int){
        val px = find(x)
        val py = find(y)
        if(px==py) return
        parent[py] = px
    }


    var connected = 0
    var mostExpensive = 0
    var expandCost = 0
    while(pq.isNotEmpty()){
        val cur = pq.poll()
        val px = find(cur.from)
        val py = find(cur.to)
        if(px==py) continue
        union(px,py)
        expandCost += cur.cost
        mostExpensive = (cur.cost).coerceAtLeast(mostExpensive)
        if(++connected == n-1) break
    }
    print(expandCost-mostExpensive)
}