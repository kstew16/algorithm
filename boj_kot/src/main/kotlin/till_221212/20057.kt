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

    var sandBlownOut = 0

    fun Int.left() = (this+3)%4
    fun Int.right() = (this+1)%4
    class Tornado(var py:Int, var px:Int, var d:Int){
        fun move():Boolean{
            val ny = dy[d] + py ; val nx = dx[d] + px
            if(ny !in 0 until n || nx !in 0 until n) return false
            py = ny; px = nx
            blow()
            return true
        }
        fun blow(){
            // 범위 밖으로 나가는 모래 측정해야함
            val currentSand = field[py][px]
            var sandUsed = 0
            val right = d.right()
            // val left = d.left()
            // 1퍼 1퍼 7퍼 7퍼 10퍼 10퍼 2퍼 2퍼 5퍼 나머지
            val dSide = intArrayOf(1,-1,1,-1,1,-1,2,-2,0,0)
            val dDirectional = intArrayOf(-1,-1,0,0,1,1,0,0,2,1)
            val blowingRatio = intArrayOf(1,1,7,7,10,10,2,2,5)
            for(i in 0 until 9){
                val ny = py + dDirectional[i]*dy[d] +dSide[i]*dy[right]
                val nx = px + dDirectional[i]*dx[d] +dSide[i]*dx[right]
                val sandBlowing = (blowingRatio[i]*currentSand)/100
                sandUsed += sandBlowing
                if(ny in 0 until n && nx in 0 until n) field[ny][nx]+=sandBlowing
                else sandBlownOut += sandBlowing
            }
            val ny = py + dy[d]
            val nx = px + dx[d]
            val sandBlowing = currentSand-sandUsed
            if(ny in 0 until n && nx in 0 until n) field[ny][nx]+=sandBlowing
            else sandBlownOut += sandBlowing
            field[py][px] = 0
        }
        fun turnLeft(){this.d = this.d.left()}
    }

    var movingDistance = 1 // 초기 이동량은 1
    val tornado = Tornado(n/2,n/2,3)
    var active = true
    while(active){
        for(i in 0 until 2){
            repeat(movingDistance){
                active = tornado.move()
            }
            if(!active) break
            tornado.turnLeft()
        }
        movingDistance+=1
    }
    print(sandBlownOut)
}