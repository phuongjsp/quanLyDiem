package doan.zera.jsp.controller.phongDaoTao.monHoc;

import com.jfoenix.controls.JFXComboBox;
import doan.zera.jsp.DTO.MonHocDTO;
import doan.zera.jsp.controller.FXMLController;
import doan.zera.jsp.model.MonHoc;
import doan.zera.jsp.model.Nganh;
import doan.zera.jsp.repositories.MonHocGiaoVienRepository;
import doan.zera.jsp.repositories.MonHocRepository;
import doan.zera.jsp.repositories.NganhRepository;
import doan.zera.jsp.util.FXMLname;
import doan.zera.jsp.util.HelperUlti;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
@FXMLname(value = "phongDaoTao/monHoc/list", title = "Quản lý môn học", prefWidth = 980)
public class MonHocController extends FXMLController implements Initializable {
    @FXML
    public TableView<MonHocDTO> monHocTable;
    @FXML
    public TableColumn<MonHocDTO, String> maMh;
    @FXML
    public TableColumn<MonHocDTO, String> tenMh;
    @FXML
    public TableColumn<MonHocDTO, String> maNganh;
    @FXML
    public TableColumn<MonHocDTO, Double> heSo;
    @FXML
    public TableColumn<MonHocDTO, Integer> SLGV;
    @FXML
    public JFXComboBox<Label> cbbNganh;
    public Nganh nganhScope;
    private List<Nganh> nganhs;

    private NganhRepository nganhRepository;

    private MonHocRepository monHocRepository;

    private ApplicationContext context;

    private MonHocGiaoVienRepository monHocGiaoVienRepository;

    public MonHocController(NganhRepository nganhRepository, MonHocRepository monHocRepository, ApplicationContext context, MonHocGiaoVienRepository monHocGiaoVienRepository) {
        this.nganhRepository = nganhRepository;
        this.monHocRepository = monHocRepository;
        this.context = context;
        this.monHocGiaoVienRepository = monHocGiaoVienRepository;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        initSet();
    }

    private void initSet() {
        setDataCbbNganh(cbbNganh);
        cbbNganh.getItems().add(HelperUlti.newLabel("all", "Tất cả"));
    }

    @Override
    public void onRefreshMain(ActionEvent actionEvent) {
        initSet();
    }

    public void setDataCbbNganh(JFXComboBox<Label> _cbbNganh) {
        this.nganhs = nganhRepository.findAll();
        cbbNganh.getItems().clear();
        this.nganhs.forEach(nganh ->
                _cbbNganh.getItems().add(HelperUlti.newLabel(nganh.getMaNganh(), nganh.getTenNganh())));

    }

    void setMonHocTable(Nganh nganh) {
        monHocTable.getItems().clear();
        if (nganh != null)
            for (int i = 0; i < monHocRepository.countAllByNganh(nganh); i++) {
                MonHoc monHoc = monHocRepository
                        .findAllByNganh(nganh, PageRequest.of(i, 1)).get(0);
                MonHocDTO monHocDTO = new MonHocDTO(monHoc);
                monHocDTO.setSoLuongGV((int) monHocGiaoVienRepository.countAllByMonHoc(monHoc));
                monHocTable.getItems().add(monHocDTO);
            }
        else for (int i = 0; i < monHocRepository.count(); i++) {
            MonHoc monHoc = monHocRepository
                    .findAll(PageRequest.of(i, 1)).getContent().get(0);
            MonHocDTO monHocDTO = new MonHocDTO(monHoc);
            monHocDTO.setSoLuongGV((int) monHocGiaoVienRepository.countAllByMonHoc(monHoc));
            monHocTable.getItems().add(monHocDTO);
        }
        monHocTable.refresh();

    }

    void initCol() {
        maMh.setCellValueFactory(new PropertyValueFactory<>("maMonHoc"));
        tenMh.setCellValueFactory(new PropertyValueFactory<>("tenMonHoc"));
        maNganh.setCellValueFactory(new PropertyValueFactory<>("maNganh"));
        heSo.setCellValueFactory(new PropertyValueFactory<>("heSo"));
        SLGV.setCellValueFactory(new PropertyValueFactory<>("soLuongGV"));
    }

    @FXML
    public void onTimKiemMh(ActionEvent actionEvent) {
        if (cbbNganh.getSelectionModel().getSelectedItem() == null) return;
        Label label = cbbNganh.getSelectionModel().getSelectedItem();
        if (label.getId().equals("all")) setMonHocTable(null);
        else
            for (Nganh nganh : nganhs)
                if (label.getId().equals(nganh.getMaNganh())) {
                    setMonHocTable(nganh);
                    nganhScope = nganh;
                }

    }

    public void save(MonHoc monHoc) {
        if (monHoc.getId() == 0)
            monHocTable.getItems().add(new MonHocDTO(monHocRepository.save(monHoc)));
        else {
            for (int i = 0; i < monHocTable.getItems().size(); i++)
                if (monHocTable.getItems().get(i).getId() == monHoc.getId())
                    monHocTable.getItems().set(i, new MonHocDTO(monHocRepository.save(monHoc)));

        }
    }

    @FXML
    public void onThemMh(ActionEvent actionEvent) {
        AddMonHocController addMonHocController = (AddMonHocController) showNewScene(context.getBean(AddMonHocController.class), "Thêm mới môn học");
        addMonHocController.setMonHocController(this);
        addMonHocController.setNganhs(nganhs);
        addMonHocController.setMonHoc(new MonHoc());
    }

    @FXML
    public void handleEditOption(ActionEvent actionEvent) {
        MonHoc monHoc = monHocTable.getSelectionModel().getSelectedItem().getMonHoc();
        AddMonHocController addMonHocController = (AddMonHocController) showNewScene(new AddMonHocController(),
                "Cập nhật môn học - " + monHoc.getTenMonHoc());
        addMonHocController.setMonHocController(this);
        addMonHocController.setNganhs(nganhs);
        addMonHocController.setMonHoc(monHoc);

    }

    @FXML
    public void handleDeleteOption(ActionEvent actionEvent) {
        MonHocDTO monHocDTO = monHocTable.getSelectionModel().getSelectedItem();
        monHocGiaoVienRepository.deleteByMonHoc(monHocDTO.getMonHoc());
        monHocRepository.deleteById(monHocDTO.getId());
        monHocTable.getItems().remove(monHocDTO);
        monHocTable.refresh();
    }

    @FXML
    public void handleListGiaoVien(ActionEvent actionEvent) {
        DanhSachGiaoVienController danhSachGiaoVienController = (DanhSachGiaoVienController) showNewScene(context.getBean(DanhSachGiaoVienController.class), "Danh sách giáo viên giảng dạy");
        danhSachGiaoVienController.setMonHoc(this, monHocTable.getSelectionModel().getSelectedItem().getMonHoc());
    }
}
