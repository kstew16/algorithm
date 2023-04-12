fun main(){
    // 20분, 1트
    class Solution {
        fun solution(e: Int, starts: IntArray): IntArray {
            var answer = IntArray(starts.size){0}
            println(starts.size)
            // dp[i] -> i 까지의 최빈값
            val dp = IntArray(e+1){0}
            val appeared = IntArray(e+1){1}
            var i=2
            while(i<=e){
                var cur = i
                while(cur<=e){
                    appeared[cur] ++
                    cur+=i
                }
                i++
            }
            var maxAppeared = 0
            var appearedNo = 0
            for(i in e downTo 1){
                if(appeared[i]>=maxAppeared){
                    maxAppeared = appeared[i]
                    appearedNo = i
                }
                dp[i] = appearedNo
            }
            starts.forEachIndexed{index,s->
                answer[index] = dp[s]
            }
            return answer
        }
    }
}