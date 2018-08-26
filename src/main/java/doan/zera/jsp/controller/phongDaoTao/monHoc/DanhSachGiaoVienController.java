package doan.zera.jsp.controller.phongDaoTao.monHoc;

import doan.zera.jsp.DTO.GiaoVienDTO;
import doan.zera.jsp.DTO.MonHocDTO;
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
@FXMLname(value = "phongDaoTao/monHoc/danhSachGiaoVien")
public class DanhSachGiaoVienController extends FXMLController implements Initializable {
    @FXML
    public TableView<GiaoVienDTO> giaoVienTableIn;
    @FXML
    public TableColumn<GiaoVienDTO, String> maGvIn;
    @FXML
    public TableColumn<GiaoVienDTO, String> hoTenGvIn;
    @FXML
    public TableColumn<GiaoVienDTO, String> gioiTinhIn;
    @FXML
    public TableColumn<GiaoVienDTO, String> hocViIn;
    @FXML
    public TableView<GiaoVienDTO> giaoVienTableOut;
    @FXML
    public TableColumn<GiaoVienDTO, String> maGvOut;
    @FXML
    public TableColumn<GiaoVienDTO, String> hoTenGvOut;
    @FXML
    public TableColumn<GiaoVienDTO, String> gioiTinhOut;
    @FXML
    public TableColumn<GiaoVienDTO, String> hocViOut;
    @FXML
    public StackPane rootPane;
    private MonHocDTO monHocDTO;
    private MonHocController monHocController;
    private GiaoVienRepository giaoVienRepository;
    private MonHocRepository monHocRepository;
    private MonHocGiaoVienRepository monHocGiaoVienRepository;

    public DanhSachGiaoVienController(GiaoVienRepository giaoVienRepository, MonHocRepository monHocRepository, MonHocGiaoVienRepository monHocGiaoVienRepository) {
        this.giaoVienRepository = giaoVienRepository;
        this.monHocRepository = monHocRepository;
        this.monHocGiaoVienRepository = monHocGiaoVienRepository;
    }

    void initColIn() {
        maGvIn.setCellValueFactory(new PropertyValueFactory<>("maGv"));
        hoTenGvIn.setCellValueFactory(new PropertyValueFactory<>("hoTenFull"));
        gioiTinhIn.setCellValueFactory(new PropertyValueFactory<>("gioiTinhString"));
        hocViIn.setCellValueFactory(new PropertyValueFactory<>("hocVi"));
    }

    void initColOut() {
        maGvOut.setCellValueFactory(new PropertyValueFactory<>("maGv"));
        hoTenGvOut.setCellValueFactory(new PropertyValueFactory<>("hoTenFull"));
        gioiTinhOut.setCellValueFactory(new PropertyValueFactory<>("gioiTinhString"));
        hocViOut.setCellValueFactory(new PropertyValueFactory<>("hocVi"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initColIn();
        initColOut();

    }

    public void setMonHoc(MonHocController monHocController, MonHoc monHoc) {
        this.monHocController = monHocController;
        MonHoc mh = monHocRepository.findById(monHoc.getId()).get();
        this.monHocDTO = new MonHocDTO(mh);
        monHocDTO.setGiaoViens(new HashSet<>(monHocGiaoVienRepository.findGiaoVienByMonHoc(mh)));
        setGiaoVienTableIn();
        setGiaoVienTableOut();
    }

    public void setGiaoVienTableIn() {
        monHocDTO.getGiaoViens().forEach(this::setItemGiaoVienTableIn);
    }

    public void setGiaoVienTableOut() {
        if (monHocDTO.getGiaoViens().isEmpty())
            giaoVienRepository.findAllByNganh(monHocDTO.getNganh()).forEach(this::setItemGiaoVienTableOut);
        else giaoVienRepository.findAllByNganhAndIdNotIn(monHocDTO.getNganh(),
                monHocDTO.getGiaoViens().stream()
                        .map(GiaoVien::getId)
                        .collect(Collectors.toList()))
                .forEach(this::setItemGiaoVienTableOut);
    }

    @FXML
    public void handleDeleteOption(ActionEvent actionEvent) {
        GiaoVienDTO giaoVienDTO = giaoVienTableIn.getSelectionModel().getSelectedItem();
        giaoVienTableOut.getItems().add(giaoVienDTO);
        giaoVienTableIn.getItems().remove(giaoVienDTO);
        monHocGiaoVienRepository.deleteByMonHocAndGiaoVien(this.monHocDTO.getMonHoc(), giaoVienDTO.getGiaoVien());
        refresh();
    }

    @FXML
    public void handleAddOption(ActionEvent actionEvent) {
        GiaoVienDTO giaoVienDTO = giaoVienTableOut.getSelectionModel().getSelectedItem();
        giaoVienTableIn.getItems().add(giaoVienDTO);
        giaoVienTableOut.getItems().remove(giaoVienDTO);
        MonHocGiaoVien monHocGiaoVien = new MonHocGiaoVien();
        monHocGiaoVien.setMonHoc(this.monHocDTO.getMonHoc());
        monHocGiaoVien.setGiaoVien(giaoVienDTO.getGiaoVien());
        monHocGiaoVienRepository.save(monHocGiaoVien);
        refresh();
    }

    private void refresh() {

        giaoVienTableIn.refresh();
        giaoVienTableOut.refresh();
    }

    private void setItemGiaoVienTableOut(GiaoVien giaoVien) {
        giaoVienTableOut.getItems().add(new GiaoVienDTO(giaoVien));
    }

    private void setItemGiaoVienTableIn(GiaoVien giaoVien) {
        giaoVienTableIn.getItems().add(new GiaoVienDTO(giaoVien));
    }

    @FXML
    public void onSave(ActionEvent actionEvent) {
        HelperUlti.close(rootPane);
        monHocController.setMonHocTable(monHocController.nganhScope);
    }

    @FXML
    public void onCancel(ActionEvent actionEvent) {
        HelperUlti.close(rootPane);
    }
}
