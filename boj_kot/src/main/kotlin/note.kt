import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val arr = readLine().toCharArray()
    val n = arr.size
    // dp[i][j] i 이상 j 이하의 문자열은 팰린드롬인가
    val dp = Array(n){i-> BooleanArray(n){j-> i==j} }

    // 최소 집합은 0 번째 문자에서 출발하여 팰린드롬 문자열의 시작과 끝을 잇는 그래프를 BFS 탐색하는 형태로 구하겠음
    // 일단 문자열 한 개는 항상 팰린드롬 == 자신으로의 이동은 항상 가능
    val graph = Array(n){mutableListOf<Int>().apply { this.add(it) } }
    for(from in n-2 downTo 0){
        for(to in from+1 until n){
            // 일단 서로가 같아야 팰린드롬 형성 시도라도 해볼 수 있음
            if(arr[to] == arr[from]){
                // 두 문자 사이에 아무 것도 없거나 하나만 있는 경우 팰린드롬
                if((to-1)-(from+1)<=0) {
                    dp[from][to] = true
                    graph[from].add(to)
                }
                //  뭐가 있는데 걔들도 팰린드롬인경우 팰린드롬
                else if(dp[from+1][to-1]) {
                    dp[from][to] = true
                    graph[from].add(to)
                }
            }
        }
    }
    // 자 이제 문제가 바뀌었다. dp[i] 는 i 에서 순간이동이 가능한 노드
    // a 로 순간이동 시 a+1 발판에서 출발하게 된다 (팰린드롬을 마무리지었으면 그 다음 문자열부터 새로운 팰린드롬)
    // 발판을 가장 빠르게 이동하는 경로를 crucio

    val visited = BooleanArray(n+1){false}.apply{this[0] = true}
    data class Node(val num:Int,val cost:Int)
    val queue = LinkedList<Node>().apply { this.add(Node(0,0)) }
    while(queue.isNotEmpty()){
        val cur = queue.pollFirst()
        if(cur.num==n){
            print(cur.cost)
            break
        }
        graph[cur.num].forEach {
            if(!visited[it+1]){
                visited[it+1] = true
                queue.add(Node(it+1,cur.cost+1))
            }
        }
    }
}