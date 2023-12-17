package tis.hellosearchword.process.domain

import org.springframework.stereotype.Service
import tis.hellosearchword.collect.domain.CollectService

private const val ZERO = 0

@Service
class FrequencyExtractService(
    private val collectService: CollectService
) {

    fun getFrequencies(): Map<String, Int> {
        val keywordToFrequency = mutableMapOf<String, Int>()

        for (searchedWord in collectService.findAll()) {
            val frequency = keywordToFrequency.computeIfAbsent(searchedWord.word) { ZERO }
            keywordToFrequency[searchedWord.word] = frequency + 1
        }

        collectService.deleteAll()
        return keywordToFrequency
    }
}