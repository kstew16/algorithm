package first

fun main(){
    class Solution {
        fun solution(queue1: IntArray, queue2: IntArray): Int {
            var answer: Int = Int.MAX_VALUE
            val fullLength = queue1.size + queue2.size
            val connectedAccSum12 = LongArray(fullLength+1)
            val connectedAccSum21 = LongArray(fullLength+1)
            var i = 0
            var s = 0L
            while(i<queue1.size){
                s += queue1[i].toLong()
                connectedAccSum12[1+i++] = s
            }
            i = 0
            while(i<queue2.size){
                s += queue2[i].toLong()
                connectedAccSum12[1+queue1.size+i++] = s
            }
            // 이미 조건이 만족된 경우 리턴
            if(connectedAccSum12[queue1.size]*2==connectedAccSum12[fullLength]) return 0

            i = 0
            s = 0L
            while(i<queue2.size){
                s += queue2[i].toLong()
                connectedAccSum21[1+i++] = s
            }
            i = 0
            while(i<queue1.size){
                s += queue1[i].toLong()
                connectedAccSum21[1+queue2.size+i++] = s
            }
            // 합을 둘로 나눌 수 없는 경우 바로 리턴
            if((connectedAccSum12[fullLength])%2L==1L) return -1
            val target = connectedAccSum12[fullLength]/2L
            // 두 연결 누적합에 대해서 구간별 분석을 실시
            answer = getMinMove(connectedAccSum12,queue1.size,queue2.size,target).coerceAtMost(answer)
            answer = getMinMove(connectedAccSum21,queue2.size,queue1.size,target).coerceAtMost(answer)
            return if(answer==Int.MAX_VALUE) -1 else answer
        }
        fun getMinMove(accSum:LongArray,range1:Int,range2:Int,target:Long):Int{
            var minMove = Int.MAX_VALUE
            val fullRange = range1+range2
            for(j in 1..fullRange){
                // accSum[j] - accSum[i] == target 인 i를 [0:j-1] 구간에서 찾기
                val targetSumI = accSum[j] - target
                if(targetSumI < 0) continue
                var lo = 0
                var hi = j-1
                var mid:Int
                //[lo:mid] 구간에 답이 있도록
                while(lo+1<hi){
                    mid = (lo + hi) /2
                    if(accSum[mid]>targetSumI) hi = mid-1
                    else lo = mid
                }
                // 만족하는 i 가 없으면 그냥 스킵
                if(accSum[lo]!=targetSumI && accSum[hi]!=targetSumI) continue
                val i = if(accSum[lo] == targetSumI) lo else hi

                // 이 경우는 다른 배열로 처리
                if(i+1>range1) continue
                // [i+1:j] 구간이 조건을 만족
                val lengthNeed = j-i
                when{
                    // 이미 조건이 만족된 경우는 외부에서 처리함
                    //(i==1 && j == range1) -> return 0
                    // 앞 큐 내부에 목표구간이 처음부터 있는경우 앞 큐의 필요구간을 뒷 큐로 옮기고 뒷 큐를 전부 데려와야 함
                    (i==0 && j < range1) -> minMove = (lengthNeed+range2).coerceAtMost(minMove)
                    // 앞 큐 내부에 목표구간이 중간부터 끝까지 있는 경우 앞 큐에서 필요없는 부분만 뒷 큐로 옮겨버리면 됨
                    //
                    (i>0 && j == range1) -> minMove = (range1-lengthNeed).coerceAtMost(minMove)
                    // 앞 큐의 내부에 조건을 만족하는 구간이 있는 경우
                    // 앞 큐에서 조건을 만족하는 구간까지 다 보내버리고 (j)
                    // 뒷 큐 전체 + 앞 큐에서 조건을 만족하는데 필요 없는 구간을 다시 데리고 와야 함
                    (i>0 && j < range1) -> minMove = (j+i+range2).coerceAtMost(minMove)
                    // 두 큐에 걸쳐있는 경우
                    // 앞 큐에서 필요 없는 부분 보내고 (i)
                    // 뒷 큐에서 필요한 부분 가져오고 (j - range1)
                    (i+1<=range1 && j<=fullRange) -> minMove = ((j-range1) + i).coerceAtMost(minMove)
                    // (i+1>range1 인 경우는 다른 배열로 처리)
                }
            }
            return minMove
        }
    }
    val sol = Solution()
    val queue1 = intArrayOf(3,2,7,2)
    val queue2 = intArrayOf(4,6,5,1)
    print(sol.solution(queue1,queue2))
}