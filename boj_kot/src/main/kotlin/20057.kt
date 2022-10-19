import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    val n = readLine().toInt()
    val field = Array(n){
        val st = StringTokenizer(readLine())
        IntArray(n){st.getInt()}
    }
    // 위 오른 아래 왼 순으로 0,1,2,3
    val dy = intArrayOf(-1,0,1,0)
    val dx = intArrayOf(0,1,0,-1)
    fun Int.turnRight() = (this+1)%4
    fun Int.turnLeft() = (this+3)%4

    var sandBlownOut = 0

    class Tornado(var py:Int, var px:Int){
        fun move(d:Int){

        }
        fun blow(d:Int){
            // 범위 밖으로 나가는 모래 측정해야함
        }
    }

    var moveCount = 0
    val limit = n*n-1
    var movingDistance = 1 // 초기 이동량은 1
    var movingDirection = 3 // 초기 운동방향은 좌측
    val tornado = Tornado(n/2,n/2)
    while(moveCount<limit){
        for(i in 0 until 2){
            repeat(movingDistance){
                tornado.move(movingDirection)
                tornado.blow(movingDirection)
            }
            movingDirection = movingDirection.turnLeft()
        }
        movingDistance+=1
    }
    print(sandBlownOut)
}