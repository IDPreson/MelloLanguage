package com.company.Lexical;

import java.util.ArrayList;
public class LexicalTokens {
    public static class Token {
        public String name;
        public String type;

        @Override
        public String toString() {
            return "Token{" +
                    "name='" + name + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }
    /*
    定义TokenList容器，存放Tokens
    */
    ArrayList<Token> TokenVector=new ArrayList<>();
    /*
    将解析完毕的Token放入容器中
    */
    void addLexicalToken(String name, String type, String tempWords) {
        if (!name.equals("")) {
            Token tempTocken= new Token();
            tempTocken.name = tempWords;
            tempTocken.type = type;
            TokenVector.add(tempTocken);
        }
    }
    /*
    返回所有Tokens
    */
    ArrayList<Token> getTokens() {
        return TokenVector;
    }
}
