import java.io.BufferedReader
import java.io.InputStreamReader

// 한 시간 걸림 이유 : Yes 말고 YES 출력하고 있었음 : 하... 하....하.......

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (n,m) = br.readLine().split(" ").map{it.toInt()}
    val graph = Array(n){
        br.readLine().chunked(1)
    }

    val dx = arrayOf(0, 0, 1, -1)
    val dy = arrayOf(1, -1, 0, 0)

    val visited = Array(n){
        BooleanArray(m){false}
    }

    var found = false
    for(i in 0 until n){
        if(found)break
        for(j in 0 until m){
            if(found)break
            else if(!visited[i][j]){
                val sourceType = graph[i][j]

                fun dfs(source:IntArray, current:IntArray){
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
                                } else dfs(current, intArrayOf(ny,nx))
                            }
                        }
                    }
                }
                dfs(source = intArrayOf(-1,-1), current = intArrayOf(i,j))
            }

        }
    }

    println(if(found) "Yes" else "No")

}
