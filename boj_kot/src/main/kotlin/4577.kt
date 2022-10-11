import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    var count = 0
    while(true){
        count+=1
        val st = StringTokenizer(readLine())
        val n = st.nextToken().toInt()
        val m = st.nextToken().toInt()
        if(n==0||m==0) break
        // d1은 땅에 새겨진 마크등을 나타냄, d2는 땅 위에 박스,캐릭터,벽 등이 있는지를 의미
        val d1 = Array(n){CharArray(m)}
        val d2 = Array(n){CharArray(m)}
        var cy = 1
        var cx = 1
        data class Coord(val y:Int,val x:Int)
        val targets = mutableListOf<Coord>()
        repeat(n){ y->
            readLine().forEachIndexed { x, c ->
                when(c){
                    '.'->{
                        d1[y][x] = '.'
                        d2[y][x] = '.'
                    }
                    '#'->{
                        d1[y][x] = '#'
                        d2[y][x] = '#'
                    }
                    '+'->{
                        d1[y][x] = '+'
                        d2[y][x] = '.'
                        targets.add(Coord(y,x))
                    }
                    'b'->{
                        d1[y][x] = '.'
                        d2[y][x] = 'b'
                    }
                    'B'->{
                        d1[y][x] = '+'
                        d2[y][x] = 'b'
                        targets.add(Coord(y,x))
                    }
                    'w'->{
                        d1[y][x] = '.'
                        d2[y][x] = 'w'
                        cy = y
                        cx = x
                    }
                    'W'->{
                        d1[y][x] = '+'
                        d2[y][x] = 'w'
                        cy = y
                        cx = x
                        targets.add(Coord(y,x))
                    }
                }
            }
        }
        fun printMap(succeed:Boolean){
            print("Game $count: ")
            if(succeed) println("complete") else println("incomplete")
            for(i in 0 until n){
                for(j in 0 until m){
                    when(d2[i][j]){
                        // 공중이 비어있으면 그냥 아래를 알려주면 됨
                        '.' -> print(d1[i][j])
                        '#' -> print('#')
                        'b' ->{
                            if(d1[i][j]=='+')print('B') else print('b')
                        }
                        'w' ->{
                            if(d1[i][j]=='+')print('W') else print('w')
                        }
                    }
                }
                println()
            }
        }
        val cmd = readLine().toCharArray()
        // U R D L
        val directionY = intArrayOf(-1,0,1,0)
        val directionX = intArrayOf(0,1,0,-1)
        var gameDone = false
        for(i in cmd.indices){
            if(gameDone) break
            fun moveCharacter(dir:Int){
                val dy = directionY[dir]
                val dx = directionX[dir]
                val ny = cy+dy
                val nx = cx+dx

                when(d2[ny][nx]){
                    '.'->{
                        // 목적지가 비어있는 경우
                        d2[ny][nx] = 'w'
                        d2[cy][cx] = '.'
                        cy = ny
                        cx = nx
                    }
                    'b'->{
                        // 목적지에 박스가 있는 경우 박스가 밀릴 곳도 확인해야 함
                        if(d2[ny+dy][nx+dx]=='.'){
                            d2[ny+dy][nx+dx]='b'
                            d2[ny][nx] = 'w'
                            d2[cy][cx] = '.'
                            cy = ny
                            cx = nx
                        }
                        // 박스가 밀리는 곳에 뭐 있으면 아무런 일도 일어나지 않음
                    }
                    // 목적지에 뭐 있으면 아무 일도 일어나지 않음
                }
                print("")

            }
            when(cmd[i]){
                'U'-> moveCharacter(0)
                'R'-> moveCharacter(1)
                'D'-> moveCharacter(2)
                'L'-> moveCharacter(3)
            }
            var boxOnTarget = 0
            for(j in targets.indices){
                val (y,x) = targets[j]
                if(d2[y][x]=='b'){
                    boxOnTarget+=1
                }
            }
            if(boxOnTarget==targets.size) gameDone=true

        }
        printMap(gameDone)
    }
}