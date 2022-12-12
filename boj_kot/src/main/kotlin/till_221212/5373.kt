import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*
//푸는데 3시간 걸린빡구현문제 근데 이거 그냥 더 똑똑한 방법이 없다는 듯 하던데?
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    data class Side(var looking:Int, var y:Int, var x:Int, var z:Int, val color:Char)
    class Cube(val sides:Array<Side>){
        fun rotateCClockwise(sideInfo:Char){
            when(sideInfo){
                'U'->{
                    this.sides.filter { it.z==0 }.forEach {
                        val ny = 2-it.x
                        val nx = it.y
                        it.x = nx
                        it.y = ny
                        if(it.looking!=0)  it.looking = when(it.looking){
                            4 -> 3; 3 -> 2; 2 -> 1; 1 -> 4
                            else -> 1023456
                        }
                    }
                }
                'D'->{
                    this.sides.filter { it.z==2 }.forEach {
                        val ny = it.x
                        val nx = 2 - it.y
                        it.x = nx
                        it.y = ny
                        if(it.looking!=5)  it.looking = when(it.looking){
                            4 -> 1; 1 -> 2; 2 -> 3; 3 -> 4
                            else -> 1023456
                        }
                    }
                }
                'F'->{
                    this.sides.filter { it.y==2 }.forEach {
                        val nx = it.z
                        val nz = 2 - it.x
                        it.x = nx
                        it.z = nz
                        if(it.looking!=2) it.looking = when(it.looking){
                            0 -> 3; 3 -> 5; 5 -> 1; 1 -> 0
                            else -> 1023456
                        }
                    }
                }
                'B'->{
                    this.sides.filter { it.y==0 }.forEach {
                        val nx = 2 - it.z
                        val nz = it.x
                        it.x = nx
                        it.z = nz
                        if(it.looking!=4) it.looking = when(it.looking){
                            0 -> 1; 1 -> 5; 5 -> 3; 3 -> 0
                            else -> 1023456
                        }
                    }
                }
                'L'->{
                    this.sides.filter { it.x==0 }.forEach {
                        val ny = it.z
                        val nz = 2 - it.y
                        it.y = ny
                        it.z = nz
                        if(it.looking!=3) it.looking = when(it.looking){
                            0 -> 4; 4 -> 5; 5 -> 2; 2 -> 0
                            else -> 1023456
                        }
                    }
                }
                'R'->{
                    this.sides.filter { it.x==2 }.forEach {
                        val ny = 2 - it.z
                        val nz = it.y
                        it.y = ny
                        it.z = nz
                        if(it.looking!=1) it.looking = when(it.looking){
                            0 -> 2; 2 -> 5; 5 -> 4; 4 -> 0
                            else -> 1023456
                        }
                    }
                }
            }
        }
        fun rotateClockwise(sideInfo:Char){
            when(sideInfo){
                'U'->{
                    this.sides.filter { it.z==0 }.forEach {
                        val ny = it.x
                        val nx = 2 - it.y
                        it.x = nx
                        it.y = ny
                        if(it.looking!=0){
                            it.looking = when(it.looking){
                                4 -> 1; 1 -> 2; 2 -> 3; 3 -> 4
                                else -> 1023456
                            }
                        }
                    }
                }
                'D'->{
                    this.sides.filter { it.z==2 }.forEach {
                        val ny = 2 - it.x
                        val nx = it.y
                        it.x = nx
                        it.y = ny
                        if(it.looking!=5){
                            it.looking = when(it.looking){
                                4 -> 3; 3 -> 2; 2 -> 1; 1 -> 4
                                else -> 1023456
                            }
                        }
                    }
                }
                'F'->{
                    this.sides.filter { it.y==2 }.forEach {
                        val nx = 2 - it.z
                        val nz = it.x
                        it.x = nx
                        it.z = nz
                        if(it.looking!=2) it.looking = when(it.looking){
                            0 -> 1; 1 -> 5; 5 -> 3; 3 -> 0
                            else -> 1023456
                        }
                    }
                }
                'B'->{
                    this.sides.filter { it.y==0 }.forEach {
                        val nx = it.z
                        val nz = 2 - it.x
                        it.x = nx
                        it.z = nz
                        if(it.looking!=4) it.looking = when(it.looking){
                            0 -> 3; 3 -> 5; 5 -> 1; 1 -> 0
                            else -> 1023456
                        }
                    }
                }
                'L'->{
                    this.sides.filter { it.x==0 }.forEach {
                        val ny = 2 - it.z
                        val nz = it.y
                        it.y = ny
                        it.z = nz
                        if(it.looking!=3) it.looking = when(it.looking){
                            0 -> 2; 2 -> 5; 5 -> 4; 4 -> 0
                            else -> 1023456
                        }
                    }
                }
                'R'->{
                    this.sides.filter { it.x==2 }.forEach {
                        val ny = it.z
                        val nz = 2 - it.y
                        it.y = ny
                        it.z = nz
                        if(it.looking!=1) it.looking = when(it.looking){
                            0 -> 4; 4 -> 5; 5 -> 2; 2 -> 0
                            else -> 1023456
                        }
                    }
                }
            }
        }
    }
    fun bufferUpperSide(cube:Cube,bw:BufferedWriter){
        val upperSide = Array(3){CharArray(3)}
        cube.sides.filter { it.looking==0 }.forEach {
            upperSide[it.y][it.x] = it.color
        }
        upperSide.forEach {
            bw.write(it)
            bw.write("\n")
        }
    }
    fun initCube():Cube{
        val sideMutableList = mutableListOf<Side>()
        for(y in 0 until 3) for(x in 0 until 3) sideMutableList.add(Side(0,y,x,0,'w'))
        for(y in 0 until 3) for(x in 0 until 3) sideMutableList.add(Side(5,y,x,2,'y'))
        for(z in 0 until 3) for(x in 0 until 3) sideMutableList.add(Side(2,2,x,z,'r'))
        for(z in 0 until 3) for(x in 0 until 3) sideMutableList.add(Side(4,0,x,z,'o'))
        for(y in 0 until 3) for(z in 0 until 3) sideMutableList.add(Side(3,y,0,z,'g'))
        for(y in 0 until 3) for(z in 0 until 3) sideMutableList.add(Side(1,y,2,z,'b'))
        return Cube(Array(54){sideMutableList[it]})
    }
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    repeat(st.getInt()){
        val commandSize = readLine().toInt()
        val command = readLine().split(" ")
        val cube = initCube()
        command.forEach {
            if(it[1]=='-') cube.rotateCClockwise(it[0])
            else cube.rotateClockwise(it[0])
            //println(it)
        }
        bufferUpperSide(cube,bw)
    }
    bw.flush()
    bw.close()
    close()
}