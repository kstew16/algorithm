import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()

    val targetCoord = Array(n*n){
        IntArray(2){-1}
    }

    // 나중에 repeat 로
    repeat(n){ y ->
        val st = StringTokenizer(readLine())
        repeat(n){ x->
            val input = st.nextToken().toInt()
            targetCoord[input-1] = intArrayOf(y,x)
            //input
        }
    }

    data class State(val y:Int, val x:Int,val type:Int,val move:Int,val changed:Int)
    fun getTargetCoords(y:Int,x:Int,type:Int):MutableList<IntArray>{
        val movable = mutableListOf<IntArray>()
        when(type){
            0 -> {
                // 나이트
                val dx = intArrayOf(1,2,2,1,-1,-2,-2,-1)
                val dy = intArrayOf(2,1,-1,-2,-2,-1,1,2)
                for(i in 0 until 8){
                    val nx = x + dx[i]
                    val ny = y + dy[i]
                    if(nx in 0 until n && ny in 0 until n) movable.add(intArrayOf(ny,nx))
                }
            }
            1 -> {
                // 룩
                for(nx in 0 until n) if(nx!=x) movable.add(intArrayOf(y,nx))
                for(ny in 0 until n) if(ny!=y) movable.add(intArrayOf(ny,x))
            }
            2 -> {
                // 비숍
                val signX = intArrayOf(1,1,-1,-1)
                val signY = intArrayOf(1,-1,-1,1)
                for(index in 0 until 4){
                    for(offset in 1 until n){
                        val nx = x + signX[index]*offset
                        val ny = y + signY[index]*offset
                        if(nx in 0 until n && ny in 0 until n) movable.add(intArrayOf(ny,nx))
                        else break
                    }
                }
            }

        }
        return movable
    }



    val arrivedDistance = Array(n*n-1){IntArray(3){Int.MAX_VALUE} }
    val changed  = Array(n*n-1){IntArray(3){Int.MAX_VALUE} }
    for(i in 0 until n*n-1){
        val (sy,sx) = targetCoord[i]
        val (ty,tx) = targetCoord[i+1]
        for(t in 0 until 3){
            val visited = Array(n){
                Array(n){
                    BooleanArray(3){false}
                }
            }
            val queue = LinkedList<State>()

            fun getArrival(round:Int){
                while (queue.isNotEmpty()){
                    val cur = queue.pollFirst()
                    if(cur.y == ty && cur.x == tx) {
                        if(cur.move==arrivedDistance[round][cur.type]){
                            changed[round][cur.type] = cur.changed.coerceAtMost(changed[round][cur.type])
                        }else if(cur.move < arrivedDistance[round][cur.type]){
                            arrivedDistance[round][cur.type] = cur.move
                            changed[round][cur.type] = cur.changed
                        }
                        //continue
                    }
                    // 말을 바꾸지 않고 진행하는 경우
                    getTargetCoords(y=cur.y,x=cur.x,type=cur.type).forEach {
                        val (ny,nx) = it
                        if(!visited[ny][nx][cur.type]) {
                            visited[ny][nx][cur.type]= true
                            queue.add(State(ny,nx,cur.type,cur.move+1,cur.changed))
                        }
                    }
                    // 말을 바꾸는 경우
                    for(type in 0 until 3){
                        if(type!=cur.type && !visited[cur.y][cur.x][type]) {
                            visited[cur.y][cur.x][type] = true
                            queue.add(State(cur.y,cur.x,type,cur.move+1,cur.changed+1))
                        }
                    }
                }
            }
            visited[sy][sx][t] = true
            queue.add(State(sy,sx,t,if(i==0) 0 else arrivedDistance[i-1][t],if(i==0) 0 else changed[i-1][t]))
            getArrival(i)
        }
    }
    var ansDist = Int.MAX_VALUE
    var minChanged = Int.MAX_VALUE
    val finalRound = n*n-2
    for(i in 0 until 3){
        if(arrivedDistance[finalRound][i]<ansDist){
            ansDist = arrivedDistance[finalRound][i]
            minChanged = changed[finalRound][i]
        }
        else if(arrivedDistance[finalRound][i] == ansDist){
            minChanged = changed[finalRound][i].coerceAtMost(minChanged)
        }
    }
    print("$ansDist $minChanged")
    //print(ans)
}