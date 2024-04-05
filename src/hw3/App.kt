package hw3


fun main() {
    val contacts = ArrayList<Person>()


    while (true) {
        print("Введите команду: ")

        when (val input = readlnOrNull()) {

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
                """
                )
            }

            else -> {
                try {
                    val command = readCommand(input ?: "")
                    if (command.isValid()) {
                        when (command) {
                            is AddCommand -> {
                                val com = input?.split(" ")
                                val name = com?.get(1) ?: ""
                                val type = com?.get(2) ?: ""
                                val value = com?.get(3) ?: ""
                                if (type == "phone") {
                                    addInfo(contacts, name, value, "")
                                } else {
                                    addInfo(contacts, name, "", value)
                                }
                            }

                            is ShowCommand -> {
                                val com = input?.split(" ")
                                showList(contacts, com?.get(1))
                            }
                        }
                    } else {
                        println("Некорректные данные для команды. Введите 'help' для списка команд!")
                    }
                } catch (e: IllegalArgumentException) {
                    println("Некорректная команда. Введите 'help' для списка команд.")
                }
            }
        }
    }
}

fun showList(list: ArrayList<Person>, name: String?) {
    if (list.size == 0) {
        println("Список контактов пуст")
    } else {
        for (person in list) {
            if (person.name == name) {
                println(person)
                return
            }
        }
        println("Такой человек не найден")
    }
}

fun addInfo(list: ArrayList<Person>, name: String, phone: String, email: String) {
    var isNew: Boolean = true
    for (person in list) {
        if (person.name == name) {
            isNew = false
            if (phone !in person.phone)
                person.phone.add(phone)
            if (email !in person.email)
                person.email.add(email)
            println("Данные контакта $name обновлены!")
            return
        }
    }
    if (isNew) {
        val phones = arrayListOf(phone)
        val emails = arrayListOf(email)
        val newPerson = Person(name, phones, emails)
        list.add(newPerson)
        println("Новый контакт $name добавлен")
    }

}

fun readCommand(input: String): Command {
    val regexAdd = Regex("""add ([A-z]+) (phone|email) (.+)""")
    val regexShow = Regex("""show ([A-z]+)""")

    return when {
        regexAdd.matches(input) -> {
            val matchResult = regexAdd.find(input)!!
            val value = matchResult.groupValues[3]
            AddCommand(value)
        }

        regexShow.matches(input) -> {
            val matchResult = regexShow.find(input)!!
            val name = matchResult.groupValues[1]
            ShowCommand(name)
        }

        else -> {
            throw IllegalArgumentException("Invalid command format")
        }
    }
}


