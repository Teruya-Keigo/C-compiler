package lexer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class Lexer {
    private BufferedReader reader;
    private int currentChar;
    private int line = 1;

    private static final Map<String, TokenType> keywords = new HashMap<>();
    static {
        keywords.put("int", TokenType.INT);
        keywords.put("void", TokenType.VOID);
        keywords.put("if", TokenType.IF);
        keywords.put("else", TokenType.ELSE);
        keywords.put("while", TokenType.WHILE);
        keywords.put("for", TokenType.FOR);
        keywords.put("return", TokenType.RETURN);
        keywords.put("read", TokenType.READ);
        keywords.put("write", TokenType.WRITE);
    }

    public Lexer(Reader source) throws IOException {
        this.reader = new BufferedReader(source);
        advance();
    }

    private void advance() throws IOException {
        currentChar = reader.read();
        if (currentChar == '\n') {
            line++;
        }
    }

    public Token getNextToken() throws IOException {
        skipWhitespaceAndComments();

        if (currentChar == -1) {
            return new Token(TokenType.EOF, "<EOF>", line);
        }

        // 識別子 or 予約語
        if (Character.isLetter(currentChar) || currentChar == '_') {
            return readIdentifierOrKeyword();
        }

        // 数字
        if (Character.isDigit(currentChar)) {
            return readNumber();
        }

        // 記号（1文字＋2文字対応）
        return readSymbol();
    }

    private void skipWhitespaceAndComments() throws IOException {
        while (Character.isWhitespace(currentChar)) {
            advance();
        }

     // コメントスキップ
        while (currentChar == '/') {
            reader.mark(1); // 1文字先読みの準備
            int nextChar = reader.read();
            if (nextChar == '/') {
                // 行末までスキップ
                while (currentChar != -1 && currentChar != '\n') {
                    advance();
                }
                advance(); // \n を読んで次の行へ
                while (Character.isWhitespace(currentChar)) {
                    advance();
                }
                // 再チェック（連続コメントにも対応）
            } else {
                // `//` じゃなければ巻き戻してreturn（演算子として処理）
                reader.reset();
                break;
            }
        }
    }

    private Token readIdentifierOrKeyword() throws IOException {
        StringBuilder sb = new StringBuilder();
        while (Character.isLetterOrDigit(currentChar) || currentChar == '_') {
            sb.append((char) currentChar);
            advance();
        }
        String word = sb.toString();
        TokenType type = keywords.getOrDefault(word, TokenType.IDENTIFIER);
        return new Token(type, word, line);
    }

    private Token readNumber() throws IOException {
        StringBuilder sb = new StringBuilder();
        while (Character.isDigit(currentChar)) {
            sb.append((char) currentChar);
            advance();
        }
        return new Token(TokenType.INT_LITERAL, sb.toString(), line);
    }

    private Token readSymbol() throws IOException {
        // 今回ここを実装："+", "-", "*", "/", "==", "<=", "&&" などを判定
        char ch = (char) currentChar;
        advance();
        switch (ch) {
        case '+': return new Token(TokenType.PLUS, "+", line);
        case '-': return new Token(TokenType.MINUS, "-", line);
        case '*': return new Token(TokenType.MULT, "*", line);
        case '/': return new Token(TokenType.DIV, "/", line);
        case ';': return new Token(TokenType.SEMI, ";", line);
        case ',': return new Token(TokenType.COMMA, ",", line);
        case '(': return new Token(TokenType.LPAREN, "(", line);
        case ')': return new Token(TokenType.RPAREN, ")", line);
        case '{': return new Token(TokenType.LBRACE, "{", line);
        case '}': return new Token(TokenType.RBRACE, "}", line);
        case '[': return new Token(TokenType.LBRACKET, "[", line);
        case ']': return new Token(TokenType.RBRACKET, "]", line);
        case '=':
            if (currentChar == '=') {
                advance();
                return new Token(TokenType.EQ, "==", line);
            } else {
                return new Token(TokenType.ASSIGN, "=", line);
            }
        case '!':
            if (currentChar == '=') {
                advance();
                return new Token(TokenType.NEQ, "!=", line);
            }
            break;
        case '<':
            if (currentChar == '=') {
                advance();
                return new Token(TokenType.LE, "<=", line);
            } else {
                return new Token(TokenType.LT, "<", line);
            }
        case '>':
            if (currentChar == '=') {
                advance();
                return new Token(TokenType.GE, ">=", line);
            } else {
                return new Token(TokenType.GT, ">", line);
            }
        case '&':
            if (currentChar == '&') {
                advance();
                return new Token(TokenType.AND, "&&", line);
            }
            break;
        case '|':
            if (currentChar == '|') {
                advance();
                return new Token(TokenType.OR, "||", line);
            }
            break;
    }
        return new Token(TokenType.UNKNOWN, Character.toString(ch), line);
    }
}
