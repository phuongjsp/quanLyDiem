package doan.zera.jsp.DTO;

import doan.zera.jsp.model.GiaoVien;
import doan.zera.jsp.model.MonHoc;
import doan.zera.jsp.util.HelperUlti;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class GiaoVienDTO extends GiaoVien {

    private String gioiTinhString;
    private String ngaySinhString;
    private String hoTenFull;
    private String maNganh;
    private long SLMonHocGiangDay;
    private Set<MonHoc> monHocs;

    public GiaoVienDTO(GiaoVien giaoVien) {
        this.setHoTenFull(giaoVien.getHo() + " " + giaoVien.getTen());
        this.setId(giaoVien.getId());
        this.setHo(giaoVien.getHo());
        this.setTen(giaoVien.getTen());
        this.setMaGv(giaoVien.getMaGv());
        this.setGioiTinh(giaoVien.isGioiTinh());
        this.gioiTinhString = giaoVien.isGioiTinh() ? "Nam" : "Nữ";
        this.setNgaySinh(giaoVien.getNgaySinh());
        if (giaoVien.getNgaySinh() != null) this.ngaySinhString = HelperUlti.getDateString(giaoVien.getNgaySinh());
        this.setHocVi(giaoVien.getHocVi());
        this.setNganh(giaoVien.getNganh());
        if (giaoVien.getNganh() != null)
            this.setMaNganh(giaoVien.getNganh().getMaNganh());
        this.setQueQuan(giaoVien.getQueQuan());
        this.setUser(giaoVien.getUser());
    }

    public GiaoVien getGiaoVien() {
        GiaoVien giaoVien = new GiaoVien();
        giaoVien.setId(this.getId());
        giaoVien.setHo(this.getHo());
        giaoVien.setTen(this.getTen());
        giaoVien.setMaGv(this.getMaGv());
        giaoVien.setGioiTinh(this.isGioiTinh());
        giaoVien.setNgaySinh(this.getNgaySinh());
        giaoVien.setHocVi(this.getHocVi());
        giaoVien.setNganh(this.getNganh());
        giaoVien.setQueQuan(this.getQueQuan());
        giaoVien.setUser(this.getUser());
        return giaoVien;
    }
}
