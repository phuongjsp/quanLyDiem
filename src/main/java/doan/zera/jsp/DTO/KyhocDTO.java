package doan.zera.jsp.DTO;

import doan.zera.jsp.model.Kyhoc;
import doan.zera.jsp.util.HelperUlti;
import lombok.Data;

@Data
public class KyhocDTO extends Kyhoc {
    private String ngayBatDauHocString;

    public KyhocDTO(Kyhoc kyhoc) {
        this.setId(kyhoc.getId());
        this.setTenKyHoc(kyhoc.getTenKyHoc());
        this.setMaKyHoc(kyhoc.getMaKyHoc());
        this.setNgayBatDauHoc(kyhoc.getNgayBatDauHoc());
        this.setNgayBatDauHocString(HelperUlti.getDateString(kyhoc.getNgayBatDauHoc()));
    }

    public Kyhoc getKyhoc() {
        Kyhoc kyhoc = new Kyhoc();
        kyhoc.setId(this.getId());
        kyhoc.setTenKyHoc(this.getTenKyHoc());
        kyhoc.setMaKyHoc(this.getMaKyHoc());
        kyhoc.setNgayBatDauHoc(this.getNgayBatDauHoc());
        return kyhoc;
    }
}
