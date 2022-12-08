import java.util.StringTokenizer
import java.io.BufferedReader
import java.io.InputStreamReader
fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))) {
    var st = StringTokenizer(readLine())
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    data class Edge(val s:Int,val e:Int,val cost:Int)
    val ansSb = StringBuilder("")
    repeat(st.getInt()){
        st = StringTokenizer(readLine())
        val v = st.getInt(); val r = st.getInt(); val w = st.getInt()
        val visited = BooleanArray(v+1){false}
        val edges = ArrayList<Edge>(2*r+w)
        repeat(r){
            st = StringTokenizer(readLine())
            val a = st.getInt(); val b = st.getInt(); val c = st.getInt()
            edges.add(Edge(a,b,c))
            edges.add(Edge(b,a,c))
        }
        repeat(w){
            st = StringTokenizer(readLine())
            edges.add(Edge(st.getInt(),st.getInt(),-st.getInt()))
        }
        fun isThereMinusCycle(start:Int):Boolean{
            visited[start] = true
            val distance = IntArray(v+1){Int.MAX_VALUE}.apply { this[start] = 0 }
            for(i in 1..v){
                for(j in edges.indices){
                    val cur = edges[j].s
                    if(distance[cur]==Int.MAX_VALUE) continue
                    val next = edges[j].e
                    val newCost = edges[j].cost + distance[cur]
                    if(newCost<distance[next]) {
                        distance[next] = newCost
                        visited[next] = true
                    }
                }
            }
            for(j in edges.indices){
                val cur = edges[j].s
                if(distance[cur]==Int.MAX_VALUE) continue
                val next = edges[j].e
                val newCost = edges[j].cost + distance[cur]
                if(newCost<distance[next]){
                    return true
                }
            }
            return false
        }
        var minusCycle = false
        for(s in 1..v){
            if(!visited[s]) minusCycle = isThereMinusCycle(s)
            if(minusCycle) break
        }
        ansSb.append(if(minusCycle) "YES\n" else "NO\n")
    }
    print(ansSb)
}