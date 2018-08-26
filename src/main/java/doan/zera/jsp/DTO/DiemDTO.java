package doan.zera.jsp.DTO;

import doan.zera.jsp.model.Diem;
import lombok.Data;

import java.text.DecimalFormat;

@Data
public class DiemDTO extends Diem {

    private String maSV;
    private String hoTenSv;
    private String maKhoa;
    private double tongDiem;
    private double diemGiuaKy; // (diem chuyen can + 2xdiem kiem tra) /3
    private String tenMonHoc;
    private String hoTenGV;
    private String maGV;
    //=diem giua ki x 3 + diemThi x7
    private String diemChu;//ABCDF
    private double TL;

    public DiemDTO(Diem diem) {
        setDiem(diem);
    }

    public DiemDTO() {

    }

    public Diem getDiem() {
        Diem diem = new Diem();
        diem.setId(this.getId());
        diem.setThoiKhoaBieu(this.getThoiKhoaBieu());
        diem.setSinhVien(this.getSinhVien());
        diem.setDiemThi(this.getDiemThi());
        diem.setDiemKiemTra(this.getDiemKiemTra());
        diem.setChuyenCan(this.getChuyenCan());
        return diem;
    }

    public void setDiem(Diem diem) {
        this.setId(diem.getId());
        this.setThoiKhoaBieu(diem.getThoiKhoaBieu());
        this.setSinhVien(diem.getSinhVien());
        this.setDiemThi(diem.getDiemThi());
        this.setDiemKiemTra(diem.getDiemKiemTra());
        this.setChuyenCan(diem.getChuyenCan());
        //
        this.setMaSV(diem.getSinhVien().getMaSv());
        this.setHoTenSv(diem.getSinhVien().getHo() + " " + diem.getSinhVien().getTen());
        this.setMaKhoa(diem.getSinhVien().getKhoaHoc().getMaKhoa());
        this.setDiemGiuaKy((diem.getChuyenCan() + (2 * diem.getDiemKiemTra())) / 3);
        DecimalFormat sf = new DecimalFormat("##.00");
        this.setDiemGiuaKy(Double.parseDouble(sf.format(this.getDiemGiuaKy())));
        this.setTongDiem(((this.getDiemGiuaKy() * 30) + (this.getDiemThi() * 70)) / 100);
        this.setTongDiem(Double.parseDouble(sf.format(this.getTongDiem())));
        setDC(this.getTongDiem());
        this.setTL(diem.getThoiKhoaBieu().getTinChi());
        this.setTenMonHoc(diem.getThoiKhoaBieu().getMonHoc().getTenMonHoc());
        this.setMaGV(diem.getThoiKhoaBieu().getGiaoVien().getMaGv());
        this.setHoTenGV(diem.getThoiKhoaBieu().getGiaoVien().getHo() + " " + diem.getThoiKhoaBieu().getGiaoVien().getTen());

    }

    public void setDC(double diem) {
        if (diem <= 10 && diem >= 8.5) this.diemChu = "A";
        if (diem <= 8.4 && diem >= 7) this.diemChu = "B";
        if (diem <= 6.9 && diem >= 5.5) this.diemChu = "C";
        if (diem <= 5.4 && diem > 4) this.diemChu = "D";
        if (diem < 4) this.diemChu = "F";
    }
}
