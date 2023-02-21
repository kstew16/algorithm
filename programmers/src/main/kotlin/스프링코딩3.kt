fun main(){
    class Solution {
        fun solution(queries: Array<IntArray>): IntArray {
            fun getTries(arr: IntArray):Int{
                var tries = 0
                var s = 0
                var e = arr.size-1
                while(s<e){
                    tries += kotlin.math.abs(arr[e] - arr[s])
                    s++
                    e--
                }
                return tries
            }
            var answer = IntArray(queries.size)
            for(i in queries.indices){
                if(getTries(queries[i])%2==0) answer[i] = 0
                else answer[i] = 1
            }
            return answer
        }
    }
    val s1 = arrayOf(
        intArrayOf(0,2,0,1),
        intArrayOf(0,1,0,1)
    )
    Solution().solution(s1)
}