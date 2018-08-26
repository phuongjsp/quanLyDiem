package doan.zera.jsp.controller.phongDaoTao.kyHoc;

import doan.zera.jsp.DTO.KyhocDTO;
import doan.zera.jsp.controller.FXMLController;
import doan.zera.jsp.model.Kyhoc;
import doan.zera.jsp.repositories.KyhocRepository;
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
@FXMLname(value = "phongDaoTao/kyHoc/list", title = "Quản lý học kỳ", prefWidth = 980)
public class KyHocController extends FXMLController implements Initializable {
    @FXML
    public TableView<KyhocDTO> kyHocTable;
    @FXML
    public TableColumn<KyhocDTO, String> maKyHoc;
    @FXML
    public TableColumn<KyhocDTO, String> tenKyHoc;
    @FXML
    public TableColumn<KyhocDTO, String> ngayBatDauHoc;

    private KyhocRepository kyhocRepository;

    private ApplicationContext context;

    public KyHocController(KyhocRepository kyhocRepository, ApplicationContext context) {
        this.kyhocRepository = kyhocRepository;
        this.context = context;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        setKyHocTable();
    }

    @Override
    public void onRefreshMain(ActionEvent actionEvent) {
        setKyHocTable();
    }

    private void setKyHocTable() {
        kyHocTable.getItems().clear();
        kyhocRepository.findAll().forEach(kyhoc ->
                kyHocTable.getItems().add(new KyhocDTO(kyhoc)));
    }

    void initCol() {
        maKyHoc.setCellValueFactory(new PropertyValueFactory<>("maKyHoc"));
        tenKyHoc.setCellValueFactory(new PropertyValueFactory<>("tenKyHoc"));
        ngayBatDauHoc.setCellValueFactory(new PropertyValueFactory<>("ngayBatDauHocString"));
    }

    public void save(Kyhoc kyhoc) {
        if (kyhoc.getId() != 0) {
            kyhoc = kyhocRepository.save(kyhoc);
            for (int i = 0; i < kyHocTable.getItems().size(); i++) {
                if (kyHocTable.getItems().get(i).getId() == kyhoc.getId()) {
                    kyHocTable.getItems().set(i, new KyhocDTO(kyhoc));
                }
            }
        } else {
            kyHocTable.getItems().add(new KyhocDTO(kyhocRepository.save(kyhoc)));
        }
        kyHocTable.refresh();
    }

    @FXML
    public void btnThemKyHoc(ActionEvent actionEvent) {
        AddKyHocController addKyHocController =
                (AddKyHocController) showNewScene(context.getBean(AddKyHocController.class), "Thêm mới kỳ học");
        addKyHocController.setKyHocController(this);
        addKyHocController.setKyhoc(new Kyhoc());
    }

    @FXML
    public void handleEditOption(ActionEvent actionEvent) {
        Kyhoc kyhoc = kyHocTable.getSelectionModel().getSelectedItem();
        AddKyHocController addKyHocController = (AddKyHocController) showNewScene(context.getBean(AddKyHocController.class), "Cập nhật kỳ học - " + kyhoc.getTenKyHoc());
        addKyHocController.setKyHocController(this);
        addKyHocController.setKyhoc(((KyhocDTO) kyhoc).getKyhoc());
    }

    @FXML
    public void handleDeleteOption(ActionEvent actionEvent) {
        Kyhoc kyhoc = kyHocTable.getSelectionModel().getSelectedItem();
        kyhocRepository.deleteById(kyhoc.getId());
        kyHocTable.getItems().remove(kyhoc);
    }


}
