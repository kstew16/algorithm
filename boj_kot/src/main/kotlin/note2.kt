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
    val case = getInt()

    repeat(case){
        st = StringTokenizer(br.readLine())
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

        val stack = mutableListOf<Int>()
        var isPossible = true

        fun dfs(source:Int, sourcePaint:Int){
            stack.add(source)
            var myPaint = if(sourcePaint==1) 2 else 1
            visited[source] = myPaint
            if(isPossible){
                graph[source].forEach {
                    if(visited[it]==0) dfs(it, myPaint)
                    // 인접인데 같게 칠해진 것 발견!
                    else if(visited[it]==visited[source])isPossible = false
                }
            }
            stack.removeAt(stack.size-1)
        }

        graph.withIndex().forEach{
            if(visited[it.index]==0)dfs(it.index,1)
        }
        bw.write(if(isPossible)"YES\n" else "NO\n")
    }


    bw.flush()
    br.close()
    bw.close()
}