package first

import kotlin.math.pow
fun main(){
    class Solution {
        infix fun Int.on(i:Int):Int = this or (1 shl i)
        infix fun Int.toggle(i:Int):Int = this xor (1 shl i)
        infix fun Int.chk(i:Int):Boolean = this and (1 shl i)>=1
        fun solution(beginning: Array<IntArray>, target: Array<IntArray>): Int {
            var answer: Int = 1000
            val n = beginning.size
            val m = beginning[0].size
            // 다르면 1 같으면 0

            val originalState = IntArray(n){ y->
                var row = 0
                for(x in 0 until m){
                    if(beginning[y][x]!=target[y][x]) row = row on x
                }
                row
            }

            val yLimit =  (2.0).pow(n).toInt()
            val xLimit = (2.0).pow(m).toInt()
            for(yChoice in 0 until yLimit){
                l1@ for(xChoice in 0 until xLimit){
                    val curState = IntArray(n){originalState[it]}
                    var moved = 0
                    for(i in 0 until n){
                        if(yChoice chk i){
                            if(++moved>=answer) continue@l1
                            curState[i] = curState[i] xor xLimit-1
                        }
                    }
                    for(i in 0 until m){
                        if(xChoice chk i){
                            if(++moved>=answer) continue@l1
                            for(y in 0 until n) curState[y] = curState[y] toggle i
                        }
                    }
                    for(i in 0 until n) if(curState[i]!=0) continue@l1
                    answer = moved.coerceAtMost(answer)
                }
            }

            return if(answer==1000) -1 else answer
        }
    }

}