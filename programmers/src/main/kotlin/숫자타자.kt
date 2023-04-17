import java.util.LinkedList

fun main(){
    class Node(val left:Int,val right:Int,val cost:Int){
        fun code() = left*10+right
    }
    class Solution {
        fun solution(numbers: String): Int {
            var answer = Int.MAX_VALUE
            val distance = arrayOf(
                intArrayOf(1,7,6,7,5,4,5,3,2,3),//0
                intArrayOf(7,1,2,4,2,3,5,4,5,6),//1
                intArrayOf(6,2,1,2,3,2,3,5,4,5),//2
                intArrayOf(7,4,2,1,5,3,2,6,5,4),//3
                intArrayOf(5,2,3,5,1,2,4,2,3,5),//4
                intArrayOf(4,3,2,3,2,1,2,3,2,3),//5
                intArrayOf(5,5,3,2,4,2,1,5,3,2),//6
                intArrayOf(3,4,5,6,2,3,5,1,2,4),//7
                intArrayOf(2,5,4,5,3,2,3,2,1,2),//8
                intArrayOf(3,6,5,4,5,3,2,4,2,1)//9
            )
            // n 번째 숫자를 누른 뒤 왼손/오른손 위치에 따른 비용의 최소값
            var queue = LinkedList<Node>().apply{this.add(Node(4,6,0))}
            for(i in numbers.indices){
                val target = numbers[i].digitToInt()
                val visited = hashMapOf<Int,Int>()
                while(queue.isNotEmpty()){

                    val curNode = queue.poll()
                    // 이번거 왼손으로 누른 거
                    val lNode = Node(target,curNode.right,curNode.cost+distance[curNode.left][target])
                    if(visited.getOrDefault(lNode.code(), Int.MAX_VALUE)>lNode.cost){
                        visited[lNode.code()] = lNode.cost
                    }
                    // 이번 거 오른 손으로 누른 거
                    val rNode = Node(curNode.left,target,curNode.cost+distance[curNode.right][target])
                    if(visited.getOrDefault(rNode.code(), Int.MAX_VALUE)>rNode.cost){
                        visited[rNode.code()] = rNode.cost
                    }
                }
                visited.forEach { (t, u) ->
                    if(t/10!=t%10) queue.add(Node(t/10,t%10,u))
                }

                if(i==numbers.length-1){
                    while(queue.isNotEmpty()){
                        answer = queue.poll().cost.coerceAtMost(answer)
                    }
                }
            }
            return answer
        }
    }
    val sb = StringBuilder("")
    repeat(25000){
        sb.append("4613")
    }
    //println(Solution().solution(sb.toString()))
    println(Solution().solution("151506"))
}
