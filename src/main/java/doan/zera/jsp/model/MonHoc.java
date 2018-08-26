package doan.zera.jsp.model;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "mon_hoc")
@Data
public class MonHoc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ma_mon_hoc")
    private String maMonHoc;
    @Column(name = "ten_mon_hoc")
    private String tenMonHoc;

    @Column(name = "he_so")
    private double heSo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nganh_id")
    private Nganh nganh;

}
