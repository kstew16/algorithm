import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.PriorityQueue
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    val n = st.getInt(); val m = st.getInt(); val x = st.getInt()
    // 이딴 짓 하면 안됩니다 알겠습니까???
    val d = Array(n+1){IntArray(n+1){Int.MAX_VALUE}}.apply { for(i in 1..n)this[i][i] = 0 }
    repeat(m){
        st = StringTokenizer(readLine())
        d[st.getInt()][st.getInt()] = st.getInt()
    }

    val visited = Array(n+1){BooleanArray(n+1)}
    fun dijkstra(from:Int,to:Int):Int{
        if(visited[from][to]) return d[from][to]
        visited[from][to] = true
        val groupDistance = IntArray(n+1){ Int.MAX_VALUE}.apply { this[from] = 0 }
        // pq -> 그룹에서 가장 가까운 순으로 정렬된 노드
        val pq = PriorityQueue<Pair<Int, Int>> {a,b-> a.second -b.second}
        pq.add(Pair(from,0))
        while(pq.isNotEmpty()){
            val (target,distToTarget) = pq.poll()
            // 현재 큐잉된 경로가 최적 경로가 아니면 버림(이미 우선순위에 의해 탐색된 노드)
            if(distToTarget > groupDistance[target]) continue
            for(i in 1..n){
                val distTargetTOI = d[target][i]
                if(distTargetTOI== Int.MAX_VALUE)continue
                val distUsingTarget = distToTarget+d[target][i]
                if(groupDistance[i]>distUsingTarget) {
                    groupDistance[i] = distUsingTarget
                    d[from][i] = distUsingTarget
                    pq.add(Pair(i,distUsingTarget))
                }
            }
        }
        return groupDistance[to]
    }
    var ans = 0
    for(i in 1..n){
        ans = (dijkstra(i,x)+dijkstra(x,i)).coerceAtLeast(ans)
    }
    print(ans)

}