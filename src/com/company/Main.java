package com.company;

import com.company.Lexical.LexicalParser;
import com.company.Lexical.LexicalTokens;
import com.company.Syntax.SyntaxParser;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        String testCode = "func int main() { " +
                "int a=1+2+3+4+5+6+7+8+9+10*(6/3*(5-2*8)); " +
                "string str='fuck you string'+'THis is FUCK STRING!!'; " +
                "if a+20*5-(2+3*8)<0 && a>0 || str!='fuck you stringTHis is FUCK STRING!!' && a>0 { " +
                "int man=18; " +
                "}" +
        "}";
        LexicalParser lexicalParser=new LexicalParser();
        ArrayList<LexicalTokens.Token> Tokens=lexicalParser.DealParser(testCode);
        for (LexicalTokens.Token tokeninfo:Tokens) {
            System.out.println(tokeninfo.name + "||" + tokeninfo.type);
        }
        SyntaxParser syntaxParser=new SyntaxParser();
        syntaxParser.setTokenVector(Tokens);
        syntaxParser.startParserTokens();
    }
}
