package tis.hellosearchword.collect.domain

interface CollectService {
    fun findAll(): List<SearchedWord>
    fun deleteAll()
    fun saveLog(q: String)
}