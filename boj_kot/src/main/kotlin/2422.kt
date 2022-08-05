import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*


fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val (n, m) = readLine().split(" ").map{it.toInt()}
    val doNotComb = Array(n+1){
        mutableListOf<Int>()
    }
    repeat(m){
        val st = StringTokenizer(readLine())
        val a =st.nextToken().toInt()
        val b = st.nextToken().toInt()
        doNotComb[a].add(b)
        doNotComb[b].add(a)
    }
    val visited = BooleanArray(n+1){false}
    val forbid = IntArray(n+1){0}
    var count = 0

    fun dfs(visiting:Int,depth:Int){
        visited[visiting] = true
        doNotComb[visiting].forEach {
            forbid[it] += 1
        }
        if(depth==3){
            count += 1
        }
        else{
            for(i in visiting+1..n){
                if(!visited[i] && forbid[i]==0){
                    dfs(i,depth+1)
                }
            }
        }
        doNotComb[visiting].forEach {
            forbid[it] -= 1
        }
        visited[visiting]= false
    }

    for(i in 1..n){
        dfs(i,1)
    }
    println(count)
    Runtime.getRuntime().gc()
    val usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
    print("$usedMemory bytes")
}