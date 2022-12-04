import java.io.InputStreamReader
import java.io.BufferedReader
import java.util.StringTokenizer

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    val n = st.getInt(); val m = st.getInt(); var sharks = st.getInt()
    val boundY = n-1
    val boundX = m-1

    val dx = intArrayOf(0,0,1,-1)
    val dy = intArrayOf(-1,1,0,0)
    class Shark(var speed:Int, var direction:Int, val weight:Int){
        fun getNewPosition(curPosition:Int, moveLeft:Int):Int{
            //val lowerBound = 0
            val upperBound:Int
            if(direction==0||direction==1){
                upperBound = boundY
                direction = if(moveLeft == 0 ) direction else if(moveLeft>0) 1 else 0
            }else{
                upperBound = boundX
                direction = if(moveLeft == 0 ) direction else if(moveLeft>0) 2 else 3
            }
            if(upperBound==0) return 0
            val absMoveLeft = kotlin.math.abs(moveLeft)
            return if(moveLeft==0) curPosition
            else if (moveLeft<0){
                if(curPosition<absMoveLeft) getNewPosition(0,absMoveLeft-curPosition)
                else curPosition - absMoveLeft
            }else{
                val moveToBound = upperBound-curPosition
                if(moveToBound<absMoveLeft) getNewPosition(upperBound,moveToBound-absMoveLeft)
                else curPosition + absMoveLeft
            }
        }
    }
    val noShark = Shark(0,0,0)
    val fishingZone = Array(2){Array(n){Array(m){noShark} }}


    repeat(sharks){
        st = StringTokenizer(readLine())
        fishingZone[0][st.getInt()-1][st.getInt()-1] = Shark(st.getInt(),st.getInt()-1,st.getInt())
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

                    val direction = focused.direction
                    val speed = focused.speed
                    val nx:Int
                    val ny:Int

                    if(direction==0||direction==1) {
                        ny = focused.getNewPosition(y,dy[direction]*speed)
                        nx = x
                    } else {
                        nx = focused.getNewPosition(x,dx[direction]*speed)
                        ny = y
                    }

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
        val v = Array(n){y->IntArray(m){x-> fishingZone[nextTurnIndex][y][x].weight} }
        print("")
    }

    print(fishWeightCaught)
}