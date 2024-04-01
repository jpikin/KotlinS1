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
                        addPhone(lst[2], lst[3])
                    else if (lst[2] == "email")
                        addEmail(lst[2], lst[3])
                    else
                        println("Ошибка ввода")

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

fun addPhone(str: String, str2: String) {
    if (str2.startsWith("+")) {
        if (str2.substring(1, str.length - 1).matches(Regex("""[0-9]+"""))) {
            println("$str $str2")
        } else {
            println("Ошибка в номере телефона")
        }
    }
}