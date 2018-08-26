package doan.zera.jsp.controller.phongDaoTao.nganh;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import doan.zera.jsp.controller.FXMLController;
import doan.zera.jsp.model.Khoa;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
@FXMLname(value = "phongDaoTao/nganh/addNganh", title = "Cập nhật ngành học")
public class AddNganhController extends FXMLController implements Initializable {
    @FXML
    public JFXTextField maNganh;
    @FXML
    public JFXTextField tenNganh;
    @FXML
    public JFXComboBox<Label> cbbKhoa;
    @FXML
    public StackPane rootPane;
    private Nganh nganh;
    private NganhController nganhController;
    private List<Khoa> khoas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setNganhController(NganhController nganhController) {
        this.nganhController = nganhController;
    }

    public void setCbbKhoa(List<Khoa> khoas) {
        this.khoas = khoas;
        khoas.forEach(khoa ->
                cbbKhoa.getItems().add(HelperUlti.newLabel(khoa.getMaKhoa(), khoa.getTenKhoa())));
    }

    public void setNganh(Nganh nganh) {
        this.nganh = nganh;
        if (nganh.getId() == 0) return;
        tenNganh.setText(nganh.getTenNganh());
        maNganh.setText(nganh.getMaNganh());
        cbbKhoa.getItems().forEach(label -> {
            if (label.getId().equals(nganh.getKhoa().getMaKhoa()))
                cbbKhoa.getSelectionModel().select(label);
        });

    }


    @FXML
    public void onSave(ActionEvent actionEvent) {
        List<String> error = new ArrayList<>();
        if (tenNganh.getText().isEmpty()) error.add("ten nganh");
        if (maNganh.getText().isEmpty()) error.add("ma nganh");
        if (cbbKhoa.getSelectionModel().isEmpty()) error.add("khoa");
        if (!error.isEmpty()) {
            HelperUlti.showDialog(rootPane, "ban chua chon", error);
            return;
        }
        if (nganh == null) nganh = new Nganh();
        nganh.setMaNganh(maNganh.getText());
        nganh.setTenNganh(tenNganh.getText());
        khoas.forEach(khoa -> {
            if (cbbKhoa.getSelectionModel().getSelectedItem().getId().equals(khoa.getMaKhoa()))
                nganh.setKhoa(khoa);
        });
        nganhController.save(nganh);
        HelperUlti.close(rootPane);
    }

    @FXML
    public void onCancel(ActionEvent actionEvent) {
        HelperUlti.close(rootPane);
    }
}
