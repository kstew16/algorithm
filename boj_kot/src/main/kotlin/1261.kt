import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.LinkedList
// 원트
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


    while(queue.isNotEmpty()){
        val cur = queue.pollFirst()
        val (curY,curX) = cur
        val curState = visited[curY][curX]
        for(i in 0..3){
            val ny = curY + dy[i]
            val nx = curX + dx[i]
            if(ny in 0 until h && nx in 0 until w){
                val wallOnTarget = maze[ny][nx]
                if(visited[ny][nx]>curState+wallOnTarget){
                    visited[ny][nx] = curState+wallOnTarget
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