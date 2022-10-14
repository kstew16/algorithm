import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val field = Array(100){BooleanArray(100) }
    repeat(n){
        val st = StringTokenizer(readLine())
        val l = st.nextToken().toInt()
        val d = st.nextToken().toInt()
        for(i in l..l+9) for(j in d..d+9) field[j][i] = true
    }
    fun maxExtensionArea(py:Int,px:Int):Int{
        val dy = 1
        val dx = 1
        var y = py
        var x:Int
        var maxArea = 1
        var minWidth = Int.MAX_VALUE
        var height = 0
        if(field[y][px]) {
            while (y in 1 until 100) {
                height += 1
                var width = 0
                x = px
                while (x in 1 until 100) {
                    if (field[y][x]) {
                        width += 1
                        x += dx
                    } else break
                }
                minWidth = width.coerceAtMost(minWidth)
                maxArea = (minWidth * height).coerceAtLeast(maxArea)
                y += dy
            }
        }
        return maxArea
    }
    var ans = 0
    for(i in 0 until 100) for(j in 0 until 100) if(field[j][i]) ans = maxExtensionArea(j,i).coerceAtLeast(ans)
    print(ans)
}