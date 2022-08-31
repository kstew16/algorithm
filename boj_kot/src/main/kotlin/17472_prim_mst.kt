import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList
import java.util.StringTokenizer

// 그래프의 각 점에서 바다가 주변에 있다면 해당 방향의 바다로 계속 진행하여, 바다를 만난 경우에만 경로 길이를 포함하여 그래프화
// 이후 그래프의 MST(최소 신장 트리) 를 Prim MST (정점 집합 연결) 알고리즘을 통해 구함

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
            // type!=boundary.type 이거 빠져있었음
            if(type != 0 && type!=boundary.type && distance>1) worldMap[boundary.type-1][type-1] = distance.coerceAtMost(worldMap[boundary.type-1][type-1])
        }
    }
    for(j in 0 until nIsland){
        worldMap[j][j] = 0
    }

    // 연결 안 된 섬 있으면 불가로 판정
    val islandVisited = BooleanArray(nIsland){false}
    fun visitAll(visiting:Int){
        islandVisited[visiting] = true
        for(i in 0 until nIsland){
            if(!islandVisited[i] && worldMap[visiting][i] != INF) visitAll(i)
        }
    }
    visitAll(0)
    if(islandVisited.any { !it }){
        print(-1)
        close()
        return
    }

    // 출발 노드가 0 인 Prim MST
    val connected = BooleanArray(nIsland){false}.apply { this[0] = true }
    val connectedMap = worldMap[0]
    var usedCost = 0

    while(connected.any { !it }){
        var closestDistance= INF
        var closestIndex = 0
        // 연결할 수 있는 섬 중 가장 적은 비용으로 연결 가능한 섬 선택
        for(i in 0 until nIsland){
            if(!connected[i] && connectedMap[i]<closestDistance){
                closestDistance = connectedMap[i]
                closestIndex = i
            }
        }
        // 가장 가까운 섬과 연결
        connected[closestIndex] = true
        connectedMap[closestIndex] = 0
        usedCost +=  closestDistance
        for(i in 0 until nIsland) connectedMap[i] = (worldMap[closestIndex][i]).coerceAtMost(connectedMap[i])
    }

    print(usedCost)
}