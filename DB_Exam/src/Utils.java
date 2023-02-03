import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static void loadTestData() {
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
                session.getTransaction().commit();
            }
        }
    }
}
