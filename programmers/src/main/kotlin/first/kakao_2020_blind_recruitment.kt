package first

fun main(){
    class Solution {
        fun solution(key: Array<IntArray>, lock: Array<IntArray>): Boolean {
            fun makeKeySet(key:Array<IntArray>):Array<Array<BooleanArray>>{
                val keySet = Array(4){Array(key.size){BooleanArray(key.size){false}} }
                val m = key.size-1
                for(y in 0 .. m){
                    for(x in 0 .. m){
                        val isKey = key[y][x]==1
                        keySet[0][y][x] = isKey
                        keySet[1][x][m-y] = isKey
                        keySet[2][m-y][m-x] = isKey
                        keySet[3][m-x][y] = isKey

                    }
                }
                return keySet
            }
            val padding = key.size-1
            // 빈 공간이 위치한 부분만을 탐색
            var minX = lock.size
            var maxX = -1
            var minY = lock.size
            var maxY = -1
            var holes = 0
            val lockZone = Array(lock.size+padding*2){y->
                BooleanArray(lock.size+padding*2){x->
                    if(y-padding in lock.indices && x-padding in lock.indices){
                        if(lock[y-padding][x-padding]==0){
                            minX = x.coerceAtMost(minX)
                            minY = y.coerceAtMost(minY)
                            maxX = x.coerceAtLeast(maxX)
                            maxY = y.coerceAtLeast(maxY)
                            holes++
                            false
                        }else true
                    } else false
                }
            }
            val keys = makeKeySet(key)
            // 구멍이 없는 경우 열 수 있다고 보는게 맞지
            if(holes==0) return true
            // 키별로 관심구역을 모두 살펴봄

            val m = key[0].size

            for(k in 0 until 4){
                //var valid = true
                // 각 지점에 맞춰서 키 넣어보기
                var holeFilled = 0
                for(y in minY-padding .. maxY){
                    l1@ for(x in minX-padding .. maxX){
                        // 키 들어서 비교 시작
                        for(j in 0 until m){
                            for(i in 0 until m){
                                if(y+j in padding until lockZone.size-padding && x+i in padding until lockZone.size-padding){
                                    // 자물쇠의 영역 안을 보는 경우 두 개의 돌기와 홈이 서로 다른 지 xor 연산 아니면 리셋하고 다음 위치로 옮겨서 넣어봄
                                    if(!lockZone[y+j][x+i] xor keys[k][j][i]){
                                        holeFilled=0
                                        continue@l1
                                    }else{
                                        if(keys[k][j][i]) holeFilled++
                                    }
                                }
                            }
                        }
                        // 여기까지 왔으면 홈이 어긋나는 부분은 없는 것, 홈을 다 채웠는지 측정
                        if(holeFilled==holes) return true
                    }
                }

            }
            return false
        }
    }
    val key = arrayOf(
        intArrayOf(0,0,0),
        intArrayOf(1,0,0),
        intArrayOf(0,1,1)
    )

    val lock = arrayOf(
        intArrayOf(1,1,1),
        intArrayOf(1,1,1),
        intArrayOf(1,1,1)
    )
    println(Solution().solution(key,lock))
}