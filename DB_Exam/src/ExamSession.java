import jakarta.persistence.*;

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
}
