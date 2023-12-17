package tis.hellosearchword.search.domain

import org.springframework.stereotype.Service
import tis.hellosearchword.collect.domain.CollectService

@Service
class SearchService(
    private val collectService: CollectService,
    private val keywordService: KeywordService
) {
    fun search(q: String): Keywords {
        return Keywords(keywordService.searchKeywordLimit5(q))
    }

    fun complete(q: String) {
        collectService.saveLog(q)
    }
}
