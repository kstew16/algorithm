import java.io.BufferedReader
import java.io.InputStreamReader


fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val str = readLine().toCharArray()
    // dp[i][j] i 에서 시작돼서 j-1 로 끝나는 옳은 문자열의 수
    // i 에서 시작되는 문자열의 개수는 =
    val dp = Array(n+1){
        IntArray(n+1){
            -1
        }
    }
    fun fillDP(i:Int,j:Int):Int{
        var value = 0
        // 빈 문자열은 옳은 문자열이다
        if(i==j) value = 1

        // 이미 아는 값은 그대로
        else if(dp[i][j]!=-1) value = dp[i][j]

        else {
            val me = str[i]
            // 닫는 문자열로부터는 옳은 문자열은 시작하지 않는다
            if (me == ')' || me == ']' || me == '}') value = 0
            // i 이상 j 미만, 즉 j-i 가 홀수이면 올바른 문자열이 될 수 없다
            else if (((j - i) % 2) == 1) value = 0
            // i 이상 j 미만 문자열이 2 개라면 올바른 문자열은 3 종류 (),{},[]로 구분될 수 있다.
            else if (j == i + 2) {
                value = if ((me != '?' && str[i + 1] == '?') ||
                    (me == '?' && str[i + 1] == ')') ||
                    (me == '?' && str[i + 1] == ']') ||
                    (me == '?' && str[i + 1] == '}') ||
                    (me == '(' && str[i + 1] == ')') ||
                    (me == '[' && str[i + 1] == ']') ||
                    (me == '{' && str[i + 1] == '}')
                ) 1
                else if (me == '?' && str[i + 1] == '?') 3
                else 0
            }
            // 나부터 시작하는 모든 옳은 문자열은 내가 전개하는 괄호를 닫는 괄호 내의 옳은 문자열 수 * 닫는 괄호 뒤로부터 시작하는 옳은문자열 수
            // 단, 내가 여는 괄호가 결정되지 않은 ? 괄호라면 경우의 수는 3배로 늘어난다. () {} []
            else {
                val closingList = mutableListOf('?')
                when (me) {
                    '?' -> closingList.addAll(listOf(')', ']', '}'))
                    '(' -> closingList.add(')')
                    '[' -> closingList.add(']')
                    '{' -> closingList.add('}')
                }
                for (k in i + 1 until j) {
                    if (str[k] in closingList){
                        value += if(me=='?' && str[k]=='?') (3*((fillDP(i + 1, k) * fillDP(k + 1, j))%100000))%100000
                        else (fillDP(i + 1, k) * fillDP(k + 1, j)) % 100000
                    }
                }
            }
        }
        dp[i][j] = value%100000
        return dp[i][j]
    }
    print(fillDP(0,n))
}