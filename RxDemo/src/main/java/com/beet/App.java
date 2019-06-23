package com.beet;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("=== RunnableTest ===");

        Runnable r1 = new Runnable() {

            public void run() {
                System.out.println("Hello world one!");
            }
        };

        Runnable r2 = () -> System.out.println("Hello world two!");

        r1.run();
        r2.run();

        BasicRx.Execute();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter String");
        String s = br.readLine();
        System.out.print("Enter Integer:");
        try {
            int i = Integer.parseInt(br.readLine());
        } catch (NumberFormatException nfe) {
            System.err.println("Invalid Format!");
        }

    }
}
