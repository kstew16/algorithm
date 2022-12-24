import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val (m,k,n) = readLine().split(" ").map{it.toInt()}
    val hLines = Array(n){
        BooleanArray(m){false}
    }
    repeat(k){
        val st = StringTokenizer(readLine())
        hLines[st.nextToken().toInt()-1][st.nextToken().toInt()-1] = true
    }

    fun simulateSucceed():Boolean{
        for(i in 0 until m){
            var posX = i
            var posY = 0
            var lastX = -1
            while(posY<n){
                if(hLines[posY][posX] && lastX != posX+1) lastX = posX++
                else if(posX>0 && hLines[posY][posX-1] && lastX!=posX-1) lastX = posX--
                else {
                    lastX = -1
                    posY+=1
                }
            }
            if(posX!=i) return false
        }
        return true
    }

    fun placeLine(nLines:Int,placed:Int):Boolean{
        for(j in 0 until n){
            // 최우측에는 가로선을 설치할 수 없다
            for(i in 0 until m-1){
                if(!hLines[j][i]){
                    // 가로선은 연속할 수 없다
                    if(i>0 && hLines[j][i-1]) continue
                    hLines[j][i] = true
                    if(placed==nLines){
                        if(simulateSucceed())return true
                    }
                    else if(placeLine(nLines,placed+1)) return true
                    hLines[j][i] = false
                }
            }
        }
        return false
    }
    if(simulateSucceed()) print(0)
    else {
        for (i in 1..3) {
            if (i != 3) {
                if (placeLine(i, 1)) {
                    print(i)
                    break
                }
            } else {
                if (placeLine(i, 1)) print(i)
                else print(-1)
            }
        }
    }
}