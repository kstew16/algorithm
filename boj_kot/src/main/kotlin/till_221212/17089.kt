import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.math.min


fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val (n, m) = readLine().split(" ").map{it.toInt()}
    val friend = Array(n+1){
        mutableListOf<Int>()
    }
    repeat(m){
        val st = StringTokenizer(readLine())
        val a =st.nextToken().toInt()
        val b = st.nextToken().toInt()
        friend[a].add(b)
        friend[b].add(a)
    }
    val visited = BooleanArray(n+1){false}
    val stack = IntArray(3){0}
    var minAns = Int.MAX_VALUE

    fun dfs(me:Int,depth:Int){
        stack[depth-1] = me
        visited[me] = true
        if(depth==3){
            minAns = (stack.sumOf { friend[it].size }-6).coerceAtMost(minAns)
        }
        else{
            for(i in friend[me]){
                if(!visited[i]){
                    var valid = true
                    for(j in 0 until depth-1){
                        if(i !in friend[stack[j]]){
                            valid=false
                            break
                        }
                    }
                    if(valid) dfs(i,depth+1)
                }
            }
        }
        stack[depth-1]=0
        visited[me]= false
    }

    for(i in 1..n){
        dfs(i,1)
    }
    if(minAns == Int.MAX_VALUE) print(-1)
    else println(minAns)
}