package data

import kotlin.browser.document

data class Student(
    val firstname: String,
    val surname: String,
    var param: Boolean

)

val StudentList =
    arrayListOf(
        Student("Sheldon", "Cooper", true),
        Student("Leonard", "Hofstadter", true),
        Student("Howard", "Wolowitz", true),
        Student("Daniel", "Foxing", true),
        Student("Andy", "Kartraid", true),
        Student("Danya", "Kypryakov",true),
        Student("Samuel", "Adamson", true)
    )