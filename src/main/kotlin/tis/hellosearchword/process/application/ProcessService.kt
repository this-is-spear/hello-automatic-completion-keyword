package tis.hellosearchword.process.application

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import tis.hellosearchword.process.domain.FrequencyExtractService
import tis.hellosearchword.process.domain.TrieService

@Service
class ProcessService(
    private val frequencyExtractService: FrequencyExtractService,
    private val trieService: TrieService
) {

    /**
     * 트라이 자료 구조를 주기적으로 업데이트합니다.
     */
    @Scheduled(fixedRate = 1000)
    fun updateTrie() {
        val frequencies = frequencyExtractService.getFrequencies()
        trieService.saveAll(frequencies)
    }
}