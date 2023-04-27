package first

import java.util.*

fun main(){
    class Solution {
        fun solution(alp: Int, cop: Int, problems: Array<IntArray>): Int {
            data class Node(val curAlp:Int,val curCop:Int,val curCost:Int)
            var targetAlp = 0
            var targetCop = 0
            problems.forEach{
                val (alpReq, copReq, _, _, _) = it
                targetAlp = targetAlp.coerceAtLeast(alpReq)
                targetCop = targetCop.coerceAtLeast(copReq)
            }
            if(targetAlp<=alp && targetCop<=cop) return 0
            // dp[alp][cop] 를 방문하는데 든 가장 적은 비용은
            // 초기 alp 값과 cop 값에서 공부만 해서 푸는 걸 최대시간으로 설정
            val dp = Array(targetAlp+1){ alpReq->
                IntArray(targetCop+1){ copReq->
                    var alpNeed = alpReq - alp
                    var copNeed = copReq - cop
                    if(alpNeed<0) alpNeed = 0
                    if(copNeed<0) copNeed = 0
                    alpNeed+copNeed
                }
            }
            var answer = dp[targetAlp][targetCop]
            val pq = PriorityQueue<Node>{a,b-> if(a.curCost<b.curCost) -1 else 1}
            for(curAlp in 0..targetAlp){
                for(curCop in 0..targetCop){
                    pq.add(Node(curAlp,curCop,dp[curAlp][curCop]))
                }
            }
            while(pq.isNotEmpty()) {
                val (curAlp, curCop, curCost) = pq.poll()
                if (curAlp >= targetAlp && curCop >= targetCop) {
                    answer = curCost
                    break
                }
                // 그새 더 효율적인 탐색이 들어왔을 경우 이번 노드 스킵
                if (dp[curAlp][curCop] < curCost) continue
                // 이번 노드로 가능한 효율적 탐색들을 큐잉
                problems.forEach {
                    val (alpReq, copReq, alpRwd, copRwd, costReq) = it
                    // 문제를 풀 수 있는가?
                    if (alpReq <= curAlp && copReq <= curCop) {
                        val newAlp = if(curAlp+alpRwd>targetAlp) targetAlp else curAlp+alpRwd
                        val newCop = if(curCop+copRwd>targetCop) targetCop else curCop+copRwd
                        val newCost = curCost + costReq
                        if (dp[newAlp][newCop] > newCost) {
                            dp[newAlp][newCop] = newCost
                            pq.add(Node(newAlp, newCop, newCost))
                        }
                    }
                }
            }

            return answer
        }
    }

    val sol = Solution()
    val alp = 0
    val cop = 0
    val problems = arrayOf(
        intArrayOf(0,150,0,0,100),
        intArrayOf(0,0,150,2,1)
    )

    print(sol.solution(alp,cop,problems))
}