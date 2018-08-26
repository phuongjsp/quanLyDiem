package doan.zera.jsp.controller.phongDaoTao.khoaHoc;

import com.jfoenix.controls.JFXTextField;
import doan.zera.jsp.controller.FXMLController;
import doan.zera.jsp.model.KhoaHoc;
import doan.zera.jsp.util.FXMLname;
import doan.zera.jsp.util.HelperUlti;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
@FXMLname(value = "phongDaoTao/khoaHoc/addKhoaHoc", title = "Cập nhật khóa học")
public class AddKhoaHocController extends FXMLController implements Initializable {
    @FXML
    public JFXTextField maKhoa;
    @FXML
    public JFXTextField tenKhoa;
    @FXML
    public JFXTextField namBatDau;
    @FXML
    public StackPane rootPane;

    private KhoaHoc khoaHoc;
    private KhoaHocController khoaHocController;


    public void setKhoa(KhoaHoc khoaHoc) {
        this.khoaHoc = khoaHoc;
        if (khoaHoc.getId() == 0) return;
        maKhoa.setText(khoaHoc.getMaKhoa());
        tenKhoa.setText(khoaHoc.getTenKhoa());
        namBatDau.setText(khoaHoc.getNamBatDau());
    }

    public void setKhoaHocController(KhoaHocController khoaHocController) {
        this.khoaHocController = khoaHocController;
    }

    @FXML
    public void onSave(ActionEvent actionEvent) {
        List<String> error = new ArrayList<>();
        if (maKhoa.getText().length() == 0) error.add("mã khóa học");
        if (tenKhoa.getText().length() == 0) error.add("tên khóa học");
        if (namBatDau.getText().length() == 0) error.add("năm bắt đầu học");
        if (!error.isEmpty()) {
            HelperUlti.showDialog(rootPane, "bạn chưa chọn", error);
            return;
        }
        if (khoaHoc == null) khoaHoc = new KhoaHoc();
        khoaHoc.setMaKhoa(maKhoa.getText());
        khoaHoc.setTenKhoa(tenKhoa.getText());
        khoaHoc.setNamBatDau(namBatDau.getText());
        khoaHocController.save(khoaHoc);
        HelperUlti.close(rootPane);
    }


    @FXML
    public void onCancel(ActionEvent actionEvent) {
        HelperUlti.close(rootPane);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
