package first

import java.util.LinkedList

fun main(){
    class Solution {
        fun solution(n: Int, weak: IntArray, dist: IntArray): Int {
            var minAttempts = Int.MAX_VALUE
            val visited = BooleanArray(weak.size){false}

            // attempts : 투입 요원 수
            // staffing : 현재 투입하는 요원의 번호
            fun dfs(attempts:Int,staffing:Int){
                if(visited.none { !it }) minAttempts = attempts.coerceAtMost(minAttempts)
                else if(attempts+1>=minAttempts || staffing>=dist.size) return
                else{
                    // 이번 요원을 투입하는 경우 투입점을 정해서 발사
                    for(i in visited.indices){
                        if(attempts+1>=minAttempts || staffing>=dist.size) return
                        if(!visited[i]){
                            // 이번 요원이 방문한 곳을 기록해뒀다 나중에 원상복구시키기 용
                            val visitedQueue = LinkedList<Int>()
                            val startPoint = weak[i]

                            val distance = dist[staffing]

                            // clockwise == increasing
                            val clockwiseEndPoint = (startPoint + distance)%n
                            when{
                                //
                                (startPoint<clockwiseEndPoint)->{
                                    for(j in weak.indices){
                                        if(!visited[j]&& (weak[j] in startPoint..clockwiseEndPoint)){
                                            visited[j] = true
                                            visitedQueue.add(j)
                                        }
                                    }
                                    if(attempts+1<minAttempts&& staffing<dist.size) dfs(attempts+1,staffing+1)

                                    // 방문기록 복구
                                    while(visitedQueue.isNotEmpty()){
                                        visited[visitedQueue.poll()] = false
                                    }
                                }
                                // dist 가 n 이상인 경우는 이미 배제했으므로 endPoint > startPoint
                                else ->{
                                    for(j in weak.indices){
                                        if(!visited[j]&& (weak[j] in startPoint until n || weak[j] in 0..clockwiseEndPoint)){
                                            visited[j] = true
                                            visitedQueue.add(j)
                                        }
                                    }
                                    if(attempts+1<minAttempts&& staffing<dist.size) dfs(attempts+1,staffing+1)

                                    // 방문기록 복구
                                    while(visitedQueue.isNotEmpty()){
                                        visited[visitedQueue.poll()] = false
                                    }
                                }
                            }

                            if(attempts+1>=minAttempts || staffing>=dist.size) return
                            // counterClockwise == decreasing
                            var counterClockwiseEndPoint = startPoint - distance

                            when{
                                //
                                (counterClockwiseEndPoint>=0)->{
                                    for(j in weak.indices){
                                        if(!visited[j]&& (weak[j] in counterClockwiseEndPoint..startPoint)){
                                            visited[j] = true
                                            visitedQueue.add(j)
                                        }
                                    }
                                    if(attempts+1<minAttempts&& staffing<dist.size) dfs(attempts+1,staffing+1)

                                    // 방문기록 복구
                                    while(visitedQueue.isNotEmpty()){
                                        visited[visitedQueue.poll()] = false
                                    }
                                }
                                // 0을 넘어 뒤로 가 버린 경우
                                else ->{
                                    counterClockwiseEndPoint += n
                                    for(j in weak.indices){
                                        if(!visited[j]&& (weak[j] in counterClockwiseEndPoint until n || weak[j] in 0..startPoint)){
                                            visited[j] = true
                                            visitedQueue.add(j)
                                        }
                                    }
                                    if(attempts+1<minAttempts&& staffing<dist.size) dfs(attempts+1,staffing+1)

                                    // 방문기록 복구
                                    while(visitedQueue.isNotEmpty()){
                                        visited[visitedQueue.poll()] = false
                                    }
                                }
                            }
                        }
                    }
                    // 이번 요원을 투입하지 않는 경우
                    if(staffing+1 < dist.size)  dfs(attempts,staffing+1)
                }
            }
            // 한 명이 다 할 수 있는 경우 탐색이 필요가 없음. 미리 배제하여 여러 바퀴 돌아서 예외 만드는 걸 막자
            dist.forEach { if(it>=n) return 1}
            dfs(0,0)
            return if(minAttempts== Int.MAX_VALUE) -1 else minAttempts
        }
    }
    val n = 200
    val weakSize = 15
    val weak = IntArray(weakSize){
        it*(n/weakSize)
    }
    val dist = IntArray(8){10}
    println(Solution().solution(n,weak,dist))
}