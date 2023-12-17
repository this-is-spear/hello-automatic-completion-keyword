package tis.hellosearchword.search.ui

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tis.hellosearchword.search.domain.Keywords
import tis.hellosearchword.search.domain.SearchService

@RestController
class SearchController(
    private val searchService: SearchService
) {

    @GetMapping("/search")
    fun searchKeyword(@RequestParam q: String) : Keywords {
        return searchService.search(q)
    }

    @GetMapping("/complete")
    fun searchComplete(@RequestParam q: String) {
        return searchService.complete(q)
    }
}