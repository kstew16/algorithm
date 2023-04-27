package first

fun main(){
    fun decodeTimeToSec(t:String):Int{
        return t.split(":").mapIndexed { index, s ->
            when(index){
                0-> 3600*s.toInt()
                1-> 60*s.toInt()
                else -> s.toInt()
            }
        }.sumOf { it }
    }

    fun encodeSecToTime(s:Int): String {
        val h = s/3600
        val m = (s-(h*3600))/60
        val s = s - (h*3600+m*60)
        val hh = if(h.toString().length==2) h.toString() else "0$h"
        val mm = if(m.toString().length==2) m.toString() else "0$m"
        val ss = if(s.toString().length==2) s.toString() else "0$s"
        return "$hh:$mm:$ss"
    }

    fun solution(play_time: String, adv_time: String, logs: Array<String>): String {
        var optimalStartSec = 0
        var maxAccPeeredSec = 0L

        // 구간합을 구할 배열의 총 길이, 10초짜리면 0~10초 11개에 영상 끝까지 보는 시청자를 위한 1초 (peer 관련 outOfIndex 방지)
        val playSec = decodeTimeToSec(play_time)+1
        val advSec = decodeTimeToSec(adv_time) // advSec 만큼의 구간합이 필요함

        // s 초부터 e 초까지 영상을 본 사람을 s~e-1(e-s초)에 대한 시청자로 기록 == e 초부터는 집계에서 뺌
        val logGradient = IntArray(playSec)
        logs.forEach { playSection->
            val (s,e) = playSection.split("-").map { decodeTimeToSec(it) }
            logGradient[s] += 1
            logGradient[e] -= 1
        }

        val peers = IntArray(playSec)
        var curPeer = 0
        for(i in peers.indices){
            curPeer += logGradient[i]
            peers[i] = curPeer
        }

        var curPeeredSec = 0L
        //e-advSec 이상 e미만 까지 영상을 본 사람들을 집계
        repeat(playSec){e->
            if(e-advSec>0){
                curPeeredSec -= peers[e-advSec-1].toLong()
            }
            if(curPeeredSec>maxAccPeeredSec){
                maxAccPeeredSec = curPeeredSec
                optimalStartSec = e-advSec
            }
            curPeeredSec += peers[e].toLong()
        }

        return encodeSecToTime(kotlin.math.max(optimalStartSec,0))
    }
    val play_time = "00:02:00"
    val adv_time = "00:00:30"
    val logs = arrayOf(
        "00:00:00-00:00:10","00:00:00-00:00:10","00:00:05-00:00:15","00:00:05-00:00:15","00:01:49-00:02:00","00:01:49-00:02:00","00:01:49-00:02:00","00:01:49-00:02:00",
    )
    println(solution(play_time,adv_time,logs))
}