package doan.zera.jsp.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "monhoc_giaovien")
@Data
public class MonHocGiaoVien {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monhoc_id")
    private MonHoc monHoc;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "giaovien_id")
    private GiaoVien giaoVien;
}
