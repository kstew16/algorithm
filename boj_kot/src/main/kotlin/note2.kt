import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Math.min
import java.util.*

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
    var apartmentCount = hashMapOf<Int,Int>()

    fun dfs(x:Int=0, y:Int=0, sourceComp:Int):Int{
        // 내 자리에 아파트가 존재할 경우
        if(field[y][x] == 1){
            var myComp = Int.MAX_VALUE
            // 상하좌우의 존재하는 단지를 확인, 방문한 적 있으면 데려옴(나중에 비교용)
            val right =
                if(x+1 < width && field[y][x+1]==1 && visited[y][x+1] > 0)visited[y][x+1]
                else 0
            if(right in 1 until myComp) myComp = right

            val down =
                if(y+1 < width && field[y+1][x]==1 && visited[y+1][x] > 0) visited[y+1][x]
                else 0
            if(down in 1 until myComp) myComp = down

            val left =
                if(x-1 >= 0 && field[y][x-1]==1 && visited[y][x-1] > 0) visited[y][x-1]
                else 0
            if(left in 1 until myComp) myComp = left

            val up =
                if(y-1 >= 0 && field[y-1][x]==1 && visited[y-1][x] > 0) visited[y-1][x]
                else 0
            if(up in 1 until myComp) myComp = up

            // 주변에 다 봤는데 단지 매겨진 거 없네
            // 그럼 부모거 쓰고 부모거도 없으면 내가 넘버링
            if(myComp == Int.MAX_VALUE)
                visited[y][x] =
                    if(sourceComp != 0) sourceComp else ++numbering
            else
                visited[y][x] =
                    if(sourceComp in 1 until myComp) sourceComp else myComp
        }
        // 내 자리에 단지가 없으면 일단 방문표시
        else visited[y][x] = 0

        // 여기까지 왔으면 주변에 의해서 나의 visited는 결정되어 있으나,
        var myComp = visited[y][x]
        if(x+1 < width && visited[y][x+1] < 0) dfs(x+1,y, myComp)
        else if(y+1 < width && visited[y+1][x] < 0) dfs(x,y+1, myComp)
        else if(x-1 >= 0 && visited[y][x-1] < 0) dfs(x-1,y, myComp)
        else if(y-1 >= 0 && visited[y-1][x] < 0) dfs(x,y-1, myComp)
        return visited[y][x]
    }

    dfs(0,0, 0)

    visited.forEach {
        println(it.joinToString(" "))
    }

    var count = 0
    visited.forEach{ array ->
        array.forEach {
            if(it>0) {
                if (it in apartmentCount.keys) {
                    apartmentCount[it] = apartmentCount[it]!! + 1
                } else {
                    count++
                    apartmentCount[it] = 1
                }
            }
        }
    }
    bw.write("$count\n")
    bw.write(apartmentCount.values.sorted().joinToString("\n"))
    bw.flush()
    bw.close()
    br.close()
}