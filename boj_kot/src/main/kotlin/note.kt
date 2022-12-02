import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val on = Array(10){ readLine().map { it=='O' }.toBooleanArray() }
    val switched = Array(10){BooleanArray(10){false} }
    val dx = intArrayOf(0,1,0,-1,0)
    val dy = intArrayOf(0,0,1,0,-1)
    fun isValidCandidate(y:Int,x:Int,currentOn:Array<BooleanArray>):Boolean{
        var valid = true
        var onCount = 0
        for(i in 0..4){
            val ny = y + dy[i]
            val nx = x + dx[i]
            if(ny !in 0 until 10 || nx !in 0 until 10){
                valid = false
                break
            }
            if(currentOn[ny][nx]) onCount+=1
        }
        if(valid && onCount<3) valid = false
        return valid
    }

    data class Node(val y:Int, val x:Int)
    val queue = LinkedList<Node>()
    val field = Array(10){y->
        BooleanArray(10){x->
            if(isValidCandidate(y,x,on)) queue.add(Node(y,x))
            on[y][x]
        }
    }
    var distance = 0
    while(queue.isNotEmpty()){
        val curCandidate = queue.pollFirst()
        if(switched[curCandidate.y][curCandidate.x]) continue
        if(isValidCandidate(curCandidate.y,curCandidate.x,field)){
            switched[curCandidate.y][curCandidate.x] = true
            distance+=1
            for(i in 0..4){
                val ny = curCandidate.y + dy[i]
                val nx = curCandidate.x + dx[i]
                field[ny][nx] = !field[ny][nx]
            }
            val visualized = Array(10){y-> field[y].map { if(it)'O' else '#' }.toCharArray()}
            for(i in 1..4){
                val ny = curCandidate.y + dy[i]
                val nx = curCandidate.x + dx[i]
                if(isValidCandidate(ny,nx,field) && !switched[ny][nx]) queue.add(Node(ny,nx))
            }
        }


    }
    var solved = true
    l1@for(i in 0 until 10){
        for(j in 0 until 10){
            if(field[i][j]) {
                solved = false
                break@l1
            }
        }
    }
    if(solved) print(distance)
    else print(-1)
}