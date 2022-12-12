import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val LIMIT = 10
    var targetCount = 0
    val canPlace = Array(LIMIT){
        val st = StringTokenizer(readLine())
        BooleanArray(LIMIT){
            val token = st.nextToken().toInt()
            if(token!=0) targetCount+=1
            token != 0
        }
    }

    fun checkSpace(y:Int,x:Int,width:Int):Boolean{
        for(dy in 0 until width){
            for(dx in 0 until width){
                val ny = y+dy
                val nx = x+dx
                if(ny in 0 until LIMIT && nx in 0 until LIMIT){
                    if(!canPlace[ny][nx]) return false
                    else continue
                }else if(ny !in 0 until LIMIT || nx !in 0 until LIMIT) return false
            }
        }
        return true
    }

    fun toggle(y:Int,x:Int,width:Int,replace:Boolean){
        for(dy in 0 until width){
            for(dx in 0 until width){
                val ny = y+dy
                val nx = x+dx
                canPlace[ny][nx] = replace
            }
        }
    }


    var papers = IntArray(5){5}
    var count = 0
    var minCount = Int.MAX_VALUE

    fun dfsPlaceSized(size:Int){
        if(targetCount == 0) minCount = count.coerceAtMost(minCount)
        // 이제 더 들어갈건데 의미없는거면 컷
        if(count+1>=minCount) return

        // Place 하는 가지
        for(y in 0 until LIMIT){
            for(x in 0 until LIMIT){
                if(canPlace[y][x]){
                    if(checkSpace(y,x,size)) {
                        if (papers[size - 1] > 0) {
                            toggle(y, x, size, replace = false)
                            count += 1
                            papers[size - 1] -= 1
                            targetCount -= size * size

                            dfsPlaceSized(size)

                            targetCount += size * size
                            toggle(y, x, size, replace = true)
                            count -= 1
                            papers[size - 1] += 1
                        }
                    }
                }
            }
        }

        // Place 하지 않는 가지
        var leftPaper = 0
        papers.withIndex().forEach {
            if(it.index<size-1){
                leftPaper += (it.index+1)*(it.index+1)*(it.value)
            }
        }
        if(leftPaper>=targetCount && size>1) dfsPlaceSized(size-1)
    }
    dfsPlaceSized(5)

    print(if(minCount==Int.MAX_VALUE) -1 else minCount)
}