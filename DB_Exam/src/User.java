import jakarta.persistence.*;
import org.hibernate.Session;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ExamSession> examSessions;

    private String name;
    private String surname;

    public User() {
    }

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public static int getUserIdByNameSurname(String name, String surname) {
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            User user = session.createQuery("FROM User WHERE name = :name AND surname = :surname", User.class)
                    .setParameter("name", name)
                    .setParameter("surname", surname)
                    .uniqueResult();
            return user != null ? user.getId() : 0;
        }
    }


    public static List<User> getAllUsers() {
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ExamSession> getExamSessions() {
        return examSessions;
    }

    public void setExamSessions(List<ExamSession> examSessions) {
        this.examSessions = examSessions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
