import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.StringTokenizer

fun main(){
    """
            var bottom = 0
            var top = 0
            var distance = 0
            if(!it.value){
                for(i in 0 until nodes){
                    visited[i] = false
                }
                queue[top++] = it.index
                visited[it.index] = true
                while(top!=bottom){
                    val using = queue[bottom++]
                    map[using].forEach {child->
                        if(!visited[child] && !inCycle[child]){
                            queue[top++] = child
                        }
                    }
                }
            }
"""
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    var st = StringTokenizer(br.readLine())
    fun getInt() = st.nextToken().toInt()
    val nodes = getInt()
    val visited = BooleanArray(nodes){false}
    val inCycle = BooleanArray(nodes){false}
    val distance = IntArray(nodes){0}
    val map = Array(nodes){
        mutableListOf<Int>()
    }
    var startPoint = -1
    repeat(nodes){
        // 역의 수는 경로 수와 일치? 이거 순환선 무조건 1개임
        st = StringTokenizer(br.readLine())
        val from = getInt() - 1
        val to = getInt() - 1
        map[from].add(to)
        map[to].add(from)
        // 여기서 하나씩 꼬인거 주의
    }


    map.withIndex().forEach {

        fun isInCycle(source:Int, visiting:Int){
            if(inCycle[startPoint]) return
            visited[visiting] = true
            map[visiting].forEach {child ->
                if(!visited[child]){
                    isInCycle(visiting, child)
                }
                else if(visited[child] && source!=startPoint && startPoint == child) inCycle[child] = true
            }
        }


        for(i in 0 until nodes){
            visited[i] = false
        }
        startPoint = it.index
        isInCycle(-1,it.index)

    }

    inCycle.withIndex().forEach {

        for(i in 0 until nodes){
            visited[i] = false
        }

        if(!it.value){
            var set = false
            startPoint = it.index

            fun dfs(visiting:Int, depth:Int){
                visited[visiting] = true
                if(!set){
                    if(inCycle[visiting]) {
                        distance[startPoint] = depth
                        set = true
                    }
                    else{
                        map[visiting].forEach{child->
                            if(!visited[child]) dfs(child,depth+1)
                        }
                    }
                }
            }

            dfs(it.index,0)
        }
    }

    distance.forEach {
        bw.write("$it ")
    }

    bw.flush()
    br.close()
    bw.close()

}

