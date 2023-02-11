/**
 * listと違い
 * - setは値が重複しない
 * - setは順序を持たない
 *
 * さまざまなメソッドがある
 * https://qiita.com/opengl-8080/items/36351dca891b6d9c9687
 */
fun main(args: Array<String>) {

    // setOf
    println("=== setOf ===")
    val numbers = setOf(1, 2, 3, 4)
    if (numbers.contains(1)) {
        println("1 is in the set")
    }
    println(numbers.toList())

    // mutableSetOf
    println("=== mutableSetOf ===")
    val mNumbers = mutableSetOf(4, 2, 3, 1)
    mNumbers.add(5)
    println(mNumbers)
    mNumbers.remove(1)
    println(mNumbers)
    println(mNumbers.sorted())
}
