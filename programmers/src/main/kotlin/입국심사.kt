fun main(){
    class Solution { // PinningBinarySearch 10분 1트
        fun solution(n: Int, times: IntArray): Long {
            // 상수처리가 람다 등으로 천장 정하는 것보다 일반적으로(최악의 케이스 제외하고)빠름
            var hi = 1000000000000000000L
            var lo = 0L
            var mid:Long
            fun check(checking:Long):Boolean{
                var finished = 0L
                times.forEach{finished += (checking/it)}
                return finished>=n.toLong()
            }
            while(lo+1<hi){
                mid = (lo+hi)/2
                if(check(mid)) hi = mid
                else lo = mid
            }
            return if(check(lo)) lo else hi
        }
    }
}