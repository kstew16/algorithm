import java.io.BufferedReader
import java.io.InputStreamReader

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val (n,m) = readLine().split(" ").map{it.toInt()}
    val targets = mutableListOf<IntArray>()
    val field = Array(n){
        y ->
        val innerField = BooleanArray(m)
        readLine().withIndex().forEach {
            when(it.value){
                '#' -> {
                    innerField[it.index] = true
                    targets.add(intArrayOf(y,it.index))
                }
                '.' -> {innerField[it.index] = false}
            }
        }
        innerField
    }
    var maxArea = 0
    fun putCrossWithDFS(depth:Int,visiting:Int,sizeFrom:Int){
        fun putCross(y:Int,x:Int,size:Int):Boolean{
            var valid = field[y][x]
            // if(!valid) return false
            for(i in 2 .. size){
                val d = i-1
                if(valid) valid = (y+d < n && y-d>=0 && x+d <m && x-d>=0) else return false
                if(valid) valid = (field[y+d][x]&&field[y-d][x]&&field[y][x+d]&&field[y][x-d]) else return false
                if(!valid) return false
            }
            // 최종 valid
            for(i in 1 .. size){
                val d = i-1
                field[y+d][x] = false
                field[y-d][x] = false
                field[y][x-d] = false
                field[y][x+d] = false
            }
            return valid
        }
        fun removeCross(y:Int,x:Int,size:Int){
            for(i in 1 .. size){
                val d = i-1
                field[y+d][x] = true
                field[y-d][x] = true
                field[y][x-d] = true
                field[y][x+d] = true
            }
        }
        val (targetY,targetX) = targets[visiting]
        if(!field[targetY][targetX]) return
        val limit = minOf((n-targetY),(targetY),(targetX),(m-targetX))+1
        var maxSize = 1
        var localMaxArea = 1
        // 경계선을 넘지 않는 범위 내에서 가능한 십자가를 넣고, 십자가를 넣을 수 있다면 true 를 반환하여 최댓값을 기록
        for(i in limit downTo(1)){
            if(putCross(targetY,targetX,i))
            {
                maxSize = i
                //var temp = localMaxArea
                localMaxArea = ((4*(i-1))+1).coerceAtLeast(localMaxArea)
                //if(temp!=localMaxArea) println(targets[visiting].joinToString(" ") + " $localMaxArea")
                break
            }
        }
        if(depth==2){
            // var temp = maxArea
            maxArea = (localMaxArea * sizeFrom).coerceAtLeast(maxArea)
            // if(maxArea!=temp) println(targets[visiting].joinToString(" ") + " $localMaxArea" + " $sizeFrom")
        }
        else{
            for(i in (visiting+1) until targets.size){
                putCrossWithDFS(depth+1,i,localMaxArea)
            }
        }
        removeCross(targetY,targetX,maxSize)

    }
    for(i in 0 until targets.size){
        putCrossWithDFS(1,i,0)
    }

    print(maxArea)
}