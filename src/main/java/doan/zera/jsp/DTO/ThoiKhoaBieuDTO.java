package doan.zera.jsp.DTO;

import doan.zera.jsp.model.ThoiKhoaBieu;
import doan.zera.jsp.util.HelperUlti;
import lombok.Data;

import java.util.Calendar;

@Data
public class ThoiKhoaBieuDTO extends ThoiKhoaBieu {
    private String tenMonHoc;
    private String tenNganh;
    private String tenGiaoVien;
    private String ngayBatDauString;
    private String ngayKetThucString;
    private int thuTrongTuan;
    private String tietDay;
    private int soLuongSV;

    public ThoiKhoaBieuDTO() {
    }

    public ThoiKhoaBieuDTO(int slSV, ThoiKhoaBieu thoiKhoaBieu) {
        if (thoiKhoaBieu == null) return;
        this.setId(thoiKhoaBieu.getId());
        this.setGiaoVien(thoiKhoaBieu.getGiaoVien());
        this.setTietBatDau(thoiKhoaBieu.getTietBatDau());
        this.setThoiGianKetThuc(thoiKhoaBieu.getThoiGianKetThuc());
        this.setThoiGianBatDau(thoiKhoaBieu.getThoiGianBatDau());
        this.setSoTiet(thoiKhoaBieu.getSoTiet());
        this.setPhongHoc(thoiKhoaBieu.getPhongHoc());
        this.setMonHoc(thoiKhoaBieu.getMonHoc());
        this.setKyhoc(thoiKhoaBieu.getKyhoc());
        this.setTongSoTiet(thoiKhoaBieu.getTongSoTiet());
        this.setTinChi(thoiKhoaBieu.getTinChi());
        //DTO data
        if (thoiKhoaBieu.getMonHoc() != null) {
            this.setTenNganh(thoiKhoaBieu.getMonHoc().getNganh().getTenNganh());
            this.setTenMonHoc(thoiKhoaBieu.getMonHoc().getTenMonHoc());
        }
        Calendar calendar = Calendar.getInstance();
        if (thoiKhoaBieu.getGiaoVien() != null)
            this.setTenGiaoVien(thoiKhoaBieu.getGiaoVien().getHo() + " " + thoiKhoaBieu.getGiaoVien().getTen());
        if (thoiKhoaBieu.getThoiGianBatDau() != null) {


            this.setNgayBatDauString(HelperUlti.getDateString(thoiKhoaBieu.getThoiGianBatDau()));
            calendar.setTime(thoiKhoaBieu.getThoiGianBatDau());
            this.setThuTrongTuan(calendar.get(Calendar.DAY_OF_WEEK));
        }
        if (thoiKhoaBieu.getThoiGianKetThuc() != null)
            this.setNgayKetThucString(HelperUlti.getDateString(thoiKhoaBieu.getThoiGianKetThuc()));

        this.setTietDay(mathTietDay(thoiKhoaBieu.getTietBatDau(), thoiKhoaBieu.getSoTiet()));
        this.setSoLuongSV(slSV);
    }

    private String mathTietDay(int tietBatDau, int soTiet) {
        switch (soTiet) {
            case 2:
                return tietBatDau + "-" + (tietBatDau + 1);
            case 3:
                return tietBatDau + "-" + (tietBatDau + 1) + "-" + (tietBatDau + 2);
            case 4:
                return tietBatDau + "-" + (tietBatDau + 1) + "-" + (tietBatDau + 2) + "-" + (tietBatDau + 3);
        }
        return tietBatDau + "";
    }

    public ThoiKhoaBieu getThoiKhoaBieu() {
        ThoiKhoaBieu thoiKhoaBieu = new ThoiKhoaBieu();
        thoiKhoaBieu.setId(this.getId());
        thoiKhoaBieu.setGiaoVien(this.getGiaoVien());
        thoiKhoaBieu.setTietBatDau(this.getTietBatDau());
        thoiKhoaBieu.setThoiGianKetThuc(this.getThoiGianKetThuc());
        thoiKhoaBieu.setThoiGianBatDau(this.getThoiGianBatDau());
        thoiKhoaBieu.setSoTiet(this.getSoTiet());
        thoiKhoaBieu.setPhongHoc(this.getPhongHoc());
        thoiKhoaBieu.setMonHoc(this.getMonHoc());
        thoiKhoaBieu.setTongSoTiet(this.getTongSoTiet());
        thoiKhoaBieu.setKyhoc(this.getKyhoc());
        thoiKhoaBieu.setTinChi(this.getTinChi());
        return thoiKhoaBieu;
    }

}
