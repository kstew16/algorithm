fun main(){
    println("100 100 9900")
    for(i in 1..100) println("$i 2 1 3 ${9901-i}")
    var i = 100
    for(x in 3..100) for(y in 1..100){
        i++
        println("$y $x 1 3 ${9901-i}")
    }
}