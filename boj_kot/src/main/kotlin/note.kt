fun main(){
    println("1000 1000")
    val sb = StringBuilder("")
    repeat(1000){
        repeat(1000){sb.append(0)}
        sb.append("\n")
    }
    print(sb)
}