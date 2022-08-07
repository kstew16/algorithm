import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

private const val HORIZONTAL = 1
private const val VERTICAL = 2
private const val DIAGONAL = 3
fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val field = Array(n){
        val st = StringTokenizer(readLine())
        IntArray(n){
            st.nextToken().toInt()
        }
    }

    // 못 가는 데 옮기라면 쓸 데 없는 짓 노노해
    if(field[n-1][n-1]==1) {
        print(0)
        return
    }

    val queue = LinkedList<IntArray>().apply { add(intArrayOf(1,0,HORIZONTAL,-1)) }
    var count = 0

    while(queue.isNotEmpty()){
        val(x2,y2,direction,from) = queue.pollFirst()
        if(x2==n-1 && y2==n-1){
            count+=1
            continue
        }

        // 이동 가능 여부 판독, 대각선 이동은 항상 가능
        var pushVertical = false
        var pushHorizontal = false

        when(direction){
            HORIZONTAL -> {
                pushHorizontal = true
            }
            VERTICAL -> {
                pushVertical = true
            }
            DIAGONAL -> {
                pushVertical = true
                pushHorizontal = true
            }
        }

        // 도착지에 벽이 있는 경우는 초반부에 검사 했음
        if(pushHorizontal){
            if(x2+1<n-1 && field[y2][x2+1]== 0) {
                queue.add(intArrayOf(x2+1,y2,HORIZONTAL,direction))
            }
            // 방의 경계로 이동하려는 경우, 도착지가 아니면 dead end 밖에 없음
            else if(x2+1 == n-1 && y2==n-1) count+=1
        }
        if(pushVertical) {
            if(y2+1<n-1 && field[y2+1][x2]==0) {
                queue.add(intArrayOf(x2,y2+1,VERTICAL,direction))
            }
            else if(y2+1==n-1 && x2==n-1) count+=1
        }
        // 대각선 밀기는 어떤 방향에서든 가능
        if(y2+1<n && x2+1<n && (field[y2][x2+1]+field[y2+1][x2]+field[y2+1][x2+1])==0) queue.add(intArrayOf(x2+1,y2+1,DIAGONAL,direction))
    }
    print(count)

}