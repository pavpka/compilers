class LexemeTypes {
    enum class LexemeClass {
        VAR,
        IDENTIFIER,
        ASSIGMENT,
        CONST,
        BEGIN_PROGRAM,
        END_PROGRAM,
        OPEN_BRACE,
        CLOSE_BRACE,
        COLON,
        SEMICOLON,
        TYPE,
        IF,
        ELSE,
        RELATION_OPERATOR,
        UNI_LOG_OPERATOR,
        BIN_LOG_OPERATOR,
        UNI_MATH_OPERATOR,
        BIN_MATH_OPERATOR,
        NEWLINE,
        COMMENT_START,
        COMMENT_END,
        COMMENT,
        UNRECOGNIZED,
    }

    enum class KeyWords(val lexeme: String) {
        Begin("Begin"),
        End("End"),
        Var("Var"),
        Const("Const"),
        If("IF"),
        Else("ELSE"),
    }

    enum class Types(val lexeme: String) {
        Boolean("Boolean"),
        Decimal("Decimal")
    }

    enum class Separators(val lexeme: String) {
        LBrace("("),
        RBrace(")"),
    }

    enum class Assignment(val lexeme: String) {
        SimpleAssign("=")
    }

    enum class Relations(val lexeme: String) {
        Equal("=="),
        Less("<"),
        More(">")
    }

    enum class BinLogicalOperations(val lexeme: String) {
        And("&"),
        Or("|")
    }

    enum class BinMathOperations(val lexeme: String) {
        Sub("-"),
        Add("+"),
        Mul("*"),
        Div("/"),
        Sup("^")
    }

    enum class UniLogicalOperations(val lexeme: String) {
        Not("!")
    }

    enum class UniMathOperations(val lexeme: String) {
        Sub("-")
    }

    enum class Comments(val lexeme: String) {
        StartComment("{"),
        EndComment("}")
    }
}