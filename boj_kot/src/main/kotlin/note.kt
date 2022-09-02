import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.LinkedList
import java.util.StringTokenizer

fun main()= BufferedReader(InputStreamReader(System.`in`)).run{
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    val n = st.getInt()
    val m = st.getInt()
    val checked = BooleanArray(n+1){false}.apply { this[0]=true}
    val smaller = Array(n+1){
        LinkedList<Int>()
    }
    val taller = Array(n+1){
        LinkedList<Int>()
    }
    repeat(m){
        st = StringTokenizer(readLine())
        val a = st.getInt()
        val b = st.getInt()
        smaller[b].add(a)
        taller[a].add(b)
    }
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    fun dfs(checking:Int){
        while(smaller[checking].isNotEmpty()){
            val target = smaller[checking].pollFirst()
            dfs(target)
        }
        bw.write(checking.toString())
    }
    dfs(1)
    bw.flush()
    bw.close()
    close()
}