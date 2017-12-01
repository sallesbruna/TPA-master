import java.util.Scanner;

public class Console {

    public static int readInt(String s){
        System.out.println(s);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static String readLine(String s){
        System.out.println(s);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
