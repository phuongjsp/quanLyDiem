package doan.zera.jsp.controller.phongDaoTao.nganh;

import doan.zera.jsp.DTO.NganhDTO;
import doan.zera.jsp.controller.FXMLController;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@FXMLname(value = "phongDaoTao/nganh/nganh", title = "Quản lý ngành học", prefWidth = 980)
public class NganhController extends FXMLController implements Initializable {
    @FXML
    public TableView<NganhDTO> nganhTable;
    @FXML
    public TableColumn<NganhDTO, String> maNganh;
    @FXML
    public TableColumn<NganhDTO, String> tenNganh;
    @FXML
    public TableColumn<NganhDTO, String> maKhoa;
    @FXML
    public TableColumn<NganhDTO, Integer> SLSV;
    @FXML
    public TableColumn<NganhDTO, Integer> SLMH;
    @FXML
    public TableColumn<NganhDTO, Integer> SLGV;


    private NganhRepository nganhRepository;
    private SinhVienRepository sinhVienRepository;
    private GiaoVienRepository giaoVienRepository;
    private MonHocRepository monHocRepository;
    private KhoaRepository khoaRepository;
    private ApplicationContext context;

    public NganhController(NganhRepository nganhRepository, SinhVienRepository sinhVienRepository, GiaoVienRepository giaoVienRepository, MonHocRepository monHocRepository, KhoaRepository khoaRepository, ApplicationContext context) {
        this.nganhRepository = nganhRepository;
        this.sinhVienRepository = sinhVienRepository;
        this.giaoVienRepository = giaoVienRepository;
        this.monHocRepository = monHocRepository;
        this.khoaRepository = khoaRepository;
        this.context = context;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        setNganhTable();

    }

    @Override
    public void onRefreshMain(ActionEvent actionEvent) {
        setNganhTable();
    }

    void setNganhTable() {
        nganhTable.getItems().clear();
        nganhRepository.findAll().forEach(nganh -> {
            nganhTable.getItems().add(new NganhDTO(nganh,
                    sinhVienRepository.countAllByNganh(nganh),
                    giaoVienRepository.countAllByNganh(nganh),
                    monHocRepository.countAllByNganh(nganh)));
        });
    }

    public void save(Nganh nganh) {
        if (nganh.getId() != 0) {
            nganh = nganhRepository.save(nganh);
            for (int i = 0; i < nganhTable.getItems().size(); i++) {
                if (nganhTable.getItems().get(i).getId() == nganh.getId()) {
                    NganhDTO nganhDTO = nganhTable.getItems().get(i);
                    nganhDTO.setNganh(nganh);
                    nganhTable.getItems().set(i, nganhDTO);
                }
            }
        } else {
            nganh = nganhRepository.save(nganh);
            nganhTable.getItems().add(new NganhDTO(nganh));
        }
        nganhTable.refresh();
    }

    void initCol() {
        maNganh.setCellValueFactory(new PropertyValueFactory<>("maNganh"));
        tenNganh.setCellValueFactory(new PropertyValueFactory<>("tenNganh"));
        maKhoa.setCellValueFactory(new PropertyValueFactory<>("maKhoa"));
        SLSV.setCellValueFactory(new PropertyValueFactory<>("SLSV"));
        SLMH.setCellValueFactory(new PropertyValueFactory<>("SLMH"));
        SLGV.setCellValueFactory(new PropertyValueFactory<>("SLGV"));
    }

    @FXML
    public void handleEditOption(ActionEvent actionEvent) throws IOException {
        Nganh nganh = nganhTable.getSelectionModel().getSelectedItem().getNganh();
        AddNganhController controller = (AddNganhController) showNewScene(context.getBean(AddNganhController.class),
                "Cập nhật ngành - " + nganh.getTenNganh());
        controller.setNganhController(this);
        controller.setCbbKhoa(khoaRepository.findAll());
        controller.setNganh(nganh);

    }


    @FXML
    public void handleDeleteOption(ActionEvent actionEvent) {
        NganhDTO nganhDTO = nganhTable.getSelectionModel().getSelectedItem();
        nganhRepository.deleteById(nganhDTO.getId());
        nganhTable.getItems().remove(nganhDTO);
    }

    @FXML
    public void btnThemNganh(ActionEvent actionEvent) {
        AddNganhController controller = (AddNganhController) showNewScene(context.getBean(AddNganhController.class), "Thêm mới ngành");
        controller.setNganhController(this);
        controller.setCbbKhoa(khoaRepository.findAll());
        controller.setNganh(new Nganh());
    }
}


