package first

import kotlin.math.*
fun main(){
    class Solution {
        fun solution(a: Int, b: Int, g: IntArray, s: IntArray, w: IntArray, t: IntArray): Long {
            val n = g.size
            var lo = 0L
            // 한 도시에 10^9 의 은과, 10^9 의 금이 모두 있는데
            // t[i] == 10^5 이고 w[i] == 1 이면 2*10^5(2*(10^9-1)+1)
            var hi = (10.0).pow(14).toLong()*4
            var mid:Long
            fun check(h:Long):Boolean{

                var mg = 0L
                var ms = 0L
                var gs = 0L
                for(i in 0 until n){
                    val nMove = if(h<t[i]) 0 else (1+(h-t[i])/(2*t[i]))
                    val capacity = if(nMove<=0) 0 else nMove.toLong()*w[i]
                    if(g[i]>=capacity){ // 금으로만 꽉 채울 수 있는 경우
                        mg += capacity
                        // 있는 은 양 또는 수용량만큼 은으로 바꿔 싣는 것도 가능
                        gs += min(s[i].toLong(),capacity)
                    }else{ // capacity>g[i] 은도 더 실을 수 있는 경우
                        mg += g[i]
                        // 금 다 싣고도 옮길 수 있는 은의 양
                        val localMs = min(s[i].toLong(),capacity - g[i]).toLong()
                        val silverLeft = (s[i] - localMs).toLong()
                        ms += localMs
                        gs += min(g[i].toLong(),silverLeft)
                    }
                }
                // 박박 긁어도 금조차 못채우면 실패
                return if(mg<a) false
                else{ // mg>=a
                    // 은으로 바꿔실을 금의 양은 치환최대치와 넘친 금의 양 중 작은 것
                    val moreSilver = min(mg-a,gs)
                    ms+moreSilver >= b
                }

            }
            while(lo+1<hi){
                mid = (lo+hi)/2L
                if(check(mid)) hi = mid
                else lo = mid
            }
            return if(check(lo)) lo
            else hi
        }
    }
}