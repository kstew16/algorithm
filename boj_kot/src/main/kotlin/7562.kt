import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))

    val dx = arrayOf(1,2,2,1,-1,-2,-2,-1)
    val dy = arrayOf(2,1,-1,-2,-2,-1,1,2)

    var st = StringTokenizer(br.readLine())
    fun getInt() = st.nextToken().toInt()
    val cases = getInt()
    repeat(cases){

        st = StringTokenizer(br.readLine())
        val width = getInt()

        st = StringTokenizer(br.readLine())
        val startX = getInt()
        val startY = getInt()
        st = StringTokenizer(br.readLine())
        val targetX = getInt()
        val targetY = getInt()

        //val queue = mutableListOf<IntArray>()
        val queue2 = Array(8 * width * width){ intArrayOf(0,0) }
        var bottom =0
        var top = 0

        val visited = Array(width){
            IntArray(width){0}
        }

        queue2[top++] = intArrayOf(startY,startX)

        while(bottom != top){

            val using = queue2[bottom++]
            val (usingY,usingX) = using
            if(usingY == targetY && usingX == targetX) break
            if(visited[targetY][targetX] != 0 )break
            for(i in 0 until 8){
                val nx = usingX + dx[i]
                val ny = usingY + dy[i]
                if(nx in 0 until width && ny in 0 until width && visited[ny][nx] == 0){
                    val depth = visited[usingY][usingX]
                    queue2[top++]=intArrayOf(ny,nx)
                    visited[ny][nx] = depth+1
                }

            }
        }


        bw.write("${visited[targetY][targetX]}\n")


    }

    bw.flush()
    bw.close()
    br.close()

}