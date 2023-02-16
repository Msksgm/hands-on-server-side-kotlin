/**
 * enumは、特定の値の一覧をひとまとめに表現したものです。
 * enumはwhen式と併用すると、全パターンのチェックをコンパイラにさせることができます。
 * これにより考慮不足からの意図せぬエラーをコンパイルの段階で防ぐことができます。
 */
package list08_enum

enum class Color {
    RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, VIOLET
}

fun getJapaneseColor(color: Color) =
    when (color) {
        Color.RED -> "赤"
        Color.ORANGE -> "橙"
        Color.YELLOW -> "黄"
        Color.GREEN -> "緑"
        Color.BLUE -> "青"
        Color.INDIGO -> "藍"
        Color.VIOLET -> "紫"
    }

fun getWarmth(color: Color) = when (color) {
    Color.RED, Color.ORANGE, Color.YELLOW -> "暖色"
    Color.GREEN -> "中性色"
    Color.BLUE, Color.INDIGO, Color.VIOLET -> "寒色"
    else -> "その他の色"
}

fun main(args: Array<String>) {
    println(getJapaneseColor(Color.BLUE))
    println(getWarmth(Color.GREEN))
}
