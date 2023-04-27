package first

fun main(){
    data class Point(val y:Int, val x:Int)
    // 기준점으로부터 떨어진 수치
    class Offset(var dy:Int, var dx:Int){
        fun turn(){
            val tmpY = this.dy
            val tmpX = this.dx
            this.dx = -tmpY
            this.dy = tmpX
        }
    }
    class Block(var size:Int,val pieces:ArrayList<Offset>){
        fun turned():Block{
            val newBlock = Block(this.size,ArrayList<Offset>())
            this.pieces.forEach {
                newBlock.pieces.add(Offset(it.dy,it.dx))
            }
            newBlock.pieces.forEach {
                it.turn()
            }
            newBlock.calibrate()
            return newBlock
        }
        fun calibrate(){
            var minX = Int.MAX_VALUE
            var minY = Int.MAX_VALUE

            this.pieces.forEach {
                minX = it.dx.coerceAtMost(minX)
                minY = it.dy.coerceAtMost((minY))
            }
            for(i in pieces.indices){
                pieces[i].dx -= minX
                pieces[i].dy -= minY
            }
            pieces.sortBy { it.dy*100+it.dx }
        }
    }
    class Solution {
        fun solution(gameBoard: Array<IntArray>, table: Array<IntArray>): Int {
            val dx = intArrayOf(1,0,-1,0)
            val dy = intArrayOf(0,1,0,-1)
            val blocks = ArrayList<Array<Block>>()
            val limit = table.size
            val tableVisited = Array(limit){BooleanArray(limit){false} }
            fun dfs(sy:Int,sx:Int,vy:Int,vx:Int,block:Block,visited:Array<BooleanArray>,arr:Array<IntArray>,type:Int){
                block.pieces.add(Offset(vy-sy,vx-sx))
                visited[vy][vx] = true
                for(i in 0..3){
                    val ny = vy + dy[i]
                    val nx = vx + dx[i]
                    if(ny in 0 until limit && nx in 0 until limit && arr[ny][nx]==type && !visited[ny][nx]){
                        dfs(sy,sx,ny,nx,block,visited,arr,type)
                    }
                }
            }
            for(y in table.indices){
                for(x in table[0].indices){
                    if(!tableVisited[y][x] && table[y][x]==1){
                        val newBlock = Block(0,ArrayList())
                        dfs(y,x,y,x,newBlock,tableVisited,table,1)
                        newBlock.size = newBlock.pieces.size
                        newBlock.calibrate()
                        val b2 = newBlock.turned()
                        val b3 = b2.turned()
                        val b4 = b3.turned()
                        val blockForms = arrayOf(newBlock,b2,b3,b4)
                        blocks.add(blockForms)
                    }
                }
            }
            val boardVisited = Array(limit){BooleanArray(limit){false} }
            val empties = ArrayList<Block>()
            for(y in gameBoard.indices){
                for(x in gameBoard[0].indices){
                    if(!boardVisited[y][x] && gameBoard[y][x] == 0){
                        val newEmptyBlock = Block(0,ArrayList())
                        dfs(y,x,y,x,newEmptyBlock,boardVisited,gameBoard,0)
                        newEmptyBlock.size = newEmptyBlock.pieces.size
                        newEmptyBlock.calibrate()
                        empties.add(newEmptyBlock)
                    }
                }
            }
            var placed = 0
            val blockUsed = BooleanArray(blocks.size){false}
            emptyLoop@ for(i in empties.indices){
                val curEmpty = empties[i]
                blockLoop@ for(j in blocks.indices){
                    if(blockUsed[j]) continue@blockLoop
                    formLoom@ for(form in 0..3){
                        val turnedBlock = blocks[j][form]
                        if(turnedBlock.size!=curEmpty.size) continue@blockLoop
                        for(k in turnedBlock.pieces.indices){
                            // 모든 조각이 같은지 확인
                            if(turnedBlock.pieces[k].dx != curEmpty.pieces[k].dx) continue@formLoom
                            if(turnedBlock.pieces[k].dy != curEmpty.pieces[k].dy) continue@formLoom
                        }
                        blockUsed[j] = true
                        placed+= turnedBlock.size
                        continue@emptyLoop
                    }
                }
            }
            return placed
        }
    }
    val gameBoard = arrayOf(
        intArrayOf(1,1,0,0,1,0),
        intArrayOf(0,0,1,0,1,0),
        intArrayOf(0,1,1,0,0,1),
        intArrayOf(1,1,0,1,1,1),
        intArrayOf(1,0,0,0,1,0),
        intArrayOf(0,1,1,1,0,0)
    )
    val gb2 = Array(50){IntArray(50){0} }
    val t2 = Array(50){IntArray(50){1}}

    val gb3 = arrayOf(
        intArrayOf(0,0,0),
        intArrayOf(1,1,0),
        intArrayOf(1,1,1)
    )
    val t3 = arrayOf(
        intArrayOf(1,1,1),
        intArrayOf(1,0,0),
        intArrayOf(0,0,0)
    )

    val table = arrayOf(
        intArrayOf(1,0,0,1,1,0),
        intArrayOf(1,0,1,0,1,0),
        intArrayOf(0,1,1,0,1,1),
        intArrayOf(0,0,1,0,0,0),
        intArrayOf(1,1,0,1,1,0),
        intArrayOf(0,1,0,0,0,0)
    )
    println(Solution().solution(
        gb3, t3
    ))
}