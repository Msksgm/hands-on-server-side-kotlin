/**
 * sealed class のメリット
 *  - コンパイル時の予測可能な列挙型を記述できる
 *  - 意図しない型を防げる
 *  - switch case文を簡潔に記述できる
 *  - サブクラスの追加が容易
 */

/**
 * sealed classは、enumにclassの柔軟性を付与したようなものです。
 * enumのようにwhen式と併用することで、全パターンのサブクラスをチェックしてくれます。
 * また制限として同一パッケージ内でしかサブクラスは記述できません。
 */
package list09_sealedModifier01

interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr

fun eval(e: Expr): Int =
    when (e) {
        is Num -> e.value
        is Sum -> eval(e.right) + eval(e.left)
        else -> throw IllegalArgumentException("Unknown expression")
    }

sealed class Expr2 {
    class Num(val value: Int) : Expr2()
    class Sum(val left: Expr2, val right: Expr2) : Expr2()
}

fun Eval2(e: Expr2): Int =
    when (e) {
        is Expr2.Num -> e.value
        is Expr2.Sum -> Eval2(e.right) + Eval2(e.left)
    }

fun main() {
    // sealed class なし
    println(eval(Sum(Sum(Num(1), Num(2)), Num(4))))

    // sealed class あり
    println(Eval2(Expr2.Sum(Expr2.Sum(Expr2.Num(1), Expr2.Num(2)), Expr2.Num(4))))
}
