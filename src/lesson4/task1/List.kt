@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
    when {
        y < 0 -> listOf()
        y == 0.0 -> listOf(0.0)
        else -> {
            val root = sqrt(y)
            // Результат!
            listOf(-root, root)
        }
    }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double {
    var sum = 0.0
    for (element in v) {
        sum += element * element
    }
    return sqrt(sum)
}

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double {
    val size = list.size
    return when {
        size == 0 -> 0.0
        else -> list.sum() / size
    }
}

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    if (list.size == 0) return list
    val averageSum = list.sum() / list.size
    for (i in 0 until list.size) {
        list[i] -= averageSum
    }
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.
 */
fun times(a: List<Int>, b: List<Int>): Int {
    var result = 0
    for (i in 0 until a.size) {
        result += a[i] * b[i]
    }
    return result
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun polynom(p: List<Int>, x: Int): Int {
    var result = 0
    for (i in 0 until p.size) {
        result += p[i] * x.toDouble().pow(i).toInt()
    }
    return result
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Int>): MutableList<Int> {
    for (i in 1 until list.size) {
        list[i] = list[i] + list[i - 1]
    }
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var n = n
    val list = mutableListOf<Int>()
    var d = 2
    while (d <= sqrt(n.toDouble())) {
        if (n % d == 0) {
            n /= d
            list.add(d)
        } else
            d += 1
    }
    if (n > 1) {
        list.add(n)
    }
    return list
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    var n = n
    var base = base
    var residue = 0
    var list = mutableListOf<Int>()
    if (n < base) {
        list.add(n)
        return list
    } else {
        while (n >= base) {
            residue = n % base
            n /= base
            list.add(residue)
        }
    }
    list.add(n)
    list.reverse()
    return list
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 *
 * Использовать функции с0тандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, n.toString(base) и подобные), запрещается.
 */
fun convertToString(n: Int, base: Int): String {
    val string = "abcdefghijklmnopqrstuvwxyz"
    val list = convert(n, base)
    var answer = ""
    for (element in list) {
        if (element < 10) {
            answer += element
        } else {
            answer += string[element - 10]
        }
    }
    return answer
}


/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    val digits = digits
    val base = base
    var answer = 0
    for (i in 0 until digits.size) {
        answer += digits[i] * base.toDouble().pow(digits.size - 1 - i).toInt()
    }
    return answer
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, str.toInt(base)), запрещается.
 */
fun decimalFromString(str: String, base: Int): Int {
    val string = "abcdefghijklmnopqrstuvwxyz"
    val str = str
    val base = base
    var list = mutableListOf<Int>()
    for (char in str) {
        if ((char.toInt() >= 'a'.toInt()) && (char.toInt() <= 'z'.toInt())) {
            val position = string.indexOf(char, 0)
            list.add(position + 10)
        } else {
            list.add(char.toString().toInt())
        }
    }
    val answer = decimal(list, base)
    return answer
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    var n = n
    var result = ""
    val arab = mutableListOf<Int>(1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000)
    val rom = mutableListOf<String>("I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M")
    var time = 12
    while (n > 0) {
        while (arab[time] > n) {
            time--
        }
        n -= arab[time]
        result += rom[time]
    }
    return result
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String {
    var sm = n
    var factor = 100000
    var k = 0
    var res = ""
    var thousand = false
    while (factor > 0) {
        k = sm / factor
        if (((factor == 100000) || (factor == 100)) && (k != 0)) {
            res += ' '
            when (k) {
                1 -> res += "сто"
                2 -> res += "двести"
                3 -> res += "триста"
                4 -> res += "четыреста"
                5 -> res += "пятьсот"
                6 -> res += "шестьсот"
                7 -> res += "семьсот"
                8 -> res += "восемьсот"
                9 -> res += "девятьсот"
            }
        } else if (((factor == 10000) || (factor == 10)) && (k != 0)) {
            res += ' '
            when (k) {
                1 -> {
                    sm -= k * factor
                    factor /= 10
                    k = sm / factor
                    when (k) {
                        0 -> res += "десять"
                        1 -> res += "одиннадцать"
                        2 -> res += "двенадцать"
                        3 -> res += "тринадцать"
                        4 -> res += "четырнадцать"
                        5 -> res += "пятнадцать"
                        6 -> res += "шестнадцать"
                        7 -> res += "семнадцать"
                        8 -> res += "восемнадцать"
                        9 -> res += "девятнадцать"
                    }
                }
                2 -> res += "двадцать"
                3 -> res += "тридцать"
                4 -> res += "сорок"
                5 -> res += "пятьдесят"
                6 -> res += "шестьдесят"
                7 -> res += "семьдесят"
                8 -> res += "восемьдесят"
                9 -> res += "девяносто"
            }
        } else if (((factor == 1000) || (factor == 1)) && (k != 0)) {
            thousand = true
            res += ' '
            when (k) {
                1 -> res += if (factor == 1000) {
                    "одна тысяча"
                } else {
                    "один"
                }
                2 -> res += if (factor == 1000) {
                    "две тысячи"
                } else {
                    "два"
                }
                3 -> res += if (factor == 1000) {
                    "три тысячи"
                } else {
                    "три"
                }
                4 -> res += if (factor == 1000) {
                    "четыре тысячи"
                } else {
                    "четыре"
                }
                5 -> res += if (factor == 1000) {
                    "пять тысяч"
                } else {
                    "пять"
                }
                6 -> res += if (factor == 1000) {
                    "шесть тысяч"
                } else {
                    "шесть"
                }
                7 -> res += if (factor == 1000) {
                    "семь тысяч"
                } else {
                    "семь"
                }
                8 -> res += if (factor == 1000) {
                    "восемь тысяч"
                } else {
                    "восемь"
                }
                9 -> res += if (factor == 1000) {
                    "девять тысяч"
                } else {
                    "девять"
                }
            }
        }
        if ((factor == 1000) && (thousand == false) && (res.length > 0)) {
            res += ' '
            res += "тысяч"
        }
        sm -= k * factor
        factor /= 10
    }
    return res.trim()
}