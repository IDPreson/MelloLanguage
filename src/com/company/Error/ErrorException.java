package com.company.Error;

public class ErrorException {
    public void outError(int type) {
        switch (type) {
            case 1:
                System.out.println("未知的返回值!");
                break;
            case 2:
                System.out.println("变量无赋值符号!");
                break;
        }
    }
}
