import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "exam_session_id")
    private ExamSession examSession;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "choice_id")
    private Choice choice;

    public Answer() {
    }

    public Answer(ExamSession examSession, Question question, Choice choice) {
        this.examSession = examSession;
        this.question = question;
        this.choice = choice;
    }

    public static List<Answer> getAnswersByExamSessionId(int id) {
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            String hql = "FROM Answer a WHERE a.examSession.id = :examSessionId";
            Query<Answer> query = session.createQuery(hql, Answer.class);
            query.setParameter("examSessionId", id);
            return query.getResultList();
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ExamSession getExamSession() {
        return examSession;
    }

    public void setExamSession(ExamSession examSession) {
        this.examSession = examSession;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Choice getChoice() {
        return choice;
    }

    public void setChoice(Choice choice) {
        this.choice = choice;
    }
}
