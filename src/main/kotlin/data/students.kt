package data

import kotlin.browser.document


data class Student(
    val firstname: String,
    val surname: String
)


val StudentList =
    arrayListOf(
        Student("Sheldon", "Cooper"),
        Student("Leonard", "Hofstadter"),
        Student("Howard", "Wolowitz"),
        Student("Daniel", "Foxing"),
        Student("Andy", "Kartraid"),
        Student("Danya", "Kypryakov"),
        Student("Samuel", "Adamson")
    )

