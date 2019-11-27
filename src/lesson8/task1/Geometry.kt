@file:Suppress("UNUSED_PARAMETER")

package lesson8.task1

import lesson1.task1.sqr
import kotlin.math.*

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
@Suppress("MemberVisibilityCanBePrivate")
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point) : this(linkedSetOf(a, b, c))

    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double {
        val dst = center.distance(other.center)
        return if (dst <= radius + other.radius) 0.0 else (dst - (radius + other.radius))
    }
    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean = center.distance(p) <= radius // для импоссибла
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
        other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
        begin.hashCode() + end.hashCode()
}

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment {
    var point1 = Point(0.0, 0.0)
    var point2 = Point(0.0, 0.0)
    var maxDistance = -1.0
    if (points.size < 2) throw  IllegalArgumentException()
    for (i in 0 until points.size) {
        for (j in i+1 until points.size) {
            val distance = points[i].distance(points[j])
            if (distance > maxDistance) {
                maxDistance = distance
                point1 = points[i]
                point2 = points[j]
            }
        }
    }
    return Segment(point1 , point2)
}

/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle {
    val distance = diameter.begin.distance(diameter.end)
    val centerCoordinatex = (diameter.begin.x + diameter.end.x) / 2
    val centerCoordinatey = (diameter.begin.y + diameter.end.y) / 2
    val center = Point(centerCoordinatex , centerCoordinatey)
    val radius = distance / 2
    return Circle(center , radius)
}

/**
 *
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0
 * (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        require(angle >= 0 && angle < PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double) : this(point.y * cos(angle) - point.x * sin(angle), angle)

    /**
     * Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point {
        val x = (other.b * cos(angle) - b * cos(other.angle)) / sin(angle - other.angle)
        val y = (other.b * sin(angle) - b * sin(other.angle)) / sin(angle - other.angle)
        return Point(x , y)
    }

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${cos(angle)} * y = ${sin(angle)} * x + $b)"
}

/**
 * Средняя
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line = lineByPoints(s.begin , s.end)

/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line {
    val angle = (atan((b.y - a.y) / (b.x - a.x)) + PI) % PI
    return Line(a , angle)
}

/**
 * Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line {
    val midPoint = Point((a.x + b.x) / 2, (a.y + b.y) / 2) // точка серединного перпендикуляра
    val k = (atan((b.y - a.y) / (b.x - a.x)) + PI) % PI
    val angle = (k + PI / 2) % PI
    return Line(midPoint , angle)
}

/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    var min = Double.POSITIVE_INFINITY
    if (circles.size < 2) throw IllegalArgumentException()
    var answer = Pair(circles[0] , circles[1])
    for (i in 0 until circles.size) {
        for ( j in i+1 until circles.size) {
            val dstCenter = circles[i].center.distance(circles[j].center)
            val sumRadius = circles[i].radius + circles[j].radius
            val dst = dstCenter - sumRadius
            if (dstCenter <= sumRadius) {
                return Pair(circles[i], circles[j])
            } else if (dst < min) {
                min = dst
                answer = Pair(circles[i] , circles[j])
            }
        }
    }
    return answer
}


/**
 * Сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle {
    val middle1 = bisectorByPoints(a , b)
    val middle2 = bisectorByPoints(b , c)
    val center = middle1.crossPoint(middle2)
    val radius = center.distance(a)
    return Circle(center , radius)
}

/**
 * Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun minContainingCircle(vararg points: Point): Circle {
    if (points.isEmpty()) throw IllegalArgumentException()
    if (points.size == 1) return Circle(points[0] , 0.0)
    var answer = Circle(Point(0.0 , 0.0) , 0.0)
    var minRadius = Double.POSITIVE_INFINITY
    val circle2 = circleByDiameter(diameter(*points))
    val trueCircle2 = Circle(circle2.center , circle2.radius + 10000 * Math.ulp(circle2.radius))
    var circleByTwoPoints = true
    for (point in points) {
        if (!trueCircle2.contains(point)) {
            circleByTwoPoints = false
            break
        }
    }
    for (i in 0 until points.size - 2) {
        for (j in i+1 until points.size - 1) {
            for (k in j+1 until points.size) {
                val circle = circleByThreePoints(points[i] , points[j], points[k])
                val trueCircle = Circle(circle.center , circle.radius + 10000 * Math.ulp(circle.radius))
                var contain = true
                for (point in points) {
                    if (!trueCircle.contains(point)) {
                        contain = false
                        break
                    }
                }
                if ((trueCircle.radius < minRadius) && (contain)) {
                    minRadius = trueCircle.radius
                    answer = trueCircle
                }
            }
        }
    }
    return if (circleByTwoPoints) {
        trueCircle2
    }
    else {
        answer
    }
}

