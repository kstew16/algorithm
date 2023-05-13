fun main(){
    val dp = Array(10){y->LongArray(10){x->
        if(x==0 || y==0) 1
        else 0
    } }
    for(j in 1 until 10){
        for(i in 1 until 10){
            dp[j][i] = dp[j-1][i] + dp[j][i-1]
        }
    }

    println(dp[9][9])
}