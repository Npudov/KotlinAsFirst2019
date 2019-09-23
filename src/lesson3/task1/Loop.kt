@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import java.lang.Double.NEGATIVE_INFINITY
import java.lang.Double.POSITIVE_INFINITY
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
    when {
        n == m -> 1
        n < 10 -> 0
        else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
    }

/**
 * Простая
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    var number = n
    var count = 0
    while (number > 0) {
        count++
        number /= 10
    }
    return count
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int) : Int {
    var result = 0
    var number1 = 1
    var number2 = 1
    if (n in 1..2) return 1
    else for (i in 3..n) {
        result = number1 + number2
        number2 = number1
        number1 = result
    }
    return result
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    var m = m
    var safetym = m
    var safetyn = n
    var k = m
    var n = n
    var i = 2
    if ((m % n == 0) && (m > n)) return m
    if ((n % m == 0) && (n > m)) return n
    if (m > n) {
     while ((k % m !=0) || (k % n != 0)) {
         m = safetym
         m *= i
         k = m
         i++
     }
    }
    else {
        while ((k % m != 0) || (k % n != 0)) {
            n = safetyn
            n *= i
            k = n
            i++
        }
    }
    return k
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int) :Int {
    var d = 0
    for (i in 2..sqrt(n.toDouble()).toInt()) {
        if (n % i == 0) {
            d = i
            return d
        }
    }
    return n
}


/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    val maxd = n / minDivisor(n)
    return maxd
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    var m = m
    var n = n
    var maxd = 0
    while (m != n) {
        if (m > n)
            m = m - n
        else
            n = n - m
    }
    maxd = m
    return when {
        (maxd ==1) -> true
        else -> false
    }
}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    for (i in sqrt(m.toDouble()).toInt()..sqrt(n.toDouble()).toInt()) {
        if ((i*i >= m ) && (i*i <= n)) return true
        }
    return false
}


/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var count = 0
    var x = x
    while (x != 1) {
        if (x % 2 == 0) {
            x /= 2
            count++
        }
        else {
            x = 3 * x + 1
            count++
        }
        }
    return count
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю.
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.sin и другие стандартные реализации функции синуса в этой задаче запрещается.
 */
fun sin(x: Double, eps: Double): Double {
        var sin = 0.0
        var n = 1
        var m = -1.0
        var number = x
        while (abs(number) > eps) {
            if (m < 0){
                m = 1.0
            }
            else{
                m = - 1.0
            }
            number = m*(x.pow(n)/factorial(n))
            sin += number
            n = n + 2
        }
        var accuracy = 1.0
        n = 1
        while (eps *(10 * n) < 1){
                n *= 10
        }
        accuracy = n.toDouble()
        return Math.round(sin * accuracy) / accuracy
    }

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.cos и другие стандартные реализации функции косинуса в этой задаче запрещается.
 */
fun cos(x: Double, eps: Double): Double {
        var cos = 1.0
        var n = 2
        var m = 1.0
        var number = x
        while ((abs(number) > eps) or (n <= 2)) {
            if (m < 0){
                m = 1.0
            }
            else {
                m = - 1.0
            }
            number = m*(x.pow(n)/factorial(n))
            if (number == POSITIVE_INFINITY){
                cos = 1.0
                break
            }
            else if (number == NEGATIVE_INFINITY){
                cos = -1.0
                break
            }
            cos += number
            n = n + 2
        }
        var accuracy = 10.0
        n = 10
        while (eps *(10 * n) < 1){
            n *= 10
        }
        accuracy = n.toDouble()
        return Math.round(cos * accuracy) / accuracy
    }
/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    var n = n
    var number = n
    var count = 0
    var answer = 0
    while (n > 0) {
        count++
        n /= 10
    }
    for (i in 1..count) {
        answer += (number % 10) * 10.0.pow(count - i).toInt()
        number /= 10
    }
    return answer
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean {
    var a = revert(n)
    if (a == n)
        return true
    else
        return false
}

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    var n = n
    var lastnumber = n % 10
    while (n > 0) {
        var c = n % 10
        if (c != lastnumber)
            return true
        n /= 10
    }
    return false
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    var digit = n
    var k = 0
    var sm = 0
    var cnt = 0
    var answer = 0
    while (k < Int.MAX_VALUE){
        k += 1
        sm = k * k
        cnt += digitNumber(sm)
        if (cnt >= digit){
            break
        }
    }
    while (cnt >= digit){
        answer = sm % 10
        sm = sm / 10
        cnt--
    }
    return answer
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    var digit = n
    var k = 0
    var sm = 0
    var cnt = 0
    var answer = 0
    while (k < Int.MAX_VALUE) {
        k += 1
        sm = fib(k)
        cnt += digitNumber(sm)
        if (cnt >= digit)
            break
    }
    if ((n==1) || (n==2)) return 1
    while (cnt >= digit) {
        answer = sm % 10
        sm /= 10
        cnt--
    }
    return answer
}
