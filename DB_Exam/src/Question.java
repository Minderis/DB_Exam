import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Choice> choices;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Answer> answers;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    private String theme;
    private String question;
    private int points;

    public Question() {
    }

    public Question(Exam exam, String theme, String question, int points) {
        this.exam = exam;
        this.theme = theme;
        this.question = question;
        this.points = points;
    }

    public static List<Question> getQuestionsByExamId(int id) {
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            String hql = "FROM Question a WHERE a.exam.id = :examId";
            Query<Question> query = session.createQuery(hql, Question.class);
            query.setParameter("examId", id);
            return query.getResultList();
        }
    }

    public static List<Question> getAllQuestions() {
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            return session.createQuery("from Question", Question.class).list();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return question;
    }
}
