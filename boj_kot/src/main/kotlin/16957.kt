import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.LinkedList
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
    val queue = LinkedList<IntArray>()


    // 다른 거 고려없이 250000 * 8 의 이동방향을 구함
    for(y in 0 until n){
        for(x in 0 until m){
            var minHeight = height[y][x]
            var ty = y
            var tx = x
            for(i in 0 until 8){
                val ny = y + dy[i]
                val nx = x + dx[i]
                if(ny in 0 until n && nx in 0 until m){
                    val tHeight = height[ny][nx]
                    if(minHeight>tHeight){
                        minHeight = tHeight
                        ty = ny
                        tx = nx
                    }
                }
            }
            direction[y][x] = intArrayOf(ty,tx)
            if(ty ==  y && tx == x) queue.add(intArrayOf(y,x))
        }
    }

    fun dfsSourcing(sy:Int,sx:Int,vy:Int,vx:Int){
        if(sy!=vy || sx!=vx){
            direction[vy][vx] = direction[sy][sx]
        }
        for(i in 0 until 8){
            val ny = vy + dy[i]
            val nx = vx + dx[i]
            if(ny in 0 until n && nx in 0 until m){
                val (nsy,nsx) = direction[ny][nx]
                if(nsy == vy && nsx == vx){
                    dfsSourcing(vy,vx,ny,nx)
                }
            }
        }
    }
    while(queue.isNotEmpty()){
        val (vy,vx) = queue.pollFirst()
        dfsSourcing(vy,vx,vy,vx)
    }

    for(y in 0 until n){
        for(x in 0 until m){
            val (ty,tx) = direction[y][x]
            visited[ty][tx] += 1
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