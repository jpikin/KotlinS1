fun main() {

    while (true) {
        println("Введите команду: ")
        val inputStr: String? = readlnOrNull()

        if (inputStr == null) {
            continue
        } else {
            if (inputStr == "exit") {
                break
            } else {
                val lst = inputStr.split(" ")
                if (lst.size != 4) {
                    println("Ошибка ввода")
                    continue
                }
                if (lst[0] == "add") {
                    if (lst[2] == "phone")
                        addPhone(lst[1], lst[3])
                    else if (lst[2] == "email")
                        addEmail(lst[1], lst[3])
                    else
                        println("Ошибка ввода")

                } else {
                    println("Введите другую команду")
                }
            }
        }
    }
}

fun addEmail(s: String, s1: String) {
    if (s1.matches(Regex("[a-z]+@[a-z]+[.][a-z]+$"))) {
        println("$s $s1")
    } else {
        println("Ошибка при вводе данных")
    }
}

fun addPhone(s: String, s1: String) {
    if (s1.startsWith("+")) {
        if (s1.substring(1, s.length - 1).matches(Regex("""[0-9]+"""))) {
            println("$s $s1")
        } else {
            println("Ошибка в номере телефона")
        }
    }
}