package doan.zera.jsp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@ToString
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    @Transient
    private String password;
    @Column(name = "enabled")
    private boolean enabled;

    public User(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

}
