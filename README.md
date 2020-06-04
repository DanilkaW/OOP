>**Задания**
- Переделать приложение, реализовав компонент «Занятие»
- Поднять состояние компонента RStudentList в созданный компонент
- StudentList преобразовать в функциональный компонент

Основной код программы, отвечающий за список студентов и учебных предметов
```Kotlin
import data.StudentList
//import data.SubjectList
import react.dom.h1
import react.dom.li
import react.dom.ol
import react.dom.render
import kotlin.browser.document


fun main() {
    render(document.getElementById("root")!!) {
        h1 {
            +"Список студентов и учебных предметов"
        }
        //rsubject(SubjectList)
        rsubject()
    }
}
```

Переделаем приложение, реализовав компонент "Занятие". Компонент реализован в файле RSubject, который представлен ниже
```Kotlin
import data.Student
//import data.Subject
import data.StudentList
//import data.SubjectList
import org.w3c.dom.events.Event
import react.*
import react.dom.h1
import react.dom.li
import react.dom.ol
import react.dom.h2

interface RSubjectProps : RProps {
    var subject: String
    var listStudent :Array<Student>

}



interface RSubjectState : RState {
    var present: Array<Boolean>
}

class RSubject : RComponent<RSubjectProps, RSubjectState>() {
    override fun componentWillMount() {
        state.apply {
            present = Array(props.listStudent.size) { false }
        }
    }
    fun RBuilder.onIndex(): (Int) -> (Event) -> Unit = {
        onClick(it)
    }

    override fun RBuilder.render() {
        +props.subject
                ol {
            rstudentlist(props.listStudent, state.present, onIndex())
        }
    }

    fun RBuilder.onClick(index: Int): (Event) -> Unit = {
        setState {
            present[index] = !present[index]
        }
    }
}



fun RBuilder.rsubject() =
    child(RSubject::class)
    {
        attrs.subject = "Технология обработки информации"
        attrs.listStudent = StudentList.toTypedArray()
    }
```

В данной части мы подняли присутствие студента на уровень выше, в предмет
```Kotlin
interface RSubjectProps : RProps {
    var subject: Array<Subject>
    var listStudent :Array<Student>
}

interface RSubjectState : RState {
    var present: Array<Boolean>
}
```

В этой части кода мы переделали массив "StudentList" в функциональный компонент
```Kotlin
import data.Student
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.*
import react.dom.span
import react.functionalComponent

interface RStudentProps : RProps {
    var student: Student
    var present: Boolean
    var onClick: (Event)->Unit
}

val RFStudent =
    functionalComponent<RStudentProps> {
        span (
            if(it.present) "present" else "absent"
        ){
            +"${it.student.firstname} ${it.student.surname}"
            attrs.onClickFunction = it.onClick
        }
    }

fun RBuilder.rstudent(student: Student, present: Boolean, onClick: (Event)->Unit) =
    child(RFStudent) {
        attrs.student = student
        attrs.present = present
        attrs.onClick = onClick
    }
```


>**Список студентов до нажатия, студетны отсутствуют (рисунок 1)**
 ![Рисунок 1](https://sun9-66.userapi.com/c855720/v855720511/238d78/_hUsE50S__A.jpg)

>**Список студентов после нажатия, студенты 1,3,4,7 присутствуют (рисунок 2)**
 ![Рисунок 1](https://sun9-51.userapi.com/c855720/v855720511/238d82/QEbT-0JYRMc.jpg)
