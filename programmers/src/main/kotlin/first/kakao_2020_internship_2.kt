package first

import java.util.LinkedList

fun main(){
    data class Node(val vy:Int, val vx:Int, val d:Int, val cost:Int)
    class Solution {
        fun solution(board: Array<IntArray>): Int {
            //var answer = Int.MAX_VALUE
            val h = board.size
            val w = board[0].size
            val minCost = Array(4){
                Array(h){
                    IntArray(w){Int.MAX_VALUE}
                }
            }.apply { (0..3).forEach { this[it][0][0] = 100 } }

            // 0과 2는 가로, 1과 3은 세로로 도로가 지어진 형태
            val dx = intArrayOf(1,0,-1,0)
            val dy = intArrayOf(0,1,0,-1)

            val queue = LinkedList<Node>().apply {
                if(w>1 && h>0 && board[0][1] ==0 )add(Node(0,1,0,100))
                if(h>1 && w>0 && board[1][0] ==0 )add(Node(1,0,1,100))
            }
            var answer = Int.MAX_VALUE
            while(queue.isNotEmpty()){
                val (vy,vx,d,cost) = queue.pollFirst()

                // 그새 이 곳을 이 방향의 도로로 오는 게 최소 비용이 아니면 스킵
                if(cost > minCost[d][vy][vx]) continue
                minCost[d][vy][vx] = cost
                for(nd in 0..3){
                    // 새로운 도로의 방향이 다르면 코너, 아니면 직선
                    val newCost = if(nd%2==d%2) cost + 100 else cost + 600
                    val ny = vy + dy[nd]
                    val nx = vx + dx[nd]
                    if(ny in 0 until h && nx in 0 until w){
                        if(board[ny][nx]==0 && minCost[nd][ny][nx]>newCost && newCost<answer) {
                            minCost[nd][ny][nx] = newCost
                            if(ny==h-1 && nx == w-1) answer = newCost.coerceAtMost(answer)
                            queue.add(Node(ny,nx,nd,newCost))
                        }
                    }
                }


            }

            (0..3).forEach { answer = (minCost[it][h-1][w-1]).coerceAtMost(answer) }
            return answer
        }
    }
    //val sampleBoard = Array(25){IntArray(25){0} }
    //sampleBoard[1][0] = 1
    //for(i in 1..24) sampleBoard[23][i] = 1
    //val sampleBoard = Array(3){IntArray(3){0} }
    //println(first.Solution().solution(sampleBoard))
    val sampleBoard = arrayOf(
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 1),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 1, 0, 0),
        intArrayOf(0, 0, 0, 0, 1, 0, 0, 0),
        intArrayOf(0, 0, 0, 1, 0, 0, 0, 1),
        intArrayOf(0, 0, 1, 0, 0, 0, 1, 0),
        intArrayOf(0, 1, 0, 0, 0, 1, 0, 0),
        intArrayOf(1, 0, 0, 0, 0, 0, 0, 0),
    )
    println(Solution().solution(sampleBoard))
}