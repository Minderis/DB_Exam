import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Entity
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @OneToMany(mappedBy = "choice", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Answer> answers;

    @Column(name = "choice_text")
    private String choiceText;

    @Column(name = "is_correct")
    private boolean isCorrect;

    public Choice() {
    }

    public Choice(Question question, String choiceText, boolean isCorrect) {
        this.question = question;
        this.choiceText = choiceText;
        this.isCorrect = isCorrect;
    }

    public static HashSet<Choice> getChoicesByQuestionId(int id) {
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Choice> cr = cb.createQuery(Choice.class);
            Root<Choice> root = cr.from(Choice.class);
            cr.select(root);
            Query<Choice> query = session.createQuery(cr);
            List<Choice> allResults = query.getResultList();
            HashSet<Choice> resultsForReturn = new HashSet<>();
            for (Choice choice : allResults) {
                if (choice.getQuestion().getId() == id) {
                    resultsForReturn.add(choice);
                }
            }
            return resultsForReturn;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public String getChoiceText() {
        return choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    @Override
    public String toString() {
        return choiceText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Choice choice)) return false;
        return choiceText.equals(choice.choiceText);
    }
}
