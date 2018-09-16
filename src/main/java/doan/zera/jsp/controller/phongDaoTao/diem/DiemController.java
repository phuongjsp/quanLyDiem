package doan.zera.jsp.controller.phongDaoTao.diem;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import doan.zera.jsp.DTO.DiemDTO;
import doan.zera.jsp.controller.FXMLController;
import doan.zera.jsp.model.Kyhoc;
import doan.zera.jsp.model.MonHoc;
import doan.zera.jsp.model.ThoiKhoaBieu;
import doan.zera.jsp.repositories.*;
import doan.zera.jsp.util.FXMLname;
import doan.zera.jsp.util.HelperUlti;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.util.Stack;

@Component
@FXMLname(value = "phongDaoTao/diem/list", title = "Quản lý điểm",
        prefHeight = 768, prefWidth = 1376, fullScreen = true)
public class DiemController extends FXMLController implements Initializable {

    @FXML
    public TableView<DiemDTO> diemTable;
    @FXML
    public TableColumn<DiemDTO, String> maSv;
    @FXML
    public TableColumn<DiemDTO, String> hoTenSv;
    @FXML
    public TableColumn<DiemDTO, String> maKhoa;
    @FXML
    public TableColumn<DiemDTO, Double> diemChuyenCan;
    @FXML
    public TableColumn<DiemDTO, Double> diemKiemTra;
    @FXML
    public TableColumn<DiemDTO, Double> diemGiuaKy;
    @FXML
    public TableColumn<DiemDTO, String> diemChu;
    @FXML
    public TableColumn<DiemDTO, Double> TL;
    @FXML
    public TableColumn<DiemDTO, Double> diemThi;
    @FXML
    public TableColumn<DiemDTO, Double> tongDiem;
    @FXML
    public Label countDiem;
    @FXML
    public JFXComboBox<Label> cbbKyHoc;
    @FXML
    public JFXComboBox<Label> cbbMonHoc;
    @FXML
    public JFXTextField jfxFilterBySv;
    @FXML
    public TableColumn<DiemDTO, String> monHoc;
    @FXML
    public JFXTextField jfxFilterByGv;
    @FXML
    public TableColumn<DiemDTO, String> hoTenGV;
    @FXML
    public TableColumn<DiemDTO, String> maGV;
    private MonHoc monHocScope = new MonHoc();
    private Kyhoc kyhocScope = new Kyhoc();
    private DiemRepository diemRepository;
    private SinhVienRepository sinhVienRepository;
    private ThoiKhoaBieuRepository thoiKhoaBieuRepository;
    private KyhocRepository kyhocRepository;
    private MonHocRepository monHocRepository;
    private ApplicationContext context;

    public DiemController(DiemRepository diemRepository, SinhVienRepository sinhVienRepository, ThoiKhoaBieuRepository thoiKhoaBieuRepository, KyhocRepository kyhocRepository, MonHocRepository monHocRepository, ApplicationContext context) {
        this.diemRepository = diemRepository;
        this.sinhVienRepository = sinhVienRepository;
        this.thoiKhoaBieuRepository = thoiKhoaBieuRepository;
        this.kyhocRepository = kyhocRepository;
        this.monHocRepository = monHocRepository;
        this.context = context;
    }

    private void clearCbbMonHoc() {
        cbbMonHoc.getItems().clear();
    }

    private void clearKyHoc() {
        cbbKyHoc.getItems().clear();
        cbbKyHoc.getItems().add(HelperUlti.newLabel("all", "Tất cả"));
    }

    private void initClear() {
        clearKyHoc();
        clearCbbMonHoc();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        initClear();
        setCbbKyHoc();
        initFilter();
        //test

    }

    @Override
    public void onRefreshMain(ActionEvent actionEvent) {
        initClear();
        setCbbKyHoc();
        initFilter();
    }

    private void setDaTaDiemTable(int idKyhoc, int idMonHoc) {
        diemTable.getItems().clear();
        if (idKyhoc == 0) {
            if (idMonHoc == 0) {
                for (int i = 0; i < diemRepository.count(); i++)
                    diemRepository.findAll(PageRequest.of(i, 1)).forEach(diem -> diemTable.getItems().add(new DiemDTO(diem)));
            } else
                thoiKhoaBieuRepository.findAllByIdMonHoc(idMonHoc).forEach(tkb -> {
                    for (int i = 0; i < diemRepository.countAllByThoiKhoaBieu(tkb); i++) {
                        diemRepository.findAllByThoiKhoaBieu(tkb, PageRequest.of(i, 1)).forEach(diem ->
                                diemTable.getItems().add(new DiemDTO(diem)));
                    }
                });
        } else {
            List<ThoiKhoaBieu> thoiKhoaBieus = new Stack<>();
            if (idMonHoc == 0) {
                thoiKhoaBieus = thoiKhoaBieuRepository.findAllByIdKyhoc(idKyhoc);
            } else {
                thoiKhoaBieus = thoiKhoaBieuRepository.findAllByIdKyhocAndIdMonHoc(idKyhoc, idMonHoc);
            }
            thoiKhoaBieus.forEach(tkb -> {
                long countDiem = diemRepository.countAllByThoiKhoaBieu(tkb);
                for (int i = 0; i < countDiem; i++) {
                    diemRepository.findAllByThoiKhoaBieu(tkb, PageRequest.of(i, 1)).forEach(diem ->
                            diemTable.getItems().add(new DiemDTO(diem)));
                }
            });
        }

    }

