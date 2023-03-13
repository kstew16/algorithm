fun main(){
    class Solution {
        fun solution(a: IntArray): Int {

            val n = a.size
            var answer: Int = n
            var min = Int.MAX_VALUE
            val lMinimum = IntArray(n){i->
                if(i==0) Int.MAX_VALUE
                else{
                    min = a[i-1].coerceAtMost(min)
                    min
                }
            }
            min = Int.MAX_VALUE
            var rMinimum = IntArray(n){Int.MAX_VALUE}
            for(i in n-2 downTo 0){
                min = a[i+1].coerceAtMost(min)
                rMinimum[i] = min
            }
            for(i in a.indices){
                if(a[i]>lMinimum[i] && a[i]>rMinimum[i]) answer--
            }

            return answer
        }
    }
    println(Solution().solution(intArrayOf(-16,27,65,-2,58,-92,-71,-68,-61,-33)))
}