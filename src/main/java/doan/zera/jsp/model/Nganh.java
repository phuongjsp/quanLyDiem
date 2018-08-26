package doan.zera.jsp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "nganh")
@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Nganh implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ma_nganh")
    private String maNganh;

    @Column(name = "ten_nganh")
    private String tenNganh;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "khoa_id")
    private Khoa khoa;

}
