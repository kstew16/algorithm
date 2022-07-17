import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*
// 처음에 제대로 조건을 생각했는데도 불구하고, 그 조건을 다 구현하지 않았음
// 조건을 상부에 기재하고 해당 조건을 체크하는 습관을 들이면 좋을 듯
fun main() {
    // INPUT ===================================
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    var st = StringTokenizer(br.readLine())
    fun getInt() = st.nextToken().toInt()
    val nodes = getInt()
    val visited = BooleanArray(nodes) { false }


    var isValid = true

    val map = Array(nodes) {
        ArrayList<Int>()
    }

    repeat(nodes-1) {
        st = StringTokenizer(br.readLine())
        val from = getInt() - 1
        val to = getInt() - 1
        map[from].add(to)
        map[to].add(from)
    }




    st = StringTokenizer(br.readLine())
    val givenPath = IntArray(nodes){
        getInt() - 1
    }

    val state = Array(nodes){
        var queueSize = map[givenPath[it]].size
        IntArray(queueSize){-1}
    }

    // SOLVE ===================================
    var bottom =0

    for(i in 0 until nodes){
        val current=givenPath[i]

        visited[current] = true
        map[current].withIndex().forEach{
            if(!visited[it.value])
                state[i][it.index] = it.value
        }

        var isQueueEmpty = true
        state[bottom].forEach { if(it != -1)isQueueEmpty = false }
        // Empty 하지 않은 queue 를 찾을 때까지 bottom 증가

        while(bottom<i && isQueueEmpty ){
            bottom++
            state[bottom].forEach { if(it != -1)isQueueEmpty = false }
        }


        if(i+1<nodes) {
            if (givenPath[i + 1] !in state[bottom]) {
                isValid = false
                break
            } else {
                //state[bottom].remove(givenPath[i + 1])
                //state[bottom][state[bottom].find { it==givenPath[i+1] }!!] = -1
                state[bottom][state[bottom].indexOf(givenPath[i+1])] = -1
            }
        }
    }




    bw.write(if(isValid)"1" else "0")

    bw.flush()
    bw.close()
    br.close()
}