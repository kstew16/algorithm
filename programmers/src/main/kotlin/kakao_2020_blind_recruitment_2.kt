fun main(){
    class Solution {
        // x,y,buildingType,taskType

        fun solution(n: Int, buildFrame: Array<IntArray>): Array<IntArray> {
            val hasRightLine = Array(n+1){BooleanArray(n+1){false} }
            val hasUpLine = Array(n+1){BooleanArray(n+1){false} }
            fun isValidHorizonLine(x:Int,y:Int):Boolean{
                return (
                    // 한 쪽이 세로선 위에 있음
                    (y>0 && hasUpLine[x][y-1]) || (y>0 && x+1<=n && hasUpLine[x+1][y-1])||
                    // 양 쪽 끝 부분이 가로선과 연결됨
                    ((x>0 && hasRightLine[x-1][y]) && (x+1<=n && hasRightLine[x+1][y]))
                )
            }

            fun isValidVerticalLine(x:Int,y:Int):Boolean{
                return(
                        // 바닥 위에 있거나
                        y==0 ||
                        // 다른 기둥 위에 있거나
                        (y>0 && hasUpLine[x][y-1])||
                        // 다른 보의 끝부분에 있어야 함
                        ((x>0 && hasRightLine[x-1][y])||hasRightLine[x][y])
                )
            }

            for(i in buildFrame.indices){
                val (x,y,buildType,taskType) = buildFrame[i]
                when(taskType){
                    0 ->{ // 삭제 작업 유효성 검사
                        when(buildType){
                            // 세로선 삭제해보고 위에있는게 유효하지 않으면 삭제 취소
                            0->{
                                hasUpLine[x][y] = false
                                if(
                                    (y+1<=n && hasRightLine[x][y+1] && !isValidHorizonLine(x,y+1))||
                                    (y+1<=n && x>0 && hasRightLine[x-1][y+1] && !isValidHorizonLine(x-1,y+1))||
                                    (y+1<=n && hasUpLine[x][y+1] && !isValidVerticalLine(x,y+1))
                                ) hasUpLine[x][y] = true
                            }
                            1->{ // 가로선 삭제해보고 위에있는게 유효하지 않으면 삭제 취소
                                // 가로선과 연결되어있다면 그 가로선이 기둥과 연결돼있어야 함
                                hasRightLine[x][y] = false
                                if(
                                    (x>0 && hasRightLine[x-1][y] && !isValidHorizonLine(x-1,y))||
                                    (hasUpLine[x][y] && !isValidVerticalLine(x,y))||
                                    (x+1 < n && hasRightLine[x+1][y] && !isValidHorizonLine(x+1,y))||
                                    (hasUpLine[x+1][y] && !isValidVerticalLine(x+1,y))
                                ) hasRightLine[x][y] = true
                            }
                        }
                    }
                    1->{ // 설치 작업 유효성 검사
                        when(buildType){
                            // 세로선 설치
                            0->{ if(isValidVerticalLine(x,y)) hasUpLine[x][y] = true }
                            // 가로선
                            1->{ if(isValidHorizonLine(x,y)) hasRightLine[x][y] = true }
                        }
                    }
                }
            }


            val result = ArrayList<IntArray>()
            for(x in 0 .. n){
                for(y in 0 .. n){
                    if(hasUpLine[x][y]) result.add(intArrayOf(x,y,0))
                    if(hasRightLine[x][y]) result.add(intArrayOf(x,y,1))
                }
            }
            return result.toTypedArray()
        }
    }
    val bF = arrayOf(
        intArrayOf(1,0,0,1),
        intArrayOf(1,1,1,1),
        intArrayOf(2,1,0,1),
        intArrayOf(2,2,1,1),
        intArrayOf(5,0,0,1),
        intArrayOf(5,1,0,1),
        intArrayOf(4,2,1,1),
        intArrayOf(3,2,1,1)
    )
    Solution().solution(5,bF).forEach {
        println(it.joinToString(","))
    }
}