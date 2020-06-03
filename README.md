>**Задания**
- Перенести массив lessons из AppProps в AppState
- Добавить компонент AddLesson, который позволяет добавить урок в массив lesson

Переносим массив lessons из AppProps в AppState
```Kotlin
interface AppProps : RProps {
    var students: Array<Student>
}

interface AppState : RState {
    var subject: Array<Subject>
    var presents: Array<Array<Boolean>>
    var newsubject: String
}
```

Добавим компонент AddLesson, который позволяет добавить урок в массив lesson
```Kotlin
package component


import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.*
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import kotlin.browser.document


interface AppLessonProps : RProps {

    var newsubject: (String) -> Any
}


val addLessons =
    functionalComponent<AppLessonProps> { props ->
        div {
            h3 {
                +"Введите название предмета"
            }
            input(type = InputType.text, name = "1")
            {
                attrs.id = "subject"
            }

            input(type = InputType.submit, name = "2") {
                attrs.onClickFunction =
                    {
                        val subject = document.getElementById("subject")
                                as HTMLInputElement
                        val tmp = subject.value
                        //console.log(tmp)
                        props.newsubject(tmp)
                    }
                }
            }
        }



fun RBuilder.addlesson(
     newsubject: (String) -> Any
) = child(addLessons){
    attrs.newsubject= newsubject
}
```


>**Запустили приложение (рисунок 1)**
 ![Рисунок 1](https://sun9-5.userapi.com/c857332/v857332163/134fbd/FX5wtPKUW7s.jpg)
 ![Рисунок 1.2](https://sun9-35.userapi.com/c857332/v857332163/134fc7/_ZlQQff2_r4.jpg)

>**Добавляем новый предмет (рисунок 2)**
 ![Рисунок 2](https://sun9-28.userapi.com/c857332/v857332163/134fd1/srxeQKFkDXQ.jpg)

>**Успешное добавление предмета (рисунок 3)**
 ![Рисунок 3](https://sun9-39.userapi.com/c857332/v857332163/134fdb/NMq-2uPN83A.jpg)
 
>**Отмечаем студентов (рисунок 4)**
 ![Рисунок 4](https://sun9-12.userapi.com/c857332/v857332992/132b50/pfYcScVvJxc.jpg)
 ![Рисунок 4.1](https://sun9-58.userapi.com/c857332/v857332163/134fef/L4rbsix8AZc.jpg)