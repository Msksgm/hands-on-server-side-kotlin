package list03_anyClassMethod

class Person(val firstName: String, val lastName: String) {
    override fun equals(other: Any?): Boolean {
        // other（Any? 型）が Person 型なのか判定し、Person 型でない場合、false で早期 return
        if (other == null || other !is Person) {
            return false
        }
        return other.firstName == firstName && other.lastName == lastName
    }

    override fun hashCode(): Int = firstName.hashCode() * 31 + lastName.hashCode()
}

fun main(args: Array<String>) {
    val person1 = Person("Alice", "Sara")
    val person2 = Person("Alice", "Sara")
    println(person1 == person2)
}
