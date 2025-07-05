package AST;

public abstract class Node {
    // ノードの位置情報や共通属性があればここに追加できる（省略可能）

    // デバッグ用：ノードの概要を文字列で表示
    public abstract String toString();

    // 今後 Visitorパターン導入する：
    // public abstract <T> T accept(NodeVisitor<T> visitor);
}
