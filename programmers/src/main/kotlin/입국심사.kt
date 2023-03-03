// 항상 가장 빨리 끝나는 테이블만 찾아서 가면
fun main(){
    data class Officer(val ability:Int, var taskWillFinish:Long)
    class Solution {
        fun solution(n: Int, times: IntArray): Long {
            fun enableToProcessIn(n:Int,minutes:Long,times:IntArray):Boolean{
                var processed = 0L
                times.forEach {
                    processed += (minutes/it.toLong())
                }
                return n.toLong()<=processed
            }
            var lo = 1L
            var hi = times.minOf { it }.toLong() * n
            var testingTimeLimit : Long
            while(lo+1L<hi){
                testingTimeLimit = (lo+hi)/2
                if(enableToProcessIn(n,testingTimeLimit,times)) hi = testingTimeLimit
                else lo = testingTimeLimit
            }
            return if(enableToProcessIn(n,lo,times)) lo else hi
        }
    }
}