import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    var st = StringTokenizer(br.readLine())
    fun getInt() = st.nextToken().toInt()
    val vertex = getInt()
    val edge = getInt()
    val graph = MutableList<MutableList<Int>>(vertex){mutableListOf()}
    val visited = IntArray(vertex){0}

    repeat(edge){
        st = StringTokenizer(br.readLine())
        val arg1  = getInt()
        val arg2 = getInt()
        graph[arg1-1].add(arg2-1)
        graph[arg2-1].add(arg1-1)
    }

    val stackOrQueue = mutableListOf<Int>()
    var cComps = 0
    fun dfs(source:Int, component:Int){
        stackOrQueue.add(source)
        visited[source] = if(component==0) ++cComps else component
        graph[source].forEach {
            if(visited[it]==0) dfs(it,visited[source])
        }
        stackOrQueue.removeAt(stackOrQueue.size-1)
    }

    graph.withIndex().forEach {
        if(visited[it.index] == 0) dfs(it.index, 0)
    }
    bw.write(cComps.toString())
    bw.flush()
    br.close()
    bw.close()
}