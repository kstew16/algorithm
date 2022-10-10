import java.io.BufferedReader
import java.io.InputStreamReader

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val n = readLine().toInt()
    val target = readLine().toInt()
    var px = n/2
    var py = n/2
    val field = Array(n){IntArray(n){0} }.apply { this[py][px]=1 }
    val dx = intArrayOf(0,1,0,-1)
    val dy = intArrayOf(-1,0,1,0)
    var num = 1
    var move = 1
    var counter = -1
    val limit = n*n
    var ax = px+1
    var ay = py+1


    fun paint(direction:Int,positionY:Int,positionX:Int,m:Int){
        val my = dy[direction]
        val mx = dx[direction]
        var ny = positionY
        var nx = positionX
        for(i in 0 until kotlin.math.min(m,n-1)){
            num+=1
            ny+=my
            nx+=mx
            if(num==target){
                ay = ny+1
                ax = nx+1
            }
            field[ny][nx] = num
        }
        px = nx
        py = ny
    }
    var direction = -1
    while(num<limit){
        counter+=1
        if(counter==2){
            move += 1
            counter = 0
        }
        direction = (direction+1)%4
        paint(direction,py,px,move)
    }
    for(i in field.indices){
        println(field[i].joinToString(" "))
    }
    print("$ay $ax")

}