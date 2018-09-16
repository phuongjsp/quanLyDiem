package doan.zera.jsp.controller.phongDaoTao.thoiKhoaBieu;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import doan.zera.jsp.DTO.SinhVienDTO;
import doan.zera.jsp.DTO.ThoiKhoaBieuDTO;
import doan.zera.jsp.controller.FXMLController;
import doan.zera.jsp.model.Diem;
import doan.zera.jsp.model.KhoaHoc;
import doan.zera.jsp.model.SinhVien;
import doan.zera.jsp.repositories.DiemRepository;
import doan.zera.jsp.repositories.KhoaHocRepository;
import doan.zera.jsp.repositories.SinhVienRepository;
import doan.zera.jsp.util.FXMLname;
import doan.zera.jsp.util.HelperUlti;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

@Component
@FXMLname(value = "phongDaoTao/thoiKhoaBieu/sinhVienDangKyHocPhan", title = "Cập nhật danh sách sinh viên đăng ký học phần")
public class SinhVienDangKyHocPhanController extends FXMLController implements Initializable {

    @FXML
    public TableColumn<SinhVienDTO, String> maSv;
    @FXML
    public TableColumn<SinhVienDTO, Integer> ho;
    @FXML
    public TableColumn<SinhVienDTO, String> ten;
    @FXML
    public TableColumn<SinhVienDTO, String> khoaHoc;
    @FXML
    public TableView<SinhVienDTO> svDkHpTable;

    @FXML
    public TableView<SinhVienDTO> svNotInTable;
    @FXML
    public TableColumn<SinhVienDTO, String> maSvN;
    @FXML
    public TableColumn<SinhVienDTO, String> hoSvN;
    @FXML
    public TableColumn<SinhVienDTO, String> tenSvN;
    @FXML
    public TableColumn<SinhVienDTO, String> khoaHocN;
    @FXML
    public JFXComboBox<Label> cbbKhoaHoc;
    @FXML
    public JFXTextField jfxFilterBySv;
    @Autowired
    SinhVienRepository sinhVienRepository;
    List<Integer> listIdSv = new Stack<>();
    private List<SinhVienDTO> sinhVienDTOSIn = new Stack<>();
    private ObservableList<SinhVienDTO> sinhvienInList = FXCollections.observableList(sinhVienDTOSIn);
    private List<SinhVienDTO> sinhVienDTOSNotIn = new Stack<>();
    private ObservableList<SinhVienDTO> sinhvienNotInList = FXCollections.observableList(sinhVienDTOSNotIn);
    private ThoiKhoaBieuDTO tkbDTO;
    private ThoiKhoaBieuController thoiKhoaBieuController;
    @Autowired
    private KhoaHocRepository khoaHocRepository;
    @Autowired
    private DiemRepository diemRepository;

    private void initColNotIn() {
        maSvN.setCellValueFactory(new PropertyValueFactory<>("maSv"));
        hoSvN.setCellValueFactory(new PropertyValueFactory<>("ho"));
        tenSvN.setCellValueFactory(new PropertyValueFactory<>("ten"));
        khoaHocN.setCellValueFactory(new PropertyValueFactory<>("maKhoaHoc"));
    }

