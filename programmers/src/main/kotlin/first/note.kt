package first

import java.util.PriorityQueue

fun main(){


    class Solution {
        fun solution(operations: Array<String>): IntArray {
            val pqAscending = PriorityQueue<Int>()
            val pqDescending = PriorityQueue<Int>{a,b-> if(a>b)-1 else 1}
            var curLength = 0
            for(i in operations.indices){
                val (opData,inputNumStr) = operations[i].split(" ")
                val inputNum = inputNumStr.toInt()
                when(opData){
                    "D"->{
                        if(curLength>0){
                            if(inputNum>0) pqDescending.poll()
                            else pqAscending.poll()
                            curLength--
                        }
                    }
                    "I"->{
                        pqAscending.add(inputNum)
                        pqDescending.add(inputNum)
                        curLength++
                    }
                }
            }

            return if(curLength==0) intArrayOf(0,0)
            else {
                if(pqAscending.size<pqDescending.size) intArrayOf(pqAscending.last(),pqAscending.first())
                else intArrayOf(pqDescending.first(),pqDescending.last())
            }
        }
    }
    val s = Solution()
    print(s.solution(arrayOf("I 1", "I 3", "I 5", "I 7", "I 9", "D -1", "D -1", "D 1", "I 2", "D 1", "D 1")).joinToString(" "))
}
