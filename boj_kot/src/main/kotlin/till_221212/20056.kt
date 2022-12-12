import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList
import java.util.StringTokenizer
// 턴을 진행해야 하는 구현인데 맵 전체의 FireBall 들에 대해서 일괄절으로 작업을 실행해야 해서,
// 현재맵과 결과맵을 턴에 따라 A맵은 결과를 B맵에 저장, B맵은 결과를 A맵에 저장 이런식으로 설정했음
// 해당 맵에서 처리할 좌표도 일일이 찾지 않고 큐잉하는 방식으로 시간을 효율적으로 사용하려고 했음
// 주의사항 : 내용이 같은 객체도 새로운 객체를 만들어서 넘겨야지 서로 공유해서 원치 않는 변경이 일어나는 수가 있다 조심하자 (Point 객체에서 틀린 적 있음)
fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    val n = st.getInt(); val m = st.getInt(); val k = st.getInt()

    val dy = intArrayOf(-1,-1,0,1,1,1,0,-1)
    val dx = intArrayOf(0,1,1,1,0,-1,-1,-1)
    data class Point(var y:Int, var x:Int)
    class FireBall(val p:Point, val m:Int, val speed:Int, val d:Int){
        fun move(){
            var ny = this.p.y + dy[d]*speed
            var nx = this.p.x + dx[d]*speed
            while(ny!in 1..n){
                if(ny==0) ny = n
                else if(ny<0) ny=(ny+speed*n)%n
                else ny%=n
            }
            while(nx!in 1..n){
                if(nx==0) nx = n
                else if(nx<0) nx=(nx+speed*n)%n
                else nx%=n
            }
            this.p.y = ny
            this.p.x = nx
        }
    }

    val field = Array(2){Array(n+1){ Array(n+1){mutableListOf<FireBall>()} }}
    //val fireBallQueue = LinkedList<FireBall>()
    val targetPoints = Array(2){LinkedList<Point>()}
    val mergingPoints =  LinkedList<Point>()
    repeat(m){
        st = StringTokenizer(readLine())
        val r = st.getInt()
        val c = st.getInt()
        val newFire = FireBall(Point(r,c),st.getInt(),st.getInt(),st.getInt())
        field[0][r][c].add(newFire)
        targetPoints[0].add(Point(r,c))
    }
    fun mergeBall(turn:Int,p:Point){
        var mSum = 0
        var sSum = 0
        var mergeCount = field[turn][p.y][p.x].size
        // 방향에서 홀수/짝수가 등장했는지 기록
        val directionInfo = BooleanArray(2){false}
        while(field[turn][p.y][p.x].isNotEmpty()){
            val curBall = field[turn][p.y][p.x].removeAt(0)
            mSum += curBall.m
            sSum += curBall.speed
            directionInfo[curBall.d%2] = true
        }
        val newM = mSum/5
        val newS = sSum/mergeCount
        if(newM!=0){
            if(directionInfo[0]!=directionInfo[1]){
                // 홀수방향만 또는 짝수방향만 등장
                for(i in 0 until 4) field[turn][p.y][p.x].add(FireBall(Point(p.y,p.x),newM,newS,2*i))
            }else{
                for(i in 0 until 4) field[turn][p.y][p.x].add(FireBall(Point(p.y,p.x),newM,newS,2*i+1))
            }
        }
    }
    repeat(k){turn->
        val turnWindow = turn%2
        val nextTurn = if(turnWindow==1) 0 else 1
        // 모든 파이어볼은 자신의 뱡향으로 속력만큼 이동한다
        while(targetPoints[turnWindow].isNotEmpty()){
            val cur = targetPoints[turnWindow].pollFirst()
            while(field[turnWindow][cur.y][cur.x].isNotEmpty()){
                val curBall = field[turnWindow][cur.y][cur.x].removeAt(0)
                curBall.move()
                val ny = curBall.p.y
                val nx = curBall.p.x
                when(field[nextTurn][ny][nx].size){
                    0 -> targetPoints[nextTurn].add(Point(ny,nx))
                    1 -> mergingPoints.add(Point(ny,nx))
                }
                field[nextTurn][ny][nx].add(curBall)
            }
        }
        // 이동이 모두 끝난 뒤, 2개 이상의 파이어볼이 있는 칸에서는 합체가 일어남
        while(mergingPoints.isNotEmpty()){
            val p = mergingPoints.pollFirst()
            mergeBall(nextTurn,p)
        }
    }
    var mSum = 0
    while(targetPoints[k%2].isNotEmpty()){
        var p =  targetPoints[k%2].pollFirst()
        field[k%2][p.y][p.x].forEach {
            mSum += it.m
        }
    }
    print(mSum)
}