import java.io.BufferedReader
import java.io.InputStreamReader

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val (n,m) = readLine().split(" ").map { it.toInt() }
    val board = Array(n){
        readLine().toCharArray().map { it=='B'}.toBooleanArray()
    }
    val sample = Array(n){ y ->
        BooleanArray(m){ x->
            (x+y)%2==0
        }
    }


    fun getDifference(startY:Int,startX:Int):Int{
        var difA = 0
        var difB = 0
        for(j in startY until startY+8)
            for(i in startX until startX+8){
                if(board[j][i]==sample[j-startY][i-startX]) difB+=1
                else difA+=1
            }
        return kotlin.math.min(difA,difB)
    }


    var minDiff = Int.MAX_VALUE
    for(y in 0 .. n-8)
        for(x in 0 .. m-8)
            minDiff = getDifference(y,x).coerceAtMost(minDiff)

    print(minDiff)
}