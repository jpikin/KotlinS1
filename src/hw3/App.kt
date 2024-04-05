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
                          если человек с таким именем уже есть в телефонной книге,
                          данные этого контакта обновятся.
                show <Имя> Ищет в записной книге совпадения по имени и выводит информацию о человеке,
                          если он есть в списке.
                find <Данные> в данных может содержаться почта или телефон. Команда ищет совпадения в 
                          записной книжке и выводит данные о человеке, если он есть в списке.
                addPhone <Имя> <Номер телефона> - добавляет к существующему контакту номер телефона 
                addEmail <Имя> <Адрес электронной почты> - добавляет к существующему контакту email адрес
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

                            is AddValues -> {
                                val com = input?.split(" ")
                                val type = com?.get(0) ?: ""
                                val name = com?.get(1) ?: ""
                                val value = com?.get(2) ?: ""
                                if(!checkNameInList(contacts, name)) {
                                    if (type == "addPhone") {
                                        addInfo(contacts, name, value, "")
                                    } else {
                                        addInfo(contacts, name, "", value)
                                    }
                                } else {
                                    println("Пользователь с именем $name не найден. Воспользуйтесь командой 'add'")
                                }
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

fun checkNameInList(list: ArrayList<Person>, name: String): Boolean {
    for (person in list) {
        if (person.name == name) {
            return false
        }
    }
    return true

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
            if (phone !in person.phone && phone != "")
                person.phone.add(phone)
            if (email !in person.email && email != "")
                person.email.add(email)
            println("Данные контакта $name обновлены!")
            return
        }
    }
    if (isNew) {
        val phones = arrayListOf<String>()
        val emails = arrayListOf<String>()
        if (phone != "") phones.add(phone)
        if (email != "") emails.add(email)
        val newPerson = Person(name, phones, emails)
        list.add(newPerson)
        println("Новый контакт $name добавлен")
    }

}

fun readCommand(input: String): Command {
    val regexAdd = Regex("""add ([A-z]+) (phone|email) (.+)""")
    val regexShow = Regex("""show ([A-z]+)""")
    val regexAddValues = Regex("""(addPhone|addEmail) ([A-z]+) (.+)""")


    return when {
        regexAddValues.matches(input) -> {
            val matchResult = regexAddValues.find(input)!!
            val value = matchResult.groupValues[3]
            AddValues(value)
        }

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

