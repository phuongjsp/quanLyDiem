package doan.zera.jsp.controller.phongDaoTao.khoa;

import doan.zera.jsp.DTO.KhoaDTO;
import doan.zera.jsp.controller.FXMLController;
import doan.zera.jsp.model.Khoa;
import doan.zera.jsp.model.Nganh;
import doan.zera.jsp.repositories.*;
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
@FXMLname(value = "phongDaoTao/khoa/list", title = "Danh sách Khoa", prefWidth = 980)
public class KhoaController extends FXMLController implements Initializable {

    public TableView<KhoaDTO> khoaTable;
    public TableColumn<KhoaDTO, String> maKhoa;
    public TableColumn<KhoaDTO, String> tenKhoa;
    public TableColumn<KhoaDTO, Integer> SLSV;
    public TableColumn<KhoaDTO, Integer> SLGV;
    public TableColumn<KhoaDTO, Integer> SLMH;

    private KhoaRepository khoaRepository;
    private SinhVienRepository sinhVienRepository;
    private GiaoVienRepository giaoVienRepository;
    private MonHocRepository monHocRepository;
    private NganhRepository nganhRepository;
    private ApplicationContext context;

    public KhoaController(KhoaRepository khoaRepository, SinhVienRepository sinhVienRepository, GiaoVienRepository giaoVienRepository, MonHocRepository monHocRepository, NganhRepository nganhRepository, ApplicationContext context) {
        this.khoaRepository = khoaRepository;
        this.sinhVienRepository = sinhVienRepository;
        this.giaoVienRepository = giaoVienRepository;
        this.monHocRepository = monHocRepository;
        this.nganhRepository = nganhRepository;
        this.context = context;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        setDataKhoaTable();
    }

    @Override
    public void onRefreshMain(ActionEvent actionEvent) {
        setDataKhoaTable();
    }

    void initCol() {
        maKhoa.setCellValueFactory(new PropertyValueFactory<>("maKhoa"));
        tenKhoa.setCellValueFactory(new PropertyValueFactory<>("tenKhoa"));
        SLSV.setCellValueFactory(new PropertyValueFactory<>("SLSV"));
        SLGV.setCellValueFactory(new PropertyValueFactory<>("SLGV"));
        SLMH.setCellValueFactory(new PropertyValueFactory<>("SLMH"));
    }

    private void setDataKhoaTable() {
        khoaTable.getItems().clear();
        khoaRepository.findAll().forEach(khoa -> {
            int sLSV = 0;
            int slMH = 0;
            int slGV = 0;
            for (Nganh nganh : nganhRepository.findAllByKhoa(khoa)) {
                sLSV += sinhVienRepository.countAllByNganh(nganh);
                slMH += monHocRepository.countAllByNganh(nganh);
                slGV += giaoVienRepository.countAllByNganh(nganh);
            }
            khoaTable.getItems().add(new KhoaDTO(khoa, sLSV, slGV, slMH));
        });
    }

    public void save(Khoa khoa) {
        if (khoa.getId() == 0) {
            khoa = khoaRepository.save(khoa);
            khoaTable.getItems().add(new KhoaDTO(khoa, 0, 0, 0));
        } else {
            khoa = khoaRepository.save(khoa);
            for (int i = 0; i < khoaTable.getItems().size(); i++) {
                if (khoaTable.getItems().get(i).getId() == khoa.getId()) {
                    KhoaDTO khoaDTO = khoaTable.getItems().get(i);
                    khoaDTO.setKhoa(khoa);
                    khoaTable.getItems().set(i, khoaDTO);
                }

            }
        }
        khoaTable.refresh();
    }

    @FXML
    public void btnThemKhoa(ActionEvent actionEvent) {
        AddKhoaController addKhoaController =
                (AddKhoaController) showNewScene(context.getBean(AddKhoaController.class), "Thêm mới khoa");
        addKhoaController.setKhoaController(this);
        addKhoaController.setKhoa(new Khoa());
    }

    @FXML
    public void handleEditOption(ActionEvent actionEvent) {
        Khoa khoa = khoaTable.getSelectionModel().getSelectedItem().getKhoa();
        AddKhoaController addKhoaController =
                (AddKhoaController) showNewScene(context.getBean(AddKhoaController.class), "Cập nhật khoa - " + khoa.getTenKhoa());
        addKhoaController.setKhoaController(this);
        addKhoaController.setKhoa(khoa);
    }

    @FXML
    public void handleDeleteOption(ActionEvent actionEvent) {
        KhoaDTO khoaDTO = khoaTable.getSelectionModel().getSelectedItem();
        khoaRepository.deleteById(khoaDTO.getId());
        khoaTable.getItems().remove(khoaDTO);
    }
}
