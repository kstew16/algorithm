package first

import java.util.PriorityQueue

fun main(){
    data class StarCandidate(val starNum:Int,val count:Int)
    class Solution{
        fun solution(a:IntArray):Int{
            var limit = a.size
            if(limit<2) return 0

            var ans = 0
            val count = IntArray(limit){0}
            a.forEach { count[it]++ }
            val pq = PriorityQueue<StarCandidate>(){a,b-> if(a.count>b.count) -1 else 1}
            count.forEachIndexed { index, i ->
                pq.add(StarCandidate(index,i))
            }
            while(pq.isNotEmpty()){
                val curCandidate = pq.poll()
                if(curCandidate.count<=ans) continue
                var len = 0
                var i = 0
                while(i in 0..limit-2){
                    if(a[i]==a[i+1]) i++
                    else if(a[i]!=curCandidate.starNum && a[i+1]!=curCandidate.starNum) i++
                    else{
                        len++
                        i+=2
                    }
                }
                ans = ans.coerceAtLeast(len)
            }
            return ans*2
        }
    }
    println(Solution().solution(intArrayOf(2, 3, 3, 2, 3, 3, 2, 3, 2, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 2, 3, 3, 2, 3, 3, 2, 3, 2)))
}