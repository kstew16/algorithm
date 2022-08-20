import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val visitable = Array(5){
        Array(4){
            Array(5){
                BooleanArray(5){false}
            }
        }
    }
    val mazeInfo = Array(5){
        // 몇 번 판넬의 몇 번 폼
        IntArray(2){0}
    }
    repeat(5){
        n ->
        for(j in 0 until 5){
            val st = StringTokenizer(readLine())
            for(i in 0 until 5){
                val block = st.nextToken() == "1"
                visitable[n][0][j][i] = block // 0 0 0 1 0 2 0 3 0 4
                visitable[n][1][i][4-j] = block // 0,4 1,4 2,4 3,4 4,4
                visitable[n][2][4-j][4-i] = block // 4,4 4,3 4,2, 4,1
                visitable[n][3][4-i][j] = block // 4,0 3,0 2,0 1,0
            }
        }
    }

    val visited = BooleanArray(5){false}
    val stack = IntArray(5){0}


    // 몇번 판넬의 몇 번 폼을 사용할 지 골라야 함 이거는 Info 에 저장이 될 텐데

    // 해당 Info에 대해서 Maze를 BFS로 탐색해야 할 듯

    // 2.그담에 이 각 판넬의 세부정보는 for문으로 반복하고,
    var minSteps = Int.MAX_VALUE
    fun assemble(){
        for(a in 0 until 4)
            for(b in 0 until 4)
                for(c in 0 until 4)
                    for(d in 0 until 4)
                        for(e in 0 until 4){
                            var level = 0
                            while(level<5){
                                mazeInfo[level][0] = stack[level]
                                mazeInfo[level][1] = when(level){
                                    0 -> a
                                    1 -> b
                                    2 -> c
                                    3 -> d
                                    4 -> e
                                    else -> 0
                                }
                                level += 1
                            }
                            val dx = intArrayOf(1,0,-1,0,0,0)
                            val dy = intArrayOf(0,-1,0,1,0,0)
                            val dz = intArrayOf(0,0,0,0,1,-1)
                            fun simulate():Int{
                                // 출발지나 도착지가 막혀있는 경우 가지치기
                                val visited3D = Array(5){
                                    Array(5){
                                        BooleanArray(5){false}
                                    }
                                }

                                if(!visitable[mazeInfo[0][0]][mazeInfo[0][1]][0][0]) return Int.MAX_VALUE
                                else if(!visitable[mazeInfo[4][0]][mazeInfo[4][1]][4][4]) return Int.MAX_VALUE
                                else{
                                    // x,y,z
                                    val queue = LinkedList<IntArray>().apply {
                                        visited3D[0][0][0] = true
                                        add(intArrayOf(0,0,0,0))
                                    }
                                    while(queue.isNotEmpty()){
                                        val (cx,cy,cz,step) = queue.pollFirst()
                                        if((step+1) >= minSteps) return Int.MAX_VALUE
                                        for(direction in 0 until 6){
                                            val nx = cx + dx[direction]
                                            val ny = cy + dy[direction]
                                            val nz = cz + dz[direction]
                                            if(nx in 0 until 5 && ny in 0 until 5 && nz in 0 until 5){
                                                if(nx == 4 && ny == 4 && nz == 4) return step+1
                                                if(visitable[mazeInfo[nz][0]][mazeInfo[nz][1]][ny][nx] && !visited3D[nx][ny][nz]){
                                                    visited3D[nx][ny][nz] = true
                                                    queue.add(intArrayOf(nx,ny,nz,step+1))
                                                }
                                            }
                                        }
                                    }
                                }
                                return Int.MAX_VALUE
                            }
                            minSteps = simulate().coerceAtMost(minSteps)
                        }
    }
    // 1.일단 01234 를 모든 가능한 순서로 배치하는 게 있음
    fun makeCombDFS(visiting:Int,depth:Int){
        visited[visiting] = true
        stack[depth] = visiting
        if(depth==4){
            //println(stack.joinToString(" "))
            assemble()
        }
        else{
            for(i in 0 until 5){
                if(!visited[i]) makeCombDFS(i,depth+1)
            }
        }
        visited[visiting] = false
        stack[depth] = 0
    }
    for(i in 0 until 5){
        makeCombDFS(i,0)
    }
    print(if(minSteps==Int.MAX_VALUE)-1 else minSteps)
}