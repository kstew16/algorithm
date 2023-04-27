package first

fun main(){
    class Solution {
        fun solution(m: Int, n: Int, sy: Int, sx: Int, ey: Int, ex: Int, k: Int): String {

            // d-> l -> r -> u
            val left = IntArray(4){0}
            var distance = 0
            // 오른쪽으로 가야 하는 횟수
            (ex-sx).run {
                if(this>0) {
                    left[2] += this
                    distance+=this
                }
                else {
                    left[1] -= this
                    distance-=this
                }
            }
            // 아래로 가야 하는 횟수
            (ey-sy).run {
                if(this>0) {
                    left[0]+= this
                    distance += this
                } else {
                    left[3] -= this
                    distance -= this
                }
            }
            // 최단거리보다 이동 가능 횟수가 적은 경우
            if(distance>k) return "impossible"
            // 더미 무브 (rl ud 등등의.. 로 채워야하는 무브의 횟수)
            var dummyMove = k - distance
            if(dummyMove%2 == 1) return "impossible"


            // 최악의 문자열을 답으로 두고 시작
            var answer = "z"

            // 최대한 du 더미무브를 사용하도록 더미무브 갯수를 조정하며 시작작
           for(horizontalDummy in 0..(dummyMove/2)){
                val movement = CharArray(k)
                val verticalDummy = (dummyMove/2) - horizontalDummy
                val curLeft = IntArray(4){
                    left[it]+ when(it){
                        0->{verticalDummy}
                        1->{horizontalDummy}
                        2->{horizontalDummy}
                        else->{verticalDummy}
                    }
                }
                var moveCount = 0
                var curX = sx
                var curY = sy

                while(moveCount<k){
                    for(i in 0..3){
                        // 사전순으로 움직일 수 있는 곳을 탐색
                        if(curLeft[i]<=0) continue
                        val nx = curX + if(i==1) -1 else if(i==2) 1 else 0
                        val ny = curY + if(i==0) 1 else if(i==3) -1 else 0
                        if(nx in 1..n && ny in 1..m){

                            movement[moveCount] = when(i){
                                0 -> 'd'
                                1 -> 'l'
                                2 -> 'r'
                                else -> 'u'
                            }
                            moveCount++
                            curLeft[i] --
                            curX = nx
                            curY = ny
                            break
                        }
                    }
                }
               // ud 도 vertical 이라 vertical 이 많다고 좋은 건 아님
                answer = answer.coerceAtMost(movement.joinToString(""))
            }
            return if(answer=="z") "impossible" else answer
        }
    }
    println(Solution().solution(40,40,40,1,1,40,2500))
}