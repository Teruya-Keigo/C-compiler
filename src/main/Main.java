package main;

import lexer.Lexer;
import parser.Parser;

import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String filename = "src/test.mc";  // 読み込むソースファイル名

        try {
            FileReader fr = new FileReader(filename);
            Lexer lexer = new Lexer(fr);
            Parser parser = new Parser(lexer);
            parser.parseProgram();

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
