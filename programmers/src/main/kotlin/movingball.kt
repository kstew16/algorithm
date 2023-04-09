fun main(){
    class Solution {
        fun solution(n: Int, m: Int, r: Int, c: Int, queries: Array<IntArray>): Long {
            // minY <= y < maxY && minX <= x < maxX 였던 범위에서만 목표지에 도달할 수 있음을 의미
            var minY = r
            var maxY = r+1
            var minX = c
            var maxX = c+1
            for(i in queries.size-1 downTo 0){
                if(minY==maxY || minX == maxX) return 0L
                val (queryType,movement) = queries[i]
                when(queryType){
                    0->{ // 전턴 좌향명령, minX 바운더리
                        maxX = (maxX+movement).coerceAtMost(m)
                        if(minX!=0) minX = (minX+movement).coerceAtMost(m)
                    }
                    1->{ // 전턴 우향, 역추적은 감소
                        minX = (minX-movement).coerceAtLeast(0)
                        if(maxX!=m) maxX = (maxX-movement).coerceAtLeast(0)
                    }
                    2->{ // 전턴 상향, 역추적은 증가
                        maxY = (maxY+movement).coerceAtMost(n)
                        if(minY!=0) minY = (minY+movement).coerceAtMost(n)
                    }
                    3->{ // 전턴 하향
                        minY = (minY-movement).coerceAtLeast(0)
                        if(maxY!=n) maxY = (maxY-movement).coerceAtLeast(0)
                    }
                }
            }
            return 1L*(maxY - minY)*(maxX-minX)
        }
    }
}