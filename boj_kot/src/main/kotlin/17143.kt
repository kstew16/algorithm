import java.io.InputStreamReader
import java.io.BufferedReader
import java.util.StringTokenizer

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    data class Shark(val speedY:Int, val speedX:Int,  val weight:Int)
    val n = st.getInt(); val m = st.getInt(); var sharks = st.getInt()
    val boundY = n-1
    val boundX = m-1
    val noShark = Shark(0,0,0)
    val fishingZone = Array(2){Array(n){Array(m){noShark} }}

    val dx = intArrayOf(0,0,1,-1)
    val dy = intArrayOf(1,-1,0,0)

    repeat(sharks){
        st = StringTokenizer(readLine())
        val r = st.getInt()-1; val c = st.getInt()-1
        val s = st.getInt(); val d = st.getInt()-1;
        fishingZone[0][r][c] = Shark(s*dy[d],s*dx[d],st.getInt())
    }

    fun getNewPosition(curPosition:Int,moveLeft:Int,upperBound:Int):Int{
        //val lowerBound = 0
        if(upperBound==0) return 0
        val absMoveLeft = kotlin.math.abs(moveLeft)
        return if(moveLeft==0) curPosition
        else if (moveLeft<0){
            if(curPosition<absMoveLeft) getNewPosition(0,absMoveLeft-curPosition,upperBound)
            else curPosition - absMoveLeft
        }else{
            val moveToBound = upperBound-curPosition
            if(moveToBound<absMoveLeft) getNewPosition(upperBound,moveToBound-absMoveLeft,upperBound)
            else curPosition + absMoveLeft
        }
    }

    var fishWeightCaught = 0
    for(fishingX in 0 ..boundX){
        //if(sharks==0) break
        val turnIndex = fishingX%2
        val nextTurnIndex = if(turnIndex==0) 1 else 0

        // fishing
        for(radarY in 0 ..boundY){
            val focused = fishingZone[turnIndex][radarY][fishingX]
            if(focused!=noShark){
                fishWeightCaught +=  focused.weight
                fishingZone[turnIndex][radarY][fishingX] = noShark
                //sharks--
                break
            }
        }

        for(y in 0 ..boundY){
            for(x in 0 ..boundX){
                val focused = fishingZone[turnIndex][y][x]
                fishingZone[turnIndex][y][x] = noShark
                if(focused != noShark){
                    val ny = getNewPosition(y,focused.speedY,boundY)
                    val nx = getNewPosition(x,focused.speedX,boundX)
                    // 비어있건, 더 작은 물고기건 weight 가 작으면 덮어쓰면 됨
                    val targetZone = fishingZone[nextTurnIndex][ny][nx]
                    if(targetZone.weight<focused.weight) fishingZone[nextTurnIndex][ny][nx] = focused
                    /*if(targetZone.weight==0) fishingZone[nextTurnIndex][ny][nx] = focused
                    else if(targetZone.weight<focused.weight){
                        fishingZone[nextTurnIndex][ny][nx] = focused
                        sharks--
                    }else sharks--
                     */
                }
            }
        }
    }

    print(fishWeightCaught)
}