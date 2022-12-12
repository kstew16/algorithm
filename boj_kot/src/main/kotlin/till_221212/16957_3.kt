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
    val visited = Array(n){IntArray(m){0} }
    val direction = Array(n){
        Array(m){
            intArrayOf(-1,-1)
        }
    }
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val dx = intArrayOf(1,1,1,0,-1,-1,-1,0)
    val dy = intArrayOf(-1,0,1,1,1,0,-1,-1)
    fun rollDFS(vx:Int,vy:Int){
        var tx = vx
        var ty = vy
        if(!direction[vy][vx].contentEquals(intArrayOf(-1,-1))){
            tx = direction[vy][vx][0]
            ty = direction[vy][vx][1]
        }
        else{
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
        }
        direction[vy][vx] = intArrayOf(tx,ty)
        if(tx == vx && ty == vy) visited[vy][vx] += 1
        else rollDFS(tx,ty)
    }


    for(y in 0 until n){
        for(x in 0 until m){
            rollDFS(x,y)
        }
    }
    visited.forEach {
        bw.write(it.joinToString(" "))
        bw.write("\n")
    }
    bw.flush()
    bw.close()
    close()
}