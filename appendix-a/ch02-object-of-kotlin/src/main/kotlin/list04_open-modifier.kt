package list04_openModifier

open class Button(val color: String) {
    fun click() {
        println("$color button was clicked!")
    }

    open fun soundEffect() {
        println("Click")
    }
}

class miniButton(color: String) : Button(color) {
    override fun soundEffect() {
        println("Boom")
    }
}

fun main(args: Array<String>) {
    // 親クラス
    val button = Button("Red")
    button.click()
    button.soundEffect()

    // 子クラス
    val miniButton = miniButton("Blue")
    miniButton.click()
    miniButton.soundEffect()
}
