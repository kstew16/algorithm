import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*
// 1시간 걸렸음, queue와 stack을 mutableList로 링크드리스트 식으로 구현하면 입력이 클 시에 시간이 느림
// 메모리 제한이 빡빡한 게 아니라면 Array 방식으로 사용하고, 메모리 제한이 빡빡할 때에만 LL 쓰면 될 듯

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))

    val dx = arrayOf(0, 0, 1, -1)
    val dy = arrayOf(1, -1, 0, 0)

    var st = StringTokenizer(br.readLine())
    fun getInt() = st.nextToken().toInt()
    val m = getInt()
    val n = getInt()

    //val queue = mutableListOf<IntArray>()
    val queue2 = Array(n*m){ intArrayOf(0,0) }
    var bottom =0
    var top = 0

    val visited = Array(n){
        IntArray(m){0}
    }

    val box = Array(n){
            y ->
        st = StringTokenizer(br.readLine())
        IntArray(m){
                x->
            val element = getInt()
            //var element = 1
            if(element==1) {
                queue2[top++] = intArrayOf(y,x)
                visited[y][x] = 1
            }
            element
        }
    }


    var maxDepth = 0
    while(bottom != top){
        val target = queue2[bottom++]
        val (targetY,targetX) = target
        for(i in 0 until 4){
            val nx = targetX + dx[i]
            val ny = targetY + dy[i]
            if(nx in 0 until m && ny in 0 until n && visited[ny][nx] == 0 && box[ny][nx] == 0){
                val depth = visited[targetY][targetX]
                queue2[top++]=intArrayOf(ny,nx)
                visited[ny][nx] = depth+1
                box[ny][nx] = 1
                maxDepth = depth.coerceAtLeast(maxDepth)
            }
        }
    }


    var found = true
    for(i in 0 until n){
        for(j in 0 until m){
            if(box[i][j]==0) found = false
        }
    }
    if(found)bw.write("$maxDepth")
    else bw.write("-1")

    bw.flush()
    bw.close()
    br.close()


}