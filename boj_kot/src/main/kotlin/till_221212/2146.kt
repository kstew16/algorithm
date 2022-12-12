import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*
import kotlin.collections.ArrayList
// 이 풀이에서 나는 다른 컴포넌트와의 최소 거리를 구하기 위해 각 컴포넌트 경계지점에서 다른 컴포넌트까지의 거리를 측정했는데
// 그럴 필요 없이 각 컴포넌트를 주위로 한 칸씩 확장해나가면서 만났을 경우 두 개의 확장량을 더하면 그게 거리더라
// 결과적으로 연산의 수와 큐잉의 개수가 1/경계노드 수 로 줄어들을 수 있었음

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
        IntArray(width){-1}
    }
    val queued = Array(width){
        IntArray(width){0}
    }
    val boundary = ArrayList<IntArray>()

    val dx = arrayOf(1,0,-1,0)
    val dy = arrayOf(0,1,0,-1)

    var cComps = 0

    fun dfs(y:Int, x:Int){
        connectedMap[y][x] = cComps
        for(i in 0 until 4){
            val nx = x + dx[i]
            val ny = y + dy[i]
            if(nx in 0 until width && ny in 0 until width && connectedMap[ny][nx] == -1){
                if(field[ny][nx]==1)dfs(ny,nx)
                else if(1!=queued[y][x]){
                    queued[y][x] = 1
                    boundary.add(intArrayOf(y,x))
                }
            }

        }
    }

    for(y in 0 until width){
        for(x in 0 until width){
            if(field[y][x]==1 && connectedMap[y][x] == -1){
                cComps += 1
                dfs(y,x)
            }
        }
    }


    var minDistance = Int.MAX_VALUE
    boundary.forEach {
        // 각 경계면에서 BFS 를 돌아 가장 가까운 섬과의 거리 측정
        source->
        var localMinDistance = Int.MAX_VALUE
        // 탐색에 쓸 자료들
        val distanceMap = Array(width){
            IntArray(width){-1}
        }

        val queue = Array(width*width*4){ intArrayOf(-1,-1) }
        var bottom = 0
        var top = 0

        val y = source[0]
        val x = source[1]
        val sourceLand = connectedMap[y][x]
        distanceMap[y][x] = 0
        queue[top++] = intArrayOf(y,x)


        while(top!=bottom){
            val target =queue[bottom++]
            val tY = target[0]
            val tX = target[1]
            for(i in 0 until 4){
                val nx = tX + dx[i]
                val ny = tY + dy[i]
                // 아직 거리를 측정하지 않은 지역에 대해서
                if(nx in 0 until width && ny in 0 until width && distanceMap[ny][nx] == -1){
                    if(connectedMap[ny][nx] == -1){
                        // 바다 지점을 측정할 시
                        queue[top++] = intArrayOf(ny,nx)
                        distanceMap[ny][nx] = distanceMap[tY][tX] + 1
                    }
                    else if(connectedMap[ny][nx] != sourceLand){
                        // 육지 중 다른 섬을 측정할 시
                        localMinDistance = (distanceMap[tY][tX]).coerceAtMost(localMinDistance)
                    }
                }
            }

        }
        minDistance = localMinDistance.coerceAtMost(minDistance)
    }
    bw.write("$minDistance")

    bw.flush()
    bw.close()
    br.close()
}