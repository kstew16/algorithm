import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList
import java.util.StringTokenizer

// 그래프의 각 점에서 바다가 주변에 있다면 해당 방향의 바다로 계속 진행하여, 바다를 만난 경우에만 경로 길이를 포함하여 그래프화
// 최단 경로만이 입력된 그래프에서 DFS 를 통한 완전 탐색, 경로 최소 경로의 백트래킹 실시 후 출력

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val (n,m) = readLine().split(" ").map { it.toInt() }
    val world = Array(n){
        val st = StringTokenizer(readLine())
        IntArray(m){
            st.nextToken().toInt()
        }
    }
    val visited = Array(n){
        BooleanArray(m){
            false
        }
    }
    data class Boundary(val y:Int,val x:Int,val direction: Int,val type:Int)
    val boundaryQueue = LinkedList<Boundary>()
    val dx = intArrayOf(1,0,-1,0)
    val dy = intArrayOf(0,1,0,-1)
    fun connectedComps(vy:Int,vx:Int,label:Int){
        world[vy][vx] = label
        for(i in 0 until 4){
            val ny = vy + dy[i]
            val nx = vx + dx[i]
            if(ny in 0 until n && nx in 0 until m && !visited[ny][nx]){
                when(world[ny][nx]){
                    1 -> {
                        visited[ny][nx] = true
                        connectedComps(ny,nx,label)
                    }
                    0 -> boundaryQueue.add(Boundary(vy,vx,i,label))
                }
            }
        }
    }
    var nIsland = 0
    for(j in 0 until n){
        for(i in 0 until m){
            if(!visited[j][i] && world[j][i] != 0) {
                visited[j][i] = true
                connectedComps(j,i,++nIsland)
            }
        }
    }

    val INF = 987654321
    // 이제 최단거리만으로 다리의 후보를 선정할 차례례
    val worldMap = Array(nIsland) {
        IntArray(nIsland){INF}
    }
    while(boundaryQueue.isNotEmpty()){
        val boundary = boundaryQueue.pollFirst()
        val directionX = dx[boundary.direction]
        val directionY = dy[boundary.direction]
        var ny = boundary.y + directionY
        var nx = boundary.x + directionX
        var distance = 0
        while(world[ny][nx]==0){
            distance+=1
            ny += directionY
            nx += directionX
            if(ny !in 0 until n || nx !in 0 until m) break
            val type = world[ny][nx]
            if(type != 0 && distance>1) worldMap[boundary.type-1][type-1] = distance.coerceAtMost(worldMap[boundary.type-1][type-1])
        }
    }

    // 모든 다리 후보가 작성되었으니, 섬을 방문하는 모든 경우를
    // 와 이게 DFS 로 가면 한 섬의 여러 다리를 쓰는 경우가 반영이 안 돼
    // Combination 으로 다리를 쓰는 경우의 수조차도 넣어야하나
    var minDistance = Int.MAX_VALUE
    val islandVisited = BooleanArray(nIsland){false}
    fun visitIsland(source:Int, visiting:Int, distance:Int,visitCount:Int){
        islandVisited[visiting] = true
        if(visitCount==nIsland){
            if(islandVisited.any { !it }) println("unexpected case")
            minDistance =distance.coerceAtMost(minDistance)
        }
        for(i in 0 until nIsland){
            if(!islandVisited[i]){
                val d = worldMap[visiting][i]
                if(d!=INF){
                    //used = true
                    visitIsland(visiting, i,distance+d,visitCount+1)
                }
            }
        }
        if(source!= -1) visitIsland(-1,source,distance,visitCount)
        //islandVisited[visiting] = false
    }
    visitIsland(-1, 0,0,1)
    if(minDistance == Int.MAX_VALUE) print(-1)
    else print(minDistance)
}