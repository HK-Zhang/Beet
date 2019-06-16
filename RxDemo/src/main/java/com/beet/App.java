package com.beet;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "=== RunnableTest ===" );

        Runnable r1 = new Runnable(){
        
            public void run() {
                System.out.println("Hello world one!");
            }
        };

        Runnable r2 = () -> System.out.println("Hello world two!");

        r1.run();
        r2.run();
    }
}
