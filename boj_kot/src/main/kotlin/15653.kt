import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList
import kotlin.math.abs
// 반례 보고 로직이 수정됨
fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val (n,m) = readLine().split(" ").map{it.toInt()}
    var red = IntArray(2){0}
    var blue = IntArray(2){0}
    data class State(val ry:Int,val rx:Int,val by:Int,val bx:Int,val distance:Int)
    val field = Array(n){y->
        val input = readLine().toCharArray()
        input.forEachIndexed{x,c->
            when(c){
                'R' -> red = intArrayOf(y,x)
                'B' -> blue = intArrayOf(y,x)
                //'O' -> target = intArrayOf(y,x)
            }
        }
        input
    }

    field[red[0]][red[1]] = '.'
    field[blue[0]][blue[1]] = '.'
    val queue = LinkedList<State>().apply{add(State(red[0],red[1],blue[0],blue[1],0))}
    val dx = intArrayOf(1,0,-1,0)
    val dy = intArrayOf(0,1,0,-1)
    val visited = Array(n){
        Array(m){
            Array(n){
                BooleanArray(m){false}
            }
        }
    }
    var ans = -1
    fun State.applyGravity(direction:Int):Boolean{
        val cur = this
        var rEscaped = false
        var bEscaped = false
        var (nry,nrx,nby,nbx,distance) = cur
        val curDy = dy[direction]
        val curDx = dx[direction]
        while(nry+curDy in 0 until n && nrx+curDx in 0 until m && field[nry+curDy][nrx+curDx]!='#'){
            nry += curDy
            nrx += curDx
            if(field[nry][nrx] == 'O'){
                rEscaped = true
                break
            }
        }
        while(nby+curDy in 0 until n && nbx+curDx in 0 until m && field[nby+curDy][nbx+curDx]!='#'){
            nby += curDy
            nbx += curDx
            if(field[nby][nbx] == 'O'){
                bEscaped = true
                break
            }
        }
        // 파란 구슬 탈출시 이 움직임은 무의미
        if(bEscaped) return false
        if(rEscaped){
            ans = distance+1
            queue.clear()
            return true
        }
        // 구슬이 굴러서 멈추는 곳이 같은 경우, 둘 다 탈출하는 경우는 이미 처리함
        if(nby == nry && nbx == nrx){
            // 그게 아니라면 멀리 있던 구슬이 바로 앞에 멈춰 서야 함
            if(direction%2 == 0){
                // x 방향으로 멀리 굴러온 구슬을 찾음, 같은 곳에서 굴러오는 경우는 없음
                // 파란 구슬이 멀리서 온 경우
                if(abs(nbx-cur.bx)>abs(nrx-cur.rx)) nbx -= curDx
                // 빨간 구슬이 멀리서 온 경우
                else nrx -= curDx
            }else{
                // 파란 구슬이 멀리서 온 경우
                if(abs(nby-cur.by)>abs(nry-cur.ry)) nby -= curDy
                // 빨간 구슬이 멀리서 온 경우
                else nry -= curDy
            }
        }
        // 이제 두 구슬이 같지 않은 지점으로 왔고, 게임 종료 상황이 아니기 때문에 이미 왔던 경우가 아니라면 큐잉해줌
        if(!visited[nry][nrx][nby][nbx]){
            visited[nry][nrx][nby][nbx] = true
            queue.add(State(nry,nrx,nby,nbx,distance+1))
        }
        return false
    }

    var found = false
    while(queue.isNotEmpty() &&!found){
        val cur = queue.pollFirst()
        for(i in 0 until 4) if(!found) found = cur.applyGravity(i)
    }

    print(ans)
}




// (!visitedA[y,x] || !visitedB[y,x]) != visited[ay][ax][by][bx]