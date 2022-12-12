import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val (n,m) = readLine().split(" ").map{it.toInt()}
    val height = Array(n){
        val st = StringTokenizer(readLine())
        IntArray(m){
            st.nextToken().toInt()
        }
    }
    val finished = Array(n){IntArray(m){0} }
    val visited = Array(n){BooleanArray(m){false} }

    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val dx = intArrayOf(1,1,1,0,-1,-1,-1,0)
    val dy = intArrayOf(-1,0,1,1,1,0,-1,-1)
    fun rollDFS(vx:Int,vy:Int,depth:Int){
        visited[vy][vx] = true
        var tx = vx
        var ty = vy
        var minHeight = height[vy][vx]
        for(i in 0 until 8){
            val nx = vx + dx[i]
            val ny = vy + dy[i]
            if(nx in 0 until m && ny in 0 until n){
                val tHeight = height[ny][nx]
                if(minHeight>tHeight){
                    tx = nx
                    ty = ny
                    minHeight = tHeight
                }
            }
        }
        rollDFS(tx,ty,depth+1)
    }


    for(y in 0 until n){
        for(x in 0 until m){
            if(!visited[y][x]) rollDFS(x,y,1)
        }
    }
    finished.forEach {
        bw.write(it.joinToString(" "))
        bw.write("\n")
    }
    bw.flush()
    bw.close()
    close()
}