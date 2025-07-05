#進捗報告(2025.7.6)
Lexerが完成しParserは途中です。

#実行方法
現在の状態でそのまま実行するとエラーが吐かれますがそれで正常ですのでご安心ください。
まだParserの開発中ですので一旦Lexerまでの実行を確認したい方のためにcodeクローン後
以下のようにMain.javaを書き換えてください。
1.以下のコードをimport文の部分に追加
'import lexer.Token;
import lexer.TokenType;'

2.Mainクラスのmainメソッド内のtry-catchの以下の文を消去
' Parser parser = new Parser(lexer);
  parser.parseProgram();'

3.同メソッド内のtry-catchのtry内に以下の文を追加
' Token token;
            do {
                token = lexer.getNextToken();
                System.out.println(token);
            } while (token.getType() != TokenType.EOF);
'

4.実行ボタンをポチッとな

以上の方法でLexerの挙動を確認できますので、ぜひ試してみてください。if文の他にwhile文やfor文にも対応しております。
どのトークンに対応しているかはC-compiler/src/lexer/Token.javaに記載されてるトークンからご確認ください。
  
