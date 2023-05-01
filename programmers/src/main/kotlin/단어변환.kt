
import java.util.*
fun main(){
    class Solution {
        fun solution(begin: String, target: String, words: Array<String>): Int {
            var answer = Int.MAX_VALUE
            fun isWordConnected(wordA:String,wordB:String):Boolean{
                var difference = 0
                for(i in wordA.indices){
                    if(wordA[i]!=wordB[i]) difference++
                    if(difference > 1 ) return false
                }
                return true
            }
            val n = words.size
            val map = Array(n){ArrayList<Int>()}
            val visited = IntArray(n){-1}
            var targetNo = -1
            val queue = LinkedList<Int>()

            for(i in 0 until n){
                if(isWordConnected(words[i],begin)){
                    queue.add(i)
                    visited[i] = 1
                }
                if(words[i] == target) targetNo = i
                for(j in i+1 until n){
                    if(isWordConnected(words[i],words[j])){
                        map[i].add(j)
                        map[j].add(i)
                    }
                }
            }

            if(targetNo == -1) return 0
            if(visited[targetNo]!=-1) return 1
            else{
                while(queue.isNotEmpty()){
                    val cur = queue.poll()
                    val nextDistance = visited[cur] + 1
                    for(i in map[cur].indices){
                        val next = map[cur][i]
                        if(visited[next] == -1){
                            if(next==targetNo) return nextDistance
                            else{
                                visited[next] = nextDistance
                                queue.add(next)
                            }
                        }
                    }
                }
            }
            return if(answer==Int.MAX_VALUE) 0 else answer
        }
    }
}