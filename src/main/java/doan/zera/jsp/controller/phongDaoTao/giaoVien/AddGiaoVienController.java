package doan.zera.jsp.controller.phongDaoTao.giaoVien;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import doan.zera.jsp.controller.FXMLController;
import doan.zera.jsp.model.GiaoVien;
import doan.zera.jsp.model.Nganh;
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
@FXMLname(value = "phongDaoTao/giaoVien/addGiaoVien")
public class AddGiaoVienController extends FXMLController implements Initializable {
    @FXML
    public JFXTextField maGv;
    @FXML
    public JFXTextField ho;
    @FXML
    public JFXTextField ten;
    @FXML
    public JFXRadioButton namGioiTinh;
    @FXML
    public JFXRadioButton nuGioiTinh;
    @FXML
    public JFXDatePicker ngaySinh;
    @FXML
    public JFXTextField queQuan;
    @FXML
    public JFXComboBox<Label> cbbNganh;
    @FXML
    public JFXTextField hocVi;
    @FXML
    public StackPane rootPane;

    private GiaoVien giaoVien;

    private GiaovienController giaovienController;

    private List<Nganh> nganhs;

    public void setGiaovienController(GiaovienController giaovienController) {
        this.giaovienController = giaovienController;
        this.giaovienController.setDataCbbNganh(cbbNganh);

    }

    public void setNganhs(List<Nganh> nganhs) {
        this.nganhs = nganhs;
    }

    public void setGiaoVien(GiaoVien giaoVien) {
        this.giaoVien = giaoVien;
        if (giaoVien.getId() == 0) return;
        maGv.setText(giaoVien.getMaGv());
        ho.setText(giaoVien.getHo());
        ten.setText(giaoVien.getTen());
        if (giaoVien.isGioiTinh()) namGioiTinh.setSelected(true);
        else nuGioiTinh.setSelected(true);
        ngaySinh.setValue(giaoVien.getNgaySinh().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        queQuan.setText(giaoVien.getQueQuan());
        cbbNganh.getItems().forEach(label -> {
            if (giaoVien.getNganh().getMaNganh().equals(label.getId()))
                cbbNganh.getSelectionModel().select(label);
        });
        hocVi.setText(giaoVien.getHocVi());

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @FXML
    public void onSave(ActionEvent actionEvent) {

        List<String> error = new ArrayList<>();
        if (maGv.getText().equals("")) error.add("Mã GV");
        if (ho.getText().equals("")) error.add("Họ");
        if (ten.getText().equals("")) error.add("Tên");
        if (ngaySinh.getValue() == null) error.add("Ngày sinh");
        if (queQuan.getText().equals("")) error.add("Quê quán");
        if (cbbNganh.getSelectionModel().getSelectedItem() == null) error.add("Ngành");
        if (hocVi.getText() == null) error.add("Học vị/Học Hàm");
        if (!error.isEmpty()) {
            HelperUlti.showDialog(rootPane, "Bạn chưa chọn", error);
            return;
        }
        if (giaoVien == null) giaoVien = new GiaoVien();
        giaoVien.setMaGv(maGv.getText());
        giaoVien.setHo(ho.getText());
        giaoVien.setTen(ten.getText());
        giaoVien.setGioiTinh(namGioiTinh.isSelected());
        giaoVien.setNgaySinh(Date.from(ngaySinh.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        giaoVien.setQueQuan(queQuan.getText());
        nganhs.forEach(nganh -> {
            if (nganh.getMaNganh().equals(cbbNganh.getSelectionModel().getSelectedItem().getId()))
                giaoVien.setNganh(nganh);
        });
        giaoVien.setHocVi(hocVi.getText());
        giaovienController.save(giaoVien);
        HelperUlti.close(rootPane);
    }

    @FXML
    public void onCancel(ActionEvent actionEvent) {
        HelperUlti.close(rootPane);
    }
}
