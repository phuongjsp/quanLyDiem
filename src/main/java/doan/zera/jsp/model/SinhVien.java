package doan.zera.jsp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "sinh_vien")
@Data
@NoArgsConstructor
@ToString
public class SinhVien implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ma_sv")
    private String maSv;

    @Column(name = "ho")
    private String ho;

    @Column(name = "ten")
    private String ten;

    @Column(name = "gioi_tinh")
    private boolean gioiTinh;

    @Column(name = "ngay_sinh")
    @Temporal(TemporalType.DATE)
    private Date ngaySinh;
    @Column(name = "que_quan")
    private String queQuan;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "khoa_hoc_id")
    private KhoaHoc khoaHoc;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nganh_id")
    private Nganh nganh;

    public Date getNgaySinh() {
        return new Date(ngaySinh.getTime());
    }
}
