package com.smart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartContactManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartContactManagerApplication.class, args);
        String text = "SMART CONTACT MANAGER STARTED...";
        printInBox(text);
    }

    private static void printInBox(String text) {
        char TOP_LEFT = '\u2554';
        char TOP_RIGHT = '\u2557';
        char BOTTOM_LEFT = '\u255A';
        char BOTTOM_RIGHT = '\u255D';
        char HORIZONTAL = '\u2550';
        char VERTICAL = '\u2551';

        int textLength = text.length() + 2;
        System.out.println(TOP_LEFT + String.valueOf(HORIZONTAL).repeat(textLength) + TOP_RIGHT);
        System.out.println(VERTICAL + " " + text + " " + VERTICAL);
        System.out.println(BOTTOM_LEFT + String.valueOf(HORIZONTAL).repeat(textLength) + BOTTOM_RIGHT);
    }
}
