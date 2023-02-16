/**
 * object宣言のサンプルコード
 */
package list07_objectDeclaration

object Red {
    fun getRgb(): String {
        return "rgb(255, 0, 0)"
    }

    fun getHsv(): String {
        return "hsv(0, 100, 100)"
    }
}

fun main(args: Array<String>) {
    println(Red.getRgb())
    println(Red.getHsv())
}
