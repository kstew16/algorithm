import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.PriorityQueue
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    data class Node(val number:Int,val distance:Int)
    data class Edge(val destination:Int,val weight:Int)
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    val n = st.getInt(); val m = st.getInt(); val x = st.getInt()

    // dijkstra 를 통해 방향성 그래프를 뒤집어 한 곳으로 모이는 방법을 찾아낼 수도 있음
    val map = Array(n+1){ mutableListOf<Edge>() }
    val reversedMap = Array(n+1){ mutableListOf<Edge>() }

    repeat(m){
        st = StringTokenizer(readLine())
        val from = st.getInt(); val to = st.getInt(); val weight = st.getInt()
        map[from].add(Edge(to,weight))
        reversedMap[to].add(Edge(from,weight))
    }
    // x 에서 출발지로 돌아가는 거리 (x 를 출발지로 하는 정방향 dijkstra)
    val distanceReturn = IntArray(n+1){ Int.MAX_VALUE}.apply { this[x]=0 }
    // 출발지들에서 x 로 오는 거리 (x 를 출발지로 하는 역방향 dijkstra)
    val distanceGathering = IntArray(n+1){ Int.MAX_VALUE}.apply { this[x]=0 }

    // x 에서 출발지로 돌아가는 거리 (x 를 출발지로 하는 정방향 dijkstra)
    fun dijkstra(from:Int){
        val pq = PriorityQueue<Node> {a,b-> a.distance -b.distance}
        pq.add(Node(from,0))
        while(pq.isNotEmpty()){
            val (target,distToTarget) = pq.poll()
            // 현재 큐잉된 경로가 최적 경로가 아니면 버림(이미 우선순위에 의해 탐색된 노드)
            if(distToTarget > distanceReturn[target]) continue
            map[target].forEach { (next,distNext)->
                val newDistToNext =distNext+distanceReturn[target]
                if(distanceReturn[next]>newDistToNext){
                    distanceReturn[next] = newDistToNext
                    pq.add(Node(next,newDistToNext))
                }
            }
        }
    }

    // 출발지들에서 x 로 오는 거리 (x 를 출발지로 하는 역방향 dijkstra)
    fun reversedDijkstra(to:Int){
        val pq = PriorityQueue<Node> {a,b-> a.distance -b.distance}
        pq.add(Node(to,0))
        while(pq.isNotEmpty()){
            val (target,distToTarget) = pq.poll()
            // 현재 큐잉된 경로가 최적 경로가 아니면 버림(이미 우선순위에 의해 탐색된 노드)
            if(distToTarget > distanceGathering[target]) continue
            reversedMap[target].forEach { (next,distNext)->
                val newDistToNext =distNext+distanceGathering[target]
                if(distanceGathering[next]>newDistToNext){
                    distanceGathering[next] = newDistToNext
                    pq.add(Node(next,newDistToNext))
                }
            }
        }
    }


    dijkstra(x)
    reversedDijkstra(x)
    var ans = 0
    for(i in 1..n) ans = (distanceReturn[i] + distanceGathering[i]).coerceAtLeast(ans)
    print(ans)

}