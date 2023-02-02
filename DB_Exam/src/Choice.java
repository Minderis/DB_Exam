import jakarta.persistence.*;

import java.util.List;

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
}
