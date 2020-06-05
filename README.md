>**Задания**
- Доработать приложение, разработать компоненты
- Компонент, отвечающий за удаление и добавление студентов
- Компонент, отвечающий за удаление и добавление предметов

Создали компонент, который отвечает за изменение в списке студентов. В нем находится функция input типа text для ввода имени и фамилии студентов.
```Kotlin
package component

import hoc.withDisplayName
import kotlinx.html.InputType
import kotlinx.html.id
import react.*
import react.dom.*

val addstudent =
    functionalComponent<RProps > {
        div {

            p{
                h3 {
                    +"Введите имя и фамилию студента"
                }
            input(type = InputType.text)
            {
                attrs.id = "student"
                attrs.placeholder = "Для удаления введите номер студента"
            }
            }
        }
    }

fun RBuilder.addstudent(
) = child(
    withDisplayName("studentsAdd", addstudent)
) {}
```

Создали компонент, который отвечает за изменение в списке предметов. В нем находится функция input типа text для ввода названия предмета.
```Kotlin
package component

import hoc.withDisplayName
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*

val addLessons =
    functionalComponent<RProps> {
        div {
            h3 {
                +"Введите название предмета"
            }
            input(type = InputType.text)
            {
                attrs.id = "subject"
               attrs.placeholder = "Для удаления введите номер предмета"
            }
        }
    }

fun RBuilder.addLessons(
) = child(
    withDisplayName("lessonsAdd", addLessons)
) {}
```

Создали компонент, в котором находится функции input типа submit и reset, которые отвечают за потдверждение редактирования списков студентов и предметов 
```Kotlin
package component

import hoc.withDisplayName
import kotlinx.html.InputType
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.*
import react.dom.*

interface Any_add_or_del_Props<O> : RProps {
    var subobjs: Array<O>
    var name : String
    var path : String
    var add: (Event) -> Unit
    var del: (Event) -> Unit
}

fun <O> Any_add_or_dell_Full(
    rChange: RBuilder.() -> ReactElement,
    rComponent: RBuilder.(Array<O>, String, String) -> ReactElement
) =
    functionalComponent<Any_add_or_del_Props<O>>
    {props ->
        div {
            h3 {
                +"Изменение"
            }
            rChange()
            input(type = InputType.submit) {
                attrs.onClickFunction = props.add
            }
            input(type = InputType.reset) {
                attrs.onClickFunction = props.del
            }
            rComponent(props.subobjs,props.name,props.path)
        }
    }

fun <O> RBuilder.Any_add_or_dell_Full(
    rChange: RBuilder.() -> ReactElement,
    rComponent: RBuilder.(Array<O>, String, String) -> ReactElement,
    subobjs: Array<O>,
    name : String,
    path : String,
    add: (Event) -> Unit,
    del: (Event) -> Unit
) = child(
    withDisplayName("Any_add_or_dell_Full",  Any_add_or_dell_Full<O>(rChange, rComponent))
){
    attrs.subobjs = subobjs
    attrs.name = name
    attrs.path = path
    attrs.add = add
    attrs.del = del
}
```

Изменили компонент app, чтобы он работал с новыми  созданными компонентами. Для того, чтобы приложение корректно работало, мы перенесли студентов из свойств в состояние. Добавили функции удаления и добавления студента или предмета. Ниже представлен код компонента app.
```Kotlin
package component

import data.*
import hoc.withDisplayName
import org.w3c.dom.HTMLInputElement
import kotlin.reflect.KClass
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import react.router.dom.*
import kotlin.browser.document

interface AppState : RState {
    var subject: Array<Subject>
    var presents: Array<Array<Boolean>>
    var students: Array<Student>
}

interface RouteNumberResult : RProps {
    var number: String
}

class App : RComponent<RProps, AppState>() {
    override fun componentWillMount() {
        state.students = StudentList
        state.subject = SubjectList
        state.presents = Array(state.subject.size) {
            Array(state.students.size) { false }
        }
    }

    fun addSubject () = {  _: Event ->
        val new_subject = document.getElementById("subject") as HTMLInputElement
        var tmp = new_subject.value
        setState {
            subject+= Subject(tmp)
            presents+= arrayOf(Array(state.students.size) { false })
        }
    }

    fun delSubject () = { _: Event ->

        val del = document.getElementById("subject") as HTMLInputElement
        val change = state.subject.toMutableList().apply {
            removeAt(del.value.toInt()-1) }
        val changePresents = state.presents.toMutableList().apply {
            removeAt(del.value.toInt() - 1)
            }
        setState{
            subject = change.toTypedArray()
            presents= changePresents.toTypedArray()
        }
    }

    fun addStudent () = {  _: Event ->
        val name = document.getElementById("student") as HTMLInputElement
        val tmp = name.value.split(" ")
        val fname = tmp[0]
        val sname = tmp[1]
        setState {
            students += Student(fname, sname)
            presents += arrayOf(Array(state.students.size){false})
        }
    }

    fun delStudent () = { _: Event ->
        val del = document.getElementById("student") as HTMLInputElement
        val change = state.students.toMutableList().apply {
           removeAt(del.value.toInt()-1)
              }
        val changePresentsf = state.presents.toMutableList().apply {
           removeAt(del.value.toInt()-1)
            }
        setState{
            students = change.toTypedArray()
            presents= changePresentsf.toTypedArray()
        }
    }

    override fun RBuilder.render() {
        header {
            h1 { +"App" }
            nav {
                ul {
                    li { navLink("/lessons") { +"Список предметов" } }
                    li { navLink("/students") { +"Список студентов" } }
                    li { navLink("/changeLesson") { +"Изменение предмета" } }
                    li { navLink("/changeStudent") { +"Изменение студента" } }
                }
            }
        }

        switch {
            route("/lessons",
                exact = true,
                render = {
                     anyList(state.subject,"Список предметов","/lessons")
                }
            )
            route("/students",
                exact = true,
                render = {
                  anyList(state.students,"Список студентов","/students")
                } )
            route("/changeLesson",
                exact = true,
                render = {
                    Any_add_or_dell_Full(
                        RBuilder::addLessons,
                        RBuilder::anyList,
                        state.subject,
                        "Изменение урока",
                        "/lessons",
                        addSubject(),
                        delSubject()
                        )
                    }
            )
            route("/changeStudent",
                exact = true,
                render = {
                    Any_add_or_dell_Full(
                    RBuilder::addstudent,
                    RBuilder::anyList,
                    state.students,
                    "Изменение студента",
                    "/students",
                    addStudent(),
                    delStudent()
                    )
                } )
             route("/lessons/:number",
                render = { route_props: RouteResultProps<RouteNumberResult> ->
                    val num = route_props.match.params.number.toIntOrNull() ?: -1
                    val lesson = state.subject.getOrNull(num)
                    if (lesson != null)
                        anyFull(
                            RBuilder::student,
                            lesson,
                            state.students,
                            state.presents[num]
                        ) { onClick(num, it) }
                    else
                        p { +"Нет такого предмета" }
                }
            )
            route("/students/:number",
                render = { route_props: RouteResultProps<RouteNumberResult> ->
                    val num = route_props.match.params.number.toIntOrNull() ?: -1
                    val student = state.students.getOrNull(num)
                    if (student != null)
                        anyFull(
                            RBuilder::lesson,
                            student,
                            state.subject,
                            state.presents.map {
                                it[num]
                            }.toTypedArray()
                        ) { onClick(it, num) }
                                   else
                        p { +"Нет такого студента" }
                }
            )
        }
    }

    fun onClick(indexLesson: Int, indexStudent: Int) =
        { _: Event ->
            setState {
                presents[indexLesson][indexStudent] =
                    !presents[indexLesson][indexStudent]
            }
        }
}

fun RBuilder.app() = child(withDisplayName("AppHoc", App::class)) {}

fun r(){
    val b = mutableListOf(1.2, 4.5, 3.2, 1.8, 3.2)
    b.removeAt(0)
    b.remove(3.2)
}
```

