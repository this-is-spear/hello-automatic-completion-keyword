package tis.hellosearchword.process.domain

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * 트라이 자료구조를 갱신합니다.
 */
@Service
class TrieService(
    private val trieRepository: TrieRepository
) {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    fun saveAll(updatedKeywords: Map<String, Int>) {
        for (updatedKeyword in updatedKeywords) {
            log.info("word is ${updatedKeyword.key}, frequency is ${updatedKeyword.value}")
            trieRepository.save(Keyword(updatedKeyword.key, updatedKeyword.value))
        }
    }
}
