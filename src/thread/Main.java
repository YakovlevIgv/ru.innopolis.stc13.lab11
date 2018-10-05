package thread;

import java.util.Scanner;

/**
 *  The class Main for test the program
 */
public class Main {
    public static void main(String[] args) {
        int Thread_files = 599;

        long time = System.currentTimeMillis();
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        //for (int cnt = 1; cnt <= cnt_files; cnt++)
        ClOccurancies finder = new ClOccurancies();
        String[] files = new String[Thread_files];
        for (int cnt = 1; cnt <= Thread_files; cnt++) {
            files[cnt - 1] = "C://Users//IIV//Documents//Tempfile" + cnt +".txt";

        }
        String words[] = {"one", "some_1", "some_2", "hibernate", "Hello", "word1", "word2"};
        finder.getOccurancies(files, words, "C://Users//IIV//Documents//temp"+".txt");
        System.out.println("It workes " +(System.currentTimeMillis()-time)/1000+ "c");
        Scanner scanner1 = new Scanner(System.in);
        scanner1.nextLine();
    }
}