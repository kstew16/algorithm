import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Math.min

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    val (n,m) = br.readLine().split(" ").map{it.toInt()}
    val haveToFill = Array(n){
        BooleanArray(m){false}
    }
    val  visited = Array(n){
        BooleanArray(m){false}
    }
    for(i in 0 until n){
        val line = br.readLine().chunked(1)
        for(j in 0 until m){
            haveToFill[i][j] = line[j] != "."
            visited[i][j] = line[j] == "."
        }
    }

    val stack = mutableListOf<IntArray>()
    fun spreadFill(maxSize:Int,y:Int,x:Int){
        var valid = false
        var validSize = 0
        for(spread in 1..maxSize){
            // 십자가가 들어갈 수 없는 공간이 있으면 채우기 중단
            if(!haveToFill[y+spread][x] || !haveToFill[y-spread][x] || !haveToFill[y][x+spread] || !haveToFill[y][x-spread]){
                break
            }
            else if(!visited[y+spread][x] || !visited[y-spread][x] || !visited[y][x+spread] || !visited[y][x-spread]){
                valid = true
                validSize = spread
            }
        }
        if(valid) {
            for(i in 1..validSize){
                visited[y+i][x] = true
                visited[y-i][x] = true
                visited[y][x+i] = true
                visited[y][x-i] = true
            }
            visited[y][x] = true
            stack.add(intArrayOf(y+1,x+1,validSize))
        }
        return
    }
    for(i in 0 until n){
        for(j in 0 until m){
            if(haveToFill[i][j]) {
                val minWidth = j.coerceAtMost(m - j - 1)
                val minHeight = i.coerceAtMost(n - i - 1)
                val spreadingSize = minWidth.coerceAtMost(minHeight)
                if(spreadingSize>0)spreadFill(spreadingSize,i,j)
            }
        }
    }
    var fulfilled = true
    for(i in 0 until n){
        for(j in 0 until m){
            fulfilled = visited[i][j]&&fulfilled
        }
    }

    if(stack.size==0 || !fulfilled){
        bw.write("-1")
    }
    else{
        bw.write("${stack.size}\n")
        stack.forEach {
            bw.write(it.joinToString(" ")+"\n")
        }
    }
    bw.flush()
    bw.close()
    br.close()
}