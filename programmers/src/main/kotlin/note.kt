class MyClass(name:String){
    var myName = name
    set(name){ println("my name donnot changes! no $name") }
    fun sayMyName(){ println(myName)}
}
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
    //println(dp[9][9])
    val c = MyClass("BigBoy")
    c.sayMyName()
    c.myName="BABO"
    c.sayMyName()
    val hm = hashMapOf<Int,Int>()

    c.let {
        it.myName="bubu"
        it.sayMyName()
    }
    c.apply {
        myName = "BABA"
        sayMyName()
    }
    val sayHi = println("hi")
    run{ sayHi }
    val sayHi2 = {
        val c = 12
        c*10
    }
    println(sayHi2)


}