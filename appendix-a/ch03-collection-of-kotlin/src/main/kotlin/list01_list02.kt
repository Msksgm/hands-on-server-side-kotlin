data class Person(var name: String, var age: Int)

fun main(args: Array<String>) {

    val alice = Person("Alice", 31)
    val bob = Person("Bob", 32)
    val people = listOf(alice, bob)
    println(people)
    bob.age = 33
    println(people)
}
