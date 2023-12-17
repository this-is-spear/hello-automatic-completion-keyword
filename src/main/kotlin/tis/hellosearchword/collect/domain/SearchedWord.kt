package tis.hellosearchword.collect.domain

import java.time.LocalDateTime

data class SearchedWord(
    val word: String,
    val date: LocalDateTime = LocalDateTime.now()
)
