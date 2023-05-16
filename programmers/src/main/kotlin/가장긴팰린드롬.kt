fun main(){
    class Solution { // 시간복잡도 약 n^2/2 정도 나옴
        fun solution(s: String): Int {
            var ans = 0
            val n = s.length
            fun evenPalindrome(seedA:Int,seedB:Int):Int{
                var pLen = 0
                while(seedA-pLen in 0 until n && seedB+pLen in 0 until n){
                    if(s[seedA-pLen]==s[seedB+pLen]) pLen++
                    else break
                }
                return pLen*2
            }
            fun oddPalindrome(seed:Int):Int{
                var pLen = 1
                while(seed-pLen in 0 until n && seed+pLen in 0 until n){
                    if(s[seed-pLen]==s[seed+pLen]) pLen++
                    else break
                }
                return 2*(pLen-1)+1
            }
//            for(i in 0 until n){
//                ans = ans.coerceAtLeast(oddPalindrome(i))
//                if(i+1 in 0 until n) ans = ans.coerceAtLeast(evenPalindrome(i,i+1))
//            }

            val mid = n/2 // 긴 팰린드롬은 중앙에 있다는 마인드 -> 끝에 붙어 있을수록 손해
            for(j in 0..mid+1){
                var i = mid - j // 중간을 기준으로 좌측부
                // 가능성 없는 곳은 들어가질 않는다는 마인드
                if((kotlin.math.min(i,n-i-1)*2+1)>ans)ans = ans.coerceAtLeast(oddPalindrome(i))
                if(i+1 in 0 until n && (kotlin.math.min(i,n-i-2)+1)*2>ans) ans = ans.coerceAtLeast(evenPalindrome(i,i+1))
                i = mid+j
                if(i<n){ // 중간 기준으로 우측부
                    if((kotlin.math.min(i,n-i-1)*2+1)>ans)ans = ans.coerceAtLeast(oddPalindrome(i))
                    if(i+1 in 0 until n && (kotlin.math.min(i,n-i-2)+1)*2>ans) ans = ans.coerceAtLeast(evenPalindrome(i,i+1))
                }
            }

            return ans
        }
        // dp 구현, isPalin 참조 횟수에서 위의 구현보다 시간적으로 오래 걸림
        fun solution2(s: String): Int {
            var maxDistance = 0
            var distance = 0
            val n = s.length
            // isPalin[a][b] a이상 b 이하가 팰린드롬인가
            val isPalin  = Array(n){a->BooleanArray(n){b-> a==b}}
            while(distance<n){
                var found = false
                for(a in 0 until n-distance){
                    val b = a+distance
                    // 둘 사이의 문자가 2개 이상 있는데 팰린드롬이 아닌 경우 지나가기
                    if(distance>2 && !isPalin[a+1][b-1]) continue
                    // 둘 사이에 문자가 없거나 팰린드롬임
                    if(s[a]==s[b]){
                        isPalin[a][b] = true
                        found = true
                    }
                }
                if(found){
                    maxDistance = distance.coerceAtLeast(maxDistance)
                    distance+=2
                    if(distance>=n && distance%2==0) distance = 1 // 홀수로 전환
                }
                else{
                    if(distance%2==0) distance = 1
                    else break
                }
            }
            return maxDistance+1
        }
    }
    println(Solution().solution("aaa"))
}