package doan.zera.jsp.DTO;

import doan.zera.jsp.model.SinhVien;
import doan.zera.jsp.util.HelperUlti;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SinhVienDTO extends SinhVien {

    private String gioiTinhString;
    private String maKhoaHoc;
    private String maNganh;
    private String ngaySinhString;
    private int slMonDangKyTrongKy;
    private int slTinDangKyTrongKy;

    public SinhVienDTO(SinhVien sinhVien) {
        this.setId(sinhVien.getId());
        this.setMaSv(sinhVien.getMaSv());
        this.setHo(sinhVien.getHo());
        this.setTen(sinhVien.getTen());
        this.setQueQuan(sinhVien.getQueQuan());
        if (sinhVien.getNgaySinh() != null) this.setNgaySinh(sinhVien.getNgaySinh());
        if (sinhVien.getNgaySinh() != null) this.ngaySinhString = HelperUlti.getDateString(sinhVien.getNgaySinh());
        this.gioiTinhString = sinhVien.isGioiTinh() ? "Nam" : "Nữ";
        if (sinhVien.getKhoaHoc() != null) {
            this.setKhoaHoc(sinhVien.getKhoaHoc());
            this.maKhoaHoc = sinhVien.getKhoaHoc().getMaKhoa();
        }
        if (sinhVien.getNganh() != null) {
            this.setNganh(sinhVien.getNganh());
            this.maNganh = sinhVien.getNganh().getMaNganh();
        }
    }

    public void setSLMhAndTinDangKy(int slMonDangKyTrongKy, int slTinDangKyTrongKy) {
        this.slMonDangKyTrongKy = slMonDangKyTrongKy;
        this.slTinDangKyTrongKy = slTinDangKyTrongKy;
    }

    public SinhVien getSinhVien() {
        SinhVien sinhVien = new SinhVien();
        sinhVien.setId(this.getId());
        sinhVien.setMaSv(this.getMaSv());
        sinhVien.setHo(this.getHo());
        sinhVien.setTen(this.getTen());
        sinhVien.setQueQuan(this.getQueQuan());
        sinhVien.setNgaySinh(this.getNgaySinh());
        sinhVien.setKhoaHoc(this.getKhoaHoc());
        sinhVien.setNganh(this.getNganh());
        sinhVien.setGioiTinh(this.isGioiTinh());
        return sinhVien;
    }

}
