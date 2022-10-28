import java.io.BufferedReader
import java.io.InputStreamReader
// 아 열심히 짰는데 느리네 ㅋㅋ 생각좀할걸
fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val arr = readLine().toCharArray()
    val n = arr.size
    // [i,j] i 이상 j 이하의 문자열을 최소 몇 개의 팰린드롬으로 분해할 수 있는가
    val INF = 10234567
    val dp = Array(n){i->
        IntArray(n){j->
            when{
                (i==j) -> 1
                else -> INF
            }
        }
    }
    fun fillDp(from:Int,to:Int):Int{
        // 두 문자가 논리적으로 존재 불가능한 범위는 0
        return if(from>to) INF
        // 이미 채워진 범위는 바로 반환, from==to 일 경우도 초기화에서 채웠음
        else if(dp[from][to]!=INF) dp[from][to]
        else{
            var value = Int.MAX_VALUE
            // 문자 2개를 판별하는 경우
            if(to==from+1) value = if(arr[from]==arr[to]) 1 else 2
            // 두 문자 사이에 1개 이상의 문자가 있다면 더 들어가서 검사해야 함
            else {
                // 좌측 우측 등의 부분 펠린드롬들의 조합
                for(i in from until to) value = (fillDp(from,i)+fillDp(i+1,to)).coerceAtMost(value)
                // 통짜로 펠린드롬
                value = with(fillDp(from+1,to-1)){
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
            value
        }
    }
    print(fillDp(0,n-1))
}