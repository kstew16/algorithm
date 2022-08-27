fun main(){
    val n = 300
    val m = 500
    val arr = Array(n){y ->
        IntArray(m){x->
            y*m+x
        }
    }
    println("$n $m")
    arr.forEach {
        print(it.joinToString(" "))
        println()
    }

}