import java.util.PriorityQueue

fun main(){
    class Solution {
        fun solution(operations: Array<String>): IntArray {
            val pqAscending = PriorityQueue<Int>()
            val pqDescending = PriorityQueue<Int>{a,b-> if(a>b)-1 else 1}
            for(i in operations.indices){
                val (opData,inputNumStr) = operations[i].split(" ")
                val inputNum = inputNumStr.toInt()
                when(opData){
                    "D"->{
                        if(pqDescending.isNotEmpty()){
                            if(inputNum>0) pqAscending.remove(pqDescending.poll())
                            else pqDescending.remove(pqAscending.poll())
                        }
                    }
                    "I"->{
                        pqAscending.add(inputNum)
                        pqDescending.add(inputNum)
                    }
                }
            }

            return if(pqDescending.isEmpty()) intArrayOf(0,0)
            else intArrayOf(pqDescending.peek(),pqAscending.peek())
        }
    }
    print(Solution().solution(arrayOf("I 10", "I 20", "D 1", "I 30", "I 40", "D -1", "D -1" )).joinToString(" "))

}