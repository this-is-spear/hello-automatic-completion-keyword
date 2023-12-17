package tis.hellosearchword.process.domain

import java.util.Optional

interface TrieRepository {
    fun search(word: String): Optional<Keyword>
    fun save(word: Keyword)
    fun searchChildren(keyword: String): List<Keyword>
}
