import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    val n = readLine().toInt()
    val tree = Array<MutableList<Int>>(n){
        mutableListOf()
    }
    repeat(n-1){
        val (a,b)=readLine().split(" ").map{it.toInt()-1}
        tree[a].add(b)
        tree[b].add(a)
    }
    val parent = IntArray(n){0}
    val visited = BooleanArray(n){false}

    fun dfs(source:Int,visiting:Int){
        parent[visiting] = source
        visited[visiting] = true
        tree[visiting].forEach {
            if(!visited[it]) dfs(visiting,it)
        }
    }
    dfs(0,0)
    for(i in 1 until n){
        bw.write("${parent[i]+1}\n")
    }
    bw.flush()
    bw.close()
    close()
}