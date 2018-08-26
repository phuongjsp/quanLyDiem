package doan.zera.jsp.controller.phongDaoTao.khoaHoc;

import doan.zera.jsp.DTO.KhoaHocDTO;
import doan.zera.jsp.controller.FXMLController;
import doan.zera.jsp.model.KhoaHoc;
import doan.zera.jsp.repositories.KhoaHocRepository;
import doan.zera.jsp.repositories.SinhVienRepository;
import doan.zera.jsp.util.FXMLname;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FXMLname(value = "phongDaoTao/khoaHoc/list", title = "Quản lý khóa học", prefWidth = 980)
public class KhoaHocController extends FXMLController implements Initializable {
    @FXML
    public TableView<KhoaHocDTO> khoaTable;
    @FXML
    public TableColumn<KhoaHocDTO, String> maKhoa;
    @FXML
    public TableColumn<KhoaHocDTO, String> tenKhoa;
    @FXML
    public TableColumn<KhoaHocDTO, String> namBatDau;
    @FXML
    public TableColumn<KhoaHocDTO, String> SLSV;
    private KhoaHocRepository khoaHocRepository;
    private SinhVienRepository sinhVienRepository;
    private ApplicationContext context;

    public KhoaHocController(KhoaHocRepository khoaHocRepository, SinhVienRepository sinhVienRepository, ApplicationContext context) {
        this.khoaHocRepository = khoaHocRepository;
        this.sinhVienRepository = sinhVienRepository;
        this.context = context;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        setList();
    }

    @Override
    public void onRefreshMain(ActionEvent actionEvent) {
        setList();
    }

    void setList() {
        khoaTable.getItems().clear();
        khoaHocRepository.findAll().forEach(khoaHoc ->
                khoaTable.getItems()
                        .add(new KhoaHocDTO(khoaHoc,
                                sinhVienRepository.countAllByKhoaHoc(khoaHoc))));
    }

    void initCol() {
        maKhoa.setCellValueFactory(new PropertyValueFactory<>("maKhoa"));
        tenKhoa.setCellValueFactory(new PropertyValueFactory<>("tenKhoa"));
        namBatDau.setCellValueFactory(new PropertyValueFactory<>("namBatDau"));
        SLSV.setCellValueFactory(new PropertyValueFactory<>("SLSV"));
    }

    @FXML
    public void btnThemKhoa(ActionEvent actionEvent) {
        AddKhoaHocController addKhoaHocController =
                (AddKhoaHocController) showNewScene(context.getBean(AddKhoaHocController.class), "Thêm mới khóa học");
        addKhoaHocController.setKhoaHocController(this);
        addKhoaHocController.setKhoa(new KhoaHoc());
    }

    public void save(KhoaHoc khoaHoc) {
        if (khoaHoc.getId() != 0) {
            khoaHocRepository.save(khoaHoc);
            for (int i = 0; i < khoaTable.getItems().size(); i++) {
                if (khoaTable.getItems().get(i).getId() == khoaHoc.getId()) {
                    KhoaHocDTO khoaDTO = khoaTable.getItems().get(i);
                    khoaDTO.setKhoaHoc(khoaHoc);
                    khoaTable.getItems().set(i, khoaDTO);
                }
            }
        } else {
            KhoaHocDTO khoaDTO = new KhoaHocDTO();
            khoaDTO.setKhoaHoc(khoaHocRepository.save(khoaHoc));
            khoaTable.getItems().add(khoaDTO);
        }
        khoaTable.refresh();
    }

    @FXML
    public void handleEditOption(ActionEvent actionEvent) {
        KhoaHocDTO khoaDTO = khoaTable.getSelectionModel().getSelectedItem();
        AddKhoaHocController addKhoaHocController =
                (AddKhoaHocController) showNewScene(context.getBean(AddKhoaHocController.class), "Cập nhật khóa học - " + khoaDTO.getTenKhoa());
        addKhoaHocController.setKhoaHocController(this);
        addKhoaHocController.setKhoa(khoaDTO.getKhoa());
    }

    @FXML
    public void handleDeleteOption(ActionEvent actionEvent) {
        KhoaHocDTO khoaDTO = khoaTable.getSelectionModel().getSelectedItem();
        khoaHocRepository.delete(khoaDTO.getKhoa());
        khoaTable.getItems().remove(khoaDTO);
    }


}
