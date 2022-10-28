import java.io.BufferedReader
import java.io.InputStreamReader
// 아 열심히 짰는데 느리네 ㅋㅋ 생각좀할걸
fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val arr = readLine().toCharArray()
    val n = arr.size
    // dp[i][j] i 이상 j 이하의 문자열은 팰린드롬인가
    val dp = Array(n){i->
        BooleanArray(n){j->
            i==j
        }
    }
    for(from in n-1 downTo 0){
        for(to in from+1 until n){
            // 일단 서로가 같아야 팰린드롬 형성 시도라도 해볼 수 있음
            if(arr[to] == arr[from]){
                // 사이에 아무 것도 없거나 하나만 있는 경우 팰린드롬
                if((to-1)-(from+1)<=0) dp[from][to] = true
                //  뭐가 있는데 걔들도 팰린드롬인경우 팰린드롬
                else if(dp[from+1][to-1]) dp[from][to] = true
            }
        }
    }
    // 자 이제 문제가 바뀌었다. dp[i] 는 i 에서 갈 수 있는 노드
    

}