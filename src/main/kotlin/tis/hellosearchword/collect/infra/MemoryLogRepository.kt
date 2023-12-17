package tis.hellosearchword.collect.infra

import org.springframework.stereotype.Repository
import tis.hellosearchword.collect.domain.SearchedWord

@Repository
class MemoryLogRepository {
    private var list: ArrayList<SearchedWord> = arrayListOf()

    fun save(word: String) {
        list.add(SearchedWord(word))
    }

    fun findAll(): List<SearchedWord> {
        return list
    }

    fun deleteAll() {
        list = arrayListOf()
    }
}