package doan.zera.jsp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "thoi_khoa_bieu")
@Data
@NoArgsConstructor
@ToString
public class ThoiKhoaBieu implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "phong_hoc")
    private String phongHoc;

    @Column(name = "tiet_bat_dau")
    private int tietBatDau;

    @Column(name = "thoi_gian_bat_dau")
    @Temporal(TemporalType.DATE)
    private Date thoiGianBatDau;
    @Column(name = "thoi_gian_ket_thuc")
    @Temporal(TemporalType.DATE)
    private Date thoiGianKetThuc;
    @Column(name = "so_tiet")
    private int soTiet;
    @Column(name = "tin_chi")
    private int tinChi;
    @Column(name = "tong_so_tiet")
    private int tongSoTiet;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ky_hoc_id")
    private Kyhoc kyhoc;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "giaovien_id")
    private GiaoVien giaoVien;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "monhoc_id")
    private MonHoc monHoc;

    public Date getThoiGianBatDau() {
        if (thoiGianBatDau == null) return null;
        return new Date(thoiGianBatDau.getTime());
    }

    public Date getThoiGianKetThuc() {
        if (thoiGianKetThuc == null) return null;
        return new Date(thoiGianKetThuc.getTime());
    }

}
