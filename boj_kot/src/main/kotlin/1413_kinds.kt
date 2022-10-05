import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

// 4시간 조금 넘게 걸렸음(요즘 왜 이렇게 오래 걸리는지 ㅜㅜ)
// 전체 경우의 수를 어떻게 할 지 몰라서 오래 걸렸음 확률론적인 풀이는 사실 10분만에 나왔는데...
// 확률론적인 문제는 분수로 확률 계산을 하기보다는 경우의 수를 따져보면서 구해야 하는데 (ULong 썼는데도 20!끼리 곱해져서 오버플로우남)
// 이 전체 경우의 수에 대해서 모든 시도의 확률이 같다면 이를 배치하는 방법의 수 = 시도하는 순서의 경우의 수임
// 그런데 이 경우의 수를 따질 때, 하위 dp 테이블 또한 배치에 따른 경우의 수를 포함하므로, 상위배치수/하위배치수 * 하위 dp 이렇게 해줘야 잘 나옴

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val primeUnder20 = ulongArrayOf(2u,3u,5u,7u,11u,13u,17u,19u)
    val st = StringTokenizer(readLine())
    val n = st.nextToken().toInt()
    val bombs = st.nextToken().toInt()
    class Fraction(var denom:ULong,var nom:ULong){
        fun minimized():Fraction{
            val usingPrime = primeUnder20.filter { it<=kotlin.math.min(nom,denom) }
            for(p in usingPrime){
                while(nom%p == 0uL && denom%p == 0uL){
                    nom/=p
                    denom/=p
                }
            }
            return this
        }
        fun print(){
            println("$denom/$nom")
        }
    }
    fun factorial(from:Int,num:Int):ULong{
        var ans = 1uL
        for(i in from..num) ans*=i.toULong()
        return ans
    }
    // dp[b][n] 폭탄이 b개, 상자가 n개 남았을 때의 해결 경우의 수
    // 폭탄이 없을 땐 해결 가능한 경우의 수가 없음, 이것을 가리키는 초기화 0
    val dp = Array(bombs+1){
        ULongArray(n+1){0uL}
    }.apply {
        for(i in 0..bombs){
            for(j in 0..kotlin.math.min(i,n)){
                // 폭탄 수가 상자 수보다 같거나 많은 경우에는 상자 수의 팩토리얼만큼의 해법이 존재 (상자를 배치하는 방법의 수)
                this[i][j] = factorial(2,j)
            }
        }
    }
    // 모든 상자를 터뜨릴 확률이 같으므로 상자를 배치한 순서가 상자를 터뜨릴 순서라고 하자
    for(b in 1 .. bombs){
        // 얻어야 하는 열쇠의 개수가 keys 개 일 때부터 아래부터 표를 채워 보면
        for(keys in 1..n){
            var sum = 0uL
            for(a in 1 .. keys){
                // 특정 열쇠 a를 가장 먼저 사용하는 열쇠 배치는 (keys-1)! 개이다.
                // 해당 열쇠를 사용하면 문제는 keys-a 개의 상자만을 b-1 개의 폭탄으로 해결하는 경우의 수 만큼을 해결 방안으로 가진다.
                // (keys-1)! 개의 배치 중, keys-a 개는 하위 문제에서 재배치하게 되므로, (keys-1)!/(keys-a)! 을 계산해준다.
                sum+=(dp[b-1][keys-a]*factorial(keys-a+1,keys-1))
                dp[b][keys] =sum
            }
        }
    }


    // b개의 폭탄으로 n개의 상자를 모두 여는 방법 수/ n 개의 상자를 배치하는 방법 수
    Fraction(dp[bombs][n],factorial(2,n)).minimized().print()

}