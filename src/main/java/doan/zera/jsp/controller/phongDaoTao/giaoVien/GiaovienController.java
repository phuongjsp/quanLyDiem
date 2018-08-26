package doan.zera.jsp.controller.phongDaoTao.giaoVien;

import com.jfoenix.controls.JFXComboBox;
import doan.zera.jsp.DTO.GiaoVienDTO;
import doan.zera.jsp.controller.FXMLController;
import doan.zera.jsp.model.Authorities;
import doan.zera.jsp.model.GiaoVien;
import doan.zera.jsp.model.Nganh;
import doan.zera.jsp.model.User;
import doan.zera.jsp.repositories.*;
import doan.zera.jsp.util.FXMLname;
import doan.zera.jsp.util.HelperUlti;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

@Component
@FXMLname(value = "phongDaoTao/giaoVien/list", title = "Quản lý giáo viên", prefWidth = 960, prefHeight = 550)
public class GiaovienController extends FXMLController implements Initializable {
    @FXML
    public TableView<GiaoVienDTO> giaoVienTable;
    @FXML
    public TableColumn<GiaoVienDTO, String> maGv;
    @FXML
    public TableColumn<GiaoVienDTO, String> ho;
    @FXML
    public TableColumn<GiaoVienDTO, String> ten;
    @FXML
    public TableColumn<GiaoVienDTO, String> gioiTinhString;
    @FXML
    public TableColumn<GiaoVienDTO, String> ngaySinhString;
    @FXML
    public TableColumn<GiaoVienDTO, String> queQuan;
    @FXML
    public TableColumn<GiaoVienDTO, String> maNganh;
    @FXML
    public TableColumn<GiaoVienDTO, String> hocVi;
    @FXML
    public JFXComboBox<Label> cbbNganh;
    @FXML
    public TableColumn<GiaoVienDTO, String> SLMonHocGiangDay;
    public Nganh nganhScope;
    @FXML
    public StackPane rootPane;
    private NganhRepository nganhRepository;
    private GiaoVienRepository giaoVienRepository;
    private ApplicationContext context;
    private MonHocGiaoVienRepository monHocGiaoVienRepository;


    private UsersRepository usersRepository;
    private AuthoritiesRepository authoritiesRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private List<Nganh> nganhs;

    public GiaovienController(NganhRepository nganhRepository, GiaoVienRepository giaoVienRepository, ApplicationContext context, MonHocGiaoVienRepository monHocGiaoVienRepository, UsersRepository usersRepository, AuthoritiesRepository authoritiesRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.nganhRepository = nganhRepository;
        this.giaoVienRepository = giaoVienRepository;
        this.context = context;
        this.monHocGiaoVienRepository = monHocGiaoVienRepository;
        this.usersRepository = usersRepository;
        this.authoritiesRepository = authoritiesRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        initSet();
    }

    private void initSet() {
        cbbNganh.getItems().add(HelperUlti.newLabel("all", "Tất cả"));
        setDataCbbNganh(cbbNganh);
    }

    @Override
    public void onRefreshMain(ActionEvent actionEvent) {
        cbbNganh.getItems().clear();
        initSet();
    }

    private void initCol() {
        maGv.setCellValueFactory(new PropertyValueFactory<>("maGv"));
        ho.setCellValueFactory(new PropertyValueFactory<>("ho"));
        ten.setCellValueFactory(new PropertyValueFactory<>("ten"));
        queQuan.setCellValueFactory(new PropertyValueFactory<>("queQuan"));
        ngaySinhString.setCellValueFactory(new PropertyValueFactory<>("ngaySinhString"));
        maNganh.setCellValueFactory(new PropertyValueFactory<>("maNganh"));
        hocVi.setCellValueFactory(new PropertyValueFactory<>("hocVi"));
        gioiTinhString.setCellValueFactory(new PropertyValueFactory<>("gioiTinhString"));
        SLMonHocGiangDay.setCellValueFactory(new PropertyValueFactory<>("SLMonHocGiangDay"));
    }

    public void setDataCbbNganh(JFXComboBox<Label> _cbbNganh) {
        nganhs = nganhRepository.findAll();
        nganhs.forEach(nganh ->
                _cbbNganh.getItems().add(HelperUlti.newLabel(nganh.getMaNganh(), nganh.getTenNganh())));
    }

