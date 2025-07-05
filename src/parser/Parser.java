package parser;

import lexer.Lexer;
import lexer.Token;
import lexer.TokenType;
import java.io.IOException;

public class Parser {
    private final Lexer lexer;
    private Token currentToken;

    public Parser(Lexer lexer) throws IOException {
        this.lexer = lexer;
        this.currentToken = lexer.getNextToken();
    }

    // トークンが期待される型なら読み進める
    private void eat(TokenType expected) throws IOException {
        if (currentToken.getType() == expected) {
            currentToken = lexer.getNextToken();
        } else {
            throw new RuntimeException("構文エラー: " + expected + " を期待していたが、見つかったのは " + currentToken);
        }
    }

    // プログラムの入口
    public void parseProgram() throws IOException {
        System.out.println("Parsing started.");

        // とりあえず変数宣言や関数を読み飛ばす構造だけ用意
        while (currentToken.getType() != TokenType.EOF) {
            parseTopLevel();  // 今後: 変数定義 or 関数定義 or main
        }

        System.out.println("Parsing completed.");
    }

    // トップレベル構文：関数定義やmainなどを処理
    private void parseTopLevel() throws IOException {
        if (currentToken.getType() == TokenType.INT || currentToken.getType() == TokenType.VOID) {
            parseFunctionOrDeclaration();
        } else {
            throw new RuntimeException("構文エラー: トップレベルで不正なトークン " + currentToken);
        }
    }

    // 仮：int の後に identifier → 関数か変数かを判定する分岐
    private void parseFunctionOrDeclaration() throws IOException {
        eat(currentToken.getType()); // INT or VOID
        eat(TokenType.IDENTIFIER);  // 関数名 or 変数名

        if (currentToken.getType() == TokenType.LPAREN) {
            parseFunctionDefinition();
        } else {
            parseGlobalVariableDeclaration();
        }
    }

    private void parseFunctionDefinition() throws IOException {
        System.out.println("関数定義を解析中...");
        // 今後：引数・本体などパース
        while (currentToken.getType() != TokenType.LBRACE) {
            eat(currentToken.getType());  // 引数リスト読み飛ばし
        }
        parseBlock();
    }

    private void parseGlobalVariableDeclaration() throws IOException {
        System.out.println("グローバル変数定義を解析中...");
        while (currentToken.getType() != TokenType.SEMI) {
            eat(currentToken.getType());
        }
        eat(TokenType.SEMI);
    }

    private void parseBlock() throws IOException {
        eat(TokenType.LBRACE);
        while (currentToken.getType() != TokenType.RBRACE) {
            eat(currentToken.getType());  // 今は読み飛ばしだけ
        }
        eat(TokenType.RBRACE);
    }
}
