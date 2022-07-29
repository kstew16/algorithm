import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Math.min
import java.util.*

// 이것도 폐기

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    var st = StringTokenizer(br.readLine())
    fun getInt() = st.nextToken().toInt()
    val width = getInt()
    val field = Array(width){
        br.readLine().chunked(1).map{it.toInt()}
    }
    val visited = Array(width){
        IntArray(width){-1}
    }

    var numbering = 0

    fun dfs(x:Int=0, y:Int=0):Int{
        // 내 자리에 아파트가 존재할 경우
        if(field[x][y] == 1){
            // 상하좌우에서 방문된+존재하는 단지가 있으면 끌고 옴
            val right = if(x+1 < width && visited[y][x+1]>0) visited[y][x+1] else 0
            val down = if(y+1 < width && visited[y+1][x]>0) visited[y+1][x] else 0
            val left = if(x-1 >= 0 && visited[y][x-1]>0) visited[y][x-1] else 0
            val up = if(y-1 >= 0 && visited[y-1][x]>0) visited[y-1][x] else 0
            // 상하좌우에서 방문된(>0) 단지가...
            with(intArrayOf(right,down,left,up).filter{ it>0 }){
                // 없어? 없으면 나는 새로운 단지
                visited[y][x] = if(this.isEmpty()) ++numbering
                // 있으면 최소값 가져오기
                else this.minOf { it }
            }
        }
        else visited[y][x] = 0
        // 나를 결정했으면 상하좌우 미방문지점 방문
        val r = if(x+1 < width && visited[y][x+1]<0) dfs(x+1,y) else 0
        val d = if(y+1 < width && visited[y+1][x]<0) dfs(x,y+1) else 0
        val l = if(x-1 >= 0 && visited[y][x-1]<0) dfs(x-1,y) else 0
        val u = if(y-1 >= 0 && visited[y-1][x]<0) dfs(x,y-1) else 0
        with(intArrayOf(r,d,l,u).filter{ it>0 }) {
            if(this.isNotEmpty()){
                // 나중에 미방문 지점에 더 작은 단지랑 연결된 곳이 있으면
                visited[y][x] = this.minOf { it }.coerceAtMost(visited[y][x])
            }
        }
        return visited[y][x]
    }

    dfs(0,0)
    visited.forEach {
        println(it.joinToString(" "))
    }
}