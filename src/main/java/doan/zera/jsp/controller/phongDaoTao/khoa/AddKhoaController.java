package doan.zera.jsp.controller.phongDaoTao.khoa;

import com.jfoenix.controls.JFXTextField;
import doan.zera.jsp.controller.FXMLController;
import doan.zera.jsp.model.Khoa;
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
@FXMLname(value = "phongDaoTao/khoa/addKhoa", title = "Cập nhật Khoa")
public class AddKhoaController extends FXMLController implements Initializable {
    @FXML
    public JFXTextField maKhoa;
    @FXML
    public JFXTextField tenKhoa;
    @FXML
    public StackPane rootPane;
    private KhoaController khoaController;
    private Khoa khoa;

    public void setKhoaController(KhoaController khoaController) {
        this.khoaController = khoaController;
    }

    public void setKhoa(Khoa khoa) {
        this.khoa = khoa;
        if (khoa.getId() == 0) return;
        maKhoa.setText(khoa.getMaKhoa());
        tenKhoa.setText(khoa.getTenKhoa());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void onSave(ActionEvent actionEvent) {
        List<String> error = new ArrayList<>();
        if (maKhoa.getText().equals("")) error.add("mã khoa");
        if (tenKhoa.getText().equals("")) error.add("tê Khoa");
        if (!error.isEmpty()) {
            HelperUlti.showDialog(rootPane, "Bạn chưa chọn", error);
            return;
        }
        if (khoa == null) khoa = new Khoa();
        khoa.setMaKhoa(maKhoa.getText());
        khoa.setTenKhoa(tenKhoa.getText());
        khoaController.save(khoa);
        HelperUlti.close(rootPane);
    }


    @FXML
    public void onCancel(ActionEvent actionEvent) {
        HelperUlti.close(rootPane);
    }
}