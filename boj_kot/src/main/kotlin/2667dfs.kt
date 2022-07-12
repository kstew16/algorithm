import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

// 4시간 걸린 이유 : 여러 그래프를 한 개의 dfs 재귀로 탐색하려고 하는 해괴망측한 짓을 저지름
// 그래서 서로 분리된 그래프를 반복문을 통해 별개의 dfs 를 진행함에 따라 해결됨. -> 30분 걸림

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
        IntArray(width){0}
    }

    var cComps = 0
    var count = 0

    fun dfs(y:Int, x:Int){
        visited[y][x] = cComps
        count ++

        var childList = mutableListOf<IntArray>()
        if(x+1<width) childList.add(intArrayOf(y,x+1))
        if(y+1<width) childList.add(intArrayOf(y+1,x))
        if(x-1>=0) childList.add(intArrayOf(y,x-1))
        if(y-1>=0) childList.add(intArrayOf(y-1,x))

        childList.forEach {
            val b = it[0]
            val a = it[1]
            if(visited[b][a] == 0 && field[b][a] == 1){
                dfs(b, a)
            }
        }
    }

    var elements = mutableListOf<Int>()
    // 이거 반복문으로 미방문 좌표에 대해서 돌게 바꿔
    //dfs(0,0, 0)
    for(y in 0 until width){
        for(x in 0 until width){
            if(field[y][x]==1 && visited[y][x] == 0){
                count = 0
                cComps += 1
                dfs(y,x)
                elements.add(count)
            }
        }
    }

    bw.write("$cComps\n")
    elements.sorted().forEach{
        bw.write("$it\n")
    }
    bw.flush()
    bw.close()
    br.close()
}