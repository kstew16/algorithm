import java.util.PriorityQueue

fun main(){
    data class Way(val destination:Int,val cost:Int)
    class Solution {
        fun solution(n: Int, paths: Array<IntArray>, gates: IntArray, summits: IntArray): IntArray {
            val isSummit = BooleanArray(n+1).apply{summits.forEach { this[it] = true }}
            val isGate = BooleanArray(n+1).apply{gates.forEach { this[it] = true }}
            var curMinIntensity = 10000007
            var answerSummit = 0
            val map = Array(n+1){ArrayList<Way>()}
            paths.forEach {(a,b,intensity)->
                map[a].add(Way(b,intensity))
                map[b].add(Way(a,intensity))
            }
            summits.sorted().forEach {summit ->
                val needIntensity = IntArray(n+1){10000007}
                needIntensity[summit] = 0
                val pq = PriorityQueue<Way>{a,b-> if(a.cost<b.cost)-1 else 1}.apply { this.add(Way(summit,0)) }
                while(pq.isNotEmpty()){
                    val (visiting,curCost) = pq.poll()
                    if(isGate[visiting] && curCost<curMinIntensity){
                        curMinIntensity = curCost
                        answerSummit = summit
                    }
                    // 같은 가격으로 오면 재탐색 아님? -> 생각을 해라 큐잉이 안되잖아
                    if(needIntensity[visiting]<curCost) continue
                    map[visiting].forEach {(next,intensity)->
                        val newIntensity = intensity.coerceAtLeast(curCost)
                        if(newIntensity<curMinIntensity && newIntensity<needIntensity[next] && !isSummit[next]){
                            needIntensity[next] = newIntensity
                            pq.add(Way(next,newIntensity))
                        }
                    }
                }
            }
            return intArrayOf(answerSummit,curMinIntensity)
        }
    }

}