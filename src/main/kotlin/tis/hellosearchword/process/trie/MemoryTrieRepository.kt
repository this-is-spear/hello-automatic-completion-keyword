package tis.hellosearchword.process.trie

import java.util.Optional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import tis.hellosearchword.process.domain.Keyword
import tis.hellosearchword.process.domain.TrieRepository

@Service
class MemoryTrieRepository : TrieRepository {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)
    private val root = TrieNode()

    override fun search(word: String): Optional<Keyword> {
        var currentNode = root
        for (char in word) {
            currentNode = currentNode.children[char] ?: return Optional.empty()
        }
        return if (currentNode.isEndOfWord) Optional.of(Keyword(word, currentNode.frequency)) else Optional.empty()
    }

    override fun save(word: Keyword) {
        var currentNode = root
        for (char in word.word) {
            currentNode = currentNode.children.computeIfAbsent(char) { TrieNode() }
        }
        currentNode.frequency += word.frequency
        currentNode.isEndOfWord = true
    }

    override fun searchChildren(keyword: String): List<Keyword> {
        var currentNode = root
        for (char in keyword) {
            currentNode = currentNode.children[char] ?: return emptyList()
        }

        log.info("children is ${currentNode.children}")

        return currentNode.children.map { Keyword(keyword + it.key, it.value.frequency) }.toList()
    }
}
