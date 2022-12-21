import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList
import java.util.StringTokenizer

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    val input1 = StringTokenizer(readLine())
    val n = input1.getInt(); val m = input1.getInt()
    val visited = Array(n){
        readLine().map { it=='X' }.toBooleanArray()
    }
    data class Point(val y:Int,val x:Int)
    val input2 = StringTokenizer(readLine())
    val input3 = StringTokenizer(readLine())
    val ty = input3.getInt()-1; val tx = input3.getInt()-1
    val dy = intArrayOf(-1,0,1,0)
    val dx = intArrayOf(0,1,0,-1)
    val queue = LinkedList<Point>().apply { this.add(Point(input2.getInt()-1,input2.getInt()-1)) }
    var found = false
    while(queue.isNotEmpty()){
        val (vy,vx) = queue.pollFirst()
        if(vy == ty && vx == tx){
            // 미손상된 목표지에 도착시 다녀올 수 있는 곳 확인
            for(i in 0..3){
                val ny = vy + dy[i]
                val nx = vx + dx[i]
                if(ny in 0 until n && nx in 0 until m && !visited[ny][nx]){
                    found = true
                    break
                }
            }
        }

        for(i in 0..3){
            val ny = vy + dy[i]
            val nx = vx + dx[i]
            if(ny in 0 until n && nx in 0 until m) {
                // 손상되지 않은 곳 방문
                if(!visited[ny][nx]) {
                    visited[ny][nx] = true
                    queue.add(Point(ny,nx))
                }
                // 손상된 목표지에 골인
                else if(ny==ty && nx == tx){
                    found = true
                    break
                }
            }
        }
    }

    if(found) print("YES") else print("NO")
}