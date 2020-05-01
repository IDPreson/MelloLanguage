package com.company.Syntax;

import com.company.Error.ErrorException;
import com.company.Lexical.LexicalTokens;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class SyntaxParser {
    Queue<LexicalTokens.Token> TokenVector=new LinkedList<>();
    ErrorException errorException=new ErrorException();
    public void setTokenVector(ArrayList<LexicalTokens.Token> tokenVector) {
        TokenVector.addAll(tokenVector);
    }
    String getValueType(String ValueTpye) {
        if (ValueTpye.equals("int")) {
            return ValueTpye;
        }
        if (ValueTpye.equals("string")) {
            return ValueTpye;
        }
        errorException.outError(1);
        return "";
    }
    int getPriority(String operators) {
        if (operators.equals("*") || operators.equals("/") || operators.equals("%")) {
            return 2;
        }
        if (operators.equals("+") || operators.equals("-")) {
            return 1;
        }
        return 0;
    }
    Queue<SyntaxAST.ASTparam> DealFuncParams(SyntaxAST.ASTFunc Node) {
        boolean flag = false;
        int times = 1;
        LexicalTokens.Token tempToken;
        SyntaxAST.ASTparam tempParam = new SyntaxAST.ASTparam();
        while (!TokenVector.isEmpty()) {
            tempToken = TokenVector.poll();
            if (tempToken.type.equals("Operators") && tempToken.name.equals(")")) {
                return Node.params;
            }
            if (flag) {
                if (times == 1) {
                    if (tempToken.type.equals("KeyWords")) {
                        tempParam.type = getValueType(tempToken.name);
                        times++;
                    }
                }
                else {
                    tempParam.name = tempToken.name;
                    Node.params.add(tempParam);
                    times = 1;
                }
            }
            if (tempToken.type.equals("Operators") && tempToken.name.equals("(")) {
                flag = true;
            }
        }
        return null;
    }
    SyntaxAST.ASTExpreesion PaserExpreession(String type) {
        SyntaxAST.ASTExpreesion expressions=new SyntaxAST.ASTExpreesion();
        LexicalTokens.Token tempToken;
        Stack<LexicalTokens.Token> OperatorStack=new Stack<>();
        while (!TokenVector.isEmpty()){
            tempToken = TokenVector.peek();
            if(type.equals("VarDeclaration")){
                if (tempToken.type.equals("Operators") && tempToken.name.equals(";")) {
                    while (!OperatorStack.isEmpty()) {
                        expressions.expression.add(OperatorStack.pop());
                    }
                    return expressions;
                }
            }
            if(type.equals("IfDeclaration")){
                if (tempToken.type.equals("Operators")) {
                    switch(tempToken.name){
                        case ">":case "<":case "==":case "<=":case ">=":case "&&":case "||":case "!=":case "{":
                            while (!OperatorStack.empty()) {
                                expressions.expression.add(OperatorStack.pop());
                            }
                            return expressions;
                    }
                }
            }
            if (tempToken.type.equals("Constant") || tempToken.type.equals("String")) {
                expressions.expression.add(tempToken);
            }
            if (tempToken.type.equals("Operators")) {
                if (tempToken.name.equals("(")) {
                    OperatorStack.push(tempToken);
                }
                if (tempToken.name.equals(")")) {
                    while (!OperatorStack.empty()) {
                        LexicalTokens.Token tempToken_1 = OperatorStack.pop();
                        if (!tempToken_1.name.equals("(")) {
                            expressions.expression.add(tempToken_1);
                        }
                        else {
                            break;
                        }
                    }
                }
                if (tempToken.name.equals("+") || tempToken.name.equals("-") || tempToken.name.equals("*") || tempToken.name.equals("/") || tempToken.name.equals("%")) {
                    if (OperatorStack.empty()) {
                        OperatorStack.push(tempToken);
                    }
                    else {
                        int Compare_1 = getPriority(tempToken.name);
                        while (!OperatorStack.empty()) {
                            if (getPriority(OperatorStack.peek().name) >= Compare_1) {
                                expressions.expression.add(OperatorStack.pop());
                            }
                            else {
                                OperatorStack.push(tempToken);
                                break;
                            }
                            if (OperatorStack.empty()) {
                                OperatorStack.push(tempToken);
                                break;
                            }
                        }
                    }
                }
            }
            if(tempToken.type.equals("KeyWords")){
                if(tempToken.name.equals("true") || tempToken.name.equals("false")){
                    expressions.expression.add(tempToken);
                }
            }
            TokenVector.poll();
        }
        return null;
    }
    SyntaxAST.ASTNode VarDeclaration(String type, LexicalTokens.Token tempToken, int state, SyntaxAST.ASTNode tempVar) {
        if (!TokenVector.isEmpty()) {
            tempToken = TokenVector.peek();
            if (tempToken.type.equals("Operators") && tempToken.name.equals(";")) {
                TokenVector.poll();
                return tempVar;
            }
            if (state == 0) {
                tempVar.Declaration = "VarDeclaration";
                tempVar.type = type;
                tempVar.name = tempToken.name;
                TokenVector.poll();
                return VarDeclaration(type, tempToken, state+1, tempVar);
            }
            if (state == 1) {
                if (tempToken.type.equals("Operators") && tempToken.name.equals("=")) {
                    TokenVector.poll();
                    return VarDeclaration(type, tempToken, state + 1, tempVar);
                }
                else {
                    errorException.outError(2);
                }
            }
            if (state == 2) {
                tempVar.expressions = PaserExpreession(tempVar.Declaration);
                return VarDeclaration(type, tempToken, state, tempVar);
            }
        }
        return tempVar;
    }
    SyntaxAST.ASTNode IfStatement() {
        SyntaxAST.ASTNode tempifNode=new SyntaxAST.ASTNode();
        LexicalTokens.Token tempToken;
        SyntaxAST.ASTIfExpression Ifexp=new SyntaxAST.ASTIfExpression();
        int state = 1;
        tempifNode.Declaration = "IfDeclaration";
        while (!TokenVector.isEmpty()) {
            tempToken = TokenVector.peek();
            if (tempToken.type.equals("Operators") && tempToken.name.equals("{")) {
                state = 5;
            }
            switch (state)
            {
                case 1:
                    Ifexp.expression_1 = PaserExpreession(tempifNode.Declaration);
                    state = 2;
                    continue;
                case 2:
                    Ifexp.compareOperator = tempToken.name;
                    state = 3;
                    TokenVector.poll();
                    continue;
                case 3:
                    Ifexp.expression_2 = PaserExpreession(tempifNode.Declaration);
                    tempifNode.If.IfExpressions.push(Ifexp);
                    Ifexp=new SyntaxAST.ASTIfExpression();
                    state = 4;
                    continue;
                case 4:
                    if (tempToken.type.equals("Operators") && (tempToken.name.equals("&&") || tempToken.name.equals("||"))) {
                        tempifNode.If.IfOperators.push(tempToken.name);
                        TokenVector.poll();
                    }
                    state = 1;
                    continue;
                case 5:
                    tempifNode.If.IfBody = DealBody();
                    state = 6;
                    break;
                case 6:
                    if(tempToken.type.equals("KeyWords") && (tempToken.name.equals("else"))){
                        tempifNode.Else.ElseBody=DealBody();
                    }
                    return tempifNode;
            }
        }
        return null;
    }
    SyntaxAST.ASTBody DealBody() {
        SyntaxAST.ASTBody BodyAddress=new SyntaxAST.ASTBody();
        LexicalTokens.Token tempToken;
        boolean flag = false;
        while (!TokenVector.isEmpty()) {
            tempToken = TokenVector.peek();
            TokenVector.poll();
            if (tempToken.type.equals("Operators") && tempToken.name.equals("}")) {
                return BodyAddress;
            }
            if (flag) {
                if (tempToken.type.equals("KeyWords")) {
                    switch (tempToken.name){
                        case "string":case "int": case "bool":
                            SyntaxAST.ASTNode var=new SyntaxAST.ASTNode();
                            var = VarDeclaration(tempToken.name, tempToken, 0, var);
                            BodyAddress.Nodes.add(var);
                            break;
                        case "if":
                            SyntaxAST.ASTNode ifstate;
                            ifstate = IfStatement();
                            BodyAddress.Nodes.add(ifstate);
                            break;
                    }
                }
            }
            if (tempToken.type.equals("Operators") && tempToken.name.equals("{")) {
                flag = true;
            }
        }
        return null;
    }
    void FuncStatement(LexicalTokens.Token Token) {
        SyntaxAST.ASTFunc tempNode=new SyntaxAST.ASTFunc();
        tempNode.type = "FuncDeclaration";
        tempNode.name = Token.name;
        if (!TokenVector.isEmpty()) {
            LexicalTokens.Token tempToken = TokenVector.poll();
            if (tempToken.type.equals("KeyWords")) {
                tempNode.returnType = getValueType(tempToken.name);
                tempNode.params = DealFuncParams(tempNode);
                tempNode.Body = DealBody();
            }
        }
        System.out.println(tempNode.name + "||" + tempNode.returnType + "||" + tempNode.type);
        while (!tempNode.Body.Nodes.isEmpty()){
            outASTree(tempNode.Body.Nodes.poll());
        }
    }
    void ParserTokens() {
        if (!TokenVector.isEmpty()) {
            LexicalTokens.Token tempToken = TokenVector.poll();
            if (tempToken.type.equals("KeyWords")) {
                if (tempToken.name.equals("func")) {
                    FuncStatement(tempToken);
                }
            }
            ParserTokens();
        }
    }
    public void startParserTokens() {
        ParserTokens();
        System.out.println("结束");
    }
    void outASTree(SyntaxAST.ASTNode tempNode){
        switch (tempNode.Declaration){
            case "VarDeclaration":
                System.out.println("VarDeclaration:" + tempNode.name + "||" + tempNode.type);
                while(tempNode.expressions.expression.isEmpty()){
                    System.out.println("expression:"+tempNode.expressions.expression.poll());
                }
                return;
            case "IfDeclaration":
                while(!tempNode.If.IfOperators.isEmpty()){
                    System.out.println("IfDeclaration"+tempNode.If.IfOperators.pop());
                }
                while(!tempNode.If.IfExpressions.isEmpty()){
                    System.out.println("IfExpressions"+tempNode.If.IfExpressions.pop());
                }
                while(!tempNode.If.IfBody.Nodes.isEmpty()){
                    System.out.println("IfBody");
                    outASTree(tempNode.If.IfBody.Nodes.poll());
                }
                while(!tempNode.Else.ElseBody.Nodes.isEmpty()){
                    System.out.println("ElseBody");
                    outASTree(tempNode.Else.ElseBody.Nodes.poll());
                }
                break;
        }
    }
}
