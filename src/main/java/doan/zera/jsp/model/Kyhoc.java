package doan.zera.jsp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ky_hoc")
@Data
@NoArgsConstructor
@ToString
public class Kyhoc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ma_ky_hoc")
    private String maKyHoc;

    @Column(name = "ten_ky_hoc")
    private String tenKyHoc;

    @Column(name = "ngay_bat_dau_hoc")
    @Temporal(TemporalType.DATE)
    private Date ngayBatDauHoc;

    public Date getNgayBatDauHoc() {
        if (ngayBatDauHoc == null) return null;
        return new Date(ngayBatDauHoc.getTime());
    }
}
