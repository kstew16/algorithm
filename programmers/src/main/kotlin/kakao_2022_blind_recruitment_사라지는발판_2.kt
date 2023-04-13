import kotlin.math.abs

fun main(){
    class Solution{
        fun solution(board:Array<IntArray>,aLoc:IntArray,bLoc:IntArray):Int{
            val yLimit = board.size; val xLimit = board[0].size
            val myBoard = Array(yLimit){y->
                IntArray(xLimit){x->
                    board[y][x]
                }
            }
            val dy = intArrayOf(0,1,0,-1)
            val dx = intArrayOf(1,0,-1,0)

            fun doGame(al:IntArray,bl:IntArray,depth:Int):Int{
                // 게임이 끝나는 경우는 움직일 수 없는 경우 뿐, 이 depth 에서 게임이 끝났음을 리턴
                val (cy,cx) = if(depth%2==0) al else bl
                if(myBoard[cy][cx]==0) return -depth
                var sum = 0
                for(i in 0..3){
                    val ny = cy + dy[i]
                    val nx = cx + dx[i]
                    if(ny in 0 until yLimit && nx in 0 until xLimit) sum+=myBoard[ny][nx]
                }
                if(sum==0) return -depth

                var max = -100
                var min = 100
                // 게임을 진행할 수 있는 경우
                myBoard[cy][cx] = 0
                for(i in 0..3){
                    val ny = cy + dy[i]
                    val nx = cx + dx[i]
                    if(ny in 0 until yLimit && nx in 0 until xLimit && myBoard[ny][nx]==1){
                        val result = if(depth%2==0){
                            doGame(intArrayOf(ny,nx),bl,depth+1)
                        }else{
                            doGame(al, intArrayOf(ny,nx),depth+1)
                        }
                        myBoard[ny][nx] = 1
                        if(result<0){
                            // 상대방을 지게 만드는 경우 가장 짧게 이기는 경우를 리턴
                            min = (abs(result)).coerceAtMost(min)
                        }else{
                            // 상대방이 이기는 경우 가장 긴 경우를 리턴
                            max = result.coerceAtLeast(max)
                        }
                    }
                }
                myBoard[cy][cx] = 1
                // 이기는 경우가 있으면 해당 숫자 리턴
                return if(min!=100) min
                // 이기는 경우가 없었으면 제일 길게 진 수 리턴
                else -max
            }
            return abs(doGame(aLoc,bLoc,0))
        }
    }
    val board = arrayOf(
        intArrayOf(1,1,1),
        intArrayOf(1,1,1),
        intArrayOf(1,1,1)
    )
    val aLoc = intArrayOf(1,0)
    val bLoc = intArrayOf(1,2)
    println(Solution().solution(board, aLoc, bLoc))

}