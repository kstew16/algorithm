import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val (n,m) = readLine().split(" ").map { it.toInt() }
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    val wall = Array(n){
        val st = StringTokenizer(readLine())
        BooleanArray(m){
            st.getInt() == 1
        }
    }

    val dr = intArrayOf(0,1,0,-1)
    val dc = intArrayOf(1,0,-1,0)

    val st = StringTokenizer(readLine())

    val h = st.getInt()
    val w = st.getInt()
    val sr = st.getInt()-1
    val sc = st.getInt()-1
    val fr = st.getInt()-1
    val fc = st.getInt()-1

    val queue = LinkedList<IntArray>().apply{
        add(intArrayOf(sr,sc,0))
    }

    val visited = Array(n){
        BooleanArray(m){false}
    }.apply { this[sr][sc] = true }

    var ans = -1
    while(queue.isNotEmpty()){
        val (curR,curC,moved) = queue.pollFirst()
        if(curR == fr && curC == fc){
            ans = moved
            break
        }
        for(i in 0..3){
            val nr = curR + dr[i]
            val nc = curC + dc[i]

            if(nr !in 0 until n || nc !in 0 until m) continue
            if(nr+h-1 !in 0 until n || nc+w-1 !in 0 until m) continue
            if(visited[nr][nc])continue

            if(i%2==0){
                // 수평이동이므로 h 만큼의 새로운 수를 살펴봐야 함
                var valid = true
                val checkC = curC + if(i==0) w else -1
                if(checkC !in 0 until m) continue
                for(j in nr until nr+h) if(j !in 0 until n  || wall[j][checkC]) valid = false
                if(!valid) continue
                visited[nr][nc] = true
                queue.add(intArrayOf(nr,nc,moved+1))
            }else{
                // 수직이동이므로 w 만큼의 새로운 수를 살펴봐야 함
                var valid = true
                val checkR = curR + if(i==1) h else -1
                if(checkR !in 0 until n) continue
                for(j in nc until nc+w) if(j !in 0 until m || wall[checkR][j]) valid=false
                if(!valid) continue
                visited[nr][nc] = true
                queue.add(intArrayOf(nr,nc,moved+1))
            }
        }
    }
    print(ans)
}