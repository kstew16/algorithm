import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
// 답 보고도 푸는데 한참 걸려서 죽을 뻔 했다 ^^ 다시풀러왔냐? 참고로 이문제 완성코드 이쁨(literally)^^ '참고'해라~~
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
    val maxDepth = IntArray(2)
    val diagonalVisitedN = BooleanArray(2*(n-1)+1) // x-y + (n-1) 가 같은 좌표 들의 음의 기울기 대각선
    val diagonalVisitedP = BooleanArray(2*(n-1)+1) // x+y 가 같은 좌표들의 양의 기울기 대각선
    fun dfs(y:Int,x:Int,depth:Int,color:Int){
        val dn = x-y+(n-1)
        val dp = x+y
        var nx = x + 2
        var ny = y
        if(nx>=n) {
            ny+=1
            nx = (ny+color)%2
        }
        val hasNext = ny<n
        // 이번 칸에 비숍을 넣을 수 있는 경우
        if(canPlace[y][x] && !diagonalVisitedP[dp] && !diagonalVisitedN[dn]){
            diagonalVisitedP[dp] = true
            diagonalVisitedN[dn] = true
            if(hasNext) dfs(ny,nx,depth+1,color)
            else maxDepth[color] = (depth+1).coerceAtLeast(maxDepth[color])
            diagonalVisitedP[dp] = false
            diagonalVisitedN[dn] = false
        }
        // 이번 칸에 비숍을 넣을 수 있든 말든
        if(hasNext) dfs(ny,nx,depth,color)
        else maxDepth[color] = (depth).coerceAtLeast(maxDepth[color])
    }
    dfs(0,0,0,0)
    dfs(0,1,0,1)
    print(maxDepth[0]+maxDepth[1])
}