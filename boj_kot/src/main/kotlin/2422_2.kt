import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

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
    val stack = mutableListOf<Int>()
    var count = 0
    fun dfs(visiting:Int,depth:Int){
        visited[visiting] = true
        stack.add(visiting)
        if(depth==3){
            var valid = true
            stack.forEach {
                included ->
                doNotComb[included].forEach {
                    forbidden->
                    if(forbidden in stack) valid = false
                    if(!valid) {
                        stack.remove(visiting)
                        visited[visiting]= false
                        return
                    }
                }
            }
            count += 1
        }
        else{
            for(i in visiting+1..n){
                if(!visited[i]){
                    dfs(i,depth+1)
                }
            }
        }
        stack.remove(visiting)
        visited[visiting]= false
    }

    for(i in 1..n){
        dfs(i,1)
    }
    println(count)

}