import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val (n,m) = readLine().split(" ").map { it.toInt() }
    val s = IntArray(2){-1}
    val c1 = IntArray(2){-1}
    val c2 = IntArray(2){-1}
    val isRoad = Array(n){
            y ->
        val input = readLine().toCharArray()
        BooleanArray(m){
                x ->
            var road:Boolean
            if(input[x]=='#') road = false
            else{
                road = true
                when(input[x]){
                    'C' -> {
                        if(c1.contains(-1)){
                            c1[0] = y
                            c1[1] = x
                        }
                        else{
                            c2[0] = y
                            c2[1] = x
                        }
                    }
                    'S' -> {
                        s[0] = y
                        s[1] = x
                    }
                }
            }
            road
        }
    }
    // 오른쪽 아래 왼쪽 위
    val dy = intArrayOf(0,1,0,-1)
    val dx = intArrayOf(1,0,-1,0)
    // (d+2)%4 연산과 같음
    // val opposite = intArrayOf(2,3,0,1)
    val arriveDirection = IntArray(4){Int.MAX_VALUE}
    fun getDistance(from:IntArray,to:IntArray,lastDirection:Int){
        val (sY,sX) = from
        val (tY,tX) = to
        data class State(val y:Int, val x:Int, val direction:Int, val moved:Int)
        val visited = Array(n){
            BooleanArray(m){false}
        }.apply { this[sY][sX] = true }
        val queue = LinkedList<State>().apply{ add(State(sY,sX,lastDirection,0))}
        while(queue.isNotEmpty()){
            val cur = queue.pollFirst()
            if(cur.x==tX && cur.y==tY) {
                arriveDirection[cur.direction] = cur.moved
                if(arriveDirection.all{it!= Int.MAX_VALUE}) return
            }
            for(d in 0..3){
                val ny = cur.y + dy[d]
                val nx = cur.x + dx[d]
                if(d!=cur.direction && ny in 0 until n && nx in 0 until m && isRoad[ny][nx] &&!visited[ny][nx]){
                    visited[ny][nx] = true
                    queue.add(State(ny,nx,d,cur.moved+1))
                    queue.add(State(cur.y,cur.x,(d+2)%4,cur.moved+2))
                }
            }
        }
        return
    }
    // w1 과 w3 의 방향을 고려하지 못해서 탈락
    val w1 = getDistance(s,c1,-1)
    val w2 = getDistance(s,c2,-1)
    //val w3 = getDistance(c1,c2)
    //val w4 = getDistance(c2,c1)
    var ans = Int.MAX_VALUE
    //if(w1!=-1 && w3!=-1) ans = (w1+w3).coerceAtMost(ans)
    // if(w2!=-1 && w4!=-1) ans = (w2+w4).coerceAtMost(ans)
    print(if(ans== Int.MAX_VALUE)-1 else ans)

}