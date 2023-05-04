fun main(){
    class Solution {
        fun solution(stones: IntArray, k: Int): Int {
            // 건널 수 있는 사람 수를 찍어야 함, 최댓값은 발판의 최대 수라고 볼 수 있겠음
            fun canGoThrough(people:Int):Boolean{
                var disconnected = 0
                for(i in stones.indices){
                    if(stones[i]<people) disconnected ++
                    else disconnected = 0
                    if(disconnected>=k) return false
                }
                return disconnected < k
            }
            var hi = stones.maxOf{it}
            var lo = 0
            var mid:Int
            while(lo+1<hi){
                mid = (lo+hi)/2
                if(canGoThrough(mid)) lo = mid
                else hi = mid
            }
            return if(canGoThrough(hi)) hi else lo
        }
    }
}