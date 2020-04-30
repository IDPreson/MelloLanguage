package com.company.Lexical;

public class LexicalStatements {
    /*
字符串状态机 防止出现 字符串解析不全现象
*/
    int stringStateMent(int pos, String codes,String tempWords,LexicalTokens lexicalTokens) {
        int length = codes.length();
        String type = "";
        for (int i = pos; i < length; i++) {
            if (codes.charAt(i) == 39) {
                System.out.println("字符串："+tempWords);
                type = "String";
                lexicalTokens.addLexicalToken(tempWords, type, tempWords);
                return i;
            }
            else {
                tempWords = tempWords + String.valueOf(codes.charAt(i));
            }
        }
        return -1;
    }
    /*
    运算符状态机 防止出现 == <= >= || && 无法解析
    */
    int OperatorsStatement(int pos, String codes, String tempWords,LexicalTokens lexicalTokens) {
        int length = codes.length();
        String type = "Operators";
        boolean flag = false;
        if (pos + 1 < length) {
            flag = true;
        }
        if (flag) {
            if (codes.charAt(pos + 1) == '=')
            {
                tempWords = String.valueOf(codes.charAt(pos));
                tempWords = tempWords + codes.charAt(pos+1);
                pos = pos + 1;
            }
            else {
                tempWords = String.valueOf(codes.charAt(pos));
            }
        }
        System.out.println("运算符cc："+tempWords);
        lexicalTokens.addLexicalToken(tempWords, type, tempWords);
        return pos;
    }
}
