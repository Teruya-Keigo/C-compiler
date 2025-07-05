
# 進捗報告（2025.7.6）

Lexerが完成し、Parserは現在開発途中です。

---

# 実行方法

現在の状態でそのまま実行するとエラーが出力されますが、**それで正常です**のでご安心ください。  
まだParserは開発中ですが、Lexerまでの挙動を確認したい方は、以下の手順で `Main.java` を書き換えてください。

---

## ✅ 手順

1. **import文の部分に以下を追加：**

   ```java
   import lexer.Token;
   import lexer.TokenType;
　　```

2. **Mainクラスの `main` メソッド内の `try-catch` で以下の文を削除：**

   ```java
   Parser parser = new Parser(lexer);
   parser.parseProgram();
   ```

3. **同メソッドの `try` 内に以下の文を追加：**

   ```java
   Token token;
   do {
       token = lexer.getNextToken();
       System.out.println(token);
   } while (token.getType() != TokenType.EOF);
   ```

4. **実行ボタンをポチッとな！**

---

以上の方法でLexerの挙動を確認できます。
`if` 文のほか、`while` 文や `for` 文にも対応しています。
対応しているトークンの一覧は、以下のファイルに記載されています：

📄 `C-compiler/src/lexer/Token.java`

---

ぜひお試しください！

