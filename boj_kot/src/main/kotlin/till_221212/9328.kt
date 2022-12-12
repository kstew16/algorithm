import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.LinkedList
// . 으로만 침투 가능한줄.. 문제를 잘 읽읍시다..
fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val cases = readLine().toInt()
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    repeat(cases){
        val (n,m) = readLine().split(" ").map{it.toInt()}
        val queue = LinkedList<IntArray>()
        val floor = Array(n){ y->
            readLine().toCharArray()
        }
        val keys = mutableSetOf<Char>()
        readLine().forEach { keys.add(it) }
        // 지도 한번 쓱 보고 locked 에다가 좌표를 입력하고 열쇠는 줍고, 열쇠 있는 방은 locked 에 넣지 말고 '.'으로 바꿔버리기
        val dx = intArrayOf(1,0,-1,0)
        val dy = intArrayOf(0,1,0,-1)
        val visited = Array(n){BooleanArray(m){false} }.apply {
            queue.forEach { (y,x)->
                this[y][x] = true
            }
        }
        var count = 0
        val locked = mutableMapOf<Char,MutableList<IntArray>>()

        fun enterRoom(ny:Int,nx:Int){
            val cur = floor[ny][nx]
            if(visited[ny][nx]) return
            visited[ny][nx] = true
            if(cur == '*') return
            when{
                (cur in 'a'..'z') ->{
                    keys.add(cur)
                    queue.add(intArrayOf(ny,nx))
                }
                (cur in 'A'..'Z') ->{
                    if(cur.lowercaseChar() in keys){
                        queue.add(intArrayOf(ny,nx))
                    }else{
                        if(locked[cur]==null) locked[cur] = mutableListOf(intArrayOf(ny,nx))
                        else locked[cur]!!.add(intArrayOf(ny,nx))
                    }
                }
                (cur == '.') -> queue.add(intArrayOf(ny,nx))
                (cur == '$') -> {
                    queue.add(intArrayOf(ny,nx))
                    count+=1
                }
            }
        }
        // 벽으로 침투하는 과정
        for(y in 0 until n){
            enterRoom(y,0)
            enterRoom(y,m-1)
        }
        for(x in 0 until m){
            enterRoom(0,x)
            enterRoom(n-1,x)
        }
        fun searchFloor(){
            while(queue.isNotEmpty()){
                val (cy,cx) = queue.pollFirst()
                for(i in 0 until 4){
                    val ny = cy + dy[i]
                    val nx = cx + dx[i]
                    if(ny in 0 until n && nx in 0 until m && !visited[ny][nx]){
                        enterRoom(ny,nx)
                    }
                }
            }
        }
        // 침투 가능한 공간으로 요원들 투입
        while (queue.isNotEmpty()){
            searchFloor()
            val open = mutableSetOf<Char>()
            locked.forEach { (door,coords)->
                if(door.lowercaseChar() in keys) {
                    coords.forEach {coord->
                        queue.add(coord)
                    }
                    open.add(door)
                }
            }
            open.forEach {
                locked.remove(it)
            }
        }
        bw.write("$count\n")
    }
    bw.flush()
    bw.close()
    close()
}