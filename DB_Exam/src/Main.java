
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;

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
        String name = sc.nextLine().trim();
        System.out.println("Please enter your surname:");
        String surname = sc.nextLine().trim();
        if (!name.isEmpty() && !surname.isEmpty()) {
            checkAddUserToDatabase(name, surname);
        } else {
            if (name.isEmpty()) {
                System.out.println("Name is empty!");
            }
            if (surname.isEmpty()) {
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
            User user = session.createQuery(cr).uniqueResult();
            session.beginTransaction();
            if (user == null) {
                user = new User(name, surname);
                session.persist(user);
                System.out.printf("Welcome %s %s\n", name, surname);
            } else {
                System.out.printf("Welcome back %s %s\n", name, surname);
            }
            Main.name = name;
            Main.surname = surname;
            session.getTransaction().commit();
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
            case "2" -> createNewQuestionByExamId(sc);
            case "3" -> editQuestionById(sc);
            case "4" -> showStatisticsMenu(sc);
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
            try (Session session = SessionFactoryMaker.getFactory().openSession()) {
                int counter = 0;
                List<Exam> exams = session.createQuery("from Exam", Exam.class).list();
                for (Exam e : exams) {
                    System.out.printf("[%d] - %s\n", ++counter, e.getTitle());
                }
                System.out.printf("[*] - return to main menu\n", ++counter);
                int input = validateAndGetInt(sc);
                if (input > 0 && input <= exams.size()) {
                    runExam(exams.get(input - 1).getTitle(), sc);
                } else if (input == -1) {
                    runMenu = false;
                } else {
                    System.out.println("Bad input. Try again...");
                }
            }
        }

    }

    private static void runExam(String title, Scanner sc) {
        int userId = User.getUserIdByNameSurname(name, surname);
        if (userId != 0) {
            try (Session session = SessionFactoryMaker.getFactory().openSession()) {
                User user = session.get(User.class, userId);
                Exam exam = Exam.getExamByTitle(title);
                ExamSession examSession = new ExamSession(exam, user);
                session.getTransaction().begin();
                session.persist(examSession);
                session.getTransaction().commit();
                List<Question> questions = examSession.getExam().getQuestions();
                Collections.shuffle(questions);
                collectAnswers(questions, sc, examSession);
                calculateAndAddTotalScoresToExamSession(examSession);
                System.out.println("Exam is done!");
                System.out.printf("%s %s you collected in total %d point(s) out of %d\n", name, surname,
                        examSession.getScore(), questions.size());
            }
        }
    }

    private static void calculateAndAddTotalScoresToExamSession(ExamSession examSession) {
        List<Answer> answers = Answer.getAnswersByExamSessionId(examSession.getId());
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
                default -> System.out.println("Bad input. Try again....");
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


    private static void createNewQuestionByExamId(Scanner sc) {
        int examId = getUserInputFromMenuToCreateNewQuestion(sc);
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            if (examId == 0) {
                return;
            }
            Exam exam = session.get(Exam.class, examId);
            createQuestion(sc, exam, exam.getTitle());
            System.out.println("Question created successfully!");
        }
    }


    private static int getUserInputFromMenuToCreateNewQuestion(Scanner sc) {
        List<Exam> exams = getExams();
        if (exams.isEmpty()) {
            return handleNoExams();
        } else {
            printExamOptionsForQuestionCreate(exams);
            return handleUserSelection(sc, exams);
        }
    }

    private static int getUserInputFromMenuToEditQuestion(Scanner sc) {
        List<Exam> exams = getExams();
        if (exams.isEmpty()) {
            return handleNoExams();
        } else {
            printExamOptionsForQuestionEdit(exams);
            return handleUserSelection(sc, exams);
        }
    }

    private static int handleNoExams() {
        System.out.println("There is no test data in system.");
        System.out.println("Choose: [1] - Start exam, to load test data!");
        return 0;
    }

    private static void printExamOptionsForQuestionCreate(List<Exam> exams) {
        System.out.println("Pickup from list theme for your question or create new one theme:");
        int counter = 0;
        for (Exam e : exams) {
            System.out.printf("[%d] - %s\n", ++counter, e.getTitle());
        }
        System.out.printf("[%d] - create new theme\n", ++counter);
        System.out.println("[*] - return to main menu");
    }

    private static void printExamOptionsForQuestionEdit(List<Exam> exams) {
        System.out.println("Pickup from list theme to get list of questions:");
        int counter = 0;
        for (Exam e : exams) {
            System.out.printf("[%d] - %s\n", ++counter, e.getTitle());
        }
        System.out.println("[*] - return to main menu");
    }

    private static int handleUserSelection(Scanner sc, List<Exam> exams) {
        boolean runMenu = true;
        while (runMenu) {
            int input = validateAndGetInt(sc);
            if (input > 0 && input <= exams.size()) {
                return exams.get(input - 1).getId();
            } else if (input == exams.size() + 1) {
                System.out.println("Enter new theme name:");
                String themeName = sc.nextLine();
                return createAndPersistNewExam(themeName).getId();
            } else if (input == -1) {
                return 0;
            } else {
                System.out.println("Bad input. Try again...");
            }
        }
        return 0;
    }

    private static Exam createAndPersistNewExam(String themeName) {
        Exam exam = new Exam(themeName);
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            session.getTransaction().begin();
            session.persist(exam);
            session.getTransaction().commit();
        }
        return exam;
    }

    private static void createQuestion(Scanner sc, Exam exam, String theme) {
        System.out.println("Enter question:");
        String newQuestion = sc.nextLine();
        System.out.println("Enter question value in points:");
        int points = validateAndGetInt(sc);
        Question question = new Question(exam, theme, newQuestion, points);
        System.out.println("Enter correct answer for your question:");
        String correctAnswer = sc.nextLine();
        System.out.println("Enter first possible false answer:");
        String falseAnswer1 = sc.nextLine();
        System.out.println("Enter second possible false answer:");
        String falseAnswer2 = sc.nextLine();
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            session.getTransaction().begin();
            session.persist(question);
            session.persist(new Choice(question, correctAnswer, true));
            session.persist(new Choice(question, falseAnswer1, false));
            session.persist(new Choice(question, falseAnswer2, false));
            session.getTransaction().commit();
        }
    }

    private static int validateAndGetInt(Scanner sc) {
        int id = 0;
        boolean isNotInt = true;
        while (isNotInt) {
            try {
                String input = sc.nextLine().trim();
                if (input.equals("*")) {
                    return -1;
                } else {
                    id = Integer.parseInt(input);
                    isNotInt = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Bad input. Try again...");
            }
        }
        return id;
    }

    private static void deleteQuestion(Question question) {
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            System.out.println(Question.getQuestionsByExamId(question.getExam().getId()).size());
            if (Question.getQuestionsByExamId(question.getExam().getId()).size() == 1) {
                Exam exam = question.getExam();
                session.getTransaction().begin();
                session.remove(question);
                session.remove(exam);
                session.getTransaction().commit();
            } else {
                session.getTransaction().begin();
                session.remove(question);
                session.getTransaction().commit();
            }

        }
        System.out.println("Question deleted successfully!");
    }

    private static String handleYesNo(Scanner sc) {
        String input = sc.nextLine().toUpperCase();
        if (input.equals("Y")) {
            System.out.println("Enter new one:");
            return sc.nextLine();
        } else if (input.equals("N")) {
            System.out.println("");
            return null;
        } else {
            System.out.println("Bad input. Try again...");
            return handleYesNo(sc);
        }
    }


    private static void editQuestionById(Scanner sc) {
        int examId = getUserInputFromMenuToEditQuestion(sc);
        if (examId == 0) {
            return;
        }
        Question question = selectQuestion(examId, sc);
        if (question == null) {
            return;
        }
        handleQuestionEdit(question, sc);
    }

    private static Question selectQuestion(int examId, Scanner sc) {
        System.out.println("Select question to edit:");
        List<Question> questions = Question.getQuestionsByExamId(examId);
        int counter = 0;
        for (Question q : questions) {
            System.out.printf("[%d] - %s\n", ++counter, q.getQuestion());
        }
        System.out.println("[*] - return to main menu");
        int input = validateAndGetInt(sc);
        if (input > 0 && input <= questions.size()) {
            Question question = questions.get(input - 1);
            return question;
        } else if (input == -1) {
            return null;
        } else {
            System.out.println("Bad input. Try again...");
            return null;
        }
    }

    private static void handleQuestionEdit(Question question, Scanner sc) {
        List<Choice> choices = question.getChoices();
        System.out.println("Question: " + question.getQuestion());
        System.out.println("[1] - Change");
        System.out.println("[2] - Delete");
        String chooseAction = sc.nextLine();
        switch (chooseAction) {
            case "1" -> {
                System.out.println("Change it? [Y/N]:");
                String textForReplace = handleYesNo(sc);
                if (textForReplace != null) {
                    question.setQuestion(textForReplace);
                }
                ArrayList<Choice> newChoices = new ArrayList<>();
                for (Choice choice : choices) {
                    System.out.println("Choice: " + choice.getChoiceText());
                    if (choice.isCorrect()) {
                        System.out.println("This is correct answer for question.");
                        System.out.println("If question is changed, replace it with correct answer for new question.");
                    }
                    System.out.println("Change it? [Y/N]:");
                    String choiceForReplace = handleYesNo(sc);
                    if (choiceForReplace != null) {
                        choice.setChoiceText(choiceForReplace);
                        newChoices.add(choice);
                    }
                }
                try (Session session = SessionFactoryMaker.getFactory().openSession()) {
                    session.getTransaction().begin();
                    session.merge(question);
                    for (Choice choice : newChoices) {
                        session.merge(choice);
                    }
                    session.getTransaction().commit();
                }
                System.out.println("Question edited successfully!");
            }
            case "2" -> deleteQuestion(question);
            default -> System.out.println("Bad input. Try again...");
        }
    }

    private static void showStatisticsMenu(Scanner sc) {
        chooseStatisticsMenu(sc);
    }

    private static void printStatisticsMenu() {
        System.out.println("""
                                
                [1] - Count all taken exams
                [2] - Count all taken exams by user
                [3] - Count average score of correct answers
                [4] - Count average score of correct answers by exams
                [5] - Show choices statistics
                [*] - return to main menu
                """);
    }

    private static void chooseStatisticsMenu(Scanner sc) {
        boolean showStatisticsMenu = true;
        while (showStatisticsMenu) {
            printStatisticsMenu();
            String input = sc.nextLine();
            switch (input) {
                case "1" -> countAllTakenExams();
                case "2" -> countAllTakenExamsByUserId();
                case "3" -> countAverageScoreOfCorrectAnswers();
                case "4" -> countAverageScoreOfCorrectAnswersByExamId();
                case "5" -> showChoicesStatistics();
                case "*" -> showStatisticsMenu = false;
                default -> System.out.println("Bad input. Try again...");
            }
        }
    }

    private static void countAllTakenExams() {
        System.out.printf("Totally was taken %d exam sessions\n", getExamSessions().size());
    }

    private static List<ExamSession> getExamSessions() {
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            List<ExamSession> examSessions = session.createQuery("from ExamSession", ExamSession.class).list();
            return examSessions;
        }
    }

    private static List<User> getUsers() {
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            List<User> users = session.createQuery("from User", User.class).list();
            return users;
        }
    }

    private static List<Answer> getAnswers() {
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            List<Answer> answers = session.createQuery("from Answer", Answer.class).list();
            return answers;
        }
    }

    private static List<Question> getQuestions() {
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            List<Question> questions = session.createQuery("from Question", Question.class).list();
            return questions;
        }
    }

    private static List<Exam> getExams() {
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            return session.createQuery("from Exam", Exam.class).list();
        }
    }

    private static void countAllTakenExamsByUserId() {
        List<User> users = getUsers();
        List<ExamSession> examSessions = getExamSessions();
        for (User u : users) {
            int counter = 0;
            for (ExamSession e : examSessions) {
                if (e.getUser().getId() == u.getId()) {
                    counter++;
                }
            }
            System.out.printf("User %s %s totaly took %d exam sessions\n", u.getName(), u.getSurname(), counter);
        }
    }

    private static void countAverageScoreOfCorrectAnswers() {
        List<Answer> answers = getAnswers();
        if (answers.size() == 0) {
            System.out.println("Any exam has not yet been taken by any user");
        } else {
            int counter = 0;
            for (Answer answer : answers) {
                if (answer.getChoice().isCorrect()) {
                    counter++;
                }
            }
            System.out.printf("Average total score of correct answers is %.2f out of 5\n",
                    (float) (counter * 5) / answers.size());
        }
    }

    private static void countAverageScoreOfCorrectAnswersByExamId() {
        List<Exam> exams = getExams();
        if (exams.size() == 0) {
            System.out.println("There is no any exams in the system. Load test data by entering exam menu.");
            return;
        }
        for (Exam exam : exams) {
            int counter = 0;
            int totalAnswers = 0;
            List<Question> questions = exam.getQuestions();
            for (Question question : questions) {
                List<Answer> answers = question.getAnswers();
                totalAnswers += answers.size();
                counter += answers.stream().filter(a -> a.getChoice().isCorrect()).count();
            }
            if (totalAnswers == 0) {
                System.out.printf("The exam %s has not yet been taken by any user\n", exam.getTitle());
            } else {
                System.out.printf("Average score of correct answers for exam %s is %.2f out of 5\n", exam.getTitle(),
                        (float) (counter * 5) / totalAnswers);
            }
        }
    }


    private static void showChoicesStatistics() {
        List<Question> questions = getQuestions();
        if (questions.size() == 0) {
            System.out.println("There is no any exams in the system. Load test data by entering exam menu.");
            return;
        }
        for (Question q : questions) {
            System.out.printf("Question: %s\n", q.getQuestion());
            List<Answer> answers = q.getAnswers();
            List<Choice> choices = q.getChoices();

            for (Choice choice : choices) {
                int counter = 0;
                for (Answer a : answers) {
                    if (a.getChoice().equals(choice)) {
                        counter++;
                    }
                }
                System.out.printf("        Choice: %s was taken %d time(s)\n", choice, counter);
            }
        }
    }
}