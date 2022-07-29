import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer


fun main()=with(BufferedReader(InputStreamReader(System.`in`))){
    var st = StringTokenizer(readLine())
    fun getInt() = st.nextToken().toInt()
    val h = getInt()
    val w = getInt()
    val field = Array(h){
        st = StringTokenizer(readLine())
        IntArray(w){
            getInt()
        }
    }
    var copy = Array(h){
        IntArray(w){
            0
        }
    }
    field.copyInto(copy)



    class Camera(_type:Int,_direction:Int){
        val type =_type
        var direction = _direction
        fun rotate(){
            // Clockwise rotation
            direction = ((direction+1)%4)
        }

        fun watch(targetField:Array<IntArray>,y:Int,x:Int){
            val goingRight = ((type == 1 && direction == 0) ||
                    (type == 2 && (direction == 0 ||direction == 2)) ||
                    (type == 3 && (direction == 0 || direction == 1)) ||
                    (type == 4 && direction != 3) ||
                    (type == 6)
                    )
            val goingDown = ((type == 1 && direction == 1) ||
                    (type == 2 && (direction == 1 ||direction == 3)) ||
                    (type == 3 && (direction == 1 || direction == 2)) ||
                    (type == 4 && direction != 0) ||
                    (type == 6)
                    )
            val goingLeft = ((type == 1 && direction == 2) ||
                    (type == 2 && (direction == 0 ||direction == 2)) ||
                    (type == 3 && (direction == 2 || direction == 3)) ||
                    (type == 4 && direction != 1) ||
                    (type == 6)
                    )
            val goingUp = ((type == 1 && direction == 3) ||
                    (type == 2 && (direction == 1 ||direction == 3)) ||
                    (type == 3 && (direction == 0 || direction == 3)) ||
                    (type == 4 && direction != 2) ||
                    (type == 6)
                    )
            if(goingLeft){
                for (i in x downTo 0){
                    if( targetField[y][i] == 6) break
                    else targetField[y][i] = 7
                }
            }
            if(goingRight){
                for(i in x until w){
                    if( targetField[y][i] == 6) break
                    else targetField[y][i] = 7
                }
            }
            if(goingDown){
                for(i in y until h){
                    if( targetField[y][i] == 6) break
                    else targetField[y][i] = 7
                }
            }
            if(goingUp){
                for (i in y downTo 0){
                    if( targetField[y][i] == 6) break
                    else targetField[y][i] = 7
                }
            }
        }
    }
}