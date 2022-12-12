import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val (n,m,k) = readLine().split(" ").map{it.toInt()}
    val arr = Array(n){
        val st = StringTokenizer(readLine())
        IntArray(m){
            st.nextToken().toInt()
        }
    }
    val rotations = Array(k){
        readLine().split(" ").map{it.toInt()}.toIntArray()
    }

    // 시계방향으로 회전할 수 있도록
    val dx = intArrayOf(1,0,-1,0)
    val dy = intArrayOf(0,1,0,-1)

    val visited = BooleanArray(k){false}
    fun applyRotation(cmd:IntArray){
        val (r,c,s) = cmd
        if(s==0) return
        val startY = r-s-1
        val startX = c-s-1
        var rotatingCount = s*2

        var targetingCount = 0
        var curY = startY
        var curX = startX
        var targetY = curY + dy[targetingCount]
        var targetX = curX + dx[targetingCount]
        var tmp1:Int
        var tmp2 = arr[curY][curX]
        while(targetX!=startX || targetY!=startY){
            tmp1 = arr[targetY][targetX]
            arr[targetY][targetX] = tmp2
            tmp2 = tmp1

            if(rotatingCount > 1) rotatingCount-=1
            else{
                targetingCount+=1
                rotatingCount = 2*s
            }
            curY = targetY
            curX = targetX
            targetY = curY + dy[targetingCount]
            targetX = curX + dx[targetingCount]
        }
        arr[targetY][targetX] = tmp2
        applyRotation(intArrayOf(r-s+2,c-s+2,s-1))
    }
    fun cancelRotation(cmd:IntArray){
        val (r,c,s) = cmd
        if(s==0) return
        val startY = r-s-1
        val startX = c-s-1
        var rotatingCount = s*2
        var targetingCount = 1

        var curY = startY
        var curX = startX
        var targetY = curY + dy[targetingCount]
        var targetX = curX + dx[targetingCount]
        var tmp1:Int
        var tmp2 = arr[curY][curX]
        while(targetX!=startX || targetY!=startY){
            tmp1 = arr[targetY][targetX]
            arr[targetY][targetX] = tmp2
            tmp2 = tmp1

            if(rotatingCount > 1) rotatingCount-=1
            else{
                targetingCount -= 1
                if(targetingCount<0)targetingCount+=4
                rotatingCount = 2*s
            }
            curY = targetY
            curX = targetX
            targetY = curY + dy[targetingCount]
            targetX = curX + dx[targetingCount]
        }
        arr[targetY][targetX] = tmp2
        cancelRotation(intArrayOf(r-s+2,c-s+2,s-1))
    }
    fun calcVal():Int{
        var localMin = Int.MAX_VALUE
        for(i in arr.indices){
            localMin = (arr[i].sum()).coerceAtMost(localMin)
        }
        return localMin
    }
    var minVal = Int.MAX_VALUE
    fun dfs(visiting:Int, depth:Int){
        visited[visiting] = true
        applyRotation(rotations[visiting])
        // 여러 개의 rotation 연산을 순열로 접근하게 함
        if(depth == rotations.size){
            // 배열의 값 구하는 부분
            minVal = calcVal().coerceAtMost(minVal)
        }
        else{
            for(i in rotations.indices){
                if(!visited[i]) dfs(i,depth+1)
            }
        }
        visited[visiting] = false
        cancelRotation(rotations[visiting])
    }

    for(i in rotations.indices){
        dfs(i,1)
    }

    println(minVal)

}