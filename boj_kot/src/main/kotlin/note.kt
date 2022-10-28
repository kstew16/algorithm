fun main(){

    val alphabet = CharArray(26).apply{
        run { ('A'..'Z').forEachIndexed { index, c -> this[index] = c } }
    }
    for(i in 0 until 2500){
        print(alphabet[i%alphabet.size])
    }
}