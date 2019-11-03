@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth
import java.lang.IllegalArgumentException

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    val parts = str.split(' ')
    val list = listOf(
        "января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября",
        "октября", "ноября", "декабря"
    )
    try {
        val day = parts[0].toInt()
        val month = if (parts[1] in list) list.indexOf(parts[1]) + 1 else return ""
        val year = parts[2].toInt()
        if (!validity(day, month, year)) return ""
        return String.format("%02d.%02d.%d", day, month, year)
    } catch (e: Exception) {
        return ""
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val parts = digital.split(".")
    if (parts.size != 3) return ""
    val map = mapOf(
        "01" to "января", "02" to "февраля", "03" to "марта", "04" to "апреля", "05" to "мая",
        "06" to "июня", "07" to "июля", "08" to "августа", "09" to "сентября", "10" to "октября", "11" to "ноября",
        "12" to "декабря"
    )
    try {
        val day = parts[0]
        val month = if (parts[1] in map) map[parts[1]] else return ""
        val year = parts[2]
        if (month != null) {
            if (!validity(day.toInt(), parts[1].toInt(), year.toInt())) return ""
        }
        return String.format("%d %s %d", day.toInt(), month, year.toInt())
    } catch (e: Exception) {
        return ""
    }
}

fun validity(day: Int, month: Int, year: Int): Boolean = !((day < 1) || (day > daysInMonth(month, year)) || (year < 0))

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String {
    val result = phone.matches(Regex("""[\d\s+\-()]*"""))
    if (!result) return ""
    val findResult = Regex("""\([^)]*\)""").find(phone)
    val findBrackets = findResult?.value
    if (findBrackets == "()") {
        return ""
    }
    val findPlus = phone.matches(Regex("""[+]+"""))
    if (findPlus) return ""
    return (Regex("""[^\d+]*""").replace(phone, ""))
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val result = jumps.matches(Regex("""[\d\s%\-]*"""))
    if (!result) return -1
    val findDigits = Regex("""\d+""").find(jumps) ?: return -1
    val list = Regex("""[^\d]+""").split(jumps)
    val answer = list.map { it.toInt() }.max()
    return answer!!.toInt()
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    val result = jumps.matches(Regex("""[\d\s+%\-]*""")) // смотрим соответсвует ли формату входная строка
    if (!result) return -1
    val findPlus = Regex("""[+]+""").find(jumps) ?: return -1 // смотрим, есть ли у нас удачные попытки
    val attempt = Regex("""(\d+\s[+%\-]*)""").findAll(jumps) // вычленяю каждую попытку, чтобы загнать её в список
    val list = attempt.map{it.groupValues[1]}
    val listResult = mutableListOf<String>()
    for (element in list) {
        val findDigits = Regex("""[+]+""").find(element) // ищем число с удачной попыткой
        if (findDigits != null) {
            val digit = Regex("""\d+""").find(element)
                listResult.add(digit!!.value)
            }
        }
    val answer = listResult.map{it.toInt()}.max()
    return answer!!.toInt()
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    val result = expression.matches(Regex("""\d+[\d\s+\-]*""")) // проверка на соответсвие формату
    if (!result) {
        throw IllegalArgumentException()
    }
    val findDoubleOperators = Regex("""[+\-]+\s[+\-]+""").find(expression)
    if (findDoubleOperators != null) {
        throw IllegalArgumentException()
    }
    val findDoubleDigits = Regex("""\d+\s\d+""").find(expression)
    if (findDoubleDigits != null) {
        throw IllegalArgumentException()
    }
    val list = Regex("""[\s]""").split(expression)
    var answer = list[0].toInt()
    for (i in 1 until list.size - 1 step 2) {
            val timeoperator = list[i]
            val digit = list[i+1].toInt()
            if (timeoperator == "+") answer += digit
            if (timeoperator == "-") answer -= digit
    }
    return answer
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val find = Regex("""([\S]+)\s\1""").find(str.toLowerCase()) ?: return -1
    return find.range.first
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    val map = mutableMapOf<String, Double>()
    val goods = Regex(""";""").split(description)
    //println(goods)
    var name = ""
    var price = 0.0
    for (good in goods){
        if (Regex("""[\S]+\s""").find(good) != null) {
            name = Regex("""[\S]+""").find(good)?.value ?: ""
        }
        if (Regex("""(?<=[\S]\s)([\d]+[\.]?[\d]*)""").find(good) != null) {
            price = Regex("""(?<=[\S]\s)([\d]+[\.]?[\d]*)""").find(good)?.value?.toDouble() ?: 0.0
        }
        if (name == ""){
            return ""
        }
        map.put(name, price)
    }
    //println(map.maxBy { it.value })
    return map.maxBy { it.value }?.key!!
}
    /*val list = description.split("; ")
    val map = mutableMapOf<String, Double>()
    for (element in list) {
        val findTitle = Regex("""[\S]+""").find(element) ?: return ""
        val findPrice = Regex("""[\d]+[\.]?[\d]*""").find(element) ?: return ""
        val x1 = findTitle.value
        val x2 = findPrice.value.toDouble()
        map[x1] = x2
    }
    return (map.maxBy { it.value }?.key!!)
}
*/
/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    val arabiclist = mutableListOf<Int>()
    //проверяем строку на корректность
    if (!Regex("""[IVXLCDM]+""").matches(roman)){
        return -1
    }
    val matches = Regex("""([IVXLCDM])""").findAll(roman)
    val digits = matches.map { it.groupValues[1] }.toList()
    var cnt = 0
    for (digit in digits){
        when (digit[0]) {
            'I' -> {
                arabiclist.add(1)
            }
            'V' -> {
                arabiclist.add(5)
            }
            'X' -> {
                arabiclist.add(10)
            }
            'L' -> {
                arabiclist.add(50)
            }
            'C' -> {
                arabiclist.add(100)
            }
            'D' -> {
                arabiclist.add(500)
            }
            'M' -> {
                arabiclist.add(1000)
            }
        }
        cnt ++
    }
    var res = 0
    for (i in 0..arabiclist.size - 1){
        res += arabiclist[i]
        if (i > 0) {
            if (arabiclist[i - 1] < arabiclist[i]) {
                res = res - 2 * arabiclist[i - 1]
            }
        }
    }
    return res
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    val result = mutableListOf<Int>()
    val commandslist:List<Char> = commands.toList()
    var bracketcnt = 0
    var commandcnt = 1
    for (i in 0..cells - 1) {
        result.add(0)
    }
    println("commands=$commands")
    //проверяем строку на корректность
    if (!Regex("""[\<\>\+\-\[\]\s]*""").matches(commands)){
        throw IllegalArgumentException("Param commands has illegal symbols")
    }
    //проверяем квадратные скобки
    for (comand in commandslist){
        if (comand == '['){
            bracketcnt ++
        } else if (comand == ']'){
            bracketcnt --
        }
        if (bracketcnt < 0){
            throw IllegalArgumentException("Param commands has illegal brackets")
        }
    }
    if (bracketcnt != 0){
        throw IllegalArgumentException("Param commands has illegal brackets")
    }
    var currcommandidx = 0
    //датчик стоит на ячейке с номером N/2 (округлять вниз)
    var currresultidx = cells / 2
    while ((commandcnt <= limit) and (currcommandidx in 0..commandslist.size - 1)){
        when (commandslist[currcommandidx]){
            ' ' -> {}
            '>' -> {
                currresultidx ++
            }
            '<' -> {
                currresultidx --
            }
            '+' -> {
                result[currresultidx] ++
            }
            '-' -> {
                result[currresultidx] --
            }
            '[' -> {
                var bracketfnd = 0
                var i = currcommandidx
                if (result[currresultidx] == 0) {
                    bracketfnd = 1
                    while (bracketfnd != 0) {
                        i ++
                        if (commandslist[i] == ']') {
                            bracketfnd --
                        } else if (commandslist[i] == '['){
                            bracketfnd ++
                        }
                    }
                    //встали на закрывающую скобку
                    currcommandidx = i
                }
                //println("[")
                //println("currcommandidx=%currcommandidx")
            }
            ']' -> {
                var bracketfnd = 0
                var i = currcommandidx
                if (result[currresultidx] != 0) {
                    bracketfnd = 1
                    while (bracketfnd != 0) {
                        i --
                        if (commandslist[i] == ']') {
                            bracketfnd ++
                        } else if (commandslist[i] == '['){
                            bracketfnd --
                        }
                    }
                    //встали на открывающую скобку
                    currcommandidx = i
                }
                //println("]")
                // println("currcommandidx=%currcommandidx")
            }
            else -> throw IllegalArgumentException()
        }
        currcommandidx ++
        commandcnt ++
        if ((currresultidx >= result.size) || (currresultidx < 0)) {
            throw IllegalStateException("Out of border")
        }
    }
    return result
}
