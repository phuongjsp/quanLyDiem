package doan.zera.jsp.controller.giaoVien;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import doan.zera.jsp.DTO.DiemDTO;
import doan.zera.jsp.controller.FXMLController;
import doan.zera.jsp.model.*;
import doan.zera.jsp.repositories.*;
import doan.zera.jsp.util.FXMLname;
import doan.zera.jsp.util.HelperUlti;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.util.converter.DoubleStringConverter;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

@Component
@FXMLname(value = "giaoVien/danhSachMonHocQuanLy", title = "Quản lý uyền hạn của User",
        prefWidth = 1080)
public class DanhSachMonHocQuanLyController extends FXMLController implements Initializable {
    @FXML
    public TableView<DiemDTO> diemTable;
    @FXML
    public TableColumn<DiemDTO, String> maSv;
    @FXML
    public TableColumn<DiemDTO, String> hoTenSv;
    @FXML
    public TableColumn<DiemDTO, Double> diemChuyenCan;
    @FXML
    public TableColumn<DiemDTO, Double> diemKiemTra;
    @FXML
    public TableColumn<DiemDTO, Double> diemGiuaKy;
    @FXML
    public TableColumn<DiemDTO, String> diemChu;
    @FXML
    public TableColumn<DiemDTO, Double> diemThi;
    @FXML
    public TableColumn<DiemDTO, Double> tongDiem;
    @FXML
    public JFXComboBox<Label> cbbKyHoc;
    @FXML
    public JFXComboBox<Label> cbbMonHoc;
    @FXML
    public StackPane rootPane;
    @FXML
    public JFXTextField jfxTFdcc;
    @FXML
    public JFXTextField jfxTFdkt;
    @FXML
    public JFXTextField jfxTFdt;
    private MonHoc monHocScope = new MonHoc();

    private DiemRepository diemRepository;
    private ThoiKhoaBieuRepository thoiKhoaBieuRepository;
    private KyhocRepository kyhocRepository;
    private GiaoVienRepository giaoVienRepository;
    private UsersRepository usersRepository;
    private GiaoVien giaoVienScope;
    private MonHocRepository monHocRepository;
    private Kyhoc kyhocScope = new Kyhoc();
    private List<DiemDTO> diemDTOS = new Stack<>();
    private ObservableList<DiemDTO> observableList = FXCollections.observableList(diemDTOS);
    private DiemDTO diemDTOScope;

    public DanhSachMonHocQuanLyController(DiemRepository diemRepository, ThoiKhoaBieuRepository thoiKhoaBieuRepository, KyhocRepository kyhocRepository, GiaoVienRepository giaoVienRepository, UsersRepository usersRepository, MonHocRepository monHocRepository) {
        this.diemRepository = diemRepository;
        this.thoiKhoaBieuRepository = thoiKhoaBieuRepository;
        this.kyhocRepository = kyhocRepository;
        this.giaoVienRepository = giaoVienRepository;
        this.usersRepository = usersRepository;
        this.monHocRepository = monHocRepository;
    }


    private void getGiaoVien() {
        giaoVienScope = new GiaoVien();
        User user = usersRepository.findUsersByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        giaoVienScope = giaoVienRepository.findByUserId(user.getId());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        newData();
    }

    @Override
    public void onRefreshMain(ActionEvent actionEvent) {
        newData();
    }

    public void newData() {
        initClear();
        getGiaoVien();
        setCbbKyHoc();

    }

    private void setCbbKyHoc() {
        cbbKyHoc.getItems().clear();
        kyhocRepository.findAll().forEach(kyhoc ->
                cbbKyHoc.getItems().add(HelperUlti.newLabel(kyhoc.getMaKyHoc(), kyhoc.getTenKyHoc())));
    }

    private void initClear() {
        cbbKyHoc.getItems().clear();
        cbbMonHoc.getItems().clear();
    }

    private void setDaTaDiemTable(int idKyhoc, int idMonHoc) {
        List<ThoiKhoaBieu> thoiKhoaBieus = thoiKhoaBieuRepository.findAllByKyhocAndGiaoVienAndMonHoc(idKyhoc, giaoVienScope.getId(), idMonHoc);
        thoiKhoaBieus.forEach(tkb -> {

            long countDiem = diemRepository.countAllByThoiKhoaBieu(tkb);
            for (int i = 0; i < countDiem; i++) {
                diemRepository.findAllByThoiKhoaBieu(tkb, PageRequest.of(i, 1)).forEach(diem ->
                        observableList.add(new DiemDTO(diem)));
            }
        });
        diemTable.setItems(observableList);
        diemTable.refresh();
    }

