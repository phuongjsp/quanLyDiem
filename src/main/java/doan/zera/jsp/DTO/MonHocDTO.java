package doan.zera.jsp.DTO;

import doan.zera.jsp.model.GiaoVien;
import doan.zera.jsp.model.MonHoc;
import lombok.Data;

import java.util.Set;

@Data
public class MonHocDTO extends MonHoc {
    private String maNganh;
    private long soLuongGV;
    private Set<GiaoVien> giaoViens;

    public MonHocDTO(MonHoc monHoc) {
        this.setId(monHoc.getId());
        this.setMaMonHoc(monHoc.getMaMonHoc());
        this.setTenMonHoc(monHoc.getTenMonHoc());
        this.setHeSo(monHoc.getHeSo());
        this.setNganh(monHoc.getNganh());
        this.setMaNganh(monHoc.getNganh().getMaNganh());

    }

    public MonHoc getMonHoc() {
        MonHoc monHoc = new MonHoc();
        monHoc.setId(this.getId());
        monHoc.setMaMonHoc(this.getMaMonHoc());
        monHoc.setTenMonHoc(this.getTenMonHoc());
        monHoc.setHeSo(this.getHeSo());
        monHoc.setNganh(this.getNganh());
        return monHoc;
    }
}
