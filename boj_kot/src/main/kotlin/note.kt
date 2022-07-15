import java.io.BufferedReader
import java.io.InputStreamReader

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (n,m) = br.readLine().split(" ").map{it.toInt()}
    val graph = Array(n){
        br.readLine().chunked(1)
    }

    val stack = mutableListOf<IntArray>()
    val dx = arrayOf(0, 0, 1, -1)
    val dy = arrayOf(1, -1, 0, 0)

    val visited = Array(n){
        BooleanArray(m){false}
    }

    // 각 좌표별 알파벳을 소스로 설정하고 해당 소스와 연결된 부분을 탐색하다 소스와 같은데 이미 visited 된 곳을 만나면 found
    // 단 점 종류 이동시마다 visited 는 초기화되어야 함

    var found = false
    for(i in 0 until n){
        if(found)break
        for(j in 0 until m){
            if(found)break
            else if(!visited[i][j]){
                val sourceType = graph[i][j]
                stack.add(intArrayOf(i,j))
                visited[i][j]=true

                fun dfs(source:IntArray){
                    if(stack.isNotEmpty()){
                        val current = stack.removeAt(stack.size-1)
                        val (currentY,currentX) = current
                        visited[currentY][currentX] = true
                        for(i in 0 until 4){
                            val nx = currentX + dx[i]
                            val ny = currentY + dy[i]
                            if(source[0] != ny || source[1] != nx) {
                                // 내가 온 지점은 볼 필요 없음
                                if (nx in 0 until m && ny in 0 until n && graph[ny][nx] == sourceType) {
                                    if (visited[ny][nx]) {
                                        // 이미 방문된 같은 종류 지점을 만난 것은 사이클이 생긴 것
                                        found = true
                                        break
                                    } else {
                                        stack.add(intArrayOf(ny, nx))
                                        visited[ny][nx] = true
                                        dfs(current)
                                    }
                                }
                            }
                        }
                    }
                }
                dfs(source = intArrayOf(-1,-1))
            }

        }
    }

    println(if(found) "YES" else "NO")

}
