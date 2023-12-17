package tis.hellosearchword.search.domain

import org.springframework.stereotype.Service
import tis.hellosearchword.process.domain.TrieRepository

@Service
class KeywordService(
    private val trieRepository: TrieRepository
) {
    fun searchKeywordLimit5(keyword: String): List<String> {
        return trieRepository.searchChildren(keyword)
            .sortedByDescending { it.frequency }
            .subList(0, 5)
            .map { it.word }
    }
}