Часть кода, отвечающая за изменение списка предметов
```Kotlin
fun addSubject () = {  _: Event ->
        val new_subject = document.getElementById("subject") as HTMLInputElement
        var tmp = new_subject.value
        setState {
            subject+= Subject(tmp)
            presents+= arrayOf(Array(state.students.size) { false })
        }
    }

    fun delSubject () = { _: Event ->

        val del = document.getElementById("subject") as HTMLInputElement
        val change = state.subject.toMutableList().apply {
            removeAt(del.value.toInt()-1) }
        val changePresents = state.presents.toMutableList().apply {
            removeAt(del.value.toInt() - 1)
            }
        setState{
            subject = change.toTypedArray()
            presents= changePresents.toTypedArray()
        }
    }
```

Часть кода, отвечающая за изменение списка студентов
```Kotlin
fun addStudent () = {  _: Event ->
        val name = document.getElementById("student") as HTMLInputElement
        val tmp = name.value.split(" ")
        val fname = tmp[0]
        val sname = tmp[1]
        setState {
            students += Student(fname, sname)
            presents += arrayOf(Array(state.students.size){false})
        }
    }

    fun delStudent () = { _: Event ->
        val del = document.getElementById("student") as HTMLInputElement
        val change = state.students.toMutableList().apply {
           removeAt(del.value.toInt()-1)
              }
        val changePresentsf = state.presents.toMutableList().apply {
           removeAt(del.value.toInt()-1)
            }
        setState{
            students = change.toTypedArray()
            presents= changePresentsf.toTypedArray()
        }
    }
```

>**Запустили приложение (рисунок 1)**
 ![Рисунок 1](https://sun9-29.userapi.com/c206720/v206720325/13b963/xRFJ88Yromw.jpg)

>**Рисунок 2, добавляем студента**
 ![Рисунок 2](https://sun9-26.userapi.com/c857620/v857620367/208404/zTJrwOtaCL4.jpg)
 ![Рисунок 2.1](https://sun9-16.userapi.com/c857620/v857620367/20840e/zv45NlZnGoI.jpg)

>**Рисунок 3, удаляем студента**
 ![Рисунок 3](https://sun9-53.userapi.com/c857620/v857620367/208422/unxt7iI7eec.jpg)
 ![Рисунок 3.1](https://sun9-8.userapi.com/c857620/v857620367/20842c/cuGHdrUT1Pc.jpg)
 
>**Рисунок 4, добавляем предмет**
 ![Рисунок 4](https://sun9-4.userapi.com/c857620/v857620367/208436/4MxqEPdtGNs.jpg)
 ![Рисунок 4.1](https://sun9-23.userapi.com/c857620/v857620367/208441/FbQt-JDYJc0.jpg)

>**Рисунок 5, удаляем предмет**
 ![Рисунок 5](https://sun9-50.userapi.com/c857620/v857620367/20844b/4BrKgIXHchQ.jpg)
 ![Рисунок 5.1](https://sun9-58.userapi.com/c857620/v857620367/208455/2j8JmwJct2U.jpg)

>**Откроем добавленный предмет (рисунок 6)**
 ![Рисунок 6](https://sun9-47.userapi.com/c857620/v857620367/20845f/_hiIf_6Qo6o.jpg)

>**Откроем добавленного студента (рисунок 7)**
 ![Рисунок 7](https://sun9-71.userapi.com/c857620/v857620367/208469/MetoTEJqCfk.jpg)