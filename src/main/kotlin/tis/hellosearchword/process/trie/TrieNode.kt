package tis.hellosearchword.process.trie

data class TrieNode(
    val children: MutableMap<Char, TrieNode> = mutableMapOf(),
    var frequency: Int = 0,
    var isEndOfWord: Boolean = false
)