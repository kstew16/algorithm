import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
import kotlin.math.abs
fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val (xs,ys) = readLine().split(" ").map { it.toInt() }
    val (xe,ye) = readLine().split(" ").map { it.toInt() }
    fun StringTokenizer.getInt() = nextToken().toInt()
    val possible = mutableListOf<IntArray>()

    // 의미 있는 텔레포트만 텔레포트로 침
    repeat(3){
        val st = StringTokenizer(readLine())
        val tsx = st.getInt()
        val tsy = st.getInt()
        val tex = st.getInt()
        val tey = st.getInt()
        if((abs(tsx-tex)+abs(tsy-tex))>10)possible.add(intArrayOf(tsx,tsy,tex,tey))
    }

    val visited = Array(1000000001){
        BooleanArray(1000000001){false}
    }
    val minDepth = abs(xe-xs) + abs(ye-ys)

    fun dfs(curX:Int,curY:Int,depth:Int){
        // 한 번 온 곳을 다시 갈 필요는 없음
        visited[curY][curX] = true
        // 쓸모 없는 탐색 차단
        if((curX!=xe || curY!=ye) && depth+1>=minDepth) return

    }


}