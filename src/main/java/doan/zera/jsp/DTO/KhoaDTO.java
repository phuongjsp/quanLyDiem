package doan.zera.jsp.DTO;

import doan.zera.jsp.model.Khoa;
import lombok.Data;

@Data
public class KhoaDTO extends Khoa {
    private long SLSV;
    private long SLMH;
    private long SLGV;

    public KhoaDTO(Khoa khoa, long sLSV, long sLGV, long sLMH) {
        setKhoa(khoa);
        this.setSLSV(sLSV);
        this.setSLGV(sLGV);
        this.setSLMH(sLMH);
    }

    public Khoa getKhoa() {
        Khoa khoa = new Khoa();
        khoa.setId(this.getId());
        khoa.setTenKhoa(this.getTenKhoa());
        khoa.setMaKhoa(this.getMaKhoa());
        return khoa;
    }

    public void setKhoa(Khoa khoa) {
        this.setId(khoa.getId());
        this.setMaKhoa(khoa.getMaKhoa());
        this.setTenKhoa(khoa.getTenKhoa());
    }
}
