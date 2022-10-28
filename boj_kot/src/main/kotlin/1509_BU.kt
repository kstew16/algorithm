import java.io.BufferedReader
import java.io.InputStreamReader
// 아 열심히 짰는데 느리네 ㅋㅋ 생각좀할걸
fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val arr = readLine().toCharArray()
    val n = arr.size
    // dp[i][j] i 이상 j 이하의 문자열을 최소 몇 개의 팰린드롬으로 분해할 수 있는가
    val INF = 10234567
    val dp = Array(n){i->
        IntArray(n){j->
            when{
                (i==j) -> 1
                else -> INF
            }
        }
    }
    for(from in n-1 downTo 0){
        for(to in from+1 until n){
            var value = Int.MAX_VALUE
            // 문자 2개에 대한 판별
            if(to==from+1) value = if(arr[from]==arr[to]) 1 else 2
            // 두 문자 사이에 1개 이상의 문자가 있다면 모든 경우들을 따져봐야 함
            else if(to>from+1){
                // 부분 펠린드롬들의 조합
                for(i in from until to)
                    value = (dp[from][i]+dp[i+1][to]).coerceAtMost(value)
                // 통짜로 펠린드롬
                value = with(dp[from+1][to-1]){
                    when(this){
                        // 문자열 사이가 펠린드롬인 경우 양 끝이 같으면 나까지 합쳐서 펠린드롬 한 개
                        // 양 끝이 다르면 3개
                        1 -> (if(arr[from]==arr[to]) 1 else 3)
                        // 문자열 사이가 펠린드롬이 아니라면 같더라도 묶을 수 없음
                        else -> this + 2
                    }
                }.coerceAtMost(value)
            }
            dp[from][to] = value
        }
    }
    print(dp[0][n-1])
}