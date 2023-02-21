import java.util.LinkedList

fun main(){
    class Solution {
        val dx = intArrayOf(1,0,-1,0)
        val dy = intArrayOf(0,1,0,-1)
        fun solution(grid: Array<String>): Int {
            data class Point(val y:Int,val x:Int)

            val h = grid.size
            val w = grid[0].length
            var answer: Int = h*w
            val visited = Array(h){
                BooleanArray(w){false}
            }
            val queue = LinkedList<Point>()

            for(fw in 0 until w){
                var fh = 0
                if(grid[fh][fw]=='.' && !visited[fh][fw]){
                    visited[fh][fw] = true
                    queue.add(Point(fh,fw))
                }
                fh = h-1
                if(fh !in 0 until h) continue
                if(grid[fh][fw]=='.' && !visited[fh][fw]){
                    visited[fh][fw] = true
                    queue.add(Point(fh,fw))
                }
            }
            for(fh in 0 until h){
                var fw = 0
                if(grid[fh][fw]=='.' && !visited[fh][fw]){
                    visited[fh][fw] = true
                    queue.add(Point(fh,fw))
                }
                fw = w-1
                if(fw !in 0 until w) continue
                if(grid[fh][w-1]=='.' && !visited[fh][w-1]){
                    visited[fh][fw] = true
                    queue.add(Point(fh,fw))
                }
            }

            while(queue.isNotEmpty()){
                val (curY,curX) = queue.pollFirst()
                for(i in 0 until 4){
                    val ny = curY + dy[i]
                    val nx = curX + dx[i]
                    if(ny in 0 until h && nx in 0 until w && !visited[ny][nx] && grid[ny][nx] == '.'){
                        visited[ny][nx] = true
                        queue.add(Point(ny,nx))
                    }
                }
            }

            for(j in 0 until h){
                for(i in 0 until w){
                    if(visited[j][i]) answer--
                }
            }
            return answer
        }
    }
    val c1 = CharArray(1000){'.'}
    val s1 = Array(1000){
        c1.joinToString("")
    }
    print(Solution().solution(s1))


}