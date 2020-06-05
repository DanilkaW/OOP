package data



data class Subject(
    val name: String
)

{
    override fun toString(): String = name
}

val SubjectList =
    arrayOf(
        Subject("Технология обработки информации"),
        Subject("Математика"),
        Subject("Философия")

    )