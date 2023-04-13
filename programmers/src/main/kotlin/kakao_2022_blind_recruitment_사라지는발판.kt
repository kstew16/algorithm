import java.util.*

fun main(){

    data class Result(var winWeight:Double,var turnLeft:Int)
    class Solution {
        infix fun Int.on(i:Int) = this or (1 shl i)
        fun toStateCode(board:Array<IntArray>,yLimit:Int,xLimit:Int):Int{
            var code = 0
            for(y in 0 until yLimit){
                for(x in 0 until xLimit){
                    val position = xLimit*y + x
                    if(board[y][x]==1) code  = code on position
                }
            }
            return code
        }
        fun solution(board: Array<IntArray>, aLoc: IntArray, bLoc: IntArray): Int {
            val yLimit = board.size
            val xLimit = board[0].size
            val dy = intArrayOf(0,1,0,-1)
            val dx = intArrayOf(1,0,-1,0)
            val visited = Array(2){
                Array(yLimit){Array(xLimit){
                    Array(yLimit){Array(xLimit){
                        hashMapOf<Int,Result>()
                    } }
                } }
            }
            // BFS 나 DFS 로 가야할 것 같은데 depth 제한 필요 없고... 그냥 진짜 승패확률, 얼마나 걸린다만
            fun dfs(board:Array<IntArray>,curALoc:IntArray,curBLoc:IntArray,aTurn:Boolean):Result{
                val visiting = visited[if(aTurn) 0 else 1][curALoc[0]][curALoc[1]][curBLoc[0]][curBLoc[1]].getOrDefault(
                    toStateCode(board,yLimit, xLimit),
                    Result(0.0,-1)
                )
                if(visiting.turnLeft!=-1){
                    visited[if(aTurn) 0 else 1][curALoc[0]][curALoc[1]][curBLoc[0]][curBLoc[1]][toStateCode(board,yLimit, xLimit)] = Result(-1.0,0)
                    return Result(-1.0,0)
                }

                val curY = if(aTurn) curALoc[0] else curBLoc[0]
                val curX = if(aTurn) curALoc[1] else curBLoc[1]
                if(board[curY][curX]==0) {
                    visited[if(aTurn) 0 else 1][curALoc[0]][curALoc[1]][curBLoc[0]][curBLoc[1]][toStateCode(board,yLimit, xLimit)] = Result(-1.0,0)
                    return Result(-1.0,0)
                }
                val choice = LinkedList<IntArray>()
                for(i in 0..3){
                    val ny = curY + dy[i]
                    val nx = curX + dx[i]
                    if(ny in 0 until yLimit && nx in 0 until xLimit){
                        if(board[ny][nx]==1) choice.add(intArrayOf(ny,nx))
                    }
                }
                if(choice.isEmpty()){
                    visited[if(aTurn) 0 else 1][curALoc[0]][curALoc[1]][curBLoc[0]][curBLoc[1]][toStateCode(board,yLimit, xLimit)] = Result(-1.0,0)
                    return Result(-1.0,0)
                }
                else{
                    val myResult = Result(0.0,0)
                    while(choice.isNotEmpty()){
                        val (ny,nx) = choice.pollFirst()
                        val myBoard = Array(yLimit){y->
                            IntArray(xLimit){x->
                                board[y][x]
                            }
                        }
                        myBoard[curY][curX] = 0
                        // 상대 승률을 음수로 받아서 내 승률로 반영
                        with(dfs(
                            myBoard,
                            if(aTurn) intArrayOf(ny,nx) else curALoc,
                            if(aTurn) curBLoc else intArrayOf(ny,nx),
                            !aTurn
                        )){
                            if(this.winWeight==-1.0) { // 무조건 이기는 수 발견
                                if (myResult.winWeight == 1.0) {
                                    myResult.turnLeft = (this.turnLeft + 1).coerceAtMost(myResult.turnLeft)
                                } else {
                                    myResult.winWeight = 1.0
                                    myResult.turnLeft = this.turnLeft + 1
                                }
                            }else if(this.winWeight==1.0){//일로가면 짐
                                myResult.winWeight = -1.0
                                myResult.turnLeft = this.turnLeft + 1
                            }
                        }
                        visited[if(aTurn) 0 else 1][curALoc[0]][curALoc[1]][curBLoc[0]][curBLoc[1]][toStateCode(board,yLimit, xLimit)] = myResult

                    }
                    return myResult
                }
            }

            val aResult = dfs(board,aLoc,bLoc,true)
            val aWins = aResult.winWeight==1.0


            fun game(board:Array<IntArray>,curALoc:IntArray,curBLoc:IntArray,aTurn:Boolean,aWins:Boolean):Int{
                val curY = if(aTurn) curALoc[0] else curBLoc[0]
                val curX = if(aTurn) curALoc[1] else curBLoc[1]
                if(board[curY][curX]==0)  return 0
                val choice = LinkedList<IntArray>()
                for(i in 0..3){
                    val ny = curY + dy[i]
                    val nx = curX + dx[i]
                    if(ny in 0 until yLimit && nx in 0 until xLimit){
                        if(board[ny][nx]==1) choice.add(intArrayOf(ny,nx))
                    }
                }
                if(choice.isEmpty()) return 0
                else{
                    var chosen = intArrayOf(0,0)
                    var maxLeftTurn = 0
                    var minLeftTurn = Int.MAX_VALUE
                    while(choice.isNotEmpty()){
                        val (ny,nx) = choice.pollFirst()
                        val myBoard = Array(yLimit){y->
                            IntArray(xLimit){x->
                                board[y][x]
                            }
                        }
                        myBoard[curY][curX] = 0
                        // 상대 승률을 음수로 받아서 내 승률로 반영
                        val newCode = toStateCode(myBoard,yLimit,xLimit)
                        val nResult = if(aTurn) visited[1][ny][nx][curBLoc[0]][curBLoc[1]][newCode]
                        else visited[0][curALoc[0]][curALoc[1]][ny][nx][newCode]
                        if(nResult==null) continue
                        else{
                            when{
                                (aWins && aTurn) ->{ // turnLeft 최소화 필요
                                    if(nResult.winWeight==-1.0 && nResult.turnLeft<minLeftTurn){
                                        chosen[0] = ny
                                        chosen[1] = nx
                                        minLeftTurn = nResult.turnLeft
                                    }
                                }
                                (!aWins && aTurn) ->{// turnLeft 최대화 필요
                                    if(nResult.turnLeft>maxLeftTurn){
                                        chosen[0] = ny
                                        chosen[1] = nx
                                        maxLeftTurn = nResult.turnLeft
                                    }
                                }
                                (aWins && !aTurn) ->{// turnLeft 최대화 필요
                                    if(nResult.turnLeft>maxLeftTurn){
                                        chosen[0] = ny
                                        chosen[1] = nx
                                        maxLeftTurn = nResult.turnLeft
                                    }
                                }
                                else->{// b 턴이고 b가 이김, turnLeft 최소화 필요
                                    if(nResult.winWeight==-1.0 && nResult.turnLeft<minLeftTurn){
                                        chosen[0] = ny
                                        chosen[1] = nx
                                        minLeftTurn = nResult.turnLeft
                                    }
                                }
                            }
                        }

                    }
                    board[curY][curX] = 0
                    println("player${if (aTurn) 0 else 1} moves to ${chosen.joinToString(" ")}")
                    return game(board, if(aTurn) chosen else curALoc, if(!aTurn) chosen else curBLoc, !aTurn, aWins)+1
                }
            }
            return game(board,aLoc,bLoc,true,aWins)
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