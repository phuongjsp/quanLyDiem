package doan.zera.jsp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "authorities")
@Data
@NoArgsConstructor
@ToString
public class Authorities implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "authority")
    private String authority;

    public Authorities(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }
}
