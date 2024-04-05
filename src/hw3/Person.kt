package hw3

data class Person(val name: String, var phone: ArrayList<String>, val email: ArrayList<String>) {
    override fun toString(): String {
        return "Имя: $name,\nТелефоны: $phone,\nАдреса e-mail: $email\n---------------------"

    }
}