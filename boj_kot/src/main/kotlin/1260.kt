import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*
// dfs는 자신 집어넣고 -> (미방문)자식 방문(재귀) -> 자신 pop - 방문 first
// bfs는 (미방문)자식 집어넣고 -> 먼저 집어넣은 것부터 방문 - 집어넣기 first
fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    var st = StringTokenizer(br.readLine())
    fun getInt() = st.nextToken().toInt()
    val vertex = getInt()
    val edge = getInt()
    val start = getInt()
    val graph = MutableList<MutableList<Int>>(vertex){mutableListOf()}
    val visited = BooleanArray(vertex){false}


    repeat(edge){
        st = StringTokenizer(br.readLine())
        val arg1  = getInt()
        val arg2 = getInt()
        graph[arg1-1].add(arg2-1)
        graph[arg2-1].add(arg1-1)
    }
    graph.forEach {
        it.sort()
    }

    val stackOrQueue = mutableListOf<Int>()
    fun dfs(source:Int){
        stackOrQueue.add(source)
        visited[source] = true
        bw.write("${source+1} ")
        graph[source].forEach {
            if(!visited[it]) dfs(it)
        }
        stackOrQueue.removeAt(stackOrQueue.size-1)
    }

    fun bfs(source:Int){
        visited[source] = true
        bw.write("${source+1} ")
        graph[source].forEach {
            if(!visited[it]) stackOrQueue.add(it)
        }
        while(stackOrQueue.isNotEmpty()){
            val target = stackOrQueue.removeAt(0)
            if(!visited[target]) bfs((target))
        }
    }

    dfs(start-1)
    for(i in 0 until vertex){
        visited[i] = false
    }
    stackOrQueue.clear()
    bw.write("\n")
    bw.flush()

    bfs(start-1)
    bw.flush()

    br.close()
    bw.close()
}