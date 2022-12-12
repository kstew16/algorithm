import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*
import kotlin.math.pow

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    val n = st.getInt(); val q = st.getInt()
    val fullSize = (2.0).pow(n).toInt()
    val field = Array(fullSize){
        st = StringTokenizer(readLine())
        IntArray(fullSize){st.getInt()}
    }
    st = StringTokenizer(readLine())
    val stormType = IntArray(q){st.getInt()}
    val dx = intArrayOf(0,1,0,-1)
    val dy = intArrayOf(-1,0,1,0)
    fun fire(){
        val check = Array(fullSize){BooleanArray(fullSize)}
        for(i in 0 until fullSize) for(j in 0 until fullSize) {
            var count = 0
            for (k in 0 until 4) {
                val ny = i + dy[k]
                val nx = j + dx[k]
                if (ny in 0 until fullSize && nx in 0 until fullSize && field[ny][nx] != 0) count += 1
            }
            if (count < 3) check[i][j] = true
        }
        for (i in 0 until fullSize) for (j in 0 until fullSize) if (check[i][j]) if (field[i][j] > 0) field[i][j] -= 1
    }
    fun rotate(bs:Int,py:Int,px:Int){
        val tmp = Array(bs){b->
            IntArray(bs){a->
                field[py+bs-a-1][px+b]
            }
        }
        for(i in 0 until bs) for(j in 0 until bs) field[py+i][px+j] = tmp[i][j]
    }
    fun storm(l:Int){
        if(l == 0) return // 이건 돌리는게 아님
        val blockSize = (2.0).pow(l).toInt()
        val blockCount = (2.0).pow(n-l).toInt()
        for(i in 0 until blockCount) for(j in 0 until blockCount) rotate(blockSize,i*blockSize,j*blockSize)
    }
    fun fireStorm(l:Int){
        storm(l)
        fire()
    }
    stormType.forEach { fireStorm(it) }
    var totalWeight = 0
    var biggest = 0
    val visited = Array(fullSize){BooleanArray(fullSize)}
    data class Node(val y:Int,val x:Int)
    fun searchIce(py:Int,px:Int):Int{
        var size = 0
        val queue = LinkedList<Node>().apply { this.add(Node(py,px)) }
        visited[py][px] = true
        while(queue.isNotEmpty()){
            size+=1
            val (cy,cx) = queue.pollFirst()
            for(i in 0 until 4){
                val ny = cy + dy[i]
                val nx = cx + dx[i]
                if(ny in 0 until fullSize && nx in 0 until fullSize&& !visited[ny][nx] && field[ny][nx]!=0){
                    visited[ny][nx] = true
                    queue.add(Node(ny,nx))
                }
            }
        }
        return size
    }
    for(i in 0 until fullSize) for(j in 0 until fullSize){
        totalWeight += field[i][j]
        if(!visited[i][j] && field[i][j]!=0) biggest = searchIce(i,j).coerceAtLeast(biggest)
    }
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    bw.write("$totalWeight\n$biggest")
    bw.flush()
    bw.close()
    close()
}