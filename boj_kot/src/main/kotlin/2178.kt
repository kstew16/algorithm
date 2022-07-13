import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    val (n,m) = br.readLine().split(" ").map { it.toInt() }
    val maze = Array(n){
        br.readLine().chunked(1).map{it.toInt()}.toIntArray()
    }

    val queue = mutableListOf<IntArray>()
    val visited = Array(n){
        IntArray(m){0}
    }


    fun visit(y:Int=0,x:Int=0,depth:Int){
        // 인접 노드 추가
        if(y+1<n && 0==visited[y+1][x] && maze[y+1][x] == 1){
            queue.add(intArrayOf(y+1,x))
            visited[y+1][x] = depth+1
        }
        if(y-1>=0 && 0==visited[y-1][x] && maze[y-1][x] == 1){
            queue.add(intArrayOf(y-1,x))
            visited[y-1][x] = depth+1
        }
        if(x+1<m && 0==visited[y][x+1] && maze[y][x+1] == 1){
            queue.add(intArrayOf(y,x+1))
            visited[y][x+1] = depth+1
        }
        if(x-1>=0 && 0==visited[y][x-1] && maze[y][x-1] == 1){
            queue.add(intArrayOf(y,x-1))
            visited[y][x-1] = depth+1
        }
    }

    queue.add(intArrayOf(0,0))
    visited[0][0] = 1
    while(queue.isNotEmpty()){
        //depth += 1
        val target = queue.removeAt(0)
        val (targetY,targetX) = target
        val depth = visited[targetY][targetX]
        //if(!visited[targetY][targetX] && maze[targetY][targetX]==1)
        visit(targetY,targetX,depth)
        if(visited[n-1][m-1]!=0)break
    }
    bw.write("${visited[n-1][m-1]}")
    bw.flush()
    bw.close()
    br.close()

}