    public void setTKBDTO(ThoiKhoaBieuController thoiKhoaBieuController, ThoiKhoaBieuDTO tkbDTO) {
        this.tkbDTO = tkbDTO;
        this.thoiKhoaBieuController = thoiKhoaBieuController;
        setData(tkbDTO.getId());
        initColNotIn();
        initKhoaHoc();
        cbbKhoaHoc.getItems().add(HelperUlti.newLabel("all", "Tất cả"));
        jfxFilterBySv.setOnKeyReleased(this::onFilterBySv);
        svNotInTable.setItems(sinhvienNotInList);
        svDkHpTable.setItems(sinhvienInList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();

    }

    private void initCol() {
        ho.setCellValueFactory(new PropertyValueFactory<>("ho"));
        maSv.setCellValueFactory(new PropertyValueFactory<>("maSv"));
        ten.setCellValueFactory(new PropertyValueFactory<>("ten"));
        khoaHoc.setCellValueFactory(new PropertyValueFactory<>("maKhoaHoc"));
    }

    private void setData(int idThoiKhoaBieu) {
        sinhvienInList.clear();
        for (Integer idSv : diemRepository.findAllSinhVienByIdTKB(idThoiKhoaBieu)) {
            sinhvienInList.add(new SinhVienDTO(sinhVienRepository.findById(idSv).get()));
            listIdSv.add(idSv);
        }
        refreshTable();
    }

    private void initKhoaHoc() {
        khoaHocRepository.findAll().forEach(kh ->
                cbbKhoaHoc.getItems().add(HelperUlti.newLabel(kh.getMaKhoa(), kh.getTenKhoa())));
    }

    @FXML
    public void handleRefresh(ActionEvent actionEvent) {
        refreshTable();
    }

    @FXML
    public void handleDeleteOption(ActionEvent actionEvent) {
        SinhVienDTO sinhVienDTO = svDkHpTable.getSelectionModel().getSelectedItem();
        Diem diem = diemRepository.findByIdSvAndIdTKB(sinhVienDTO.getId(), tkbDTO.getId());
        diemRepository.delete(diem);
        sinhvienNotInList.add(sinhVienDTO);
        sinhvienInList.remove(sinhVienDTO);
        Iterator<Integer> integers = listIdSv.iterator();
        while (integers.hasNext()) if (integers.next() == sinhVienDTO.getId()) integers.remove();
        reSetDataBothTable();
        updateSLSVinTKBctr(false);
        refreshTable();
    }

    private void reSetDataBothTable() {
        svDkHpTable.setItems(sinhvienInList);
        svNotInTable.setItems(sinhvienNotInList);
        jfxFilterBySv.setText("");
    }

    @FXML
    public void handleAddOption(ActionEvent actionEvent) {
        Diem diem = new Diem();
        SinhVienDTO sinhVienDTO = svNotInTable.getSelectionModel().getSelectedItem();
        diem.setSinhVien(sinhVienDTO.getSinhVien());
        diem.setThoiKhoaBieu(tkbDTO.getThoiKhoaBieu());
        diemRepository.save(diem);
        sinhvienInList.add(sinhVienDTO);
        sinhvienNotInList.remove(sinhVienDTO);
        listIdSv.add(sinhVienDTO.getId());
        reSetDataBothTable();
        updateSLSVinTKBctr(true);
        refreshTable();
    }

    private void refreshTable() {
        svNotInTable.setItems(sinhvienNotInList);
        svDkHpTable.setItems(sinhvienInList);
        svDkHpTable.refresh();
        svNotInTable.refresh();

    }

    private void updateSLSVinTKBctr(boolean update) {
        thoiKhoaBieuController.updateSLSVDangKyHocPhan(tkbDTO.getId(), update);
    }

    @FXML
    public void onSetCbbKhoaHoc(ActionEvent actionEvent) {
        if (cbbKhoaHoc.getSelectionModel().getSelectedItem() == null) return;
        sinhvienNotInList.clear();
        if (cbbKhoaHoc.getSelectionModel().getSelectedItem().getId().equals("all")) {
            if (listIdSv.isEmpty())
                sinhVienRepository.findAllByNganh(tkbDTO.getMonHoc().getNganh()).forEach(this::addSinhVienNotInList);
            else
                sinhVienRepository.findAllByIdNotInAndNganh(listIdSv, tkbDTO.getMonHoc().getNganh()).forEach(this::addSinhVienNotInList);
        } else {
            KhoaHoc khoaHoc = khoaHocRepository.findByMaKhoa(cbbKhoaHoc.getSelectionModel().getSelectedItem().getId());
            if (listIdSv.isEmpty())
                sinhVienRepository.findAllByKhoaHocAndNganh(khoaHoc, tkbDTO.getMonHoc().getNganh()).forEach(this::addSinhVienNotInList);
            else
                sinhVienRepository.findAllByIdNotInAndKhoaHocAndNganh(listIdSv, khoaHoc, tkbDTO.getMonHoc().getNganh()).forEach(this::addSinhVienNotInList);
        }
        svNotInTable.refresh();
    }

    private void addSinhVienNotInList(SinhVien sinhVien) {
        sinhvienNotInList.add(new SinhVienDTO(sinhVien));
    }

    public void onFilterBySv(KeyEvent keyEvent) {
        FilteredList<SinhVienDTO> filteredSV = new FilteredList<>(svNotInTable.getItems(), p -> true);
        jfxFilterBySv.textProperty().addListener((observable, oldValue, newValue) -> filteredSV.setPredicate(sinhVienDTO -> {
            if (newValue == null || newValue.isEmpty()) return true;
            String lowerCaseFilter = newValue.toLowerCase();
            if (sinhVienDTO.getMaSv().toLowerCase().contains(lowerCaseFilter)) return true;
            if (sinhVienDTO.getHo().toLowerCase().contains(lowerCaseFilter)) return true;
            if (sinhVienDTO.getTen().toLowerCase().contains(lowerCaseFilter)) return true;
            return false;
        }));
        SortedList<SinhVienDTO> sortedList = new SortedList<>(filteredSV);
        sortedList.comparatorProperty().bind(svNotInTable.comparatorProperty());
        svNotInTable.setItems(sortedList);
    }
}

