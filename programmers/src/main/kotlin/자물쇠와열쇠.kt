import java.util.LinkedList
fun main(){
    class Coord(val y:Int,val x:Int){
        fun rotateIn(m:Int) = Coord(this.x,m-1-this.y)
    }
    class Solution {
        fun solution(key: Array<IntArray>, lock: Array<IntArray>): Boolean {
            val m = key.size; val n = lock.size
            val keySet = Array(4){ArrayList<Coord>()}
            // 1. 열쇠 회전체 구성 : Array offset  회전 방식으로 (x,y) -> (n-y,x) : m^2 레벨
            for(y in 0 until m){
                for(x in 0 until m){
                    if(key[y][x]==1){
                        var keyPartCoord = Coord(y,x)
                        keySet[0].add(keyPartCoord)
                        repeat(3){
                            keyPartCoord = keyPartCoord.rotateIn(m)
                            keySet[it+1].add(keyPartCoord)
                        }
                    }
                }
            }
            // 2. Lock에 key.size-1 만큼의 패딩을 넣고, Lock 범위 계산한 새로운 영역 도출하며 빈 부분 좌표리스트 작성 n^2 레벨
            val padding = m-1
            val filled = Array(n+2*padding){y->BooleanArray(n+2*padding){x->false}}
            val checkList = ArrayList<Coord>()
            for(y in 0 until n){
                for(x in 0 until n){
                    if(lock[y][x]==1) filled[y+padding][x+padding] = true
                    else checkList.add(Coord(y+padding,x+padding))
                }
            }
            if(checkList.isEmpty()) return true
            // 3. 0 until n+padding 까지 열쇠를 끼워 보면서 완료시 바로 반환, (n+m)^2*4*m^2(최악) 약 10,000,000
            val filledTmp = LinkedList<Coord>()
            keySet.forEach{ coords->
                for(y in 0 until n+padding){
                    for(x in 0 until n+padding){

                        var valid = true
                        // 기준점 y,x 에 대해 키들 끼우고
                        for(i in coords.indices){
                            val ny = y + coords[i].y
                            val nx = x + coords[i].x
                            if(ny in padding until n+padding && nx in padding until n+padding){
                                if(!filled[ny][nx]){
                                    filled[ny][nx] = true
                                    filledTmp.add(Coord(ny,nx))
                                }else{ // 이 부분 구현 안해서 틀림
                                    valid = false
                                    break
                                }
                            }
                        }
                        // 다 찼는지 확인

                        var j = 0
                        while(valid && j in checkList.indices){
                            val cur = checkList[j]
                            if(!filled[cur.y][cur.x]) valid = false
                            j++
                        }
                        if(valid) return true
                        else{
                            while(filledTmp.isNotEmpty()){
                                val cur = filledTmp.pollFirst()
                                filled[cur.y][cur.x] = false
                            }
                        }
                    }
                }

            }

            return false
        }
    }

}
