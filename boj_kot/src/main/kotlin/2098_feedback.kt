import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
// dp를 채울 때에는 미방문이 아니면 최적 해이도록 구조를 설계해야 불필요한 재방문을 막을 수 있음.
// 이 구조를 설계할 때 뒤에서부터 조립하는거 반복문 BU 에서는 꺼렸는데 DFS 는 이게 오히려 맞는 것 같네
// 또 풀다가 미방문이랑 '불가' 를 같은 값으로 표시해서 불필요한 재방문노드가 엄청나게 생성됐었음 이거 꼭 확인
fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    infix fun Int.on(i:Int) = this or (1 shl i)
    infix fun Int.chk(i:Int) = ((this shr i) and 1) == 1

    var st = StringTokenizer(readLine())
    val n = st.nextToken().toInt()
    val graph = Array(n){
        st = StringTokenizer(readLine())
        IntArray(n){
            st.nextToken().toInt()
        }
    }

    val limit = 1000000007
    // dp[e][visited] -> e로 끝나는 'visited' 상태의 경로를 해밀턴 사이클로 만들기 위한 최소 비용
    val dp = Array(n){
        IntArray(1 shl n){-1}
    }
    fun continueCycle(e:Int, visited:Int):Int{
        // 이미 해밀턴으로 만드는 비용을 아는 경로
        if(dp[e][visited]!=-1) return dp[e][visited]
        // 귀환만 남았을 때
        if(visited==(1 shl n)-1) return if(graph[e][0] == 0) limit else graph[e][0]
        var optimalCost = limit
        // 다음 이어지는 노드별로 계산하여 최소 비용 계산
        for(next in 1 until n){
            if(visited chk next) continue
            val newCost = graph[e][next]
            if(newCost==0) continue
            optimalCost = optimalCost.coerceAtMost(continueCycle(next,visited on next)+newCost)
        }
        dp[e][visited] = optimalCost
        return dp[e][visited]
    }
    print(continueCycle(0,1))
    close()
}