import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
import kotlin.math.sqrt


fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    fun eratosthenes(s:Int,e:Int):MutableList<Int>{
        // 에라토스테네스의 체를 이용해 s부터 e 사이의 합성수들을 false 처리
        val isPrime = BooleanArray(e + 1){true}
        val output = mutableListOf<Int>()

        // e 의 제곱근 이하의
        val limit = sqrt(e.toDouble()).toInt()
        for (i in 2..limit){
            // 1을 제외한 소수에 대해서
            if(isPrime[i]){
                // 소수의 배수들을 체에서 제거
                var j = 2
                while (i*j <= e) {
                    if (isPrime[i * j]) isPrime[i * j] = false
                    j++
                }
            }
        }

        for (i in s..e){
            if (i == 0 || i == 1) continue
            if(isPrime[i]) output.add(i)
        }
        return output
    }


    val primeUnder20 = ulongArrayOf(2u,3u,5u,7u,11u,13u,17u,19u)
    class Fraction(var nom:ULong,var denom:ULong){
        fun minimized():Fraction{
            val usingPrime = primeUnder20.filter { it<=kotlin.math.min(nom,denom) }
            for(p in usingPrime){
                while(nom%p == 0uL && denom%p == 0uL){
                    nom/=p
                    denom/=p
                }
            }
            return this
        }
        fun add(b:Fraction):Fraction{
            var newDenom = this.denom*b.denom
            var newNom = this.nom*b.denom+b.nom*this.denom
            if(newNom<this.nom||newNom<this.denom||newNom<b.nom||newNom<b.denom||newDenom<this.denom||newDenom<b.denom){
                // 오버플로우 발생
                for(i in 2 until Int.MAX_VALUE){
                    if(this.denom%i.toULong()==0uL && this.nom%i.toULong()==0uL) println("$i UWAGA For ${this.denom}/${this.nom}")
                    if(b.denom%i.toULong()==0uL && b.nom%i.toULong()==0uL) println("$i UWAGA For ${b.denom}/${b.nom}")
                }
                println("${this.denom}/${this.nom} + ${b.denom}/${b.nom}")
            }
            return Fraction(newNom,newDenom).minimized()
        }
    }

    for(n in 1..20){
        for(bombs in 1..n){

            // dp[b][n] 폭탄이 b개, 상자가 n 개일 때 성공할 수 있는 확률의 분자, 분모 - 경우의 수 렬루 안되나?
            val dp = Array(bombs+1){
                Array(n+1){
                    Fraction(1uL,1uL)
                }
            }.apply { for(i in 2 .. n) this[1][i].denom=i.toULong() }
            for(b in 2 .. bombs){
                var sum = Fraction(0uL,1uL)
                for(i in 0 until n){
                    sum = sum.add(dp[b-1][i])
                    val probability = (i.toULong()+1uL)
                    if(i+1>b) {
                        dp[b][i+1] = if(sum.nom%probability==0uL) Fraction(sum.nom/probability,sum.denom).minimized()
                        else Fraction(sum.nom,sum.denom*probability).minimized()
                    }
                }
            }
            println("for $n boxes and $bombs bombs")
            println("${dp[bombs][n].nom}/${dp[bombs][n].denom}")
        }
    }
}