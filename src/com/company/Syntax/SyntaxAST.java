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
        public String var, Declaration;
        public ASTExpreesion expressions;
        public Stack<ASTIfExpression> IfExpressions;
        public Stack<String> IfOperators;
        public ASTBody Body;
        ASTNode(){
            expressions=new ASTExpreesion();
            IfExpressions=new Stack<>();
            IfOperators=new Stack<>();
            Body=new ASTBody();
        }
        @Override
        public String toString() {
            return "ASTNode{" +
                    "var='" + var + '\'' +
                    ", Declaration='" + Declaration + '\'' +
                    ", expressions=" + expressions +
                    ", IfExpressions=" + IfExpressions +
                    ", IfOperators=" + IfOperators +
                    ", Body=" + Body +
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
