package doan.zera.jsp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "khoa")
@Data
@NoArgsConstructor
@ToString
public class Khoa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ma_khoa")
    private String maKhoa;

    @Column(name = "ten_khoa")
    private String tenKhoa;
}
