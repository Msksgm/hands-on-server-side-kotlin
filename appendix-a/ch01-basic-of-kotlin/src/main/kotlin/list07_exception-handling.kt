import java.lang.NumberFormatException

fun main(args: Array<String>) {

    println("=== throw ===")
    val number = 50
    println(isPercentage(number))

    println("=== try, catch, finally ===")
    val okResult = convertInt("100")
    val ngResult = convertInt("sample")
    println("$okResult, $ngResult")

    println("=== catch の return で値を持たない場合 ===")
    convertIntExp("sample")

    println("=== catch の return で値を持つ場合 ===")
    convertIntExp1("sample")
}

// 戻り値の型が違っても問題ない
fun isPercentage(num: Int): Int =
    if (num in 0..100)
        num
    else
        throw IllegalArgumentException("this is not in range")

fun convertInt(numberString: String): Int? {
    return try {
        val number = numberString.toInt()
        number
    } catch (e: NumberFormatException){
        null
    } finally {
        println("処理が完了しました")
    }
}

fun convertIntExp(numberString: String) {
    val number = try {
        numberString.toInt()
    } catch (e: NumberFormatException){
        -999
    } finally {
        println("処理が完了しました")
    }
    println("数字でない場合は catch で終了するため出力されない")
}

fun convertIntExp1(numberString: String) {
    val number = try {
        numberString.toInt()
    } catch (e: NumberFormatException){
        null
    } finally {
        println("処理が完了しました")
    }
    println("数字でない場合も出力される")
}