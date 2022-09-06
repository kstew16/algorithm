fun main(){
    val field = Array(300){
        IntArray(800){0}
    }
    println("300 800")
    field.forEach {
        println(it.joinToString(" "))
    }
    println("150 400 1 1 150 400")
}