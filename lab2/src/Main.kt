import java.io.File

fun main() {
    val program = readFile("in.txt")
    val lexicalAnalyzer = LexicalAnalyzer(program)
    lexicalAnalyzer.scan()
    writeToFile(lexicalAnalyzer, "out.txt")
}

fun readFile(filePath: String): CharArray {
    val bytes: ByteArray = File(filePath).readBytes()
    val chars = CharArray(bytes.size)
    bytes.forEachIndexed { index, byte -> chars[index] = byte.toChar() }
    return chars
}

fun writeToFile(lexicalAnalyzer: LexicalAnalyzer, filePath: String) {
    val file = File(filePath)
    file.writeText("")
    for (i in 0 until lexicalAnalyzer.lexemTable.size) {
        val out = if (lexicalAnalyzer.idTable[i] != "") {
            "${lexicalAnalyzer.lexemTable[i]} - ${lexicalAnalyzer.idTable[i]} - lineNumber: ${lexicalAnalyzer.lines[i]}\n"
        } else {
            "${lexicalAnalyzer.lexemTable[i]} - lineNumber: ${lexicalAnalyzer.lines[i]}\n"
        }
        file.appendText(out)
    }
}