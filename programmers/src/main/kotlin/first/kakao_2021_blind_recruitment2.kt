package first

import java.util.*
fun main(){


    data class Position(val y:Int,val x:Int)
    data class Node(val y:Int,val x:Int,val cost:Int)
    class Solution {
        fun solution(board: Array<IntArray>, r: Int, c: Int): Int {
            var answer = Int.MAX_VALUE
            val count = IntArray(7){0}
            val position = Array(7){Array(2){Position(0,0)}}
            var cards = 0
            // 각 카드들의 위치 저장
            for(y in 0 until 4){
                for(x in 0 until 4){
                    val cur = board[y][x]
                    if(cur!=0){
                        cards++
                        val curCount = count[cur]
                        position[cur][curCount] = Position(y,x)
                        count[cur] = curCount+1
                    }
                }
            }
            val species = count.count{it!=0}
            val stack = Stack<Int>()
            val hasCard = BooleanArray(7){
                count[it]!=0
            }
            val visited = BooleanArray(7){
                !hasCard[it]
            }



            fun combination1(visiting:Int,depth:Int){
                visited[visiting] = true
                stack.add(visiting)
                if(depth==species){
                    val secondFirst = BooleanArray(7){
                        !hasCard[it]
                    }
                    fun combination2(visiting:Int,depth:Int){
                        if(depth == species){
                            val dx = intArrayOf(1,0,-1,0)
                            val dy = intArrayOf(0,1,0,-1)
                            // 이제 문풀 bfs


                            val boardCopy = Array(4){y-> IntArray(4){x-> board[y][x]}}
                            var totalCost = 0

                            val visitSequence = stack.toIntArray()
                            //println(visitSequence.joinToString(" "))

                            var lastPosition = Position(r,c)
                            for(i in visitSequence.indices){
                                //queue.add(Node(lastPosition.y,lastPosition.x,0))
                                val curVisiting = visitSequence[i]

                                // 첫 번째 방문할 거 정하기
                                val target1 = if(secondFirst[curVisiting]) position[curVisiting][1]
                                else position[curVisiting][0]
                                val target2 = if(!secondFirst[curVisiting]) position[curVisiting][1]
                                else position[curVisiting][0]

                                fun findDistance(from:Position,to:Position):Node{
                                    val boardVisited = Array(4){BooleanArray(4){false}}
                                    val queue = LinkedList<Node>().apply { this.add(Node(from.y,from.x,0)) }
                                    boardVisited[from.y][from.x] = true
                                    while(queue.isNotEmpty()){
                                        val cur = queue.pollFirst()
                                        if(cur.y == to.y && cur.x == to.x) return cur
                                        // 컨트롤 이동
                                        for(i in 0..3){
                                            var nx = cur.x + dx[i]
                                            var ny = cur.y + dy[i]
                                            while(nx in 0..3 && ny in 0..3){
                                                if(boardCopy[ny][nx]==0){
                                                    ny += dy[i]
                                                    nx += dx[i]
                                                } else break
                                            }
                                            if(nx !in 0..3 || ny !in 0..3){
                                                ny -= dy[i]
                                                nx -= dx[i]
                                            }
                                            if(ny in 0..3 && nx in 0..3){
                                                if(!boardVisited[ny][nx]){
                                                    boardVisited[ny][nx] = true
                                                    queue.add(Node(ny,nx,cur.cost+1))
                                                }
                                            }
                                        }
                                        for(i in 0..3){
                                            val ny = cur.y + dy[i]
                                            val nx = cur.x + dx[i]
                                            if(ny in 0..3 && nx in 0..3){
                                                if(!boardVisited[ny][nx]){
                                                    boardVisited[ny][nx]
                                                    queue.add(Node(ny,nx,cur.cost+1))
                                                }
                                            }
                                        }
                                    }
                                    return Node(-1,-1,-1)
                                }
                                with(findDistance(lastPosition,target1)){
                                    lastPosition = target1
                                    totalCost += this.cost
                                    boardCopy[target1.y][target1.x] = 0
                                    if(totalCost>answer) return // 다른 조합 알아보시오
                                }
                                with(findDistance(lastPosition,target2)){
                                    lastPosition = target2
                                    totalCost += this.cost
                                    boardCopy[target2.y][target2.x] = 0
                                    if(totalCost>answer) return // 다른 조합 알아보시오
                                }
                                totalCost+=2 // 엔터비용
                            }
                            answer = totalCost.coerceAtMost(answer)

                        }else if(hasCard[visiting]){
                            combination2(visiting+1,depth+1)
                            secondFirst[visiting] = true
                            combination2(visiting+1,depth+1)
                            secondFirst[visiting] = false
                        }
                    }
                    var i = 1
                    while(!hasCard[i]) {i++}
                    combination2(1,0)
                }else{
                    for(i in 1..6){
                        if(!visited[i]){ combination1(i,depth+1)}
                    }
                }
                stack.pop()
                visited[visiting] = false
            }

            for(i in 1..6){
                if(!visited[i]) combination1(i,1)
            }

            return answer
        }
    }
    val board = arrayOf(
        intArrayOf(1,0,0,3),
        intArrayOf(2,0,0,0),
        intArrayOf(0,0,0,2),
        intArrayOf(3,0,1,0)
    )
    println(Solution().solution(board,1,0))
}