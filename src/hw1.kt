fun main() {

    while (true) {
        println("Введите команду: ")
        val str: String? = readlnOrNull()
        if (str == null) {
            continue
        } else {
            if (str == "exit") {
                break
            } else {
                val lst = str.split(" ")
                if (lst.size != 3) {
                    println("Ошибка ввода")
                    continue
                }
                if (lst[0] == "add") {
                    if (lst[2].startsWith("+")) {
                        if (lst[2].substring(1, lst[2].length - 1).matches(Regex("""[0-9]+"""))) {
                            println("${lst[1]} ${lst[2]}")
                        } else {
                            println("Ошибка в номере телефона")
                            continue
                        }
                    } else if (lst[2].matches(Regex("[a-z]+@[a-z]+[.][a-z]+$"))) {
                        println("${lst[1]} ${lst[2]}")
                    } else {
                        println("Ошибка при вводе данных")
                    }
                } else {
                    println("Ошибка ввода")
                }
            }
        }
    }
}