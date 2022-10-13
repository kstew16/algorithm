import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val field = Array(100){BooleanArray(100) }
    data class Point(val y:Int, val x:Int)
    // dl dr ul ur
    val edge = Array(4){ mutableListOf<Point>() }
    repeat(n){
        val st = StringTokenizer(readLine())
        val l = st.nextToken().toInt()
        val d = st.nextToken().toInt()
        val r = l + 9
        val u = d + 9
        if(!field[d][l]) edge[0].add(Point(d,l))
        if(!field[d][r]) edge[1].add(Point(d,r))
        if(!field[u][l]) edge[2].add(Point(u,l))
        if(!field[u][r]) edge[3].add(Point(u,r))
        for(i in l..r) for(j in d..u) field[j][i] = true
    }
    // dl dr ul ur 의 확장 방향
    val dirY = intArrayOf(1,1,-1,-1)
    val dirX = intArrayOf(1,-1,1,-1)
    fun maxExtensionArea(p:Point,dir:Int):Int{
        val dy = dirY[dir]
        val dx = dirX[dir]
        var y = p.y
        var x = p.x
        var maxArea = 1
        var minWidth = Int.MAX_VALUE
        while(y in 0 until 100 && field[y][p.x]){
            var width = -1
            x = p.x
            while(x in 0 until 100){
                if(field[y][x]) x += dx
                else{
                    width = kotlin.math.abs(x-dx-p.x)+1
                    break
                }
            }
            // 끝까지 차있었다는 소리
            if(width == -1) width = kotlin.math.abs(x-dx-p.x)+1
            minWidth = width.coerceAtMost(minWidth)
            maxArea = (minWidth*(kotlin.math.abs(y-p.y)+1)).coerceAtLeast(maxArea)
            y+=dy
        }
        return maxArea
    }
    var ans = 0
    for(i in 0 until 4) edge[i].forEach { ans = maxExtensionArea(it,i).coerceAtLeast(ans)}
    print(ans)
}