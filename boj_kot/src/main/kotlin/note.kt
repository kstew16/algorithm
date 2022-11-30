import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList

fun main():Unit{
    val n = BufferedReader(InputStreamReader(System.`in`)).readLine().toInt()
    //0 정과 1 전산관 2 미래관 3 신앙관 4 진리관 5 한경직기념관 6 학생회관 7 형남공학관
    val map = arrayOf(
        mutableListOf(1,2),
        mutableListOf(0,2,3),
        mutableListOf(0,1,3,5),
        mutableListOf(1,2,4,5),
        mutableListOf(3,5,6),
        mutableListOf(2,3,4,7),
        mutableListOf(4,7),
        mutableListOf(5,6)
    )
    // d p[i][j] i%2 분 후에 j 번 건물에 머물고 있을 수 있는 경우의 수 어라? 규칙성 있나
    val visited = Array(8){ mutableSetOf<Int>() }
    data class Node(val num:Int,val turn:Int)
    val queue = LinkedList<Node>().apply { this.add(Node(0,0)) }
    var i = 0
    while(i<=n){
        val cur = queue.pollFirst()
        if(cur.turn!=i)
            i = cur.turn
        visited[cur.num].add(cur.turn)
        map[cur.num].forEach {
            queue.add(Node(it,cur.turn+1))
        }
    }

}