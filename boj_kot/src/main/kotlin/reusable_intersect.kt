class Point(val x:Long, val y:Long): Comparable<Point>{
    override fun compareTo(other:Point): Int {
        return when{
            this.x<other.x -> -1
            this.x==other.x ->{
                if(this.y<other.y)-1 else if(this.y==other.y) 0 else 1
            }
            else -> 1
        }
    }
}
fun getCurl(p1:Point,p2:Point,p3:Point):Int{
    var s = (p1.x*p2.y + p2.x*p3.y + p3.x*p1.y) - (p2.x*p1.y + p3.x*p2.y + p1.x*p3.y)
    return if(s>0) 1 else if(s==0L) 0 else -1
}
class Line(var point1:Point, var point2:Point){
    fun isIntersect(l2:Line):Boolean{
        var p1 = this.point1
        var p2 = this.point2
        var p3 = l2.point1
        var p4 = l2.point2

        // 1 번 라인의 기준에서 CCW 1 일직선 0 CW -1
        val curl1 = getCurl(p1,p2,p3)
        val curl2 = getCurl(p1,p2,p4)
        val cross1 = curl1*curl2
        // 2 번 라인의 기준에서
        val curl3 = getCurl(p3,p4,p1)
        val curl4 = getCurl(p3,p4,p2)
        var cross2 = curl3*curl4
        // 네 점이 일직선 상에 위치하는 경우
        return if(cross1 == 0 && cross2 == 0){
            // p1 이 p2 보다 클 경우
            if(p1>p2){
                var tmp = p2
                p2 = p1
                p1 = tmp
            }
            if(p3>p4){
                var tmp = p4
                p4 = p3
                p3 = tmp
            }
            (p3<=p2 && p1<=p4)
        } else (cross1 <= 0 && cross2 <= 0)
    }
}