    private void initCol() {
        maSv.setCellValueFactory(new PropertyValueFactory<>("maSV"));
        hoTenSv.setCellValueFactory(new PropertyValueFactory<>("hoTenSv"));
        diemChuyenCan.setCellValueFactory(new PropertyValueFactory<>("chuyenCan"));
        diemKiemTra.setCellValueFactory(new PropertyValueFactory<>("diemKiemTra"));
        diemGiuaKy.setCellValueFactory(new PropertyValueFactory<>("diemGiuaKy"));
        diemThi.setCellValueFactory(new PropertyValueFactory<>("diemThi"));
        diemChu.setCellValueFactory(new PropertyValueFactory<>("diemChu"));
        tongDiem.setCellValueFactory(new PropertyValueFactory<>("tongDiem"));
        jfxTFdcc.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
        jfxTFdkt.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
        jfxTFdt.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
    }

    @FXML
    public void onFliter(ActionEvent actionEvent) {
        List<String> error = new Stack<>();
        if (getKyhocScope().getId() == 0) error.add("Học kỳ");
        if (getMonHocScope().getId() == 0) error.add("Môn học");
        if (!error.isEmpty()) {
            HelperUlti.showDialog(rootPane, "Bạn chưa chọn", error);
            return;
        }
        diemTable.setItems(FXCollections.observableArrayList());
        setDaTaDiemTable(getKyhocScope().getId(), getMonHocScope().getId());
    }

    @FXML
    public void onActionCbbKyHoc(ActionEvent actionEvent) {
        if (cbbKyHoc.getSelectionModel().getSelectedItem() == null) return;
        kyhocScope = kyhocRepository.findByMaKyHoc(cbbKyHoc.getSelectionModel().getSelectedItem().getId());
        setCbbMonHoc(getKyhocScope());
    }

    public MonHoc getMonHocScope() {
        if (monHocScope == null)
            monHocScope = new MonHoc();
        return monHocScope;
    }

    public Kyhoc getKyhocScope() {
        if (kyhocScope == null)
            kyhocScope = new Kyhoc();
        return kyhocScope;
    }

    private void setCbbMonHoc(Kyhoc kyhoc) {

        cbbMonHoc.getItems().clear();
        List<Integer> idMonHoc = thoiKhoaBieuRepository.findAllIdByKyhocAndIdGiaoVien(kyhoc.getId(), giaoVienScope.getId());
        idMonHoc.forEach(integer -> {
            MonHoc monHoc = monHocRepository.findById(integer).get();
            cbbMonHoc.getItems().add(HelperUlti.newLabel(monHoc.getMaMonHoc(), monHoc.getTenMonHoc()));
        });
    }

    @FXML
    public void onActionCbbMonHoc(ActionEvent actionEvent) {
        if (cbbMonHoc.getSelectionModel().getSelectedItem() == null) return;
        monHocScope = monHocRepository.findByMaMonHoc(cbbMonHoc.getSelectionModel().getSelectedItem().getId());
    }

    @FXML
    public void onUpdatePoint(ActionEvent actionEvent) {
        diemDTOScope = diemTable.getSelectionModel().getSelectedItem();
        jfxTFdcc.setText(diemDTOScope.getChuyenCan() + "");
        jfxTFdkt.setText(diemDTOScope.getDiemKiemTra() + "");
        jfxTFdt.setText(diemDTOScope.getDiemThi() + "");
    }

    @FXML
    public void onSave(ActionEvent actionEvent) {
        if (jfxTFdcc.getText().isEmpty() || jfxTFdkt.getText().isEmpty() || jfxTFdcc.getText().isEmpty())
            return;
        diemDTOScope.setDiem(diemRepository.save(diemDTOScope.getDiem()));
        observableList.forEach(dto -> {
            if (diemDTOScope.getId() == dto.getId()) {
                dto.setChuyenCan(Double.parseDouble(jfxTFdcc.getText()));
                dto.setDiemKiemTra(Double.parseDouble(jfxTFdkt.getText()));
                dto.setDiemThi(Double.parseDouble(jfxTFdt.getText()));
                dto.setDiem(diemRepository.save(dto.getDiem()));
            }
        });
        diemTable.refresh();
        jfxTFdcc.setText("");
        jfxTFdkt.setText("");
        jfxTFdt.setText("");
        diemDTOScope = new DiemDTO();
    }
}