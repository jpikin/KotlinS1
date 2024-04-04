class AddCommand(val person: Person): Command(){
    override fun isValid(): Boolean {
        return person.phone.matches(Regex("""[+][0-9]{11}"""))
                || person.email.matches(Regex("[a-zA-Z0-9._%+-]+@[A-z]+\\.[A-z]{2,}"))
    }

}
sealed class Command {
    abstract fun isValid():Boolean
}
class ShowCommand(val person: Person?): Command() {
    override fun isValid(): Boolean {
        return true
    }
}

data class Person(val name:String, val phone: String, val email: String)
fun main() {
    val contacts = ArrayList<Person>()


    while (true) {
        print("Введите команду: ")

        when(val input = readlnOrNull()) {
            "exit" -> {
                println("Программа завершена.")
                break
            }
            "help" -> {
                println(
                    """Список команд:
                exit - завершить программу
                help - показать список команд
                add <Имя> phone <Номер телефона> - добавить контакт с номером телефона
                add <Имя> email <Адрес электронной почты> - добавить контакт с email адресом
                """)
            }
            else -> {
                try {
                    val command = readCommand(input ?: "")
                    if (command.isValid()) {
                        when (command) {
                            is AddCommand -> {
                                contacts.add(command.person)
                                println("Контакт успешно добавлен. $contacts")
                            }
                            is ShowCommand -> {
                                val lastContact = contacts.lastOrNull()
                                println(lastContact ?: "Not initialized")
                            }
                        }
                    } else {
                        println("Некорректные данные для команды. Введите 'help' для списка команд.")
                    }
                } catch (e: IllegalArgumentException) {
                    println("Некорректная команда. Введите 'help' для списка команд.")
                }
            }

        }
    }
}

fun readCommand(input: String): Command{
    val regexAdd = Regex("""add (\D+) (phone|email) (.+)""")

    return when {
        input == "show" -> ShowCommand(null)

        regexAdd.matches(input) -> {
            val matchResult = regexAdd.find(input)!!
            val name = matchResult.groupValues[1]
            val type = matchResult.groupValues[2]
            val value = matchResult.groupValues[3]

            when (type) {
                "phone" -> AddCommand(Person(name, value, ""))
                "email" -> AddCommand(Person(name, "", value))
                else -> throw IllegalArgumentException("Invalid command format")
            }
        }
        else -> throw IllegalArgumentException("Invalid command format")
    }
}