package doan.zera.jsp.controller.phongDaoTao.monHoc;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import doan.zera.jsp.controller.FXMLController;
import doan.zera.jsp.model.MonHoc;
import doan.zera.jsp.model.Nganh;
import doan.zera.jsp.util.FXMLname;
import doan.zera.jsp.util.HelperUlti;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.StackPane;
import javafx.util.converter.DoubleStringConverter;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
@FXMLname(value = "phongDaoTao/monHoc/addMonHoc", title = "Cập nhật môn học")
public class AddMonHocController extends FXMLController implements Initializable {
    @FXML
    public JFXTextField heSo;
    @FXML
    public JFXComboBox<Label> cbbNganh;
    @FXML
    public JFXTextField maMh;
    @FXML
    public JFXTextField tenMh;
    @FXML
    public StackPane rootPane;
    List<Nganh> nganhs;
    private MonHocController monHocController;
    private MonHoc monHoc;

    public void setMonHocController(MonHocController monHocController) {
        this.monHocController = monHocController;
        monHocController.setDataCbbNganh(cbbNganh);
    }

    public void setNganhs(List<Nganh> nganhs) {
        this.nganhs = nganhs;
    }

    public void setMonHoc(MonHoc monHoc) {
        this.monHoc = monHoc;
        if (monHoc.getId() == 0) return;
        maMh.setText(monHoc.getMaMonHoc());
        tenMh.setText(monHoc.getTenMonHoc());
        heSo.setText(monHoc.getHeSo() + "");
        cbbNganh.getItems().forEach(label -> {
            if (label.getId().equals(monHoc.getNganh().getMaNganh()))
                cbbNganh.getSelectionModel().select(label);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        heSo.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
    }

    @FXML
    public void onSave(ActionEvent actionEvent) {
        List<String> error = new ArrayList<>();
        if (maMh.getText().isEmpty()) error.add("Mã môn học");
        if (tenMh.getText().isEmpty()) error.add("Tên môn học");
        if (heSo.getText().isEmpty()) error.add("Hệ số tín chỉ");
        if (cbbNganh.getSelectionModel().getSelectedItem() == null) error.add("Ngành");
        if (!error.isEmpty()) {
            HelperUlti.showDialog(rootPane, "Bạn chưa chọn", error);
            return;
        }
        if (monHoc == null) monHoc = new MonHoc();
        monHoc.setMaMonHoc(maMh.getText());
        monHoc.setTenMonHoc(tenMh.getText());
        monHoc.setHeSo(Double.parseDouble(heSo.getText()));

        nganhs.forEach(nganh -> {
            if (nganh.getMaNganh().equals(cbbNganh.getSelectionModel().getSelectedItem().getId()))
                monHoc.setNganh(nganh);
        });
        monHocController.save(monHoc);
        HelperUlti.close(rootPane);
    }

    @FXML
    public void onCancel(ActionEvent actionEvent) {
        HelperUlti.close(rootPane);
    }
}
