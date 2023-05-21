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
                if(!isGate[b] && !isSummit[a])map[a].add(Way(b,intensity))
                if(!isGate[a] && !isSummit[b])map[b].add(Way(a,intensity))
            }
            val needIntensity = IntArray(n+1){10000007}
            val pq = PriorityQueue<Way>{a,b-> if(a.cost<b.cost)-1 else 1}
            gates.forEach { // 출발지 전부다 경로 시작점으로 해서 출발
                needIntensity[it] = 0
                pq.add(Way(it,0))
            }
            while(pq.isNotEmpty()){
                val (visiting,curCost) = pq.poll()
                if(curCost>curMinIntensity) break
                if(needIntensity[visiting]<curCost) continue
                if(isSummit[visiting] ){
                    // summit 은 추가 확장 불가, 여기까지 온 경로정보 기록
                    if(curCost<curMinIntensity||(curMinIntensity==curCost && visiting<answerSummit)){
                        curMinIntensity = curCost
                        answerSummit = visiting
                    }
                    continue
                }
                map[visiting].forEach {(next,intensity)->
                    val newIntensity = intensity.coerceAtLeast(curCost)
                    if(newIntensity<=curMinIntensity && newIntensity<needIntensity[next]){
                        needIntensity[next] = newIntensity
                        pq.add(Way(next,newIntensity))
                    }
                }
            }
            return intArrayOf(answerSummit,curMinIntensity)
        }
    }
}