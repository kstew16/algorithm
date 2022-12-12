import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    var st = StringTokenizer(readLine())
    fun getInt() = st.nextToken().toInt()
    val (n,l,r,x) = List(4){ getInt()}
    st = StringTokenizer(readLine())
    val problems = IntArray(n){ getInt() }.apply { this.sort()}
    var count = 0
    fun dfs(depth:Int,sourceIndex:Int,sum:Int,min:Int){
        if(depth>=2 && sum in l..r && problems[sourceIndex]-min >= x ) count++
        for(i in sourceIndex+1 until n) dfs(depth+1,i,sum+problems[i],min)
    }
    problems.withIndex().forEach {dfs(1,it.index,it.value,it.value)}
    println(count)
}