    public void setGiaoVienTable(Nganh nganh) {
        nganhScope = nganh;
        giaoVienTable.getItems().clear();
        if (nganh != null) {
            for (int i = 0; i < giaoVienRepository.countAllByNganh(nganh); i++) {
                GiaoVien giaoVien = giaoVienRepository.
                        findAllByNganh(nganh, PageRequest.of(i, 1)).get(0);
                GiaoVienDTO giaoVienDTO = new GiaoVienDTO(giaoVien);
                giaoVienDTO.setSLMonHocGiangDay(monHocGiaoVienRepository.countAllByGiaoVien(giaoVien));
                giaoVienTable.getItems().add(giaoVienDTO);
            }
        } else {
            for (int i = 0; i < giaoVienRepository.count(); i++) {
                GiaoVien giaoVien = giaoVienRepository.findAll(PageRequest.of(i, 1)).getContent().get(0);
                GiaoVienDTO giaoVienDTO = new GiaoVienDTO(giaoVien);
                giaoVienDTO.setSLMonHocGiangDay(monHocGiaoVienRepository.countAllByGiaoVien(giaoVien));
                giaoVienTable.getItems().add(giaoVienDTO);
            }
        }
        giaoVienTable.refresh();
    }

    public void save(GiaoVien giaoVien) {
        if (giaoVien.getId() == 0) {
            giaoVienTable.getItems().add(new GiaoVienDTO(giaoVienRepository.save(giaoVien)));
            User user = new User(giaoVien.getMaGv(), bCryptPasswordEncoder.encode("123@123A"), true);
            usersRepository.save(user);
            authoritiesRepository.save(new Authorities(giaoVien.getMaGv(), "DSMH"));
        } else
            for (int i = 0; i < giaoVienTable.getItems().size(); i++)
                if (giaoVienTable.getItems().get(i).getId() == giaoVien.getId()) {
                    giaoVien = giaoVienRepository.save(giaoVien);
                    GiaoVienDTO giaoVienDTO = new GiaoVienDTO(giaoVien);
                    giaoVienDTO.setSLMonHocGiangDay(monHocGiaoVienRepository.countAllByGiaoVien(giaoVien));
                    giaoVienTable.getItems().set(i, giaoVienDTO);
                }
        giaoVienTable.refresh();
    }

    @FXML
    public void onTimKiemGV(ActionEvent actionEvent) {
        if (cbbNganh.getSelectionModel().getSelectedItem() == null) {
            List<String> stringList = new LinkedList<>();
            stringList.add("Vui lòng chọn Ngành trước");
            HelperUlti.showDialog(rootPane, "Lỗi", stringList);
            return;
        }
        Label label = cbbNganh.getSelectionModel().getSelectedItem();
        if (label.getId().equals("all")) setGiaoVienTable(null);
        else
            nganhs.stream().filter(nganh -> label.getId().equals(nganh.getMaNganh())).forEach(this::setGiaoVienTable);
    }

    @FXML
    public void onThemGv(ActionEvent actionEvent) {
        AddGiaoVienController addGiaoVienController =
                (AddGiaoVienController) showNewScene(context.getBean(AddGiaoVienController.class), "Thêm mới giáo viên");
        addGiaoVienController.setGiaovienController(this);
        addGiaoVienController.setGiaoVien(new GiaoVien());
        addGiaoVienController.setNganhs(nganhs);
    }

    public void updateSlMonHoc(String maGv, boolean add) {
        giaoVienTable.getItems().forEach(giaoVienDTO -> {
            if (giaoVienDTO.getMaGv().equals(maGv)) {
                if (add) giaoVienDTO.setSLMonHocGiangDay(giaoVienDTO.getSLMonHocGiangDay() + 1);
                else giaoVienDTO.setSLMonHocGiangDay(giaoVienDTO.getSLMonHocGiangDay() - 1);
            }
        });
    }


    @FXML
    public void handleEditOption(ActionEvent actionEvent) {
        GiaoVien giaoVien = giaoVienTable.getSelectionModel().getSelectedItem().getGiaoVien();
        AddGiaoVienController addGiaoVienController =
                (AddGiaoVienController) showNewScene(context.getBean(AddGiaoVienController.class), "Cập nhật giáo viên");
        addGiaoVienController.setGiaovienController(this);
        addGiaoVienController.setNganhs(nganhs);
        addGiaoVienController.setGiaoVien(giaoVien);
    }

    @FXML
    public void handleDeleteOption(ActionEvent actionEvent) {
        GiaoVienDTO giaoVienDTO = giaoVienTable.getSelectionModel().getSelectedItem();
        monHocGiaoVienRepository.deleteByGiaoVien(giaoVienDTO.getGiaoVien());
        giaoVienRepository.deleteById(giaoVienDTO.getId());
        giaoVienTable.getItems().remove(giaoVienDTO);
        giaoVienTable.refresh();
    }

    @FXML
    public void handleListMonHoc(ActionEvent actionEvent) {
        DanhSachMonHocController monHocController = (DanhSachMonHocController) showNewScene(context.getBean(DanhSachMonHocController.class), "Danh sách môn học giảng dạy");
        monHocController.setGiaoVienAndGiaovienController(giaoVienTable.getSelectionModel().getSelectedItem().getGiaoVien(), this);
    }
}
