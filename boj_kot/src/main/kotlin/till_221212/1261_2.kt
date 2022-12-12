import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.LinkedList
// 0부터 쭉 한 번 보고 가는 건 의미 없는 개선이었다. 크기가 크면 유리하긴 하더라
fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    val (w,h) = br.readLine().split(" ").map { it.toInt() }
    val maze = Array(h){
        br.readLine().chunked(1).map{it.toInt()}.toIntArray()
    }
    val dx = intArrayOf(0,0,1,-1)
    val dy = intArrayOf(1,-1,0,0)

    val queue = LinkedList<IntArray>().apply { addFirst(intArrayOf(0,0)) }
    val visited = Array(h){
        IntArray(w){Int.MAX_VALUE}
    }.apply{this[0][0] = 0}

    // 먼저 안 부수고 갈 수 있는 길 다 찾음(연결된 0 이 많은 일부 사례에서 개선 기대)
    while(queue.isNotEmpty()){
        val cur = queue.pollFirst()
        val (curY,curX) = cur
        for(i in 0..3){
            val ny = curY + dy[i]
            val nx = curX + dx[i]
            if(ny in 0 until h && nx in 0 until w && maze[ny][nx]==0 && visited[ny][nx] != 0){
                visited[ny][nx] = 0
                queue.add(intArrayOf(ny,nx))
            }
        }
    }
    // 이제 부수면서 다시 출발
    queue.add(intArrayOf(0,0))
    val visited2 = Array(h){
        BooleanArray(w){false}
    }.apply{this[0][0] = true}

    while(queue.isNotEmpty()){
        val cur = queue.pollFirst()
        val (curY,curX) = cur
        val curState = visited[curY][curX]
        for(i in 0..3){
            val ny = curY + dy[i]
            val nx = curX + dx[i]
            if(ny in 0 until h && nx in 0 until w){
                val wallOnTarget = maze[ny][nx]
                if(visited[ny][nx]>curState+wallOnTarget || !visited2[ny][nx]){
                    visited[ny][nx] = curState+wallOnTarget
                    visited2[ny][nx] = true
                    queue.add(intArrayOf(ny,nx))
                }
            }
        }
    }

    bw.write("${visited[h-1][w-1]}")
    bw.flush()
    bw.close()
    br.close()
}