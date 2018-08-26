package doan.zera.jsp.model;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "giao_vien")
@Data
public class GiaoVien implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ma_gv")
    private String maGv;

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
    @JoinColumn(name = "nganh_id")
    private Nganh nganh;
    @Column(name = "hoc_vi")
    private String hocVi;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;


    public Date getNgaySinh() {
        if (ngaySinh == null) return null;
        return new Date(ngaySinh.getTime());
    }

}
