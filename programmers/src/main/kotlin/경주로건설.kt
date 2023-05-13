
import java.util.*
fun main(){
    data class CurWay(val cy:Int,val cx:Int,val curCost:Int,val wasHorizontal:Boolean)
    class Solution {
        fun solution(board: Array<IntArray>): Int {
            val n = board.size
            val dx = intArrayOf(1,0,-1,0)
            val dy = intArrayOf(0,1,0,-1)
            var answer = 0
            val queue = LinkedList<CurWay>().apply{
                this.add(CurWay(0,0,0,true))
                this.add(CurWay(0,0,0,false))
            }
            val visitedHorizontal = Array(n){IntArray(n){Int.MAX_VALUE}}
            val visitedVertical = Array(n){IntArray(n){Int.MAX_VALUE}}
            while(queue.isNotEmpty()){
                val (cy,cx,curCost,wasHorizontal) = queue.pollFirst()
                for(i in 0..3){
                    val isHorizontal = (i%2==0)
                    val ny = dy[i] + cy
                    val nx = dx[i] + cx
                    val newCost = curCost + if(isHorizontal xor wasHorizontal) 600 else 100
                    if(ny in 0 until n && nx in 0 until n && board[ny][nx]==0){
                        if(isHorizontal && visitedHorizontal[ny][nx]>newCost){
                            visitedHorizontal[ny][nx] = newCost
                            queue.add(CurWay(ny,nx,newCost,isHorizontal))
                        }
                        if(!isHorizontal && visitedVertical[ny][nx]>newCost){
                            visitedVertical[ny][nx] = newCost
                            queue.add(CurWay(ny,nx,newCost,isHorizontal))
                        }
                    }
                }
            }
            return kotlin.math.min(visitedHorizontal[n-1][n-1],visitedVertical[n-1][n-1])
        }
    }
}