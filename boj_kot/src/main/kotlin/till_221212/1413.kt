import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer


fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val primeUnder20 = ulongArrayOf(2u,3u,5u,7u,11u,13u,17u,19u)
    val st = StringTokenizer(readLine())
    val n = st.nextToken().toInt()
    val bombs = st.nextToken().toInt()
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
            return Fraction(newNom,newDenom).minimized()
        }
    }
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
            if(i+1>b) dp[b][i+1] = Fraction(sum.nom,sum.denom*(i.toULong()+1uL)).minimized()
        }
    }
    print("${dp[bombs][n].nom}/${dp[bombs][n].denom}")
}