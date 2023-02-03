package list02_getterSetter

class Button(val color: String, var number: Int) {
    fun click() {
        println("$number of $color button was clicked!")
    }
}

fun main(args: Array<String>) {

    val button = Button("Red", 3)
    // getter が呼びされている
    println("button.color: ${button.color}")
    println("button.number: ${button.number}")
    button.click()

    // button.color = "Blue" コンパイルエラーが発生する
    // setter が呼び出されている
    button.number = 1
    button.click()
}


// 以下 Go のコード
//type button struct {
//    color string
//}
//
//func (b button) getColor() string {
//    return b.color
//}
//
//func (b *button) setColor(color string) {
//    b.color = color
//}
//
//func main() {
//    b := button{color: "Red"}
//    fmt.Printf("%+v \n", b)
//    b.setColor("Blue")
//    fmt.Println(b.getColor())
//}