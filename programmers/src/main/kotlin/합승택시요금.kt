import java.util.PriorityQueue
data class Edge(val no:Int,val cost:Int)
fun main(){
    class Solution {
        fun solution(n: Int, s: Int, a: Int, b: Int, fares: Array<IntArray>): Int {
            var answer: Int = Int.MAX_VALUE
            // 플로이드 와샬은 V^3
            // n 다익스트라는 V(V+E)log(V) 희소그래프에서나 이기겠네
            val distance = Array(n+1){IntArray(n+1){Int.MAX_VALUE}}
            val map = Array(n+1){ArrayList<Edge>()}
            fares.forEach{(f,t,c)->
                map[f].add(Edge(t,c))
                map[t].add(Edge(f,c))
            }
            fun dijkstra(point:Int){
                val curDistance = distance[point].apply{this[point] = 0}
                val pq = PriorityQueue<Edge>(){a,b -> if(a.cost<b.cost) -1 else 1}.apply{this.add(Edge(point,0))}
                for(i in 1..n){if(distance[i][point]!=Int.MAX_VALUE){
                    // 이 반복문은 미리 구해진 최단거리를 가지고 오는 반복문인데, 평균적으로는 빠르나 최악은 느림
                    // 해당 노드가 돌아돌아 가는 노드인데 pq에 추가했다가는 쓸데없는 걸 빨리 탐색한 꼴이 되기 때문
                    distance[point][i] = distance[i][point]
                    pq.add(Edge(i,distance[i][point]))
                }}
                while(pq.isNotEmpty()){
                    val (cur,cost) = pq.poll()
                    if(curDistance[cur]<cost) continue
                    else map[cur].forEach{(next,costToNext)->
                        val newCost  = cost + costToNext
                        if(curDistance[next]>newCost){
                            curDistance[next] = newCost
                            pq.add(Edge(next,newCost))
                        }
                    }
                }
            }
            for(i in 1..n) dijkstra(i)
            for(with in 1..n){
                val withCost = distance[s][with] + distance[with][a] + distance[with][b]
                answer = withCost.coerceAtMost(answer)
            }
            return answer
        }
    }

}