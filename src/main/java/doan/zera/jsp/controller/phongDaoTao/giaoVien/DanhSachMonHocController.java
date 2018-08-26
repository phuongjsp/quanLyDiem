package doan.zera.jsp.controller.phongDaoTao.giaoVien;

import doan.zera.jsp.DTO.GiaoVienDTO;
import doan.zera.jsp.controller.FXMLController;
import doan.zera.jsp.model.GiaoVien;
import doan.zera.jsp.model.MonHoc;
import doan.zera.jsp.model.MonHocGiaoVien;
import doan.zera.jsp.repositories.GiaoVienRepository;
import doan.zera.jsp.repositories.MonHocGiaoVienRepository;
import doan.zera.jsp.repositories.MonHocRepository;
import doan.zera.jsp.util.FXMLname;
import doan.zera.jsp.util.HelperUlti;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
@FXMLname(value = "phongDaoTao/giaoVien/danhSachMonHoc")
public class DanhSachMonHocController extends FXMLController implements Initializable {
    @FXML
    public StackPane rootPane;
    @FXML
    public TableView<MonHoc> monHocTableIn;
    @FXML
    public TableColumn<MonHoc, String> maMhIn;
    @FXML
    public TableColumn<MonHoc, String> tenMhIn;
    @FXML
    public TableColumn<MonHoc, String> hesoIn;
    @FXML
    public TableView<MonHoc> monHocTableOut;
    @FXML
    public TableColumn<MonHoc, String> maMhOut;
    @FXML
    public TableColumn<MonHoc, String> tenMhOut;
    @FXML
    public TableColumn<MonHoc, String> hesoOut;
    private MonHocRepository monHocRepository;
    private GiaoVienRepository giaoVienRepository;
    private MonHocGiaoVienRepository monHocGiaoVienRepository;

    private GiaoVienDTO giaoVienDTO;
    private GiaovienController giaovienController;

    public DanhSachMonHocController(MonHocRepository monHocRepository, GiaoVienRepository giaoVienRepository, MonHocGiaoVienRepository monHocGiaoVienRepository) {
        this.monHocRepository = monHocRepository;
        this.giaoVienRepository = giaoVienRepository;
        this.monHocGiaoVienRepository = monHocGiaoVienRepository;
    }

    public void setGiaoVienAndGiaovienController(GiaoVien giaoVien, GiaovienController giaovienController) {
        GiaoVien gv = giaoVienRepository.findById(giaoVien.getId()).get();
        this.giaoVienDTO = new GiaoVienDTO(gv);
        giaoVienDTO.setMonHocs(new HashSet<>(monHocGiaoVienRepository.findMonHocByGiaoVien(gv)));
        this.giaovienController = giaovienController;
        setMonHocTableIn();
        setMonHocTableOut();
    }

    void initColIn() {
        maMhIn.setCellValueFactory(new PropertyValueFactory<>("maMonHoc"));
        tenMhIn.setCellValueFactory(new PropertyValueFactory<>("tenMonHoc"));
        hesoIn.setCellValueFactory(new PropertyValueFactory<>("heSo"));
    }

    void initColOut() {
        maMhOut.setCellValueFactory(new PropertyValueFactory<>("maMonHoc"));
        tenMhOut.setCellValueFactory(new PropertyValueFactory<>("tenMonHoc"));
        hesoOut.setCellValueFactory(new PropertyValueFactory<>("heSo"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initColIn();
        initColOut();
    }

    private void setMonHocTableIn() {
        giaoVienDTO.getMonHocs().forEach(monHocTableIn.getItems()::add);
    }

    private void setMonHocTableOut() {
        if (giaoVienDTO.getMonHocs().isEmpty())
            monHocRepository.findAllByNganh(giaoVienDTO.getNganh()).forEach(monHocTableOut.getItems()::add);
        else
            monHocRepository.findAllByNganhAndIdNotIn(giaoVienDTO.getNganh(),
                    giaoVienDTO.getMonHocs().stream().map(MonHoc::getId)
                            .collect(Collectors.toList())).forEach(monHocTableOut.getItems()::add);
    }

    private void refresh() {
        monHocTableIn.refresh();
        monHocTableOut.refresh();
    }

    @FXML
    public void handleDeleteOption(ActionEvent actionEvent) {
        MonHoc monHoc = monHocTableIn.getSelectionModel().getSelectedItem();
        monHocTableIn.getItems().remove(monHoc);
        monHocTableOut.getItems().add(monHoc);
        monHocGiaoVienRepository.deleteByMonHocAndGiaoVien(monHoc, this.giaoVienDTO.getGiaoVien());
        updateSlMhInForm(false);
        refresh();
    }

    @FXML
    public void handleAddOption(ActionEvent actionEvent) {
        MonHoc monHoc = monHocTableOut.getSelectionModel().getSelectedItem();
        monHocTableIn.getItems().add(monHoc);
        monHocTableOut.getItems().remove(monHoc);
        MonHocGiaoVien monHocGiaoVien = new MonHocGiaoVien();
        monHocGiaoVien.setMonHoc(monHoc);
        monHocGiaoVien.setGiaoVien(this.giaoVienDTO.getGiaoVien());
        monHocGiaoVienRepository.save(monHocGiaoVien);
        updateSlMhInForm(true);
        refresh();

    }

    private void updateSlMhInForm(boolean add) {
        giaovienController.updateSlMonHoc(giaoVienDTO.getMaGv(), add);
    }

    @FXML
    public void onCancel(ActionEvent actionEvent) {
        HelperUlti.close(rootPane);
    }

    @FXML
    public void onSave(ActionEvent actionEvent) {
        HelperUlti.close(rootPane);
        giaovienController.setGiaoVienTable(giaovienController.nganhScope);
    }
}