    private void initCol() {
        maSv.setCellValueFactory(new PropertyValueFactory<>("maSV"));
        hoTenSv.setCellValueFactory(new PropertyValueFactory<>("hoTenSv"));
        maKhoa.setCellValueFactory(new PropertyValueFactory<>("maKhoa"));
        diemChuyenCan.setCellValueFactory(new PropertyValueFactory<>("chuyenCan"));
        diemKiemTra.setCellValueFactory(new PropertyValueFactory<>("diemKiemTra"));
        diemGiuaKy.setCellValueFactory(new PropertyValueFactory<>("diemGiuaKy"));
        diemThi.setCellValueFactory(new PropertyValueFactory<>("diemThi"));
        diemChu.setCellValueFactory(new PropertyValueFactory<>("diemChu"));
        tongDiem.setCellValueFactory(new PropertyValueFactory<>("tongDiem"));
        TL.setCellValueFactory(new PropertyValueFactory<>("TL"));
        monHoc.setCellValueFactory((new PropertyValueFactory<>("tenMonHoc")));
        hoTenGV.setCellValueFactory((new PropertyValueFactory<>("hoTenGV")));
        maGV.setCellValueFactory((new PropertyValueFactory<>("maGV")));
    }

    private void setCbbKyHoc() {
        clearKyHoc();
        kyhocRepository.findAll().forEach(kyhoc ->
                cbbKyHoc.getItems().add(HelperUlti.newLabel(kyhoc.getMaKyHoc(), kyhoc.getTenKyHoc())));
    }

    @FXML
    public void onActionCbbKyHoc(ActionEvent actionEvent) {
        if (cbbKyHoc.getSelectionModel().getSelectedItem() == null) return;
        if (cbbKyHoc.getSelectionModel().getSelectedItem().getId().equals("all"))
            kyhocScope = new Kyhoc();
        else
            kyhocScope = kyhocRepository.findByMaKyHoc(cbbKyHoc.getSelectionModel().getSelectedItem().getId());
        setCbbMonHoc(getKyhocScope());
    }

    private void setCbbMonHoc(Kyhoc kyhoc) {
        clearCbbMonHoc();
        if (kyhoc.getId() == 0)
            thoiKhoaBieuRepository.findAllMonHoc().forEach(monHoc ->
                cbbMonHoc.getItems().add(HelperUlti.newLabel(monHoc.getMaMonHoc(),
                        monHoc.getTenMonHoc())));
        else
            thoiKhoaBieuRepository.findAllIdMonHoc(kyhoc)
                    .forEach(monHoc ->
                            cbbMonHoc.getItems().add(HelperUlti.newLabel(monHoc.getMaMonHoc(), monHoc.getTenMonHoc())));
    }

    @FXML
    public void onActionCbbMonHoc(ActionEvent actionEvent) {
        if (cbbMonHoc.getSelectionModel().getSelectedItem() == null) return;

        monHocScope = monHocRepository.findByMaMonHoc(cbbMonHoc.getSelectionModel().getSelectedItem().getId());
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

    @FXML
    public void onFliter(ActionEvent actionEvent) {
        diemTable.setItems(FXCollections.observableArrayList());
        setDaTaDiemTable(getKyhocScope().getId(), getMonHocScope().getId());
    }

    private void initFilter() {
        jfxFilterBySv.setOnKeyReleased(event -> {
            FilteredList<DiemDTO> filteredList = new FilteredList<>(diemTable.getItems(), p -> true);
            jfxFilterBySv.textProperty().addListener((observable, oldValue, newValue) ->
                    filteredList.setPredicate(diemDTO -> {
                        if (newValue == null || newValue.isEmpty()) return true;
                        String lowerCaseFilter = newValue.toLowerCase();
                        if (diemDTO.getMaSV().toLowerCase().contains(lowerCaseFilter)) return true;
                        else if (diemDTO.getHoTenSv().toLowerCase().contains(lowerCaseFilter)) return true;
                        return false;
                    }));
            SortedList<DiemDTO> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(diemTable.comparatorProperty());
            diemTable.setItems(sortedList);
        });
        jfxFilterByGv.setOnKeyReleased(event -> {
            FilteredList<DiemDTO> filteredGV = new FilteredList<>(diemTable.getItems(), p -> true);
            jfxFilterByGv.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredGV.setPredicate(diemDTO -> {
                    if (newValue == null || newValue.isEmpty()) return true;
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (diemDTO.getMaGV().toLowerCase().contains(lowerCaseFilter)) return true;
                    else if (diemDTO.getHoTenGV().toLowerCase().contains(lowerCaseFilter)) return true;
                    return false;
                });
            });
            SortedList<DiemDTO> sortedList = new SortedList<>(filteredGV);
            sortedList.comparatorProperty().bind(diemTable.comparatorProperty());
            diemTable.setItems(sortedList);
        });
    }

    @FXML
    public void onThongKe(ActionEvent actionEvent) {
        showNewScene(context.getBean(ThongKeDiemController.class), "Thống kê");
    }
}
