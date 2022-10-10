// 내 좌표 저장해놓고 2 4 2 4 1 3 1 3 이러면서 이동한 칸 수가 총 칸이 되면 break, 좌표 출력하고 꺾은 횟수 출력

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val st = StringTokenizer(readLine())
    val h = st.nextToken().toInt()
    val w = st.nextToken().toInt()

    var visiting = Array(2){IntArray(2)}.apply {
        this[0][0] = -1
        this[0][1] = h
        this[1][0] = w
        this[1][1] = -1
    }
    var turn = 0

    var ah = 0
    var av = 0
    var ch = -1
    var cv = -1
    while(true){
        val moveType1 = turn%2
        val moveType2 = ((turn - moveType1)/2)%2
        var curVisiting = visiting[moveType1][moveType2]
        val lastOppositeMove = when{
            // 오른쪽으로 가는 무브
            (moveType1==0&&moveType2==0) -> visiting[0][1]
            // 아래로 가는 무브
            (moveType1==1&&moveType2==0) -> visiting[1][1]
            // 왼쪽으로 가는 무브
            (moveType1==0&&moveType2==1) -> visiting[0][0]
            // 위로 가는 무브
            (moveType1==1&&moveType2==1) -> visiting[1][0]
            else -> -3
        }

        if(moveType1==0){
            // Horizontal Move
            if(moveType2==0) {
                // 우측으로 가는 무브
                curVisiting +=1
                cv = visiting[1][0] - 1
            }
            else {
                // 좌측으로 가는 무브
                curVisiting -=1
                cv = visiting[1][1] + 1
            }
            visiting[0][moveType2] = curVisiting
            ch = curVisiting
        }
        else{
            // Vertical Move
            if(moveType2==0) {
                // 아래로 가는 무브
                curVisiting -=1
                ch = visiting[0][1] - 1
            }
            else {
                curVisiting +=1
                ch = visiting[0][0]+1
            }
            visiting[1][moveType2] = curVisiting
            cv = curVisiting
        }

        //if(moveType1==0) println("$ch 행 점령 시도, $cv 열에 도착함")
        //else println("$cv 열 점령 시도, $ch 행에 도착함")

        if(curVisiting+1 == lastOppositeMove || curVisiting-1 == lastOppositeMove){
            //println("스택 1")
            //println("스택 2 $ch $cv")
            ah = ch+1
            av = cv+1
            break
        }
        turn+=1
    }
    println(turn)
    println("$ah $av")

}