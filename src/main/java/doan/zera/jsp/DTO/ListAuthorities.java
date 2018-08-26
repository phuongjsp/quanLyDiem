package doan.zera.jsp.DTO;

import lombok.Data;

import java.util.List;
import java.util.Stack;

@Data
public class ListAuthorities {
    private String username;
    private boolean admin, giaoVien, qlDiem, qlGiaoVien, qlKhoa, qlKhoaHoc, qlKyHoc, qlMonHoc, qlNganh, qlSinhVien, qlTKB;

    public ListAuthorities(String username, boolean admin, boolean giaoVien, boolean qlDiem, boolean qlGiaoVien, boolean qlKhoa, boolean qlKhoaHoc, boolean qlKyHoc, boolean qlMonHoc, boolean qlNganh, boolean qlSinhVien, boolean qlTKB) {
        this.username = username;
        this.admin = admin;
        this.giaoVien = giaoVien;
        this.qlDiem = qlDiem;
        this.qlGiaoVien = qlGiaoVien;
        this.qlKhoa = qlKhoa;
        this.qlKhoaHoc = qlKhoaHoc;
        this.qlKyHoc = qlKyHoc;
        this.qlMonHoc = qlMonHoc;
        this.qlNganh = qlNganh;
        this.qlSinhVien = qlSinhVien;
        this.qlTKB = qlTKB;
    }

    public ListAuthorities(String username, List<String> role) {
        this.username = username;
        role.forEach(s -> {
            if (s.equals("ADMIN")) this.admin = true;
            if (s.equals("DSMH")) this.giaoVien = true;
            if (s.equals("QLDS")) this.qlDiem = true;
            if (s.equals("QLGV")) this.qlGiaoVien = true;
            if (s.equals("QLK")) this.qlKhoa = true;
            if (s.equals("QLKH")) this.qlKhoaHoc = true;
            if (s.equals("QLKYH")) this.qlKyHoc = true;
            if (s.equals("QLMH")) this.qlMonHoc = true;
            if (s.equals("QLNH")) this.qlNganh = true;
            if (s.equals("QLSV")) this.qlSinhVien = true;
            if (s.equals("QLTKB")) this.qlTKB = true;
        });
    }

    public ListAuthorities() {

    }

    public List<String> getRoles() {
        List<String> role = new Stack<>();
        if (admin) role.add("ADMIN");
        if (giaoVien) role.add("DSMH");
        if (qlDiem) role.add("QLDS");
        if (qlGiaoVien) role.add("QLGV");
        if (qlKhoa) role.add("QLK");
        if (qlKhoaHoc) role.add("QLKH");
        if (qlKyHoc) role.add("QLKYH");
        if (qlMonHoc) role.add("QLMH");
        if (qlNganh) role.add("QLNH");
        if (qlSinhVien) role.add("QLSV");
        if (qlTKB) role.add("QLTKB");
        return role;
    }

    public void setListAuthorities(ListAuthorities la) {
        this.username = la.username;
        this.admin = la.admin;
        this.giaoVien = la.giaoVien;
        this.qlDiem = la.qlDiem;
        this.qlGiaoVien = la.qlGiaoVien;
        this.qlKhoa = la.qlKhoa;
        this.qlKhoaHoc = la.qlKhoaHoc;
        this.qlKyHoc = la.qlKyHoc;
        this.qlMonHoc = la.qlMonHoc;
        this.qlNganh = la.qlNganh;
        this.qlSinhVien = la.qlSinhVien;
        this.qlTKB = la.qlTKB;
    }
}
