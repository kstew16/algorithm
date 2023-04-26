fun main(){
    class Solution {
        fun solution(n: Int): Int {
            val mod = 1000000007L

            val atomics = LongArray(n+1){
                when{
                    it==0 -> 1
                    it==1 -> 1
                    it==2 -> 2
                    it==3 -> 5
                    it%3==0 -> 4
                    it%3==2 -> 2
                    else -> 0
                }
            }
            val dp = LongArray(n+1)
            dp[0] = 1
            for(i in 1..n){
                for(j in 1 .. i){
                    dp[i] = (dp[i]+dp[i-j]*atomics[j]%mod)%mod
                }
            }

            return dp[n].toInt()
        }
    }
    println(Solution().solution(100))
}