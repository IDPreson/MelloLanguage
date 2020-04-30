package com.company.Lexical;

import java.util.ArrayList;
import java.util.HashMap;

public class LexicalParser {
    HashMap<String,Boolean> KeyWords=new HashMap<>();
    HashMap<String,Boolean> Operators=new HashMap<>();
    String tempWords = "";
    LexicalStatements lexicalStatements=new LexicalStatements();
    LexicalTokens lexicalTokens=new LexicalTokens();
    void init() {
        KeyWords.put("int",true);
        KeyWords.put("string",true);
        KeyWords.put("if",true);
        KeyWords.put("else",true);
        KeyWords.put("for",true);
        KeyWords.put("bool",true);
        KeyWords.put("func",true);
        KeyWords.put(";",true);
        //-----------------------------------------------------
        Operators.put("{", true);
        Operators.put("}", true);
        Operators.put("(", true);
        Operators.put(")", true);
        Operators.put("+", true);
        Operators.put("-", true);
        Operators.put("*", true);
        Operators.put("/", true);
        Operators.put("%", true);
        Operators.put("=", true);
        Operators.put("!=", true);
        Operators.put("'", true);
        Operators.put(">", true);
        Operators.put("<", true);
        Operators.put(",", true);
        Operators.put("&&", true);
        Operators.put("||", true);
    }
    int isOperators(String tempWords) {
        if (!Operators.containsKey(tempWords)) {
            return 0;
        }
        else {
            return 2;
        }
    }
    /*
    解析是否是关键字
    */
    int isKeyWords(String tempWords) {
        if (!KeyWords.containsKey(tempWords)) {
            return isOperators(tempWords);
        }
        else {
            return 1;
        }
    }
    /*
    判断Token类型
    */
    void TokenType(String tempWords) {
        String type = "";
        switch (isKeyWords(tempWords)) {
            case 1:
                System.out.println("关键字："+tempWords);
                type = "KeyWords";
                break;
            case 2:
                System.out.println("运算符dd："+tempWords);
                type = "Operators";
                break;
            default:
                if (!tempWords.equals("")) {
                    System.out.println("常量值："+tempWords);
                    type = "Constant";
                }
                break;
        }
        lexicalTokens.addLexicalToken(tempWords, type,tempWords);
    }
    /*
    运算符判断第一步
    */
    boolean isOperators(char tempChar) {
        switch (tempChar) {
            case '+':case '-':case '*':case '/':case '%':case '=':case '(':case ')':case '{':case '}':case ';':case 39:case '>':case '<':
            case ',':case '!':
                return true;
            default:
                return false;
        }
    }
    /*
    语法解析核心
    */
    public ArrayList<LexicalTokens.Token> DealParser(String codes) {
        int length = codes.length();
        tempWords="";
        init();
        for(int pos=0;pos<length;pos++){
            if (codes.charAt(pos) == ' ' || codes.charAt(pos) == '\n') {
                TokenType(tempWords);
                tempWords = "";
            }
            else {
                if (isOperators(codes.charAt(pos))) {
                    TokenType(tempWords);
                    tempWords = "";
                    switch (codes.charAt(pos)) {
                        case 39:
                            pos = lexicalStatements.stringStateMent(pos + 1, codes, tempWords,lexicalTokens);
                            break;
                        case '=':case '>':case '<':case '!':
                            pos = lexicalStatements.OperatorsStatement(pos, codes, tempWords,lexicalTokens);
                            break;
                        default:
                            tempWords = String.valueOf(codes.charAt(pos));
                            System.out.println("运算符ss："+tempWords);
                            lexicalTokens.addLexicalToken(tempWords, "Operators", tempWords);
                            break;
                    }
                    tempWords = "";
                }
                else {
                    tempWords = tempWords + codes.charAt(pos);
                }
            }
        }
        return lexicalTokens.getTokens();
    }
}
