class Solution {

    fun solution(n: Int, computers: Array<IntArray>): Int {
        val visited = BooleanArray(n)
        var answer = 0

        fun dfs(visiting:Int){
            visited[visiting] = true
            for(i in 0 until n){
                if(!visited[i]&&computers[visiting][i]==1) dfs(i)
            }
        }

        for(i in 0 until n){
            if(!visited[i]){
                answer++
                dfs(i)
            }
        }
        return answer


    }
}