import java.io.BufferedReader
import java.io.InputStreamReader

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val (r,c) = readLine().split(" ").map{it.toInt()}
    val field = Array(r){readLine().toCharArray()}
    val visitedAlphabet = BooleanArray(26){false}
    val visited = Array(r){BooleanArray(c)}
    val dx = intArrayOf(1,0,-1,0)
    val dy = intArrayOf(0,1,0,-1)
    var maxDistance = 0
    fun dfs(y:Int,x:Int,depth:Int){
        val alphabetCode = field[y][x].code - 65
        visited[y][x] =  true
        visitedAlphabet[alphabetCode] = true
        maxDistance = depth.coerceAtLeast(maxDistance)
        for(i in 0 until 4){
            val ny = y + dy[i]
            val nx = x + dx[i]
            if(ny in 0 until r && nx in 0 until c && !visited[ny][nx] && !visitedAlphabet[field[ny][nx].code-65]){
                dfs(ny,nx,depth+1)
            }
        }
        visited[y][x] =  false
        visitedAlphabet[alphabetCode] = false
    }
    dfs(0,0,1)
    print(maxDistance)
}