package list01_instance

class Empty

class Button {
    fun click() {
        println("clicked!")
    }
}

class ButtonConstructor(private var color: String) {
    fun click() {
        println("$color button was clicked!")
    }
}

fun main(args: Array<String>) {

    // インスタンス化
    val empty = Empty()
    val button = Button()
    button.click()

    // コンストラクタ
    // 補足:
    //  - コンストラクタとはインスタンス生成時に呼び出されるメソッドのこと
    //  - シグニチャとはメソッド名と引数（数、型、順番）のこと
    val subButton = ButtonConstructor("Red")
    // private にすることで外部から参照不可にできる
    // subButton.color = "Blue"
    subButton.click()
}

