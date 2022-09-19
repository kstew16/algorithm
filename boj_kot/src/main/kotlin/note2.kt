import java.io.BufferedReader
import java.io.InputStreamReader

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val (n,m) = readLine().split(" ").map{it.toInt()}
    // visited[i][a][b][k] i 길이의 문자열에서 A 를 a 개, B 를 b 개, C 를 i-a-b 개 사용했을 때 k 값의 방문 가능 여부
    val visited = Array(n+1){Array(n+1){Array(n+1){BooleanArray(1+n*(n-1)/2){false} } } }
    val ans = CharArray(n)

    fun solve(i:Int,a:Int,b:Int,k:Int):Boolean{
        if(visited[n][a][b][m]) return true
        if(i in 0 until n){
            // 문자열에 a 를 추가하면 추가되는 k는 없음
            if(a in 0 until n && !visited[i+1][a+1][b][k]) {
                visited[i+1][a+1][b][k] = true
                ans[i] = 'A'
                if(solve(i+1,a+1,b,k)) return true
            }
            // 문자열에 b 를 추가하면 현재 문자열의 a 값 만큼 k 가 증가됨
            if(b in 0 until n && !visited[i+1][a][b+1][k+a]){
                visited[i+1][a][b+1][k+a]=true
                ans[i] = 'B'
                if(solve(i+1,a,b+1,k+a)) return true
            }
            // 문자열에 c 를 추가하면 현재 문자열의 a,b 의 수만큼 k가 증가됨
            if(i-a-b in 0 until n && !visited[i+1][a][b][k+a+b]) {
                visited[i+1][a][b][k+a+b]=true
                ans[i] = 'C'
                if(solve(i+1,a,b,k+a+b)) return true
            }
        }
        return false
    }
    if(solve(0,0,0,0)){print(ans.joinToString(""))}
    else print(-1)
}