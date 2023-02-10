package list05_interface

interface Clickable {
    fun click()
    fun defaultImpl() = println("これはデフォルトの実装です")
    val color: String

    // プロパティのデフォルト値も設定できるみたい
    val size: String
        get() = "normal"
}

class Button(override val color: String) : Clickable {
    override fun click() = println("interface button")
}

fun main(args: Array<String>) {
    val button = Button("Red")
    button.click()

    // interface のデフォルト実装
    button.defaultImpl()

    // プロパティをもたせる
    println("color is ${button.color}")

    // プロパティにデフォルト値をもたせる
    println("size is ${button.size}")
}
