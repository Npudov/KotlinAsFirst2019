@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.File

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val map = mutableMapOf<String, Int>()
    val lines = File(inputName).readLines()
    for (element in substrings) {
        val symbols = "-().^+[]" // символы , которые нуждаются в экранировании, экранирую
        var pattern = element.toLowerCase()
        for (symbol in symbols) {
            pattern = pattern.replace(symbol.toString(), """\""" + symbol)
        }
        val matchResult = Regex(pattern = "(?=$pattern)").findAll(lines.toString().toLowerCase())
        val cnt = matchResult.map { it.value }.count()
        map[element] = cnt
        if (matchResult == null) {
            map[element] = 0
        }
    }
    return map
}


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val lines = File(inputName).readLines()
    var str = ""
    for (line in lines) {
        str = Regex("""(?<=[жчшщЖЧШЩ])[ы]""").replace(line, "и")
        str = Regex("""(?<=[жчшщЖЧШЩ])[я]""").replace(str, "а")
        str = Regex("""(?<=[жчшщЖЧШЩ])[ю]""").replace(str, "у")
        str = Regex("""(?<=[жчшщЖЧШЩ])[Ы]""").replace(str, "И")
        str = Regex("""(?<=[жчшщЖЧШЩ])[Я]""").replace(str, "А")
        str = Regex("""(?<=[жчшщЖЧШЩ])[Ю]""").replace(str, "У")
        writer.write(str)
        writer.newLine()
    }
    writer.close()
}


/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val lines = File(inputName).readLines()
    var maxLengthStr = -1
    for (line in lines) {
        val currentLine = line.trim()
        val currentLength = currentLine.length
        if (currentLength > maxLengthStr) {
            maxLengthStr = currentLength
        }
    }
    for (line in lines) {
        val lengthLine = line.trim().length
        val cntSpace = (maxLengthStr - lengthLine) / 2
        val str = line.trim().padStart(lengthLine + cntSpace)
        writer.write(str)
        writer.newLine()
    }
    writer.close()
}
/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    val map = mutableMapOf<String, Int>()
    val lines = File(inputName).readLines()
    for (line in lines) {
        val str = line.toLowerCase()
        var listLineWords = Regex("""[^А-Яа-яёЁA-Za-z]+""").split(str)
        listLineWords = listLineWords.filter { !it.isBlank() }
        for (element in listLineWords) {
            if (element !in map) {
                map[element] = 1
            }
            else {
                map[element] = map[element]!! + 1
            }
        }
    }
    val lastMap = map.toList().sortedByDescending { (key, value) -> value }.toMap()
    if (lastMap.size <= 20) return lastMap
    else {
        var cnt = 0
        val answer = mutableMapOf<String, Int>()
        for ((key, value) in lastMap) {
            answer[key] = value
            cnt++
            if (cnt == 20) break
        }
        return answer
    }
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val lines = File(inputName).readLines()
    val dict = mutableMapOf<Char, String>()
    for ((key, value) in dictionary) {
        dict[key.toLowerCase()] = value.toLowerCase()
    }
    var str = ""
    for (line in lines) {
        if ((line.isEmpty()) && (dict['\n'] != null)) {
            str += dict['\n']
        }
        else {
            for (char in line) {
                if (dict[char.toLowerCase()] != null) {
                    if (char.isUpperCase()) {
                        str += dict[char.toLowerCase()]?.capitalize()
                    } else {
                        str += dict[char]
                    }
                } else {
                    str += char
                }
            }
        }
        writer.write(str)
        writer.newLine()
        str = ""
    }
    writer.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *0
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val lines = File(inputName).readLines()
    var set: MutableSet<Char>
    var str = ""
    var max = -1
    val list = mutableListOf<String>()
    val newListWords = mutableListOf<String>()
    for (line in lines) {
        str = line.toLowerCase()
        set = str.toSet().toMutableSet()
        if (set.size == line.length) {
            list.add(line)
        }
    }
    var word = ""
    for (i in 0 until list.size) {
        if (list[i].length == max) {
            newListWords.add(list[i])
        }
        if (list[i].length > max) {
            newListWords.clear()
            newListWords.add(list[i])
            max = list[i].length
        }
    }
    writer.write(newListWords.joinToString())
    writer.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
    <body>
        <p>
            Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
            Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
        </p>
        <p>
            Suspendisse <s>et elit in enim tempus iaculis</s>.
        </p>
    </body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val lines = File(inputName).readLines()
    val stack = mutableListOf<String>() // храним открывающиеся тэги
    var currentLine = ""
    var strIsEmpty = false
    stack.add("<html>")
    stack.add("<body>")
    stack.add("<p>")
    currentLine = "<html><body><p>"
    for (line in lines) { // бежим по строке
        currentLine += line
        val str = line
        if ((str == "") && !strIsEmpty) {
            strIsEmpty = true
            stack.remove("<p>")
            currentLine += createCloseTag("<p>")
        }
        else if ((str != "") && strIsEmpty) {
            strIsEmpty = false
            stack.add("<p>")
            currentLine = "<p>$currentLine"
        }
        val findSymbols = Regex("""(\*\*|\*|~~)""").findAll(currentLine)
        val list = findSymbols.map { it.groupValues[1] }
        for (element in list) {
            if (element == "*") {
                if (stack[stack.size - 1] != "<i>") {
                    stack.add("<i>")
                    currentLine = currentLine.replaceFirst(element , "<i>")
                }
                else {
                    stack.remove("<i>")
                    val tag = createCloseTag("<i>")
                    currentLine = currentLine.replaceFirst(element , tag)
                }
            }
            if (element == "**") {
                if (stack[stack.size - 1] != "<b>") {
                    stack.add("<b>")
                    currentLine = currentLine.replaceFirst(element , "<b>")
                }
                else {
                    stack.remove("<b>")
                    val tag = createCloseTag("<b>")
                    currentLine = currentLine.replaceFirst(element , tag)
                }
            }
            if (element == "~~") {
                if (stack[stack.size - 1] != "<s>") {
                    stack.add("<s>")
                    currentLine = currentLine.replaceFirst(element , "<s>")
                }
                else {
                    stack.remove("<s>")
                    val tag = createCloseTag("<s>")
                    currentLine = currentLine.replaceFirst(element , tag)
                }
            }
        }
        writer.write(currentLine)
        writer.newLine()
        currentLine = ""
    }
    for (tags in stack.reversed()) {
        currentLine += createCloseTag(tags)
    }
    writer.write(currentLine)
    writer.close()
}

fun createCloseTag(openTag: String) : String { // преобразование открытого тэга в закрытый
    val tag = Regex("""([A-Za-z]+)""").find(openTag)
    val answer = "<" + "/" + tag?.value + ">"
    return answer
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
* Утка по-пекински
    * Утка
    * Соус
* Салат Оливье
    1. Мясо
        * Или колбаса
    2. Майонез
    3. Картофель
    4. Что-то там ещё
* Помидоры
* Фрукты
    1. Бананы
    23. Яблоки
        1. Красные
        2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
  <body>
    <ul>
      <li>
        Утка по-пекински
        <ul>
          <li>Утка</li>
          <li>Соус</li>
        </ul>
      </li>
      <li>
        Салат Оливье
        <ol>
          <li>Мясо
            <ul>
              <li>
                  Или колбаса
              </li>
            </ul>
          </li>
          <li>Майонез</li>
          <li>Картофель</li>
          <li>Что-то там ещё</li>
        </ol>
      </li>
      <li>Помидоры</li>
      <li>
        Фрукты
        <ol>
          <li>Бананы</li>
          <li>
            Яблоки
            <ol>
              <li>Красные</li>
              <li>Зелёные</li>
            </ol>
          </li>
        </ol>
      </li>
    </ul>
  </body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val lines = File(inputName).readLines()
    val stack = mutableListOf<Pair<String, Int>>()
    var currentLine = ""
    var level = 0
    var firstIteration = false
    var timeStr = ""
    stack.add(Pair("<html>", level))
    stack.add(Pair("<body>", level))
    currentLine = ("<html><body>")
    for (line in lines) {
        currentLine += line
        val findSymbols = Regex("""(\*|[\d]+\.)""").find(line)
        val range = findSymbols?.range?.first
        if (!firstIteration) { // отдельная обработка первой итерации
            firstIteration = true
            if (findSymbols?.value == "*") {
                stack.add(Pair("<ul>", level))
                stack.add(Pair("<li>", level))
                currentLine = currentLine.replaceFirst("*", "<ul><li>")
                continue
            } else {
                stack.add(Pair("<ol>", level))
                stack.add(Pair("<li>", level))
                if (findSymbols != null) {
                    currentLine = currentLine.replaceFirst(findSymbols.value, "<ol><li>")
                }
                continue
            }
        }
        if (range == 4 * level) {
            if (findSymbols.value == "*") { //элемент ненумерованного списка
                if (stack[stack.size - 1] != Pair("<li>", level)) {
                    stack.add(Pair("<li>", level))
                    currentLine = currentLine.replaceFirst("*", "<li>")
                } else {
                    stack.remove(Pair("<li>", level))
                    currentLine = "</li>$currentLine"
                    stack.add(Pair("<li>", level))
                    currentLine = currentLine.replaceFirst("*", "<li>")
                }
            }
            else { //элемент нумерованного списка
                if (stack[stack.size - 1] != Pair("<li>", level)) {
                    stack.add(Pair("<li>", level))
                    currentLine = currentLine.replaceFirst(findSymbols.value, "<li>")
                } else {
                    stack.remove(Pair("<li>", level))
                    currentLine = "</li>$currentLine"
                    stack.add(Pair("<li>", level))
                    currentLine = currentLine.replaceFirst(findSymbols.value, "<li>")
                }
            }
        }
        if (range != null) {
            if (range > 4 * level) { // список углубляется
                level = range / 4
                if (findSymbols.value == "*") {
                    stack.add(Pair("<ul>", level))
                    stack.add(Pair("<li>", level))
                    currentLine = currentLine.replaceFirst("*", "<ul><li>")
                } else {
                    stack.add(Pair("<ol>", level))
                    stack.add(Pair("<li>", level))
                    currentLine = currentLine.replaceFirst(findSymbols.value, "<ol><li>")
                }
            }
        }
        if (range != null) {
            if (range < 4 * level) {
                level = range / 4
                if (findSymbols.value == "*") {
                    while (stack[stack.size - 1].second > level) {
                        timeStr += createCloseTag(stack[stack.size - 1].first)
                        stack.removeAt(stack.size - 1)
                    }
                    stack.remove(Pair("<li>", level))
                    stack.add(Pair("<li>", level))
                    currentLine = currentLine.replaceFirst("*", "<li>")
                    currentLine = "$timeStr</li>$currentLine"
                } else {
                    while (stack[stack.size - 1].second > level) {
                        timeStr += createCloseTag(stack[stack.size - 1].first)
                        stack.removeAt(stack.size - 1)
                    }
                    stack.remove(Pair("<li>", level))
                    stack.add(Pair("<li>", level))
                    currentLine = currentLine.replaceFirst(findSymbols.value, "<li>")
                    currentLine = "$timeStr</li>$currentLine"
                }
            }
        }
            writer.write(currentLine)
            writer.newLine()
            currentLine = ""
            timeStr = ""
        }
    for (element in stack.reversed()) {
        currentLine += createCloseTag(element.first)
    }
    writer.write(currentLine)
    writer.close()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
   19935
*    111
--------
   19935
+ 19935
+19935
--------
 2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
  235
*  10
-----
    0
+235
-----
 2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val list = mutableListOf<String>()
    val len = (lhv * rhv).toString().length + 1
    //val len = lhv.toString().length + rhv.toString().length
    list.add(lhv.toString().padStart(len)) // первая строка
    list.add("*"+ rhv.toString().padStart(len - 1)) //вторая строка
    list.add("".padStart(len, '-')) // Третья строка
    var cnt = 0
    var sign = ""
    for (digit in rhv.toString().reversed() ) {
        sign = if (cnt > 0) "+" else ""
        val sm = lhv * digit.toString().toInt()
        list.add(sign + sm.toString().padStart(len - cnt - sign.length))
        cnt++
    }
    list.add("".padStart(len, '-'))
    val composition = lhv * rhv
    list.add(composition.toString().padStart(len))
    for (element in list) {
        writer.write(element)
        writer.newLine()
    }
    writer.close()
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
  19935 | 22
 -198     906
 ----
    13
    -0
    --
    135
   -132
   ----
      3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}

