import java.io.BufferedReader
import java.io.InputStreamReader
// 전날에 푼 문제(ABC G1)랑 비슷함, 맞긴 했는데 방문 순서에 따라 답을 못 찾을 수도 있는 위험한 코드 (C 부터 들어가는데 A부터 돌아가게 하면 못 찾음)
// 왜냐, visited가 노드를 완전히 반영하지 않고 있음
// 입력 CBACAAC 에 대해서
// BCAA 는 못한 코드배치를 CBAA는 성공할 수 있기 떄문
fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val limit = IntArray(3){0}
    readLine().forEach {
        when(it){
            'A' -> limit[0] += 1
            'B' -> limit[1] += 1
            'C' -> limit[2] += 1
            else ->  {
                print(-1)
                return
            }
        }
    }
    val length = limit.sum()
    val ans = CharArray(length)
    // visited[i][a][b][c][s1][s2] 길이가 i 인 문자열일때 a,b,c 개의 ABC 를 사용하고
    // 방금 전에 s1, 두 번 전에 s2를 사용한 곳을 방문한 적이 있는지 표시
    val visited = Array(length+1){Array(limit[0]+1){Array(limit[1]+1){Array(limit[2]+1){Array(4){BooleanArray(4){false} } } } } }
    fun solve(depth:Int,a:Int,b:Int,c:Int,s1:Int,s2:Int):Boolean{
        if(depth == length) return true
        if(c in 0 until limit[2] && s1!=2 && s2!=2){
            if(visited[depth+1][a][b][c+1][2][s1]) return false
            visited[depth+1][a][b][c+1][2][s1] = true
            ans[depth] = 'C'
            if(solve(depth+1,a,b,c+1,2,s1)) return true
        }
        if(b in 0 until limit[1] && s1!=1){
            if(visited[depth+1][a][b+1][c][1][s1]) return false
            visited[depth+1][a][b+1][c][1][s1] = true
            ans[depth] = 'B'
            if(solve(depth+1,a,b+1,c,1,s1)) return true
        }
        if(a in 0 until limit[0]){
            if(visited[depth+1][a+1][b][c][0][s1]) return false
            visited[depth+1][a+1][b][c][0][s1] = true
            ans[depth] = 'A'
            if(solve(depth+1,a+1,b,c,0,s1)) return true
        }
        return false
    }
    if(solve(0,0,0,0,3,3)) print(ans.joinToString(""))
    else print(-1)
}