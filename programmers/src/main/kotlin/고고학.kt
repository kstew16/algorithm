fun main(){
    class Solution {
        fun solution(clockHands: Array<IntArray>): Int {
            // 첫 줄의 초이스에 따라 아랫줄은 모~두 정해집니다.
            var answer = Int.MAX_VALUE
            val n = clockHands[0].size
            val curTry = IntArray(n){0}
            val dx = intArrayOf(0,1,0,-1,0)
            val dy = intArrayOf(0,0,1,0,-1)

            fun dfs(depth:Int){
                if(depth==n){
                    var tried = 0
                    val copyBoard = Array(n){y->IntArray(n){x-> clockHands[y][x] } }
                    fun wind(y:Int,x:Int,times:Int){
                        for(i in 0..4){
                            val ny = y+dy[i]
                            val nx = x+dx[i]
                            if(ny in 0 until n && nx in 0 until n){
                                copyBoard[ny][nx] += times
                                if(copyBoard[ny][nx]>3) copyBoard[ny][nx] -= 4
                            }
                        }
                    }
                    for(i in 0 until n){
                        val winds = curTry[i]
                        tried +=  winds
                        wind(0,i,winds)
                    }
                    for(y in 1 until n){
                        for(x in  0 until n){
                            val upside = copyBoard[y-1][x]
                            if(upside!=0){
                                val winds = 4-upside
                                tried+= winds
                                wind(y,x,winds)
                            }
                        }
                    }
                    if(copyBoard[n-1].all{it==0}){
                        answer = tried.coerceAtMost(answer)
                    }
                }
                else{
                    for(i in 0..3){
                        curTry[depth] = i
                        dfs(depth+1)
                    }
                }
            }
            dfs(0)

            return answer
        }
    }
    val board = arrayOf(
        intArrayOf(0,3,3,0),
        intArrayOf(3,2,2,3),
        intArrayOf(0,3,2,0),
        intArrayOf(0,3,3,3)
    )
    println((Solution().solution(board)))
}