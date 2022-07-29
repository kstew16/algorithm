import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer


fun main()=with(BufferedReader(InputStreamReader(System.`in`))){
    var st = StringTokenizer(readLine())
    fun getInt() = st.nextToken().toInt()
    val h = getInt()
    val w = getInt()
    val target = mutableListOf<IntArray>()
    val field = Array(h){
            y->
        st = StringTokenizer(readLine())
        IntArray(w){
                x->
            val input = getInt()
            if(input in 1..5) target.add(intArrayOf(y,x))
            input
        }
    }

    fun copyArr(from:Array<IntArray>,to:Array<IntArray>){
        for(j in 0 until h)
            for(i in 0 until w)
                to[j][i] = from[j][i]
    }

    class Camera(_type:Int,_direction:Int){
        val type =_type
        var direction = _direction

        fun watch(targetField:Array<IntArray>,y:Int,x:Int){
            val goingRight = ((type == 1 && direction == 0) ||
                    (type == 2 && (direction == 0 ||direction == 2)) ||
                    (type == 3 && (direction == 0 || direction == 1)) ||
                    (type == 4 && direction != 3) ||
                    (type == 5)
                    )
            val goingDown = ((type == 1 && direction == 1) ||
                    (type == 2 && (direction == 1 ||direction == 3)) ||
                    (type == 3 && (direction == 1 || direction == 2)) ||
                    (type == 4 && direction != 0) ||
                    (type == 5)
                    )
            val goingLeft = ((type == 1 && direction == 2) ||
                    (type == 2 && (direction == 0 ||direction == 2)) ||
                    (type == 3 && (direction == 2 || direction == 3)) ||
                    (type == 4 && direction != 1) ||
                    (type == 5)
                    )
            val goingUp = ((type == 1 && direction == 3) ||
                    (type == 2 && (direction == 1 ||direction == 3)) ||
                    (type == 3 && (direction == 0 || direction == 3)) ||
                    (type == 4 && direction != 2) ||
                    (type == 5)
                    )
            if(goingLeft){
                for (i in x-1 downTo 0){
                    if( targetField[y][i] == 6) break
                    else if(targetField[y][i]!=0) continue
                    else targetField[y][i] = 7
                }
            }
            if(goingRight){
                for(i in x+1 until w){
                    if( targetField[y][i] == 6) break
                    else if(targetField[y][i]!=0) continue
                    else targetField[y][i] = 7
                }
            }
            if(goingDown){
                for(i in y+1 until h){
                    if( targetField[i][x] == 6) break
                    else if(targetField[i][x]!=0) continue
                    else targetField[i][x] = 7
                }
            }
            if(goingUp){
                for (i in y-1 downTo 0){
                    if( targetField[i][x] == 6) break
                    else if(targetField[i][x]!=0) continue
                    else targetField[i][x] = 7
                }
            }
        }
    }

    val copy = Array(h){
        IntArray(w){
            0
        }
    }

    var minCount = 64

    fun countBlind(map:Array<IntArray>):Int {
        var count = 0
        for(element in map)
            for(innerElement in element)
                if(innerElement == 0) count += 1
        return count
    }

    val stack = mutableListOf<Int>()
    fun permute(depth:Int){
        if(depth == target.size){
            // stack 에 카메라 번호별로 direction 이 0,1,2,3 이렇게 자리잡을 수 있게
            copyArr(from=field,to=copy)
            stack.withIndex().forEach {
                val (b,a) = target[it.index]
                val camType = field[b][a]
                val direction = it.value
                val cam = Camera(camType,direction)
                // 수정되는건 copy
                cam.watch(copy,b,a)
            }
            minCount = countBlind(copy).coerceAtMost(minCount)
            return
        }
        val (b,a) = target[depth]
        val possibleCamDirection = when(field[b][a]){
            1 -> 3
            2 -> 1
            3 -> 3
            4 -> 3
            5 -> 0
            else -> 0
        }
        for(i in 0..possibleCamDirection){
            stack.add(i)
            permute(depth+1)
            stack.removeAt(stack.size-1)
        }
    }
    permute(0)
    println(minCount)
}