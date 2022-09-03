fun main(){
    val cArr = mutableListOf<Char>()
    for(c in 'a' .. 'z') cArr.add(c)
    for(c in '0' .. '9') cArr.add(c)
    for(c in 'A' .. 'Z') cArr.add(c)
    println("50 50")
    val table = Array(50){
        CharArray(50){'.'}
    }
    for(i in 0 until 50){
        val char = cArr[i]
        for(y in 0 until (50-i)){
            for(x in 0 until (50-i)){
                table[y][x] = char
            }
        }
    }
    table.forEach {
        println(it.joinToString(""))
    }
}