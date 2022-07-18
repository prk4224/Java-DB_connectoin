import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main(args: Array<String>) {

    val br = BufferedReader(InputStreamReader(System.`in`))
    val array1 = br.readLine()!!.split(' ').map{ it.toInt()}
    val array2 = br.readLine()!!.split(' ').map{ it.toInt()}

    println(array1)
    println(array2)

}

