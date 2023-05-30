fun main(){
    open class MyToy :ToyInterface{
        override val age: Int
            get() = 5
        override fun playWith() {
            super.playWith()
            println("I'm $age years old!")
        }
    }
    class NewToy:MyToy(){
        override fun playWith() {
            //super.playWith()
            println("hey!")
        }
    }

    val m = MyToy()
    m.playWith()
    val n = NewToy()
    n.playWith()

}