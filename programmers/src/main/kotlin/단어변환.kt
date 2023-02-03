import java.util.*
fun main(){
    data class State(val dest:Int,val dist:Int)
    class Solution {
        fun isWordConnected(a:String, b:String):Boolean{
            var dist = 0
            for(i in a.indices){
                if(a[i]!=b[i]){
                    dist++
                    if(dist>1) return false
                }
            }
            return true
        }
        fun solution(begin: String, target: String, words: Array<String>): Int {
            val targetIndex = words.indexOf(target)+1
            if(targetIndex == -1) return 0
            val n = words.size
            val map = Array(n+1){mutableListOf<Int>()}
            // begin 으로부터 연결성을 판별, 지도에 반영
            for(i in 0 until n){
                if(isWordConnected(begin,words[i])){
                    map[0].add(i+1)
                    map[i+1].add(0)
                }
            }
            // word 내 요소간의 연결성 판별, 지도에 반영
            for(i in 0 until n){
                for(j in i+1 until n){
                    if(isWordConnected(words[i],words[j])){
                        map[i+1].add(j+1)
                        map[j+1].add(i+1)
                    }
                }
            }

            val distance = IntArray(n+1){Int.MAX_VALUE}.apply{this[0] = 0}
            val pq = PriorityQueue<State>{a,b-> if(a.dist<b.dist) -1 else 1 }.apply{this.add(State(0,0))}
            while(pq.isNotEmpty()){
                val cur = pq.poll()
                if(cur.dest == targetIndex) break
                if(distance[cur.dest]<cur.dist) continue
                val newDist = cur.dist+1
                map[cur.dest].forEach{
                    if(distance[it]>newDist){
                        distance[it] = newDist
                        pq.add(State(it,newDist))
                    }
                }
            }


            return with(distance[targetIndex]){
                if(this == Int.MAX_VALUE) 0
                else this
            }
        }
    }
    Solution().solution("hit","cog",arrayOf("hot", "dot", "dog", "lot", "log", "cog"))
}