import java.util.*
fun main(){
    class Solution {
        fun solution(operations: Array<String>): IntArray {
            var s = 0
            val minPQ = PriorityQueue<Int>()
            val maxPQ = PriorityQueue<Int>{a,b-> if(a>b)-1 else 1}
            operations.map{it.split(" ")}.forEach{(cmdType,numString)->
                val num = numString.toInt()
                if(cmdType=="I"){
                    s++
                    minPQ.add(num)
                    maxPQ.add(num)
                }else{
                    if(s>0) s--
                    if(num<0){// 최솟값 삭제
                        val removed = minPQ.poll()
                        maxPQ.remove(removed)
                    }else{
                        val removed = maxPQ.poll()
                        minPQ.remove(removed)
                    }
                }
            }
            return if(s==0) intArrayOf(0,0)
            else intArrayOf(maxPQ.poll(),minPQ.poll())
        }
    }
}