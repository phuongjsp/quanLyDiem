package doan.zera.jsp.DTO;

import doan.zera.jsp.model.Nganh;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NganhDTO extends Nganh {
    private long SLSV;
    private long SLMH;
    private long SLGV;
    private String maKhoa;

    public NganhDTO(Nganh nganh, long sLSV, long sLGV, long sLMH) {
        setNganh(nganh);
        this.setSLSV(sLSV);
        this.setSLMH(sLMH);
        this.setSLGV(sLGV);
    }

    public NganhDTO(Nganh nganh) {
        setNganh(nganh);
    }

    public Nganh getNganh() {
        Nganh nganh = new Nganh();
        nganh.setId(this.getId());
        nganh.setTenNganh(this.getTenNganh());
        nganh.setMaNganh(this.getMaNganh());
        nganh.setKhoa(this.getKhoa());
        return nganh;
    }

    public void setNganh(Nganh nganh) {
        this.setId(nganh.getId());
        this.setTenNganh(nganh.getTenNganh());
        this.setMaNganh(nganh.getMaNganh());
        this.setKhoa(nganh.getKhoa());
        this.setMaKhoa(nganh.getKhoa().getMaKhoa());
    }
}

