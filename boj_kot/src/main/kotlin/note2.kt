import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*


private fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    var st = StringTokenizer(br.readLine())
    fun getInt() = st.nextToken().toInt()
    val width = getInt()
    val field = Array(width){
        br.readLine().split(" ").map { it.toInt() }.toIntArray()
    }
    val visited = Array(width){
        IntArray(width){-1}
    }
    val boundaryQueue = Array(width*width*4){ intArrayOf(-1,-1) }
    var bottom = 0
    var top = 0

    val dx = arrayOf(1,0,-1,0)
    val dy = arrayOf(0,1,0,-1)

    var cComps = 0
    var count = 0

    fun dfs(y:Int, x:Int){
        visited[y][x] = cComps
        count ++

        for(i in 0 until 4){
            val nx = x + dx[i]
            val ny = y + dy[i]
            if(nx in 0 until width && ny in 0 until width && visited[ny][nx] == -1){
                if(field[ny][nx]==1)dfs(ny,nx)
                else{
                    visited[ny][nx] = 0
                    boundaryQueue[top++] = intArrayOf(ny,nx)
                }
            }

        }
    }

    var elements = mutableListOf<Int>()
    for(y in 0 until width){
        for(x in 0 until width){
            if(field[y][x]==1 && visited[y][x] == -1){
                count = 0
                cComps += 1
                dfs(y,x)
                elements.add(count)
            }
        }
    }


    bw.flush()
    bw.close()
    br.close()
}