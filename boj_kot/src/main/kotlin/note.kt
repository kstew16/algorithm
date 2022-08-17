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

    fun dfsPlace(sourceY:Int){
        if(targetCount == 0) minCount = count.coerceAtMost(minCount)
        for(y in sourceY until LIMIT){
            for(x in 0 until LIMIT){
                if(canPlace[y][x]){
                    for(size in 1..5){
                        if(checkSpace(y,x,size)){
                            if(papers[size-1]>0){
                                toggle(y,x,size, replace = false)
                                count += 1
                                papers[size-1] -= 1
                                targetCount -= size*size
                                dfsPlace(y)
                                targetCount += size*size
                                toggle(y,x,size, replace = true)
                                count -= 1
                                papers[size-1] += 1
                            }
                            else{

                            }
                        }
                        else break
                    }
                }
            }
        }
    }
    dfsPlace(0)

    print(if(minCount==Int.MAX_VALUE) -1 else minCount)
}