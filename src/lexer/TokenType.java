package lexer;

public enum TokenType {
	// キーワード
    INT, VOID, IF, ELSE, WHILE, FOR, RETURN, READ, WRITE,

    // 演算子・記号
    PLUS, MINUS, MULT, DIV,
    ASSIGN, EQ, NEQ, LT, GT, LE, GE,
    AND, OR,
    SEMI, COMMA,
    LPAREN, RPAREN, LBRACE, RBRACE, LBRACKET, RBRACKET,

    // 識別子・リテラル
    IDENTIFIER, INT_LITERAL,

    // 特殊
    EOF, UNKNOWN
}
