import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val LIMIT = 10
    var targetCount = 0

    var papers = IntArray(5){5}
    var count = 0
    var minCount = Int.MAX_VALUE

    val canPlace = Array(LIMIT){
        val st = StringTokenizer(readLine())
        BooleanArray(LIMIT){
            val token = st.nextToken().toInt()
            if(token!=0) targetCount+=1
            token != 0
        }
    }

    fun check(y:Int,x:Int,size:Int):Boolean{
        if(papers[size-1]<=0) return false
        for(dy in 0 until size){
            for(dx in 0 until size){
                val ny = y+dy
                val nx = x+dx
                if(ny !in 0 until LIMIT || nx !in 0 until LIMIT) return false
                else if(!canPlace[ny][nx]) return false
            }
        }
        return true
    }

    fun toggle(y:Int,x:Int,size:Int,replace:Boolean){
        for(dy in 0 until size){
            for(dx in 0 until size){
                val ny = y+dy
                val nx = x+dx
                canPlace[ny][nx] = replace
            }
        }
        papers[size - 1] += if(replace) 1 else -1
        count += if (replace) -1 else 1
        targetCount += if(replace) size*size else -(size*size)
    }



    fun dfsPlace(){
        if(targetCount == 0) {
            minCount = count.coerceAtMost(minCount)
            return
        }
        // 이제 더 들어갈건데 의미없는거면 컷
        if(count+1>=minCount) return

        var leftPaper = 0
        papers.withIndex().forEach {
            leftPaper += (it.index+1)*(it.index+1)*(it.value)
        }
        if(leftPaper<targetCount) return

        var ty = -1
        var tx = -1

        var y = 0
        while(ty==-1 && y in 0 until LIMIT) {
            var x = 0
            while (tx == -1 && x in 0 until LIMIT) {
                if (canPlace[y][x]) {
                    ty = y
                    tx = x
                }
                x+=1
            }
            y+=1
        }

        var placed = false
        for(i in 5 downTo 1){
            if(check(ty,tx,i)) {
                placed = true
                toggle(ty, tx, i, replace = false)
                dfsPlace()
                toggle(ty, tx, i, replace = true)
            }
        }
        if(!placed) return

    }

    dfsPlace()

    print(if(minCount==Int.MAX_VALUE) -1 else minCount)
}