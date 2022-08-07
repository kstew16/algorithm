import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main()=with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val areaPopulation = Array(n){
        val st = StringTokenizer(readLine())
        IntArray(n){
            st.nextToken().toInt()
        }
    }
    val checkArea = Array(n){
        IntArray(n){0}
    }
    val localPopulation = IntArray(5){0}
    var minDifference = Int.MAX_VALUE

    fun countPopulations(x:Int,y:Int,d1:Int,d2:Int){
        fun reset(){
            for(i in localPopulation.indices) localPopulation[i]=0
            (0 until n).forEach{
                i->
                (0 until n).forEach {
                    j->
                    checkArea[i][j] = 0
                }
            }
        }
        fun colorCountArea(){
            // 경계선 채우기
            for(i in 0..d1){
                checkArea[x-1+i][y-1-i]=5
                checkArea[x+d2-1+i][y+d2-1-i]=5
            }

            for(j in 1 until d2){
                checkArea[x+j-1][y+j-1]=5
                checkArea[x+j-1+d1][y+j-1-d1]=5
            }

            // 경계선 내부의 지역을 색칠
            (1..n).forEach {
                r->
                var from = 0
                var to = 0
                (1..n).forEach {
                    c->
                    if(checkArea[r-1][c-1]==5){
                        if(from == 0) from = c
                        else to = c
                    }
                }
                if(from != 0 && to !=0){
                    (from..to).forEach {
                        checkArea[r-1][it-1] = 5
                    }
                }
            }

            for(c in 1 .. n){
                for(r in 1 .. n){
                    if(checkArea[r-1][c-1]!=5){
                        if((r in 1 until x+d1)&&(c in 1..y))checkArea[r-1][c-1] = 1
                        else if((r in 1..x+d2)&&(c in y+1..n))checkArea[r-1][c-1] = 2
                        else if((r in x+d1..n)&&(c in 1 until y-d1+d2))checkArea[r-1][c-1] = 3
                        else if((r in x+d2+1..n)&&(c in y-d1+d2..n))checkArea[r-1][c-1] = 4
                    }
                    localPopulation[checkArea[r-1][c-1]-1] += areaPopulation[r-1][c-1]
                }
            }
        }

        reset()
        colorCountArea()
        var localMax = 0
        var localMin = Int.MAX_VALUE
        localPopulation.forEach {
            localMax = it.coerceAtLeast(localMax)
            localMin = it.coerceAtMost(localMin)
        }
        minDifference = (localMax-localMin).coerceAtMost(minDifference)
    }

    for(x in 1 .. n){
       for(y in 1 .. n){
           for(d1 in 1 .. (n - x - 1).coerceAtMost(y-1)){
               for(d2 in 1 .. (n-y).coerceAtMost(n-x-d1)){
                   countPopulations(x,y,d1,d2)
               }
           }
       }
    }

    print(minDifference)
}