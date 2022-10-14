import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val field = Array(100){BooleanArray(100) }
    data class Point(val y:Int, val x:Int)
    // dl dr ul ur
    val edge = Array(4){ mutableListOf<Point>() }
    val visited = Array(4){Array(100){BooleanArray(100)} }
    repeat(n){
        val st = StringTokenizer(readLine())
        val l = st.nextToken().toInt()
        val d = st.nextToken().toInt()
        val r = l + 9
        val u = d + 9

        for(i in l..r) for(j in d..u){
            when{
                (i==l) -> {
                    edge[0].add(Point(j,i))
                    edge[2].add(Point(j,i))
                }
                (i==r) -> {
                    edge[1].add(Point(j,i))
                    edge[3].add(Point(j,i))
                }
                (j==d) -> {
                    edge[0].add(Point(j,i))
                    edge[1].add(Point(j,i))
                }
                (j==u) -> {
                    edge[2].add(Point(j,i))
                    edge[3].add(Point(j,i))
                }
            }
            field[j][i] = true
        }
    }
    // dl dr ul ur 의 확장 방향 (ur,ul,dr,dl)
    val dirY = intArrayOf(1,1,-1,-1)
    val dirX = intArrayOf(1,-1,1,-1)
    fun maxExtensionArea(p:Point,dir:Int):Int{
        val dy = dirY[dir]
        val dx = dirX[dir]
        var y = p.y
        var x:Int
        var maxArea = 1
        var minWidth = Int.MAX_VALUE
        var height = 0
        if(field[y][p.x]){
            while(y in 1 until 100){
                height += 1
                var width = 0
                x = p.x
                while(x in 1 until 100){
                    if(field[y][x]){
                        width += 1
                        x += dx
                    }
                    else break
                }
                minWidth = width.coerceAtMost(minWidth)
                maxArea = (minWidth*height).coerceAtLeast(maxArea)
                y+=dy
            }
        }
        return maxArea
    }
    var ans = 0
    //for(i in 0 until 4) edge[i].forEach { ans = maxExtensionArea(it,i).coerceAtLeast(ans)}
    for(i in 0 until 4) edge[i].forEach { if(!visited[i][it.y][it.x]) {
        visited[i][it.y][it.x] = true
        ans = maxExtensionArea(it,i).coerceAtLeast(ans)}
    }
    print(ans)
    //for(i in 0 until 100) for(j in 0 until 100) if(field[j][i]) ans = maxExtensionArea(Point(j,i),0).coerceAtLeast(ans)
    //print(ans)
}