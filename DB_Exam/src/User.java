import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;

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
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cr = cb.createQuery(User.class);
            Root<User> root = cr.from(User.class);
            cr.select(root).where(cb.and(
                    cb.equal(root.get("name"), name),
                    cb.equal(root.get("surname"), surname)));
            Query<User> query = session.createQuery(cr);
            List<User> results = query.getResultList();
            if(results.size() > 0 && results.size() < 2) {
                return results.get(0).getId();
            } else {
                System.out.printf("Found few users with same name and surname.");
                System.out.println("Please login with different name.");
            }
        }
        return 0;
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
