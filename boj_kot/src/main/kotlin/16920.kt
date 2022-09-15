import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    var st = StringTokenizer(readLine())
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    val n = st.getInt()
    val m = st.getInt()
    val nPlayers = st.getInt()
    st = StringTokenizer(readLine())
    val turn = IntArray(nPlayers){st.getInt()}
    data class Castle(val y:Int,val x:Int,val distance:Int)
    val buildQueuePlayer = Array(nPlayers){LinkedList<Castle>()}

    val field = Array(n){y->
        val input = readLine().toCharArray()
        input.forEachIndexed {x,c->
            if(c!='.'||c!='#') c.digitToIntOrNull()?.let {buildQueuePlayer[it-1].add(Castle(y,x,0)) }
        }
        input
    }
    val dy = intArrayOf(0,1,0,-1)
    val dx = intArrayOf(1,0,-1,0)
    var round = 0
    while (buildQueuePlayer.any { it.isNotEmpty() }){
        round+=1
        for(i in 0 until nPlayers){
            val nowPlayer = buildQueuePlayer[i]
            val turnLimit = round*turn[i]
            while(nowPlayer.isNotEmpty()){
                // 이번에 배치할 게 턴 제한 안에 있어야 함
                if((nowPlayer.first.distance+1)>(turnLimit)) break
                val (cy,cx,d) = nowPlayer.pollFirst()
                for(j in 0 until 4){
                    val ny = cy+dy[j]
                    val nx = cx+dx[j]
                    if(ny in 0 until n && nx in 0 until m && field[ny][nx]=='.'){
                        field[ny][nx] = (i+1).digitToChar()
                        nowPlayer.add(Castle(ny,nx,d+1))
                    }
                }
            }
        }
    }
    val ans = sortedMapOf<Char,Int>()
    for(y in 0 until n){
        for(x in 0 until m){
            val target = field[y][x]
            if(target == '#' || target == '.')continue
            if(target in ans.keys) ans[target]?.let { ans[target] = 1+it }
            else ans[target] = 1
        }
    }
    print(ans.values.joinToString(" "))
}