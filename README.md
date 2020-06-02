>**Задания**
- Создать группу радиокнопок, которые отвечают за смену цвета
- Избавиться от повторяющегося кода с помощью функции высших порядком над массивом данных

Основной код программы
```Kotlin
fun main() {
    document.getElementById("root")!!
        .append {
            h1 {
                attributes += "id" to "header"
                +"Students"
                onClickFunction = onCLickFunction()

            }
            ol {
                attributes += "id" to "listStudents"
                StudentsList.map {
                    li {
                        +"${it.firstname} ${it.surname}"
                        attributes += "id" to it.firstname
                        onClickFunction = Click(it)
                    }
                }
            }
            input (options = arrayListOf("blue", "green", "red"))
        }
}
```
Код функции, которая отвечает за радиокнопки
```Kotlin
fun TagConsumer<HTMLElement>.input(
    classes : String? = null,
    options: List<String>,
    block : P.() -> Unit = {}
) : HTMLElement = p(
    classes
) {
    options.forEach {
        +it
        input (InputType.radio, name = "colors"){
            value = it
            onClickFunction = {
                val selectColor =
                    document.getElementById("root")!!
                    selectColor.setAttribute("style", "color:${value}")
            }
        }
    }
    block()
}
```

>**Запуск приложения, до нажатия**
 ![Рисунок 1](https://sun9-41.userapi.com/c206524/v206524753/11032e/TFHJT1rY96Y.jpg)
 
 >**После нажатия на кнопку, отвечающую за синий цвет**
 ![Рисунок 2](https://sun9-49.userapi.com/c206824/v206824985/135555/7LBqXw4Thw8.jpg)
 
 >**За зеленый цвет**
 ![Рисунок 3](https://sun9-65.userapi.com/c206824/v206824985/13555c/V007zuYl-vc.jpg)
 
 >**За красный цвет**
 ![Рисунок 4](https://sun9-19.userapi.com/c206824/v206824985/135563/0cqOoNjbPTs.jpg)
