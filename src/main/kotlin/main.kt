import data.Student
import data.StudentsList
import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.js.h1
import kotlinx.html.js.li
import kotlinx.html.js.ol
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.events.Event
import kotlin.browser.document
import kotlin.dom.clear


var ascending = true


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




private fun H1.onCLickFunction(): (Event) -> Unit {
    return {
        val listStudents = document.getElementById("listStudents")!!
        listStudents.clear()
        listStudents.append {
            if (ascending)
                StudentsList.sortBy { it.firstname }
            else
                StudentsList.sortByDescending { it.firstname }
            ascending = !ascending
            StudentsList.map {
                li {
                    attributes += "id" to it.firstname
                    +"${it.firstname} ${it.surname}"
                    onClickFunction = Click(it)
                }
            }
        }
    }
}



private fun Click(Student: Student): (Event) -> Unit {
    return {
        val student = document.getElementById(Student.firstname)!!
        if (Student.param) {
            student.setAttribute("style", "color:gray")
            Student.param = !Student.param
        }
        else {
            student.setAttribute("style", "color:black")
            Student.param = !Student.param
        }
    }
}

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
