class Solution {
    fun solution(gems: Array<String>): IntArray {
        val fullKind = gems.toSet().size
        val gemCart = hashMapOf<String,Int>()
        var curKinds = 0
        var lo = 0 ; var hi = 0
        var shortestLo = 0; var shortestHi = 0
        var shortest = gems.size+1
        while(hi<gems.size){
           gemCart[gems[hi]] = (gemCart.getOrDefault(gems[hi],0) + 1).also{if(it==1)curKinds++}
            if(curKinds==fullKind){
                while(gemCart[gems[lo]]!=1){
                    gemCart[gems[lo]] = gemCart[gems[lo]]!! - 1
                    lo++
                }
                if(hi-lo+1<shortest){
                    shortestLo = lo
                    shortestHi = hi
                    shortest = hi - lo + 1
                }
            }
            hi++
        }
        return intArrayOf(shortestLo+1,shortestHi+1)
    }
}