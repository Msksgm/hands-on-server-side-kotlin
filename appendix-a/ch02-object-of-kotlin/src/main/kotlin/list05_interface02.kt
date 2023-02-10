package list05_interface

interface Name {
    val firstName: String
    val lastName: String
}

class Person(override val firstName: String, override val lastName: String) : Name

fun main(args: Array<String>) {
    val person = Person("Alice", "Sample01")
    println("firstname:${person.firstName}, lastName:${person.lastName}")
}
