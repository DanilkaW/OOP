package data




data class Student(
    val firstname: String,
    val surname: String
)

{
    override fun toString(): String =
        "$firstname $surname"
}

val StudentList =
    arrayOf(
        Student("Sheldon", "Cooper"),
        Student("Leonard", "Hofstadter"),
        Student("Howard", "Wolowitz"),
        Student("Daniel", "Foxing"),
        Student("Andy", "Kartraid"),
        Student("Danya", "Kypryakov"),
        Student("Samuel", "Adamson")
    )