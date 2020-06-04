>**Задания**
- Доработать приложение, добавив компонент, разработанный в последнем задании прошлого урока на отдельную страницу приложения и прописав маршруты к нему

Добавили ранее созданый компонент AddLesson на отдельную страницу приложения, прописав маршрут к нему
```Kotlin
package component


import data.*
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import react.router.dom.*


interface AppProps : RProps {

    var students: Array<Student>
}

interface AppState : RState {
    var subject: Array<Subject>
    var presents: Array<Array<Boolean>>
}

interface RouteNumberResult : RProps {
    var number: String
}

class App : RComponent<AppProps, AppState>() {
    override fun componentWillMount() {
        state.subject = SubjectList
        state.presents = Array(state.subject.size) {
            Array(props.students.size) { false }
        }
    }

    fun new_subject (): (String) -> Any = { new_subject ->
        setState {
            subject+= Subject(new_subject)
            presents+= arrayOf(Array(props.students.size) { false })
        }
    }

    override fun RBuilder.render() {
        header {
            h1 { +"App" }
            nav {
                ul {
                    li { navLink("/Lessons") { +"Список предметов" } }
                    li { navLink("/Students") { +"Список студентов" } }
                    li { navLink("/AddLesson") { +"Добавить предмет" } }
                }
            }
        }

        switch {
            route("/Lessons",
                exact = true,
                render = {
                    subjectList(state.subject)
                }
            )
            route("/Students",
                exact = true,
                render = {
                    studentList(props.students)
                } )
            route("/AddLesson",
                exact = true,
                render = {
                    addlesson(new_subject())
                }
            )
            route("/lessons/:number",
                render = { route_props: RouteResultProps<RouteNumberResult> ->
                    val num = route_props.match.params.number.toIntOrNull() ?: -1
                    val subject = state.subject.getOrNull(num)
                    if (subject != null)
                        lessonFull(
                            subject,
                            props.students,
                            state.presents[num]
                        ) { onClick(num, it) }
                    else
                        p { +"Нет такого предмета!" }
                }
            )
            route("/students/:number",
                render = { route_props: RouteResultProps<RouteNumberResult> ->
                    val num = route_props.match.params.number.toIntOrNull() ?: -1
                    val student = props.students.getOrNull(num)
                    if (student != null)
                        studentFull(
                            state.subject,
                            student,
                            state.presents.map {
                                it[num]
                            }.toTypedArray()
                        ) { onClick(it, num) }
                    else
                        p { +"Нет такого студента!" }
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

fun RBuilder.app(
    students: Array<Student>
) = child(App::class) {
    attrs.students = students
}
```

Часть кода, которая отвечает за переход между страницами
```Kotlin
li { navLink("/Lessons") { +"Список предметов" } }
li { navLink("/Students") { +"Список студентов" } }
li { navLink("/AddLesson") { +"Добавить предмет" } }
```

Часть кода, отвечающая за отрисовку компонентов
```Kotlin
switch {
    route("/Lessons",
        exact = true,
        render = {
            subjectList(state.subject)
    })
    route("/Students",
        exact = true,
        render = {
            studentList(props.students)
    })
    route("/AddLesson",
        exact = true,
        render = {
            addlesson(new_subject())
    })
```

>**Запустили приложение (рисунок 1)**
 ![Рисунок 1](https://sun9-36.userapi.com/c857124/v857124664/191455/lhhHtFu1748.jpg)

>**Добавляем новый предмет (рисунок 2)**
 ![Рисунок 2](https://sun9-10.userapi.com/c857124/v857124664/19145f/cHlcvLv0GyM.jpg)

>**Успешное добавление предмета (рисунок 3)**
 ![Рисунок 3](https://sun9-25.userapi.com/c857124/v857124664/191469/UYBv1bt0ALU.jpg)
 
>**Отмечаем студентов (рисунок 4)**
 ![Рисунок 4](https://sun9-47.userapi.com/c857124/v857124664/191473/OAtagwonZ_M.jpg)
 ![Рисунок 4.1](https://sun9-9.userapi.com/c857124/v857124664/19147d/PZbN6R0hVG0.jpg)

>**Откроем список студентов (рисунок 5)**
 ![Рисунок 5](https://sun9-26.userapi.com/c857124/v857124664/191491/g6NPZZukUww.jpg)

>**Нажмем на студента и отметим его на нескольких предметах (рисунок 6)**
 ![Рисунок 6](https://sun9-31.userapi.com/c857124/v857124664/191487/pzld9Zsj2fI.jpg)