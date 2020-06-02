>**Задания**

-Переделать список студентов с помощью элементов и компонентов React

Код функционального компонента, который отвечает за "Список студентов"
```Kotlin
fun RBuilder.rstudent(student: Student) =
    child(
        functionalComponent<RStudentProps> {
            +"${it.student.firstname} ${it.student.surname}"
        }
    ){
        attrs.student = student
    }
```

Основной код программы
```Kotlin
import data.StudentList
import react.dom.h1
import react.dom.render
import kotlin.browser.document


fun main() {
    render(document.getElementById("root")!!) {
        h1 {
            +"Список студентов"
        }
        StudentList(StudentList)
    }
}
```

>**На рисунке представлен список студентов, который был создан одним компонентом, в котором находятся семь функциональных компонентов**
 ![Рисунок 1](https://sun9-72.userapi.com/c857736/v857736968/2042c0/ZpHzPLZPmbo.jpg)

