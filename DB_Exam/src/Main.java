import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Scanner;

public class Main {
    private static String name;
    private static String surname;


    public static void main(String[] args) {
        SessionFactory factory = SessionFactoryMaker.getFactory();
        Session session = factory.openSession();
        Scanner sc = new Scanner(System.in);

        boolean runProgram = true;
        while (runProgram) {
            if (name == null || surname == null) {
                loginForm(sc);
            } else {

            }
        }
    }

    private static void loginForm(Scanner sc) {
        System.out.println("Please enter your name:");
        String name = sc.nextLine();
        System.out.println("Please enter your surname:");
        String surname = sc.nextLine();
        if (name.length() > 0 && surname.length() > 0) {
            System.out.printf("Welcome %s %s\n", name, surname);
            Main.name = name;
            Main.surname = surname;
        } else {
            if (!(name.length() > 0)) {
                System.out.println("Name is empty!");
            }
            if (!(surname.length() > 0)) {
                System.out.println("Surname is empty!");
            }
            System.out.println("Try again...");
        }
    }


}