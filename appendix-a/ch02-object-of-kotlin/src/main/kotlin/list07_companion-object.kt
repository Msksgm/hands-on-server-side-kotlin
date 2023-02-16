/**
 * companion objectのサンプルコード
 *
 * privateコンストラクタのFactoryメソッド
 * メリット:
 *  - 他のクラスからは直接インスタンス化できないため、Factory メソッドを通じてのみインスタンス生成が可能になる
 */
package list07_objectDeclaration

class PointXY private constructor(val x: Int, val y: Int) {
    companion object {
        fun create(x: Int, y: Int): PointXY {
            return PointXY(x, y)
        }
    }
}

fun main(args: Array<String>) {
    println(PointXY.create(1, 2))
}
