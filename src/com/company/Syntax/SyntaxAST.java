package com.company.Syntax;

import com.company.Lexical.LexicalTokens;

import java.util.*;

public class SyntaxAST {
    public static class AST {
       public String type, name;

        @Override
        public String toString() {
            return "AST{" +
                    "type='" + type + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
    public static class ASTExpreesion {
        public Queue<LexicalTokens.Token> expression;
        ASTExpreesion(){
            expression=new LinkedList<>();
        }
        @Override
        public String toString() {
            return "ASTExpreesion{" +
                    "expression=" + expression +
                    '}';
        }
    }
    public static class ASTIfExpression {
        public ASTExpreesion expression_1;
        public ASTExpreesion expression_2;
        public String compareOperator;
        ASTIfExpression(){
            expression_1=new ASTExpreesion();
            expression_2=new ASTExpreesion();
        }
        @Override
        public String toString() {
            return "ASTIfExpression{" +
                    "expression_1=" + expression_1 +
                    ", expression_2=" + expression_2 +
                    ", compareOperator='" + compareOperator + '\'' +
                    '}';
        }
    }
    public static class ASTNode extends AST {
        public String Declaration;
        public ASTExpreesion expressions;
        public ASTIf If;
        public ASTBody Body;
        public ASTElse Else;
        ASTNode(){
            expressions=new ASTExpreesion();
            If=new ASTIf();
            Body=new ASTBody();
            Else=new ASTElse();
        }

        @Override
        public String toString() {
            return "ASTNode{" +
                    "Declaration='" + Declaration + '\'' +
                    ", expressions=" + expressions +
                    ", If=" + If +
                    ", Body=" + Body +
                    ", Else=" + Else +
                    '}';
        }
    }
    public static class ASTElse extends AST{
        public ASTBody ElseBody;
        ASTElse(){
            ElseBody=new ASTBody();
        }

        @Override
        public String toString() {
            return "ASTElse{" +
                    "ElseBody=" + ElseBody +
                    '}';
        }
    }
    public static class ASTIf extends AST{
        public ASTBody IfBody;
        public Stack<ASTIfExpression> IfExpressions;
        public Stack<String> IfOperators;
        ASTIf(){
            IfExpressions=new Stack<>();
            IfOperators=new Stack<>();
            IfBody=new ASTBody();
        }

        @Override
        public String toString() {
            return "ASTIf{" +
                    "IfBody=" + IfBody +
                    ", IfExpressions=" + IfExpressions +
                    ", IfOperators=" + IfOperators +
                    '}';
        }
    }
    public static class ASTBody extends AST {
        public Queue<ASTNode> Nodes;
        ASTBody(){
            Nodes=new LinkedList<>();
        }
        @Override
        public String toString() {
            return "ASTBody{" +
                    "Nodes=" + Nodes +
                    '}';
        }
    }
    public static class ASTparam extends AST {

    }
    public static class ASTFunc extends AST {
        public String returnType;
        public Queue<ASTparam> params;
        public ASTBody Body;
        ASTFunc(){
            params=new LinkedList<>();
            Body=new ASTBody();
        }
        @Override
        public String toString() {
            return "ASTFunc{" +
                    "returnType='" + returnType + '\'' +
                    ", params=" + params +
                    ", Body=" + Body +
                    '}';
        }
    }
}
