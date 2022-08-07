import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val dx = arrayOf(0,0,1,-1)
    val dy = arrayOf(1,-1,0,0)
    val field = Array(5){
        val st = StringTokenizer(readLine())
        CharArray(5){
            st.nextToken().first()
        }
    }
    var stack = CharArray(6)
    val ans = mutableListOf<String>()
    fun dfs(depth:Int,visitingY:Int,visitingX:Int){
        stack[depth] = field[visitingY][visitingX]
        if(depth==5){
            val newStr = stack.joinToString("")
            if(newStr !in ans) ans.add(newStr)
            return
        }
        for(i in 0 until 4){
            val ny = visitingY + dy[i]
            val nx = visitingX + dx[i]
            if(ny in 0 until 5 && nx in 0 until 5){
                dfs(depth+1,ny,nx)
            }
        }
    }
    for(i in 0 until 5){
        for(j in 0 until 5){
            stack = CharArray(6)
            dfs(0,i,j)
        }
    }
    println(ans.size)

}