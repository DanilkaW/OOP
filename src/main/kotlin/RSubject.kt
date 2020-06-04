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
