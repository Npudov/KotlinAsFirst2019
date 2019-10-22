@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

import java.util.Collections.max
import kotlin.math.max

/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
    shoppingList: List<String>,
    costs: Map<String, Double>
): Double {
    var totalCost = 0.0

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
    phoneBook: MutableMap<String, String>,
    countryCode: String
) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
    text: List<String>,
    vararg fillerWords: String
): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}


/**
 * Простая
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val answerMap = mutableMapOf<Int, MutableList<String>>()
    for ((students, value) in grades) {
        if (answerMap[value] == null) {
            answerMap[value] = mutableListOf(students)
        }
        else {
            answerMap[value]?.add(students)
        }
    }
    return answerMap
}

/**
 * Простая
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean {
    var cnt = 0
    for ((key, value) in a) {
        if (a[key] == b[key]) {
            cnt++
        }
    }
    return a.size == cnt
}

/**
 * Простая
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>): Unit {
    for ((key, value) in b) {
        if (a[key] == b[key]) {
            a.remove(key)
        }
    }
}

/**
 * Простая
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках.
 * В выходном списке не должно быть повторяюихся элементов,
 * т. е. whoAreInBoth(listOf("Марат", "Семён, "Марат"), listOf("Марат", "Марат")) == listOf("Марат")
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> = (a.toSet().intersect(b.toSet())).toList()

/**
 * Средняя
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    val result = mapA.toMutableMap()
    for ((name, phonenumber) in mapB) {
        if (name in result) {
            if (result[name] != mapB[name]) {
                val temporaryA = result[name]
                val temporaryB = mapB[name]
                result[name] = "$temporaryA, $temporaryB"
            }
        }
        else {
            result[name] = phonenumber
        }
    }
    return result
}

/**
 * Средняя
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> {
    val stockPrices = stockPrices
    val result = mutableMapOf<String, Double>()
    val timeMap = mutableMapOf<String, MutableList<Double>>()
    for (element in stockPrices) {
        if (element.first in timeMap) {
            timeMap[element.first]?.add(element.second)
        }
        else {
            timeMap[element.first] = mutableListOf(element.second)
        }
    }
    for ((name , value) in timeMap) {
        result[name] = value.sum() / value.size
    }
    return result
}

/**
 * Средняя
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? {
    var minimum = Double.POSITIVE_INFINITY
    var result: String? = null
    for ((name, value) in stuff) {
        if ((value.first == kind) && (value.second < minimum)) {
            minimum = value.second
            result = name
        }
    }
    return result
}

/**
 * Средняя
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean {
    val string = chars.joinToString()
    val res = string.toLowerCase().toSet()
    val word = word.toLowerCase().toSet().toMutableSet()
    word.removeAll(res)
    return (word.size == 0)
}

/**
 * Средняя
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> {
    val list = list
    val result = mutableMapOf<String, Int>()
    for (element in list) {
        if (element in result) {
            result[element] = result[element]!! + 1
        }
        else {
            result[element] = 1
        }
    }
    return result.filterValues { it != 1}
}

/**
 * Средняя
 *
 * Для заданного списка слов определить, содержит ли он анаграммы
 * (два слова являются анаграммами, если одно можно составить из второго)
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun hasAnagrams(words: List<String>): Boolean {
    val res = mutableSetOf<Set<Char>>()
    for (element in words) {
        res.add(element.toSet())
    }
    return (res.size != words.size)
}
/**
 * Сложная
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta")
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat")
 *        )
 */
fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {
    val result = mutableMapOf<String, MutableSet<String>>()
    val listPeople = mutableListOf<String>() // fiil the list of people who has  friends
    for ((key, _) in friends) {
        listPeople.add(key)
    }
    for (people in listPeople ) {
        result[people] = mutableSetOf<String>()
        val findfriends = mutableMapOf<String, Boolean>()
        for (element in listPeople) { // indicate, that we haven't found friends for this person yet
            findfriends[element] = false
        }
        val querum = mutableListOf(people) // person for himself we find friends
        while (querum.isNotEmpty()) {
            val person = querum[0]
            querum.remove(person)
            findfriends[person] = true
            for (friend in friends.getOrDefault(person, setOf())) {
                if (friend != people) {
                    result[people]?.add(friend)
                }
                if (friend !in listPeople) { // this person don't have friends
                    result[friend] = mutableSetOf()
                }
                if (findfriends[friend] == false) { // if we haven't found friends for this person
                    querum.add(friend)
                }
            }
        }
    }
    return result
}

/**
 * Сложная
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    val map = mutableMapOf<Int, Int>()
    if (list.isEmpty()) return Pair(-1, -1)
    map[list[0]] = 0
    for (i in 1 until list.size) {
        if (((number - list[i]) in map)) {
            return Pair(map[number - list[i]]!!, i)
        }
        map[list[i]] = i
    }
    return Pair(-1, -1)
}


/**
 * Очень сложная
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Перед решением этой задачи лучше прочитать статью Википедии "Динамическое программирование".
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
    val f = Array(treasures.size + 1, { Array( capacity + 1, {0}) }) //двумерный массив
    val weigth = Array(treasures.size + 1, {0}) // массив весов
    val prices = Array(treasures.size + 1, {0}) // массив цен
    val res = mutableSetOf<String>()
    var k = 0
    for (i in treasures){
        weigth[k + 1] = i.value.first
        prices[k + 1] = i.value.second
        //println(i.value.first)
        k += 1
    }
    //println(treasures.size)
    for (i in 1..treasures.size){
        for (j in 0..capacity) {
            //println("i = $i, j=$j")
            if (j >= weigth[i]) {
                //println("i = $i, w[i] = $w[i]")
                f[i][j] = max(f[i - 1][j], f[i - 1][j - weigth[i]] + prices[i])
            }
            else {
                //println("i = $i, j=$j")
                f[i][j] = f[i - 1][j]
            }
            //println("i = $i, j=$j")
            //println(f[i][j])
        }
    }
    k = capacity
    for (i in treasures.size downTo(1)){
        //println("k= $k, i=$i")
        if (f[i][k] != f[i - 1][k]){
            //println(w[i])
            //println(treasures.toList()[i].first)
            res.add(treasures.toList()[i - 1].first)
            k -= weigth[i]
        }
    }
    return res
}
