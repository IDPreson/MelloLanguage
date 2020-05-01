package com.company;

import com.company.Lexical.LexicalParser;
import com.company.Lexical.LexicalTokens;
import com.company.Syntax.SyntaxParser;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        String testCode = "func int main() { " +
                "int a=-1+2+3+4+5+6+7+8+9+10*(6/3*(5-2*8));" +
                "string str='fuck you string'+'THis is FUCK STRING!!';" +
                "a=a+5*6-(8+9/3);"+
                "if a+20*5-(2+3*8)<0 && a>0 || str!='fuck you stringTHis is FUCK STRING!!' && a>0 && str=='123456' {" +
                "   int man=18; " +
                "   if 1+2+3*5>5 {"+
                "       int man_2=50;"+
                "       if str=='MineCraft' {"+
                "           string mystr='It is a good game!';"+
                "       }"+
                "       else {"+
                "            string teststr='test String!';"+
                "            if a>10 {"+
                "               int mm=10;"+
                "            }"+
                "       }"+
                "   }"+
                "}"+
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
