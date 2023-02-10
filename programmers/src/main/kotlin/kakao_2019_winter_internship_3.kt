fun main(){
    class Solution {
        fun getMaxDisconnected(stones:IntArray,offset:Int):Int{
            var maxDisconnected = 0
            var curDisconnected = 0
            for(i in stones.indices){
                // 밟고 나서 0이 된다고 해도 지나갈 수는 있는 길
                if(stones[i]<offset){
                    curDisconnected++
                }else{
                    maxDisconnected = curDisconnected.coerceAtLeast(maxDisconnected)
                    curDisconnected = 0
                }
            }
            // 마지막까지 끊겨 있는 상태일 수 있음
            maxDisconnected = curDisconnected.coerceAtLeast(maxDisconnected)
            return maxDisconnected
        }
        fun solution(stones: IntArray, k: Int): Int {
            // hi : 모든 돌이 같은 높이에 있는 경우
            var hi = stones.maxOf{it}
            var lo = stones.minOf{it}
            var mid : Int
            // 찾는 것 : maxDisconnected 를 k 보다 작게 만드는 선에서의 offset 의 최댓값
            while(lo+1<hi){
                mid = ((0L+hi+lo)/2L).toInt()
                // mid 에서 건너는 것이 가능하다면 mid 포함해서 상위 범위 재탐색
                if(k>getMaxDisconnected(stones,mid)) lo = mid
                // 불가능하다면 mid 포함하지 않고 상위 범위 재탐색
                else hi = mid
            }
            // 여기 틀려서 한 번 틀림 부등호 반대로 했더라
            return if(getMaxDisconnected(stones,hi)<k) hi
            else lo
        }
    }
    val sample1 = IntArray(200000){
        it+1000000
    }
    val s2 = intArrayOf(1,1,1,1, 2, 4, 5, 3, 2, 1, 4, 2, 5, 1,1,1,1,1,1,1,1,1,1,1,1)
    println(Solution().solution(s2,1))
}