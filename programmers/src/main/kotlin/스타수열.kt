fun main(){
    class Solution {
        fun solution(a: IntArray): Int {
            val limit = a.size
            if(limit<2) return 0

            val lastUpdated = hashMapOf<Int,Int>()
            val maxLen = IntArray(limit){0}
            /*if(a[0]!=a[1]){
                lastUpdated[a[0]] = 2
                lastUpdated[a[1]] = 2
            }*/

            for(i in 1 until limit){
                val f = a[i-1]
                val s = a[i]
                val newUpdate = hashMapOf<Int,Int>()

                if(f==s) intArrayOf(f) else intArrayOf(f,s).forEach{ k->
                    newUpdate[k] = kotlin.math.max(
                        lastUpdated.getOrDefault(k,0),
                        maxLen[k] + if(f==s) 0 else 2
                    )
                }
                lastUpdated.forEach{(k,len)->
                    maxLen[k] = maxLen[k].coerceAtLeast(len)
                }
                lastUpdated.clear()
                newUpdate.forEach{(k,len)->
                    lastUpdated[k] = len
                }
            }

            var ans = 0
            for(k in 0 until limit) ans = ans.coerceAtLeast(maxLen[k])
            lastUpdated.forEach{(_,len)->
                ans = ans.coerceAtLeast(len)
            }
            return ans
        }
    }
    println(Solution().solution(intArrayOf(2, 3, 3, 2, 3, 3, 2, 3, 2, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 2, 3, 3, 2, 3, 3, 2, 3, 2)))
}