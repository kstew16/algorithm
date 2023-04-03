import java.util.*

fun main(){
    data class Position(val y:Int, val x:Int)
    data class State(val lPosition: Position, val rPosition:Position,val turn:Int, val distance:Int)

    class Solution {
        fun solution(board:Array<IntArray>):Int{
            val n = board.size
            // 왼발 기준으로 오른발이 우, 하 ,좌 , 상 에 위치한 상태를 표시
            val visited = Array(n){Array(n){BooleanArray(4){false} } }.apply{this[0][0][0] = true}
            val queue = LinkedList<State>().apply{this.add(State(Position(0,0),Position(0,1),0,0))}

            fun isValidPoint(y:Int,x:Int):Boolean{
                if(y!in 0 until n || x !in 0 until n) return false
                else{
                    if(board[y][x] == 1) return false
                }
                return true
            }

            // 해당 방향으로 돌 수 없으면 null, 돌 수 있으면 축발은 고정되어있으므로 다른 발의 새로운 위치를 반환
            fun getTurn(lPosition:Position,rPosition:Position):Int{
                val dy = rPosition.y - lPosition.y
                val dx = rPosition.x - lPosition.x
                return when{
                    (dy==0 && dx==1) -> 0
                    (dy==1 && dx==0) -> 1
                    (dy==0 && dx==-1) -> 2
                    (dy==-1 &&dx==0) -> 3
                    else-> 5 // error
                }
            }

            fun turnOrNull(axis:Position,other:Position,type:Int):Position?{
                val dy = other.y - axis.y
                val dx = other.x - axis.x
                //0 -> clockWise, 1->counter
                // 축발이 아닌 발이 새로 위치하게 될 자리
                val ny = axis.y + if(type==0) + dx else - dx
                val nx = axis.x + if(type==0) - dy else dy
                if(!isValidPoint(ny,nx)) return null

                // 축발이 아닌 발이 돌아가면서 지나가는 자리
                val cy:Int
                val cx:Int
                if(axis.x == nx){
                    cy = ny
                    cx = other.x
                }else{ // axis.y == ny
                    cy = other.y
                    cx = nx
                }
                if(!isValidPoint(cy,cx)) return null
                return Position(ny,nx)
            }

            // 하,좌,상,우
            val gdy = intArrayOf(1,0,-1,0)
            val gdx = intArrayOf(0,-1,0,1)


            while(queue.isNotEmpty()){
                val (lp,rp,turn,dist) = queue.pollFirst()
                if((lp.x == n-1 && lp.y == n-1)||(rp.x == n-1 && rp.y == n-1)) return dist
                // 좌축발 기준
                for(turnType in 0..1){
                    turnOrNull(axis=lp,other=rp,turnType)?.let {nPosition->
                        val newTurn = getTurn(lp,nPosition)
                        if(!visited[lp.y][lp.x][newTurn]){
                            visited[lp.y][lp.x][newTurn] = true
                            queue.add(State(lp,nPosition,newTurn,dist+1))
                        }
                    }
                    turnOrNull(axis = rp, other = lp,turnType)?.let {nPosition->
                        val newTurn = getTurn(nPosition,rp)
                        if(!visited[nPosition.y][nPosition.x][newTurn]){
                            visited[nPosition.y][nPosition.x][newTurn] = true
                            queue.add(State(nPosition,rp,newTurn,dist+1))
                        }
                    }
                }
                for(i in 0..3){
                    val nly = lp.y + gdy[i]
                    val nlx = lp.x + gdx[i]
                    val nry = rp.y + gdy[i]
                    val nrx = rp.x + gdx[i]
                    if(isValidPoint(nly,nlx)&&isValidPoint(nry,nrx)){
                        if(!visited[nly][nlx][turn]){
                            visited[nly][nlx][turn] = true
                            queue.add(State(Position(nly,nlx),Position(nry,nrx),turn,dist+1))
                        }
                    }
                }
            }

            return -1
        }
    }
    val board = arrayOf(
        intArrayOf(0,0,0,1,1),
        intArrayOf(0,0,0,1,0),
        intArrayOf(0,1,0,1,1),
        intArrayOf(1,1,0,0,1),
        intArrayOf(0,0,0,0,0)
    )
    println(Solution().solution(board))
}

