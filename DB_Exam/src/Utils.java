import org.hibernate.Session;
import java.util.ArrayList;

public class Utils {
    public static void loadTestData() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Arturas", "Minderis"));
        users.add(new User("Darius", "Guginis"));
        users.add(new User("Tomas", "Ramanauskas"));
        ArrayList<Exam> exams = new ArrayList<>();
        exams.add(new Exam("Geography: capital cities"));
        exams.add(new Exam("Math: sum of two numbers"));
        exams.add(new Exam("Information technologies"));
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question(exams.get(0), "Geography: capital cities", "Capital of Australia?", 1));
        questions.add(new Question(exams.get(0), "Geography: capital cities", "Capital of France?", 1));
        questions.add(new Question(exams.get(0), "Geography: capital cities", "Capital of Germany?", 1));
        questions.add(new Question(exams.get(0), "Geography: capital cities", "Capital of Italy?", 1));
        questions.add(new Question(exams.get(0), "Geography: capital cities", "Capital of Canada?", 1));
        questions.add(new Question(exams.get(1), "Math: sum of two numbers", "5 + 12 = ?", 1));
        questions.add(new Question(exams.get(1), "Math: sum of two numbers", "62 + 14 = ?", 1));
        questions.add(new Question(exams.get(1), "Math: sum of two numbers", "88 + 22 = ?", 1));
        questions.add(new Question(exams.get(1), "Math: sum of two numbers", "143 + 18 = ?", 1));
        questions.add(new Question(exams.get(1), "Math: sum of two numbers", "77 + 69 = ?", 1));
        questions.add(new Question(exams.get(2), "Information technologies",
                "What is the difference between a database and a data warehouse?", 1));
        questions.add(new Question(exams.get(2), "Information technologies",
                "What is the purpose of a firewall in network security?", 1));
        questions.add(new Question(exams.get(2), "Information technologies",
                "What is the most common way to deploy machine learning models in production?", 1));
        questions.add(new Question(exams.get(2), "Information technologies",
                "What is the main benefit of cloud computing?", 1));
        questions.add(new Question(exams.get(2), "Information technologies",
                "What is Agile software development?", 1));
        ArrayList<Choice> choices = new ArrayList<>();
        choices.add(new Choice(questions.get(0), "Canberra", true));
        choices.add(new Choice(questions.get(0), "Sydney", false));
        choices.add(new Choice(questions.get(0), "Melbourne", false));
        choices.add(new Choice(questions.get(1), "Paris", true));
        choices.add(new Choice(questions.get(1), "Lyon", false));
        choices.add(new Choice(questions.get(1), "Marseille", false));
        choices.add(new Choice(questions.get(2), "Berlin", true));
        choices.add(new Choice(questions.get(2), "Hamburg", false));
        choices.add(new Choice(questions.get(2), "Bonn", false));
        choices.add(new Choice(questions.get(3), "Rome", true));
        choices.add(new Choice(questions.get(3), "Milan", false));
        choices.add(new Choice(questions.get(3), "Vatican", false));
        choices.add(new Choice(questions.get(4), "Ottawa", true));
        choices.add(new Choice(questions.get(4), "Toronto", false));
        choices.add(new Choice(questions.get(4), "Quebec City", false));

        choices.add(new Choice(questions.get(5), "17", true));
        choices.add(new Choice(questions.get(5), "27", false));
        choices.add(new Choice(questions.get(5), "16", false));
        choices.add(new Choice(questions.get(6), "76", true));
        choices.add(new Choice(questions.get(6), "75", false));
        choices.add(new Choice(questions.get(6), "77", false));
        choices.add(new Choice(questions.get(7), "110", true));
        choices.add(new Choice(questions.get(7), "100", false));
        choices.add(new Choice(questions.get(7), "111", false));
        choices.add(new Choice(questions.get(8), "161", true));
        choices.add(new Choice(questions.get(8), "171", false));
        choices.add(new Choice(questions.get(8), "151", false));
        choices.add(new Choice(questions.get(9), "146", true));
        choices.add(new Choice(questions.get(9), "136", false));
        choices.add(new Choice(questions.get(9), "156", false));

        choices.add(new Choice(questions.get(10),
                "A database is optimized for transaction processing, " +
                        "while a data warehouse is optimized for querying and analysis.", true));
        choices.add(new Choice(questions.get(10), "A database is a collection of data organized in a " +
                "specific way, while a data warehouse is a large repository of data optimized " +
                "for querying and analysis.", false));
        choices.add(new Choice(questions.get(10), "A database is a flat file, while a data warehouse " +
                "is a relational database.", false));
        choices.add(new Choice(questions.get(11),
                "To filter incoming and outgoing network traffic based on " +
                        "predetermined security rules.", true));
        choices.add(new Choice(questions.get(11), "To encrypt all network traffic.", false));
        choices.add(new Choice(questions.get(11), "To prevent unauthorized access to a private network.",
                false));
        choices.add(new Choice(questions.get(12), "As a REST API.", true));
        choices.add(new Choice(questions.get(12), "As a standalone application.", false));
        choices.add(new Choice(questions.get(12), "As a library integrated into other software.",
                false));
        choices.add(new Choice(questions.get(13), "Increased flexibility compared " +
                "to traditional on-premise solutions.", true));
        choices.add(new Choice(questions.get(13), "Lower costs compared to traditional on-premise solutions.",
                false));
        choices.add(new Choice(questions.get(13), "Increased security compared to traditional " +
                "on-premise solutions.", false));
        choices.add(new Choice(questions.get(14), "A iterative and incremental approach to software " +
                "development that emphasizes collaboration and flexibility.", true));
        choices.add(new Choice(questions.get(14), "A sequential approach to software development that " +
                "emphasizes planning and design.", false));
        choices.add(new Choice(questions.get(14), "A waterfall approach to software development that " +
                "emphasizes documentation and testing.", false));
        ArrayList<ExamSession> examSessions = new ArrayList<>();
        examSessions.add(new ExamSession(exams.get(0), users.get(0), 1));
        examSessions.add(new ExamSession(exams.get(2), users.get(0),1));
        examSessions.add(new ExamSession(exams.get(1), users.get(0),2));
        examSessions.add(new ExamSession(exams.get(0), users.get(1), 2));
        examSessions.add(new ExamSession(exams.get(2), users.get(1), 2));
        examSessions.add(new ExamSession(exams.get(1), users.get(1),3));
        examSessions.add(new ExamSession(exams.get(0), users.get(2),1));
        examSessions.add(new ExamSession(exams.get(2), users.get(2),3));
        examSessions.add(new ExamSession(exams.get(1), users.get(2),2));
        ArrayList<Answer> answers = new ArrayList<>();
        answers.add(new Answer(examSessions.get(0), questions.get(3), choices.get(11)));
        answers.add(new Answer(examSessions.get(0), questions.get(0), choices.get(2)));
        answers.add(new Answer(examSessions.get(0), questions.get(1), choices.get(4)));
        answers.add(new Answer(examSessions.get(0), questions.get(2), choices.get(6)));
        answers.add(new Answer(examSessions.get(0), questions.get(4), choices.get(14)));
        answers.add(new Answer(examSessions.get(1), questions.get(10), choices.get(32)));
        answers.add(new Answer(examSessions.get(1), questions.get(12), choices.get(36)));
        answers.add(new Answer(examSessions.get(1), questions.get(13), choices.get(40)));
        answers.add(new Answer(examSessions.get(1), questions.get(14), choices.get(44)));
        answers.add(new Answer(examSessions.get(1), questions.get(11), choices.get(35)));
        answers.add(new Answer(examSessions.get(2), questions.get(9), choices.get(27)));
        answers.add(new Answer(examSessions.get(2), questions.get(8), choices.get(24)));
        answers.add(new Answer(examSessions.get(2), questions.get(7), choices.get(22)));
        answers.add(new Answer(examSessions.get(2), questions.get(5), choices.get(17)));
        answers.add(new Answer(examSessions.get(2), questions.get(6), choices.get(19)));
        answers.add(new Answer(examSessions.get(3), questions.get(1), choices.get(5)));
        answers.add(new Answer(examSessions.get(3), questions.get(2), choices.get(6)));
        answers.add(new Answer(examSessions.get(3), questions.get(3), choices.get(11)));
        answers.add(new Answer(examSessions.get(3), questions.get(0), choices.get(0)));
        answers.add(new Answer(examSessions.get(3), questions.get(4), choices.get(13)));
        answers.add(new Answer(examSessions.get(4), questions.get(12), choices.get(37)));
        answers.add(new Answer(examSessions.get(4), questions.get(14), choices.get(43)));
        answers.add(new Answer(examSessions.get(4), questions.get(10), choices.get(30)));
        answers.add(new Answer(examSessions.get(4), questions.get(11), choices.get(35)));
        answers.add(new Answer(examSessions.get(4), questions.get(13), choices.get(39)));
        answers.add(new Answer(examSessions.get(5), questions.get(5), choices.get(16)));
        answers.add(new Answer(examSessions.get(5), questions.get(7), choices.get(22)));
        answers.add(new Answer(examSessions.get(5), questions.get(6), choices.get(18)));
        answers.add(new Answer(examSessions.get(5), questions.get(9), choices.get(27)));
        answers.add(new Answer(examSessions.get(5), questions.get(8), choices.get(24)));
        answers.add(new Answer(examSessions.get(6), questions.get(0), choices.get(1)));
        answers.add(new Answer(examSessions.get(6), questions.get(1), choices.get(5)));
        answers.add(new Answer(examSessions.get(6), questions.get(2), choices.get(8)));
        answers.add(new Answer(examSessions.get(6), questions.get(4), choices.get(12)));
        answers.add(new Answer(examSessions.get(6), questions.get(3), choices.get(11)));
        answers.add(new Answer(examSessions.get(7), questions.get(14), choices.get(42)));
        answers.add(new Answer(examSessions.get(7), questions.get(10), choices.get(30)));
        answers.add(new Answer(examSessions.get(7), questions.get(13), choices.get(39)));
        answers.add(new Answer(examSessions.get(7), questions.get(11), choices.get(35)));
        answers.add(new Answer(examSessions.get(7), questions.get(12), choices.get(37)));
        answers.add(new Answer(examSessions.get(8), questions.get(6), choices.get(19)));
        answers.add(new Answer(examSessions.get(8), questions.get(7), choices.get(21)));
        answers.add(new Answer(examSessions.get(8), questions.get(5), choices.get(17)));
        answers.add(new Answer(examSessions.get(8), questions.get(8), choices.get(25)));
        answers.add(new Answer(examSessions.get(8), questions.get(9), choices.get(27)));

        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            if (session.createQuery("from Question", Question.class).list().size() == 0 &&
                    session.createQuery("from Choice", Choice.class).list().size() == 0 &&
                    session.createQuery("from Exam", Question.class).list().size() == 0) {
                session.getTransaction().begin();
                for (Exam exam : exams) {
                    session.persist(exam);
                }
                for (Question question : questions) {
                    session.persist(question);
                }
                for (Choice choice : choices) {
                    session.persist(choice);
                }
                for (User user :users) {
                    session.persist(user);
                }
                for (ExamSession examSession :examSessions) {
                    session.persist(examSession);
                }
                for (Answer answer :answers) {
                    session.persist(answer);
                }
                session.getTransaction().commit();
            }
            System.out.println("Test data was loaded successfully!");
        }
    }
}
