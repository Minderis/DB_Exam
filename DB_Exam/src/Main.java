
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.*;

public class Main {
    private static String name;
    private static String surname;


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean runProgram = true;
        while (runProgram) {
            if (name == null || surname == null) {
                displayLoginForm(sc);
            } else {
                printMainMenu();
                chooseMainMenu(sc);
            }
        }
    }

    private static void displayLoginForm(Scanner sc) {
        System.out.println("Please enter your name:");
        String name = sc.nextLine();
        System.out.println("Please enter your surname:");
        String surname = sc.nextLine();
        if (name.length() > 0 && surname.length() > 0) {
            checkAddUserToDatabase(name, surname);
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

    private static void checkAddUserToDatabase(String name, String surname) {
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cr = cb.createQuery(User.class);
            Root<User> root = cr.from(User.class);
            cr.select(root).where(cb.and(
                    cb.equal(root.get("name"), name),
                    cb.equal(root.get("surname"), surname)));
            Query<User> query = session.createQuery(cr);
            List<User> results = query.getResultList();
            if (results.size() == 0) {
                session.getTransaction().begin();
                session.persist(new User(name, surname));
                session.getTransaction().commit();
                System.out.printf("Welcome %s %s\n", name, surname);
            } else {
                System.out.printf("Welcome back %s %s\n", name, surname);
            }
            Main.name = name;
            Main.surname = surname;
        }
    }

    private static void printMainMenu() {
        System.out.println("""
                                
                Choose action:
                [1] - Start exam
                [2] - Create new question
                [3] - Edit question
                [4] - Statistics
                [*] - Quit program
                """);
    }

    private static void chooseMainMenu(Scanner sc) {
        String input = sc.nextLine();
        switch (input) {
            case "1" -> startExam(sc);
            case "2" -> createNewQuestionByExamId();
            case "3" -> editQuestionById();
            case "4" -> showStatisticsMenu();
            case "*" -> {
                System.out.println("Bye!");
                System.exit(0);
            }
            default -> System.out.println("Bad input. Try again...");
        }
    }


    private static void startExam(Scanner sc) {
        Utils.loadTestData();
        selectExamMenu(sc);
    }

    private static void selectExamMenu(Scanner sc) {
        boolean runMenu = true;
        while (runMenu) {
            System.out.println("""
                                    
                    Select exam theme:
                    [1] - Geography: capital cities
                    [2] - Math: sum of two numbers
                    [3] - Information technologies
                    [*] - return to main menu
                    """);
            String input = sc.nextLine();
            switch (input) {
                case "1" -> runExam(1, sc);
                case "2" -> runExam(2, sc);
                case "3" -> runExam(3, sc);
                case "*" -> runMenu = false;
                default -> System.out.println("Bad input. Tray again...");
            }
        }

    }

    private static void runExam(int index, Scanner sc) {
        int userId = User.getUserIdByNameSurname(name, surname);
        if (userId != 0) {
            try (Session session = SessionFactoryMaker.getFactory().openSession()) {
                User user = session.get(User.class, userId);
                Exam exam = session.get(Exam.class, index);
                ExamSession examSession = new ExamSession(exam, user);
                session.getTransaction().begin();
                session.persist(examSession);
                session.getTransaction().commit();
                List<Question> questions = examSession.getExam().getQuestions();
                Collections.shuffle(questions);
                collectAnswers(questions, sc, examSession);
                calculateAndAddTotalScoresToExamSession(examSession);
                System.out.println("Exam is done!");
                System.out.printf("%s %s you collected in total %d point(s) out of 5\n", name, surname, examSession.getScore());
            }
        }
    }

    private static void calculateAndAddTotalScoresToExamSession(ExamSession examSession) {
        ArrayList<Answer> answers = Answer.getAnswersByExamSessionId(examSession.getId());
        int counter = 0;
        for (Answer answer : answers) {
            if (answer.getChoice().isCorrect()) {
                counter++;
            }
        }
        examSession.setScore(counter);
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            session.getTransaction().begin();
            session.merge(examSession);
            session.getTransaction().commit();
        }
    }

    private static void collectAnswers(List<Question> questions, Scanner sc, ExamSession examSession) {
        for (Question question : questions) {
            System.out.println(question.getQuestion());
            List<Choice> choices = question.getChoices();
            Collections.shuffle(choices);
            printAndSelectChoices(choices, sc, examSession);

        }
    }

    private static void printAndSelectChoices(List<Choice> choices, Scanner sc, ExamSession examSession) {
        int counter = 0;
        System.out.println("");
        for (Choice choice : choices) {
            System.out.printf("[%s] - %s\n", ++counter, choice.getChoiceText());
        }
        System.out.println("");
        boolean showMenu = true;
        while (showMenu) {
            String input = sc.nextLine();
            switch (input) {
                case "1" -> {
                    addAnswer(choices.get(0), examSession);
                    showMenu = false;
                }
                case "2" -> {
                    addAnswer(choices.get(1), examSession);
                    showMenu = false;
                }
                case "3" -> {
                    addAnswer(choices.get(2), examSession);
                    showMenu = false;
                }
                default -> System.out.println("Bad input. Tray again....");
            }
        }
    }

    private static void addAnswer(Choice choice, ExamSession examSession) {
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            session.getTransaction().begin();
            session.persist(new Answer(examSession, choice.getQuestion(), choice));
            session.getTransaction().commit();
        }
    }


    private static void createNewQuestionByExamId() {
        System.out.println("Not implemented yet.");
    }

    private static void editQuestionById() {
        System.out.println("Not implemented yet.");
    }

    private static void showStatisticsMenu() {
        System.out.println("Not implemented yet.");
    }
}