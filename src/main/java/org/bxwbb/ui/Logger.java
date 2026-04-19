package org.bxwbb.ui;

public class Logger {

    public static void printOverflowException(Node node) {
        System.err.println("OverflowException: " + node.getName());
    }

}
