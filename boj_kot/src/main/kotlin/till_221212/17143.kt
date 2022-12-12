import java.io.InputStreamReader
import java.io.BufferedReader
import java.util.StringTokenizer
// 임실에서 풀었다가 디버깅 못 돌려서 참패했었음
fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    val n = st.getInt(); val m = st.getInt(); var sharks = st.getInt()
    val boundY = n-1
    val boundX = m-1

    val dx = intArrayOf(0,0,1,-1)
    val dy = intArrayOf(-1,1,0,0)
    class Shark(var speed:Int, var direction:Int, val weight:Int){
        // 새 포지션을 구할 때 일차원 수직선 상에서 왔다갔다거린다 생각하고 남은 이동을 (양수 음수) 재귀적으로 구함
        fun getNewPosition(curPosition:Int, moveLeft:Int):Int{
            val upperBound:Int
            // 현재 수직선에서 이동하는 거리의 부호가 상어의 방향을 결정함
            if(direction==0||direction==1){
                upperBound = boundY
                direction = if(moveLeft == 0 ) direction else if(moveLeft>0) 1 else 0
            }else{
                upperBound = boundX
                direction = if(moveLeft == 0 ) direction else if(moveLeft>0) 2 else 3
            }
            // 수직선의 길이가 0 인 경우 이동할 수 없음
            if(upperBound==0) return 0
            var absMoveLeft = kotlin.math.abs(moveLeft)
            // 이 때 수행시간을 줄이기 위해 수직선의 길이(upperBound) 의 2배수의 나머지만 처리해줌
            absMoveLeft%=(upperBound*2)
            return if(moveLeft==0) curPosition
            // 음수방향으로 이동할 때는 lower bound == 0 에 충돌하게 됨, Bound 까지 필요한 move 수는 curPosition - 0
            else if (moveLeft<0){
                if(curPosition<absMoveLeft) getNewPosition(0,absMoveLeft-curPosition)
                else curPosition - absMoveLeft
            }
            // 반대로 양수 방향으로 이동할 때는 upper bound (배열 경계값) 에 충돌하게 됨. 필요한 move 수는 upperBound - curPosition
            else{
                val moveToBound = upperBound-curPosition
                if(moveToBound<absMoveLeft) getNewPosition(upperBound,moveToBound-absMoveLeft)
                else curPosition + absMoveLeft
            }
        }
    }
    // 상어가 없는 칸은 동일한 명목상의 null 객체로 채워줌
    val noShark = Shark(0,0,0)
    // 메모리를 절약하기 위하여 낚시가 진행중인 공간을 두 개만 사용,
    // 공간의 사용은 낚시왕이 낚시하고 있는 좌표 fishingX%2 에서 낚시 진행 후, 새 좌표를 구해 다른 맵에 저장
    val fishingZone = Array(2){Array(n){Array(m){noShark} }}


    repeat(sharks){
        st = StringTokenizer(readLine())
        fishingZone[0][st.getInt()-1][st.getInt()-1] = Shark(st.getInt(),st.getInt()-1,st.getInt())
    }


    var fishWeightCaught = 0
    var fishExpected = 0
    for(fishingX in 0 ..boundX){
        // 상어가 없으면 계산을 더 할 필요가 없음
        if(sharks==0) break
        val turnIndex = fishingX%2
        val nextTurnIndex = if(turnIndex==0) 1 else 0
        // fishing
        for(radarY in fishExpected ..boundY){
            val focused = fishingZone[turnIndex][radarY][fishingX]
            if(focused!=noShark){
                fishWeightCaught +=  focused.weight
                fishingZone[turnIndex][radarY][fishingX] = noShark
                sharks--
                break
            }
        }
        fishExpected = boundY
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
                    if(targetZone.weight==0) fishingZone[nextTurnIndex][ny][nx] = focused
                    else if(targetZone.weight<focused.weight){
                        fishingZone[nextTurnIndex][ny][nx] = focused
                        sharks--
                    }else sharks--
                    if(fishingX+1 == nx) fishExpected = ny.coerceAtMost(fishExpected)
                }
            }
        }
    }

    print(fishWeightCaught)
}