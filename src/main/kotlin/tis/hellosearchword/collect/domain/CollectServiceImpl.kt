package tis.hellosearchword.collect.domain

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import tis.hellosearchword.collect.infra.MemoryLogRepository

@Service
class CollectServiceImpl(
    private val logRepository: MemoryLogRepository
) : CollectService {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun findAll(): List<SearchedWord> {
        return logRepository.findAll()
    }

    override fun deleteAll() {
        logRepository.deleteAll()
    }

    override fun saveLog(q: String) {
        log.info("save log : $q")
        logRepository.save(q)
    }
}