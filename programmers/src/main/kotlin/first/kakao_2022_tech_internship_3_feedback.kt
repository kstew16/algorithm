package first

fun main(){
    class Solution {
        fun solution(alp: Int, cop: Int, problems: Array<IntArray>): Int {
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
            val dp = Array(targetAlp+2){
                IntArray(targetCop+2){
                    1000000009
                }
            }
            val minAlp = if(alp>targetAlp) targetAlp else alp
            val minCop = if(cop>targetCop) targetCop else cop
            dp[minAlp][minCop] = 0
            for(curAlp in minAlp..targetAlp){
                for(curCop in minCop..targetCop){
                    val curCost = dp[curAlp][curCop]
                    dp[curAlp+1][curCop] = (1+curCost).coerceAtMost(dp[curAlp+1][curCop])
                    dp[curAlp][curCop+1] = (1+curCost).coerceAtMost(dp[curAlp][curCop+1])
                    problems.forEach {
                        val (alpReq, copReq, alpRwd, copRwd, costReq) = it
                        // 문제를 풀 수 있는가?
                        if(alpReq<=curAlp && copReq<=curCop){
                            // 문풀이 가능하면 그 문제로 dp 배열 업데이트
                            val newAlp = if(curAlp+alpRwd>targetAlp) targetAlp else curAlp+alpRwd
                            val newCop = if(curCop+copRwd>targetCop) targetCop else curCop+copRwd
                            val newCost = curCost + costReq
                            dp[newAlp][newCop] = newCost.coerceAtMost(dp[newAlp][newCop])
                        }
                    }
                }
            }
            return dp[targetAlp][targetCop]
        }
    }

    val sol = Solution()
    val alp = 0
    val cop = 150
    val problems = arrayOf(
        intArrayOf(0,150,2,0,1),
        intArrayOf(150,0,0,2,1)
    )

    print(sol.solution(alp,cop,problems))
}