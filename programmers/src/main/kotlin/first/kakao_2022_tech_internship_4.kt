package first

import java.lang.Integer.max
import java.util.PriorityQueue

fun main(){
    class Solution {
        fun solution(n: Int, paths: Array<IntArray>, gates: IntArray, summits: IntArray): IntArray {
            var easiestTop = 0
            var minIntensity = 10000001
            val isBottom = BooleanArray(n+1).apply { gates.forEach { this[it] = true }}
            val isTop =  BooleanArray(n+1).apply { summits.forEach { this[it] = true }}

            //data class Route(val s:Int, val e:Int, val w:Int)
            data class Route(val nodeNum:Int, val intensity:Int) // 출발지에서 출발한 경로
            // map 에서 간선을 담당하는 data class
            data class Edge(val dest:Int, val cost:Int)

            // 출발지로 가거나 봉우리에서 오는 길이 없는 그래프임
            val map = Array(n+1){ArrayList<Edge>()}
            // 경로는 낮은 가중치 순으로 정렬
            val routesPq = PriorityQueue(compareBy<Route> {it.intensity})

            for(k in paths.indices){
                var (a,b,w) = paths[k]
                when{
                    // 시작지에서 어디론가 가는 경우
                    (isBottom[a]) ->{
                        // 시작지로 가는 것만 아니면 출발한 루트로 편입시킴
                        if(!isBottom[b]){
                            //map[a].add(Edge(b,w))
                            routesPq.add(Route(b,w))
                        }
                    }
                    // 봉우리에서 어디로 가는 건 없음. b->a 로 가는 경로를 b에 따라서 추가함
                    (isTop[a]) ->{
                        if(!isTop[b]){
                            // b가 출발지일 경우 출발 루트로 편입
                            if(isBottom[b]) routesPq.add(Route(a,w))
                            else{
                                // 아니면 그냥 그래프에 추가
                                map[b].add(Edge(a,w))
                            }
                        }
                    }
                    // 중간지점에서 가는 경우
                    else->{
                        // b 가 출발지인 경우 b->a 가 루트로 포함됨
                        if(isBottom[b]) routesPq.add(Route(a,w))
                        // b 가 봉우리인 경우 a->b 가 그래프에 추가
                        else if(isTop[b]) map[a].add(Edge(b,w))
                        // 둘 다 중간지점이면 그래프에 둘 다 추가
                        else{
                            map[a].add(Edge(b,w))
                            map[b].add(Edge(a,w))
                        }
                    }
                }
            }
            val intensityTo = IntArray(n+1){10000001}
            while(routesPq.isNotEmpty()){
                val cur = routesPq.poll()
                // 더 어려운 난이도의 코스를 탐색하게 되는 경우 반복문 종료
                if(cur.intensity > minIntensity) break
                // 이미 낮은 난이도의 코스로 와 본 경우 스킵
                //if(intensityTo[cur.nodeNum]<cur.intensity) continue
                // 방문중인 노드가 봉우리 노드인 경우 기록, 더 이어갈 수 없음
                if(isTop[cur.nodeNum]){
                    if((cur.intensity<minIntensity)||(cur.intensity==minIntensity && cur.nodeNum<easiestTop)){
                        easiestTop = cur.nodeNum
                        minIntensity = cur.intensity
                    }
                }
                // 출발지 노드로 가는 간선은 map 에 없고, 봉우리에 도착한 경로는 새로운 경로를 낳지 않음
                // 그 봉우리 2번으로 빠르게 가서 1번으로 가는 노드 차단되는지 확인할 것
                else{
                    map[cur.nodeNum].forEach { (dest,cost) ->
                        // 현재 경로의 난이도와 새로운 간선의 난이도중 높은 것을 새 난이도로 설정
                        val newIntensity = max(cur.intensity,cost)
                        // 새 경로가 원래 알던 코스보다 난이도 낮은 경로를 탐색하는 경우에만 해당 루트를 pq에 편입
                        if(intensityTo[dest]>newIntensity){
                            intensityTo[dest] = newIntensity
                            routesPq.add(Route(dest,newIntensity))
                        }
                    }
                }
            }
            return intArrayOf(easiestTop,minIntensity)
        }
    }
    val n = 6
    val paths = arrayOf(
        intArrayOf(1,2,3),
        intArrayOf(2,3,5),
        intArrayOf(2,4,2),
        intArrayOf(2,5,4),
        intArrayOf(3,4,4),
        intArrayOf(4,5,3),
        intArrayOf(4,6,1),
        intArrayOf(5,6,1)
    )
    val gates = intArrayOf(1,3)
    val summits = intArrayOf(5)
    val s = Solution()
    print(s.solution(n,paths,gates,summits).joinToString(" "))
}
