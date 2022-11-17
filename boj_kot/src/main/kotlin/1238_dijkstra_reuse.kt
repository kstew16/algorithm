import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.PriorityQueue
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    val n = st.getInt(); val m = st.getInt(); val x = st.getInt()
    val edge = Array(n+1) { mutableListOf<Pair<Int,Int>>() }
    repeat(m){
        st = StringTokenizer(readLine())
        edge[st.getInt()].add(Pair(st.getInt(),st.getInt()))
    }
    val distance = Array(n+1){IntArray(n+1){ Int.MAX_VALUE} }
    fun dijkstra(from:Int,to:Int):Int{
        if(distance[from][to]!= Int.MAX_VALUE) return distance[from][to]
        val groupDistance = IntArray(n+1){ Int.MAX_VALUE}.apply { this[from] = 0 }
        // pq -> 그룹에서 가장 가까운 순으로 정렬된 노드
        val pq = PriorityQueue<Pair<Int, Int>> {a,b-> a.second -b.second}
        pq.add(Pair(from,0))
        while(pq.isNotEmpty()){
            val (target,distToTarget) = pq.poll()
            // 현재 큐잉된 경로가 최적 경로가 아니면 버림(이미 우선순위에 의해 탐색된 노드)
            if(distToTarget > groupDistance[target]) continue
            edge[target].forEach { (next,distNext)->
                val newDistToNext =distNext+groupDistance[target]
                if(groupDistance[next]>newDistToNext){
                    groupDistance[next] = newDistToNext
                    pq.add(Pair(next,newDistToNext))
                }
            }
        }
        for(i in 1..n) distance[from][i] = groupDistance[i]
        return groupDistance[to]
    }
    var ans = 0
    for(i in 1..n){
        ans = (dijkstra(i,x)+dijkstra(x,i)).coerceAtLeast(ans)
    }
    print(ans)

}