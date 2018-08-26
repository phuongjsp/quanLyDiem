package doan.zera.jsp.DTO;

import doan.zera.jsp.model.KhoaHoc;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KhoaHocDTO extends KhoaHoc {
    private long SLSV;

    public KhoaHocDTO(KhoaHoc khoaHoc, long sLSV) {
        setKhoaHoc(khoaHoc);
        this.setSLSV(sLSV);
    }

    public void setKhoaHoc(KhoaHoc khoaHoc) {
        this.setId(khoaHoc.getId());
        this.setTenKhoa(khoaHoc.getTenKhoa());
        this.setMaKhoa(khoaHoc.getMaKhoa());
        this.setNamBatDau(khoaHoc.getNamBatDau());
    }

    public KhoaHoc getKhoa() {
        KhoaHoc khoaHoc = new KhoaHoc();
        khoaHoc.setId(this.getId());
        khoaHoc.setMaKhoa(this.getMaKhoa());
        khoaHoc.setTenKhoa(this.getTenKhoa());
        khoaHoc.setNamBatDau(this.getNamBatDau());
        return khoaHoc;
    }

}
