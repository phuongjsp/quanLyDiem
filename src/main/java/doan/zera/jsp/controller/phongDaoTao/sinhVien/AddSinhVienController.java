package doan.zera.jsp.controller.phongDaoTao.sinhVien;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import doan.zera.jsp.controller.FXMLController;
import doan.zera.jsp.model.SinhVien;
import doan.zera.jsp.util.FXMLname;
import doan.zera.jsp.util.HelperUlti;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@Component
@FXMLname(value = "phongDaoTao/sinhVien/addSinhVien", title = "Cập nhật thông tin sinh viên")
public class AddSinhVienController extends FXMLController implements Initializable {
    @FXML
    public JFXRadioButton namGioiTinh;
    @FXML
    public JFXComboBox<Label> cbbKhoaHoc;
    @FXML
    public JFXComboBox<Label> cbbNganhHoc;
    @FXML
    public JFXDatePicker ngaySinh;
    @FXML
    public StackPane rootPane;
    @FXML
    public JFXTextField maSinhVien;
    @FXML
    public JFXTextField ho;
    @FXML
    public JFXTextField ten;
    @FXML
    public JFXTextField queQuan;
    @FXML
    public JFXRadioButton nuGioiTinh;
    private SinhVienController sinhVienController;
    private SinhVien sinhVien;

    public void setSinhVienController(SinhVienController sinhVienController) {
        this.sinhVienController = sinhVienController;
        setDataKhoaHocsAndNganhs();
    }

    public void setSinhVien(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
        if (sinhVien.getId() == 0) return;
        maSinhVien.setText(sinhVien.getMaSv());
        ho.setText(sinhVien.getHo());
        ten.setText(sinhVien.getTen());
        queQuan.setText(sinhVien.getQueQuan());
        ngaySinh.setValue(sinhVien.getNgaySinh().
                toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        if (sinhVien.isGioiTinh()) namGioiTinh.setSelected(true);
        else nuGioiTinh.setSelected(true);
        cbbKhoaHoc.getItems().forEach(label -> {
            if (sinhVien.getKhoaHoc().getMaKhoa().equals(label.getId()))
                cbbKhoaHoc.getSelectionModel().select(label);
        });
        cbbNganhHoc.getItems().forEach(label -> {
            if (sinhVien.getNganh().getMaNganh().equals(label.getId()))
                cbbNganhHoc.getSelectionModel().select(label);
        });
    }

    @FXML
    public void onSave(ActionEvent actionEvent) {
        List<String> error = new ArrayList<>();
        if (ngaySinh.getValue() == null)
            error.add("Ngày sinh");
        if (cbbKhoaHoc.getSelectionModel().getSelectedItem() == null)
            error.add("Khóa học");
        if (cbbNganhHoc.getSelectionModel().getSelectedItem() == null)
            error.add("Nganh hoc");
        if (maSinhVien.getText().equals(""))
            error.add("Mã sinh viên");
        if (ho.getText().equals(""))
            error.add("Họ");
        if (ten.getText().equals(""))
            error.add("Tên");
        if (queQuan.getText().equals(""))
            error.add("Quê quán");
        if (!error.isEmpty()) {
            HelperUlti.showDialog(rootPane, "Bạn chưa chọn", error);
            return;
        }
        if (sinhVien == null)
            sinhVien = new SinhVien();
        sinhVien.setMaSv(maSinhVien.getText());
        sinhVien.setHo(ho.getText());
        sinhVien.setTen(ten.getText());
        sinhVien.setQueQuan(queQuan.getText());
        sinhVien.setNgaySinh(Date.from(ngaySinh.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        sinhVien.setGioiTinh(namGioiTinh.isSelected());
        sinhVien.setKhoaHoc(sinhVienController.getKhoaFormCbb(cbbKhoaHoc));
        sinhVien.setNganh(sinhVienController.getNganhFromCbb(cbbNganhHoc));
        sinhVienController.save(sinhVien);
        HelperUlti.close(rootPane);
    }

    @FXML
    public void onCancel(ActionEvent actionEvent) {
        HelperUlti.close(rootPane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void setDataKhoaHocsAndNganhs() {
        cbbKhoaHoc.getItems().clear();
        cbbNganhHoc.getItems().clear();
        sinhVienController.setDataCbbKhoaHocAndCbbNganhHoc(cbbKhoaHoc, cbbNganhHoc);
    }
}
