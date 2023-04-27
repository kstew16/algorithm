package first

fun main(){
    class Solution {
        fun solution(n: Int): Int {
            val mod = 1000000007L
            val originals = intArrayOf(1,1,2,5) // 불규칙적인 atomic 블럭 미리 입력
            val dp = LongArray(n+1).apply{this[0]=1}
            val accSumMod3 = LongArray(3){0} // 이번 타일 확장에 영향을 줄 수 있는 atomic 타일모음들은 3으로 나눈 나머지로 구분됨
            val delayed = longArrayOf(1,0,0) // 타일을 배치하지 않는것도 방법으로 취급해줘야 경우의 수를 셀 수 있음
            for(i in 1..n){
                // 불규칙적이고 atomic 한 타일 (1~3 길이의 블럭)
                for(j in 0..3){
                    if(i-j<0) break
                    else dp[i] = (dp[i] + originals[j]*dp[i-j]%mod)%mod
                }
                // 규칙적이고 atomic 한 타일
                val mod3 = i%3
                dp[i] = (dp[i] + accSumMod3[(mod3+1)%3]*2)%mod // 계단형
                dp[i] = (dp[i] + accSumMod3[(mod3+2)%3]*2)%mod // ㄴㅇㄱ 형
                dp[i] = (dp[i] + accSumMod3[mod3]*4)%mod // 막대기형
                //  한 턴씩 지연해서 더해줘야 크기 3 이하의 불규칙한 atomic 배제 가능
                accSumMod3[mod3] = (accSumMod3[mod3] + delayed[mod3])%mod
                delayed[mod3] = dp[i]%mod
            }
            return dp[n].toInt()
        }
    }
    println(Solution().solution(10000))
}