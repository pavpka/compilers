class LexicalAnalyzer(private val programSymbols: CharArray) {
    private var symbolPosition: Int = 0
    private fun getSymbol(): Char = programSymbols[symbolPosition]
    private fun getNextSymbol(): Char =
        if (symbolPosition + 1 < programSymbols.size) programSymbols[symbolPosition + 1] else '#'

    val lexemTable: ArrayList<String> = arrayListOf()
    val idTable: ArrayList<String> = arrayListOf()
    val lines: ArrayList<Int> = arrayListOf()
    val outputTypes: ArrayList<String> = arrayListOf(
        "CONST",
        "IDENTIFIER",
        "TYPE",
        "COMMENT",
        "UNI_MATH_OPERATOR",
        "BIN_MATH_OPERATOR",
        "UNI_LOG_OPERATOR",
        "UNI_LOG_OPERATOR",
        "RELATION_OPERATOR"
    )
    var lineNumber = 1

    fun scan() {
        var lexem = ""
        while (symbolPosition < programSymbols.size) {
            val symbol = getSymbol()
            val isStartOfComment = symbol == '{'
            val isSpace = symbol == ' '
            val isColon = symbol == ':'
            val isSemiColon = symbol == ';'
            val isEndOfLine = (symbol == '\r' && getNextSymbol() == '\n')
            if (isColon) addToTable(LexemeTypes.LexemeClass.COLON, getSymbol().toString(), lineNumber)
            else if (isSemiColon) addToTable(LexemeTypes.LexemeClass.SEMICOLON, symbol.toString(), lineNumber)
            else if (isStartOfComment) getComment()
            else if (isEndOfLine) {
                addToTable(LexemeTypes.LexemeClass.NEWLINE, symbol.toString(), lineNumber)
                symbolPosition++
                lineNumber++
            } else if (!isSpace) {
                lexem += symbol

                val type = recognize(lexem)
                val typeLexemeWithNextSymbol = recognize(lexem + getNextSymbol())

                if (typeLexemeWithNextSymbol != LexemeTypes.LexemeClass.UNRECOGNIZED) {
                    symbolPosition++
                    continue
                } else addToTable(type, lexem, lineNumber)
            }
            symbolPosition++
            lexem = ""
        }

    }

    fun getComment() {
        var comment = ""
        addToTable(LexemeTypes.LexemeClass.COMMENT_START, " ", lineNumber)
        while (getSymbol() != '}') {
            comment += getSymbol()
            symbolPosition++
            if(getSymbol()=='\r' && getNextSymbol()=='\n') lineNumber++
        }
        addToTable(LexemeTypes.LexemeClass.COMMENT, comment.substring(1),lineNumber)
        addToTable(LexemeTypes.LexemeClass.COMMENT_END, " ",lineNumber)
    }

    fun recognize(lexem: String): LexemeTypes.LexemeClass {
        when (lexem) {
            "Var" -> return LexemeTypes.LexemeClass.VAR
            "=" -> return LexemeTypes.LexemeClass.ASSIGMENT
            "Begin" -> return LexemeTypes.LexemeClass.BEGIN_PROGRAM
            "End" -> return LexemeTypes.LexemeClass.END_PROGRAM
            "IF" -> return LexemeTypes.LexemeClass.IF
            "ELSE" -> return LexemeTypes.LexemeClass.ELSE
            "(" -> return LexemeTypes.LexemeClass.OPEN_BRACE
            ")" -> return LexemeTypes.LexemeClass.CLOSE_BRACE
        }

        val constPattern: Regex = "\\d+".toRegex()
        val idPattern: Regex = "[a-zA-Z]+".toRegex()

        if (LexemeTypes.BinMathOperations.values().map { it.lexeme }.contains(lexem) &&
            (lexemTable[lexemTable.size - 1] == "CONST" ||
                    lexemTable[lexemTable.size - 1] == "IDENTIFIER" ||
                    lexemTable[lexemTable.size - 1] == "CLOSE_BRACE")
        ) {
            return LexemeTypes.LexemeClass.BIN_MATH_OPERATOR
        } else if (LexemeTypes.UniMathOperations.values().map { it.lexeme }.contains(lexem)) {
            return LexemeTypes.LexemeClass.UNI_MATH_OPERATOR
        } else if (LexemeTypes.BinLogicalOperations.values().map { it.lexeme }.contains(lexem) &&
            (lexemTable[lexemTable.size - 1] == "CONST" ||
                    lexemTable[lexemTable.size - 1] == "IDENTIFIER" ||
                    lexemTable[lexemTable.size - 1] == "CLOSE_BRACE")
        ) {
            return LexemeTypes.LexemeClass.BIN_LOG_OPERATOR
        } else if (LexemeTypes.UniLogicalOperations.values().map { it.lexeme }.contains(lexem)) {
            return LexemeTypes.LexemeClass.UNI_LOG_OPERATOR
        } else if (LexemeTypes.Types.values().map { it.lexeme }.contains(lexem)) {
            return LexemeTypes.LexemeClass.TYPE
        } else if (LexemeTypes.Relations.values().map { it.lexeme }.contains(lexem)) {
            return LexemeTypes.LexemeClass.RELATION_OPERATOR
        } else if (lexem.matches(idPattern)) {
            return LexemeTypes.LexemeClass.IDENTIFIER
        } else if (lexem.matches(constPattern)) {
            return LexemeTypes.LexemeClass.CONST
        }
        return LexemeTypes.LexemeClass.UNRECOGNIZED
    }

    fun addToTable(type: LexemeTypes.LexemeClass, lexem: String, line: Int) {
        lexemTable.add(type.name)
        lines.add(line)
        if (outputTypes.contains(type.name)) {
            idTable.add(lexem)
        } else idTable.add("")
    }
}