import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

// 이거 폐기

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
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
    var apartmentCount: Dictionary<Int,Int>

    fun dfs(sourceNum:Int, x:Int=0, y:Int=0){
        // -1로 초기화된 visited 에 대해서
        // 아파트가 있으면 visited 를 단지 번호로 채우되
        if(field[y][x] == 1)
            visited[y][x] =
                if(sourceNum == 0){
                    // 확장 중이 아니었으면 새 단지로 채워주고
                    ++ numbering
                }
                // 확장중인 경우에는 확장중인 넘버로 채워줌
                else sourceNum
        // 아파트가 없으면 0으로 채움
        else visited[y][x] = 0

        var myNum = visited[y][x]
        if(x+1 < width){
            // 연결된 확장이 미방문 지점이면 내 색을 칠하러
            // 또는 연결된 확장의 단지 번호가 "존재하는 단지인" 나보다 크면 내 단지로 편입하러 출발
            if((visited[y][x+1] == -1) || (myNum in 1 until visited[y][x+1])) dfs(myNum, x+1, y)
            // 연결된 확장이 방문 지점 + 단지가 있는 지점인데 나보다 작은 넘버링이면 받아옴
            if(visited[y][x+1] in 1 until myNum) visited[y][x] = visited[y][x+1]
        }
        if(y+1 < width ){
            if((visited[y+1][x] == -1)||(myNum in 1 until visited[y+1][x])) dfs(myNum, x, y+1)
            if(visited[y+1][x] in 1 until myNum) visited[y][x] = visited[y+1][x]
        }
        if(x-1 >= 0){
            // 연결된 확장이 미방문 지점이면 내 색을 칠하러
            // 또는 연결된 확장의 단지 번호가 "존재하는 단지인" 나보다 크면 내 단지로 편입하러 출발
            if((visited[y][x-1] == -1) || (myNum in 1 until visited[y][x-1])) dfs(myNum, x-1, y)
            // 연결된 확장이 방문 지점 + 단지가 있는 지점인데 나보다 작은 넘버링이면 받아옴
            if(visited[y][x-1] in 1 until myNum) visited[y][x] = visited[y][x-1]
        }
        if(y-1 >= 0 ){
            if((visited[y-1][x] == -1)||(myNum in 1 until visited[y-1][x])) dfs(myNum, x, y-1)
            if(visited[y-1][x] in 1 until myNum) visited[y][x] = visited[y-1][x]
        }
    }

    dfs(0)
    visited.forEach {
        println(it.joinToString(" "))
    }
}