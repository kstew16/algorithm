fun main(){
    class Solution {
        fun solution(n: Int, results: Array<IntArray>): Int {
            var answer = 0
            val upSide = Array(n+1){ArrayList<Int>()}
            val downSide = Array(n+1){ArrayList<Int>()}
            results.forEach{(winner,loser)->
                downSide[winner].add(loser)
                upSide[loser].add(winner)
            }
            val visited = BooleanArray(n+1){false}
            var visitCount = 0
            fun countConnected(isUp:Boolean,visiting:Int){
                visited[visiting] = true
                visitCount++
                val map = if(isUp) upSide[visiting] else downSide[visiting]
                map.forEach{if(!visited[it]) countConnected(isUp,it)}
            }
            for(i in 1..n){
                for(j in 1..n){visited[j]=false}
                visitCount = -1 // 출발점 방문횟수 2회 미리 빼서 출발
                countConnected(true,i)
                countConnected(false,i)
                if(visitCount==n) answer++
            }
            return answer
        }
    }
}