import java.io.BufferedReader
import java.io.InputStreamReader
// 이거는 답 안 보면 못 풀었겠다
// 모든 DP 문제 속에서 condition 이 가능한 경우를 찾아라! 인 경우 condition 을 만족하는지 자체도 dp 에 기록하는 식으로 할 수 있음
// 그걸 표현하는 비트를 만들 수 있는데, 이 문제에 대해서는 0~9가 모두 포함되는가 를 비트로 2^10 개의 조건배열을 추가했음
// 저번 dp도 비슷했는데 항상 조건을 만족하게 유지하려 하지 말고 조건 유지 안 하는걸 데려다가 조건 만족하는 애만 출력하는 방식으로 하면
// 구현에서의 어려움이 훨씬 줄어드는 듯.
fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val n = br.readLine().toInt()
    when{
        (n<10) -> {
            print(0)
            return
        }
        (n==10) -> {
            print(1)
            return
        }
        (n==11) -> {
            print(3)
            return
        }
    }
    // 길이
    val dp = Array(n+1){
        // 시작수
        Array(10){
            // 끝 수
            LongArray(10){
                0
            }
        }
    }.apply {
        this[10][9][0] = 1L // 9876543210
        //this[11][9][1] = 1L // 98765432101
        //this[11][8][0] = 1L // 89876543210
        this[11][1][9] = 1L // 10123456789 얘는 확장으로 안 생겨서 넣었음
    }
    val mod = 100000000
    for(l in 10 until n){
        // 0 으로 시작할 수 없다
        for(s in 1 until 10){
            for(e in 0 until 10){
                // 길이가 l 인 s로 시작하고 e로 끝나는 계단수의 개수!
                val cur = dp[l][s][e]
                // 각 완전계단수는 엘리베이션을 통해 계단수 길이 (l-2-겹치는범위-앞뒤수에의한 영향) l+2 길이의 완전계단수에 추가한다.
                if(l+2<=n){
                    var repeatTime = l-2
                    if(s==9) repeatTime-=1
                    if(s==1) repeatTime-=1
                    if(e==9) repeatTime-=1
                    if(e==0) repeatTime-=1
                    repeatTime+=1 // 이상이하
                    repeat(repeatTime){
                        dp[l+2][s][e] = (dp[l+2][s][e] + cur)%mod
                    }
                }
                // 각 완전계단수는 앞/뒤에 따라 길이 한 개를 연장시킬 수 있다. 이 때 연장되어 생성된 완전계단수는 앞/뒤 가 바뀐다.
                when(s){
                    // 1 은 0으로 시작하는 완전계단수를 만들 수 없다
                    1->{ dp[l+1][2][e] = (dp[l+1][2][e] + cur)%mod }
                    // 9 는 10으로 시작하는 완전계단수를 만들 수 없다
                    9->{ dp[l+1][8][e] = (dp[l+1][8][e] + cur)%mod }
                    // 나머지는 자유다
                    else->{
                        dp[l+1][s-1][e] = (dp[l+1][s-1][e] + cur)%mod
                        dp[l+1][s+1][e] = (dp[l+1][s+1][e] + cur)%mod
                    }
                }
                when(e){
                    0->{dp[l+1][s][1] = (dp[l+1][s][1] + cur)%mod}
                    9->{dp[l+1][s][8] = (dp[l+1][s][8] + cur)%mod}
                    else -> {
                        dp[l+1][s][e-1] = (dp[l+1][s][e-1]+cur)%mod
                        dp[l+1][s][e+1] = (dp[l+1][s][e+1]+cur)%mod
                    }
                }

            }
        }
    }

    var whole = 0L
    for(l in 10..n){
        var sum = 0L
        for(s in 1 until 10){
            for(e in 0 until 10){
                sum = (sum+dp[l][s][e])%mod
            }
        }

        println("$l : $sum")
        whole+= sum
    }
    print(whole)
}