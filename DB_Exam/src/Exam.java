import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

@Entity
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Question> questions;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ExamSession> examSessions;

    @Column(unique=true)
    private String title;

    public Exam() {
    }

    public Exam(String title) {
        this.title = title;
    }

    public static Exam getExamByTitle(String title) {
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            String hql = "FROM Exam a WHERE a.title = :title";
            Query<Exam> query = session.createQuery(hql, Exam.class);
            query.setParameter("title", title);
            return query.uniqueResult();
        }
    }

    public static List<Exam> getAllExams() {
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            return session.createQuery("from Exam", Exam.class).list();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<ExamSession> getExamSessions() {
        return examSessions;
    }

    public void setExamSessions(List<ExamSession> examSessions) {
        this.examSessions = examSessions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
