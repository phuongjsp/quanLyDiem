package doan.zera.jsp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "diem")
@Data
@NoArgsConstructor
@ToString
public class Diem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "thoi_khoa_bieu_id")
    private ThoiKhoaBieu thoiKhoaBieu;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sinhvien_id")
    private SinhVien sinhVien;


    @Column(name = "chuyen_can")
    private double chuyenCan;


    @Column(name = "diem_kiem_tra")
    private double diemKiemTra;

    @Column(name = "diem_thi")
    private double diemThi;

}
