import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryMaker {
    private static SessionFactory factory;

    private static void configureFactory() {
        try {
            factory = new Configuration()
                    .addAnnotatedClass(Question.class)
                    .addAnnotatedClass(Choice.class)
                    .addAnnotatedClass(Exam.class)
                    .addAnnotatedClass(ExamSession.class)
                    .addAnnotatedClass(Answer.class)
                    .addAnnotatedClass(User.class)
                    .configure()
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.out.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static org.hibernate.SessionFactory getFactory() {
        if (factory == null) {
            configureFactory();
        }
        return factory;
    }
}
