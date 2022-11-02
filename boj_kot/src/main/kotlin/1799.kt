import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    val n = st.getInt()
    val canPlace = Array(n){
        st = StringTokenizer(readLine())
        BooleanArray(n){
            st.getInt() == 1
        }
    }
    var maxDepth = 0
    val dx = intArrayOf(1,1,-1,-1)
    val dy = intArrayOf(1,-1,-1,1)
    data class Point(var y:Int,var x:Int)
    fun dfs(y:Int,x:Int,depth:Int){
        canPlace[y][x] = false
        //val changed = Array(n){BooleanArray(n)}
        val changedCoord = Array(n*2){Point(-1,-1)}
        var changedCount = 0
        l1@ for(j in 0 until 4){
            for(i in 1 until n){
                val ny = y + dy[j]*i
                val nx = x + dx[j]*i
                if(ny !in 0 until n || nx !in 0 until n) continue@l1
                else if(canPlace[ny][nx]){
                    val p = changedCoord[changedCount++]
                    p.x = nx
                    p.y = ny
                    canPlace[ny][nx] = false
                }
            }
        }
        maxDepth = depth.coerceAtLeast(maxDepth)
        for(j in 0 until n) {
            for (i in 0 until n) {
                if (canPlace[j][i]) {
                    dfs(j, i, depth + 1)
                }
            }
        }
        /*
        for(j in 0 until n) {
            for (i in 0 until n) {
                if (changed[j][i]) {
                    canPlace[y][x] = true
                }
            }
        }
         */
        while(changedCount>0){
            changedCount-=1
            val p = changedCoord[changedCount]
            canPlace[p.y][p.x] = true
        }
        canPlace[y][x] = true
    }

    for(j in 0 until n){
        for(i in 0 until n){
            if(canPlace[j][i]){
                dfs(j,i,1)
            }
        }
    }
    print(maxDepth)
}