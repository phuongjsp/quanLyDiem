package doan.zera.jsp.controller.phongDaoTao.kyHoc;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import doan.zera.jsp.controller.FXMLController;
import doan.zera.jsp.model.Kyhoc;
import doan.zera.jsp.util.FXMLname;
import doan.zera.jsp.util.HelperUlti;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@FXMLname(value = "phongDaoTao/kyHoc/addKyHoc", title = "Cập nhật học kỳ")
public class AddKyHocController extends FXMLController {
    @FXML
    public JFXTextField maKyHoc;
    @FXML
    public JFXTextField tenKyHoc;

    @FXML
    public StackPane rootPane;
    @FXML
    public JFXDatePicker ngayBatDauHoc;
    private KyHocController kyHocController;
    private Kyhoc kyhoc;

    public void setKyHocController(KyHocController kyHocController) {
        this.kyHocController = kyHocController;
    }


    public void setKyhoc(Kyhoc kyhoc) {
        this.kyhoc = kyhoc;
        if (kyhoc.getId() == 0) return;
        maKyHoc.setText(kyhoc.getMaKyHoc());
        tenKyHoc.setText(kyhoc.getTenKyHoc());
        ngayBatDauHoc.setValue(kyhoc.getNgayBatDauHoc().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    @FXML
    public void onSave(ActionEvent actionEvent) {
        List<String> error = new ArrayList<>();
        if (maKyHoc.getText().length() == 0) error.add("mã học kỳ");
        if (tenKyHoc.getText().length() == 0) error.add("tên học kỳ");
        if (ngayBatDauHoc.getValue() == null) error.add("Ngày bắt đầu học kỳ");
        if (!error.isEmpty()) {
            HelperUlti.showDialog(rootPane, "bạn chưa chọn", error);
            return;
        }
        if (kyhoc == null) kyhoc = new Kyhoc();
        kyhoc.setMaKyHoc(maKyHoc.getText());
        kyhoc.setTenKyHoc(tenKyHoc.getText());
        kyhoc.setNgayBatDauHoc(Date.from(ngayBatDauHoc.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        kyHocController.save(kyhoc);
        HelperUlti.close(rootPane);
    }

    @FXML
    public void onCancel(ActionEvent actionEvent) {
        HelperUlti.close(rootPane);
    }
}
