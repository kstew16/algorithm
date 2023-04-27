package first

fun main(){
    class Solution {
        fun solution(sequence: IntArray): Long {
            var answer: Long = 0
            //PP -> 1로 시작하는 펄스, NP -> -1로 시작하는 펄스
            var curPositivePulsedSum = 0L
            var curNegativePulsedSum = 0L
            var curMinPPS = 0L
            var curMinNPS = 0L
            for(i in sequence.indices){
                val curSequence = sequence[i].toLong()
                curPositivePulsedSum += if(i%2==0) curSequence else -curSequence
                curMinPPS = curPositivePulsedSum.coerceAtMost(curMinPPS)
                curNegativePulsedSum -= if(i%2==0) curSequence else -curSequence
                curMinNPS = curNegativePulsedSum.coerceAtMost(curMinNPS)
                answer = answer.coerceAtLeast(kotlin.math.max(curNegativePulsedSum-curMinNPS,curPositivePulsedSum-curMinPPS))
            }
            return answer
        }
    }
    println(Solution().solution(intArrayOf(
        2, 3, -6, 1, 3, -1, 2, 4
    )))
}