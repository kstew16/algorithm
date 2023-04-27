package first

import java.util.PriorityQueue

fun main(){
    data class Employee(val no:Int,val score:Int)
    class Solution {
        fun solution(scores: Array<IntArray>): Int {
            // cutLine[i] -> a 점수가 i점인 경우 b 점수는 cutLine[i] 점 이상이어야 인센을 받을 수 있음
            val cutLine = IntArray(100001){0}
            var maxAScore = 0
            scores.forEach {(a,b)->
                // 점수별 최댓값을 먼저 기록. a 점인데 b점 미만이라면 탈락이라는 기준을 제시할 수 있음
                if(a-1>=0) cutLine[a-1] = cutLine[a-1].coerceAtLeast(b)
                // 순회 최적화를 위하여 a의 최댓값 기록
                maxAScore = a.coerceAtLeast(maxAScore)
            }
            // cutLine[100] = 1000 즉, a가 100점인데 b 1000점을 받은 사람이 있다면 a 가 99점이라도 1000점을 넘어야 함.
            // 즉 커트라인은 단조증가해야하므로 커트라인을 뒤에서부터 순회하며 기준을 재정리해야 함
            var curMaxCutLine = 0
            for(aScore in maxAScore downTo 0){
                curMaxCutLine = curMaxCutLine.coerceAtLeast(cutLine[aScore])
                cutLine[aScore] = curMaxCutLine
            }
            // 자 이제 점수별 커트라인이 생성되었음. score 을 pq에 넣었다 빼면서 랭크부여
            val pq = PriorityQueue<Employee>{a,b->
                if(a.score>b.score) -1 else 1
            }
            for(i in scores.indices){
                if(scores[i][1]>=cutLine[scores[i][0]]){
                    pq.add(Employee(i,scores[i].sum()))
                }
            }
            var rank = 0
            var sameRank = 0
            var scoreForRank = Int.MAX_VALUE
            var ans = -1

            val testRankInfo = IntArray(scores.size)
            while(pq.isNotEmpty()){
                val cur = pq.poll()
                if(cur.score == scoreForRank){
                    sameRank++
                }
                if(cur.score<scoreForRank){
                    rank += sameRank
                    sameRank = 0
                    rank++
                    scoreForRank = cur.score
                }
                if(cur.no==0) ans = rank
                testRankInfo[cur.no] = rank
            }
            println(testRankInfo.joinToString(" "))

            return ans
        }
    }
    val scores = arrayOf(
        intArrayOf(100000,0),
        intArrayOf(100000,100000),
        intArrayOf(2,1),
        intArrayOf(2,1),
        intArrayOf(2,1),
        intArrayOf(1,1),
        intArrayOf(1,1),
        intArrayOf(0,1),
    )
    println(Solution().solution(scores))
}