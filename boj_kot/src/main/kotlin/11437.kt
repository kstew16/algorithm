import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList
import java.util.StringTokenizer

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    var st : StringTokenizer
    val n = readLine().toInt()

    val treeTmp = Array(n+1){LinkedList<Int>()}
    val parent = IntArray(n+1)
    repeat(n-1){
        st = StringTokenizer(readLine())
        val p = st.nextToken().toInt(); val c = st.nextToken().toInt()
        treeTmp[p].add(c)
        treeTmp[c].add(p)
    }

    val visited = BooleanArray(n+1)
    val treeDepth = IntArray(n+1)
    fun dfs(depth:Int,source:Int,visiting:Int){
        parent[visiting] = source
        treeDepth[visiting] = depth
        visited[visiting] = true
        while(treeTmp[visiting].isNotEmpty()){
            val cur = treeTmp[visiting].pollFirst()
            if(!visited[cur]) dfs(depth+1,visiting,cur)
        }
    }
    dfs(0,0,1)
    val t = readLine().toInt()
    val sb = StringBuilder()
    repeat(t){
        st = StringTokenizer(readLine())
        var a = st.nextToken().toInt(); var b = st.nextToken().toInt()
        var deepNode:Int
        var shallowNodeNode:Int
        if(treeDepth[a]>treeDepth[b]){
            deepNode = a
            shallowNodeNode = b
        }else{
            deepNode = b
            shallowNodeNode = a
        }
        while(treeDepth[deepNode]>treeDepth[shallowNodeNode]){
            deepNode = parent[deepNode]
        }
        while(deepNode!=shallowNodeNode){
            deepNode = parent[deepNode]
            shallowNodeNode = parent[shallowNodeNode]
        }
        sb.append("${deepNode}\n")
    }
    print(sb)
}