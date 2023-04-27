package first

fun main(){
    class Solution {
        fun solution(board: Array<IntArray>, skill: Array<IntArray>): Int {
            var answer = 0
            val h = board.size
            val w = board[0].size

            val powerPropagation =  Array(h){IntArray(w){0} }
            for(i in skill.indices){
                val power = if(skill[i][0]==1) -skill[i][5] else skill[i][5]
                val (_,sY,sX,fY,fX) = skill[i]
                powerPropagation[sY][sX] += power
                if(fY+1<h) powerPropagation[fY+1][sX] -= power
                if(fX+1<w) powerPropagation[sY][fX+1] -= power
                if(fY+1<h && fX+1<w) powerPropagation[fY+1][fX+1] += power
            }


            for(j in 0 until h){
                if(j+1<h) for(i in 0 until w)  powerPropagation[j+1][i] += powerPropagation[j][i]
                var curEnergy = IntArray(w){0}
                for(i in 0 until w){
                    // 새로운 전파 수용
                    curEnergy[i] += powerPropagation[j][i]
                    // 에너지 적용 및 결과 확인
                    board[j][i] += curEnergy[i]
                    if(board[j][i]>0) answer++
                    // 에너지 우로 전파
                    if(i+1<w) powerPropagation[j][i+1] += powerPropagation[j][i]
                }
            }


            return answer
        }
    }
    val limit = 10
    val board = Array(limit){
        IntArray(limit){1000}
    }
    var test = 0
    val skill = Array(250000){
        if(it>=125500) {
            test+=1
            intArrayOf(2,0,0,limit-2,limit-2,1)
        }
        else {
            test-=1
            intArrayOf(1,0,0,limit-2,limit-2,1)
        }
    }

    val board2 = arrayOf(
            intArrayOf(5,5,5,5,5),
            intArrayOf(5,5,5,5,5),
            intArrayOf(5,5,5,5,5),
            intArrayOf(5,5,5,5,5)
    )
    val skill2 = arrayOf(
            intArrayOf(1,0,0,3,4,4),
            intArrayOf(1,2,0,2,3,2),
            intArrayOf(2,1,0,3,1,2),
            intArrayOf(1,0,1,3,3,1)
    )
    println(Solution().solution(board2,skill2))
    //println(first.Solution().solution(board,skill))
}
