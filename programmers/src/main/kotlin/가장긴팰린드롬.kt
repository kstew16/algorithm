fun main(){
    class Solution {
        fun solution(s: String): Int {
            var answer = 1
            val arr = s.toCharArray()
            fun getLplSingleSeed(seed:Int):Int{
                var lpl = 1
                var s = seed-1
                var e = seed+1
                while(s>=0&&e<arr.size){
                    if(arr[s]==arr[e]){
                        lpl+=2
                        s--
                        e++
                    }else break
                }
                return lpl
            }
            fun getLplDoubleSeed(seed1:Int,seed2:Int):Int{
                var lpl = 0
                var s = seed1
                var e = seed2
                while(s>=0&&e<arr.size){
                    if(arr[s]==arr[e]){
                        lpl+=2
                        s--
                        e++
                    }else break
                }
                return lpl
            }
            for(seed in 1 until arr.size){
                answer = answer.coerceAtLeast(getLplSingleSeed(seed))
                answer = answer.coerceAtLeast(getLplDoubleSeed(seed-1,seed))
            }
            return answer
        }
    }
    println(Solution().solution("abcdcba"))
}