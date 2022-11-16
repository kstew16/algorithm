import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.LinkedList
import java.util.StringTokenizer
// 1번 틀리고 맞음. 입력에서 중복된 입력이 잘못된 그래프구조를 만들 수 있는 경우가 있음 주의
fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val (n,m) = readLine().split(" ").map { it.toInt() }
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st:StringTokenizer
    val conditionLeft = IntArray(n+1){0}
    val next = Array(n+1){ hashSetOf<Int>()}
    val queue = LinkedList<Int>()
    repeat(m){
        st = StringTokenizer(readLine())
        val k = st.getInt()
        var last = 0
        repeat(k){
            val num = st.getInt()
            if(last!=0 && !next[last].contains(num)){
                conditionLeft[num]+=1
                next[last].add(num)
            }
            last = num
        }
    }
    for(i in 1..n) if(conditionLeft[i]==0) queue.add(i)
    val visited = BooleanArray(n+1){false}.apply { this[0]=true }
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    while (queue.isNotEmpty()) {
        val cur = queue.pollFirst()
        visited[cur] = true
        bw.write("$cur\n")
        next[cur].forEach {
            conditionLeft[it]-=1
            if(conditionLeft[it]==0) queue.add(it)
        }
    }
    if(visited.all { it }) bw.flush()
    else print(0)
}