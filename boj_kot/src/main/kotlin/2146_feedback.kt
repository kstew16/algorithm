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
    val connectedMap = Array(width){
        IntArray(width){0}
    }

    val dx = arrayOf(1,0,-1,0)
    val dy = arrayOf(0,1,0,-1)

    var cComps = 0

    val queue = LinkedList<IntArray>()
    fun numbering(y:Int, x:Int){
        queue.add(intArrayOf(y,x))
        connectedMap[y][x] = cComps
        for(i in 0 until 4){
            val nx = x + dx[i]
            val ny = y + dy[i]
            if(nx in 0 until width && ny in 0 until width && connectedMap[ny][nx] == 0){
                if(field[ny][nx]==1)numbering(ny,nx)
            }
        }
    }


    for(y in 0 until width){
        for(x in 0 until width){
            if(field[y][x]==1 && connectedMap[y][x] == 0){
                cComps += 1
                numbering(y,x)
            }
        }
    }
    // 여기까지는 동일했는데 나는 바운더리를 구해서 바운더리별로 BFS 를 돌렸고
    // 다른 분은 섬을 넘버링하면서 섬의 모든 포인트를 큐에 넣고 섬을 확장해가면서 굴렸음
    val distanceMap = Array(width){
        IntArray(width){0}
    }
    var minDistance = Int.MAX_VALUE
    // 섬을 확장해가면서 바다를 다 채울 때까지 BFS
    // 다른 섬과 만나면 다른 섬까지의 거리는 내가 확장한 거리 + 다른 섬이 확장한 거리
    while(queue.isNotEmpty()){
        for(i in 0 until queue.size){
            val current = queue.pollFirst()
            val curY = current[0]
            val curX = current[1]
            for(j in 0 until 4){
                val ny = curY + dy[j]
                val nx = curX + dx[j]
                if(ny in 0 until width && nx in 0 until width){
                    val target = connectedMap[ny][nx]
                    if(target == 0){
                        // 바다로 확장하는 경우 해당 위치를 재탐색해야하며, 거리를 표시한다
                        queue.add(intArrayOf(ny,nx))
                        distanceMap[ny][nx] = distanceMap[curY][curX] + 1
                        connectedMap[ny][nx] = connectedMap[curY][curX]
                    }
                    else if(target != connectedMap[curY][curX]){
                        // 바다가 아닌 다른(확장된) 섬 인 경우 해당 지점
                        minDistance = (distanceMap[curY][curX]+distanceMap[ny][nx]).coerceAtMost(minDistance)
                    }

                }
            }
        }
    }


    bw.write("$minDistance")
    bw.flush()
    bw.close()
    br.close()
}