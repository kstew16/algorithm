package first

fun main(){
    class Solution {
        fun solution(n: Int, results: Array<IntArray>): Int {
            var answer = 0
            val parent = Array(n+1){ArrayList<Int>()}
            val child = Array(n+1){ArrayList<Int>()}
            results.forEach{(p,c)->
                parent[c].add(p)
                child[p].add(c)
            }
            val visited = BooleanArray(n+1){false}
            var visitCount = 0
            fun dfs(visiting:Int,isReverse:Boolean){
                visited[visiting] = true
                visitCount ++
                if(isReverse){
                    parent[visiting].forEach {
                        if(!visited[it]) dfs(it,true)
                    }
                }else{
                    child[visiting].forEach {
                        if(!visited[it]) dfs(it,false)
                    }
                }
            }
            for(i in 1..n){
                for(j in visited.indices)visited[j] = false
                visitCount = -1
                dfs(i,true)
                dfs(i,false)
                if(visitCount==n) answer++
            }
            return answer
        }
    }
    val results = arrayOf(
        intArrayOf(4,3),
        intArrayOf(4,2),
        intArrayOf(3,2),
        intArrayOf(1,2),
        intArrayOf(2,5)
    )
    println(Solution().solution(5,results))
}