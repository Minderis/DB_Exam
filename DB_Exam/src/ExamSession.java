import jakarta.persistence.*;
import org.hibernate.Session;

import java.util.List;

@Entity
public class ExamSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "examSession", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Answer> answers;

    private int score;

    public ExamSession() {
    }

    public ExamSession(Exam exam, User user) {
        this.exam = exam;
        this.user = user;
    }

    public ExamSession(Exam exam, User user, int score) {
        this.exam = exam;
        this.user = user;
        this.score = score;
    }

    public static List<ExamSession> getAllExamSessions() {
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            return session.createQuery("from ExamSession", ExamSession.class).list();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
