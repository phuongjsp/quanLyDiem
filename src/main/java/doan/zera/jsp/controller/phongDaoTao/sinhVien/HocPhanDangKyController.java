package doan.zera.jsp.controller.phongDaoTao.sinhVien;

import doan.zera.jsp.DTO.ThoiKhoaBieuDTO;
import doan.zera.jsp.controller.FXMLController;
import doan.zera.jsp.model.Diem;
import doan.zera.jsp.model.Kyhoc;
import doan.zera.jsp.model.SinhVien;
import doan.zera.jsp.model.ThoiKhoaBieu;
import doan.zera.jsp.repositories.DiemRepository;
import doan.zera.jsp.repositories.ThoiKhoaBieuRepository;
import doan.zera.jsp.util.FXMLname;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

@Component
@FXMLname(value = "phongDaoTao/sinhVien/hocPhanDangKy", title = "Đang ký học phần cho sinh viên")
public class HocPhanDangKyController extends FXMLController implements Initializable {
    @FXML
    public TableView<ThoiKhoaBieuDTO> monHocChuaDangKy;
    @FXML
    public TableColumn<ThoiKhoaBieuDTO, String> tenMhN;
    @FXML
    public TableColumn<ThoiKhoaBieuDTO, String> hoTenGiaoVienN;
    @FXML
    public TableColumn<ThoiKhoaBieuDTO, String> tinChiN;
    @FXML
    public TableColumn<ThoiKhoaBieuDTO, String> phongHocN;
    @FXML
    public TableColumn<ThoiKhoaBieuDTO, String> thuN;
    @FXML
    public TableColumn<ThoiKhoaBieuDTO, String> tietN;
    @FXML
    public TableView<ThoiKhoaBieuDTO> monHocDaDangKy;
    @FXML
    public TableColumn<ThoiKhoaBieuDTO, String> tenMh;
    @FXML
    public TableColumn<ThoiKhoaBieuDTO, String> hoTenGiaoVien;
    @FXML
    public TableColumn<ThoiKhoaBieuDTO, String> tinChi;
    @FXML
    public TableColumn<ThoiKhoaBieuDTO, String> phongHoc;
    @FXML
    public TableColumn<ThoiKhoaBieuDTO, String> thu;
    @FXML
    public TableColumn<ThoiKhoaBieuDTO, String> tiet;
    @FXML
    public Label kyhocScope;

    private SinhVien sinhVien;
    private Kyhoc kyhoc;
    private List<ThoiKhoaBieuDTO> dtosDDK = new Stack<>();
    private List<ThoiKhoaBieuDTO> dtosCDK = new Stack<>();
    private ObservableList<ThoiKhoaBieuDTO> observableDaDangKyList = FXCollections.observableList(dtosDDK);
    private ObservableList<ThoiKhoaBieuDTO> observableChuaDangKyList = FXCollections.observableList(dtosCDK);
    @Autowired
    private ThoiKhoaBieuRepository thoiKhoaBieuRepository;
    @Autowired
    private DiemRepository diemRepository;
    private List<Integer> idTKBDaDangKys = new Stack<>();
    private SinhVienController sinhVienController;

    public void setSinhVienAndKyhoc(SinhVienController sinhVienController,
                                    SinhVien sinhVien, Kyhoc kyhoc) {
        this.sinhVienController = sinhVienController;
        this.sinhVien = sinhVien;
        this.kyhoc = kyhoc;
        setDataMonHocDaDangKyTable();
        setDataMonHocChuaDangKy();
        monHocDaDangKy.setItems(observableDaDangKyList);
        monHocChuaDangKy.setItems(observableChuaDangKyList);
        initCol();
        initColN();
        kyhocScope.setText(kyhoc.getTenKyHoc());
    }

    private void initCol() {
        setInitCol(tenMh, hoTenGiaoVien, tinChi, phongHoc, thu, tiet);
    }

    private void initColN() {
        setInitCol(tenMhN, hoTenGiaoVienN, tinChiN, phongHocN, thuN, tietN);
    }

    private void setInitCol(TableColumn<ThoiKhoaBieuDTO, String> tenMhN, TableColumn<ThoiKhoaBieuDTO, String> hoTenGiaoVienN, TableColumn<ThoiKhoaBieuDTO, String> tinChiN, TableColumn<ThoiKhoaBieuDTO, String> phongHocN, TableColumn<ThoiKhoaBieuDTO, String> thuN, TableColumn<ThoiKhoaBieuDTO, String> tietN) {
        tenMhN.setCellValueFactory(new PropertyValueFactory<>("tenMonHoc"));
        hoTenGiaoVienN.setCellValueFactory(new PropertyValueFactory<>("tenGiaoVien"));
        tinChiN.setCellValueFactory(new PropertyValueFactory<>("tinChi"));
        phongHocN.setCellValueFactory(new PropertyValueFactory<>("phongHoc"));
        thuN.setCellValueFactory(new PropertyValueFactory<>("thuTrongTuan"));
        tietN.setCellValueFactory(new PropertyValueFactory<>("tietDay"));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void setDataMonHocDaDangKyTable() {
        observableDaDangKyList.clear();
        diemRepository.findAllIdThoiKhoaBieuByIdKyHocAndIdSinhVien(kyhoc.getId(), sinhVien.getId()).forEach(integer -> {
            ThoiKhoaBieu thoiKhoaBieu = thoiKhoaBieuRepository.findById(integer).get();
            observableDaDangKyList.add(new ThoiKhoaBieuDTO(0, thoiKhoaBieu));
            idTKBDaDangKys.add(integer);
        });
        refeshBothTable();
    }

    private void setDataMonHocChuaDangKy() {
        observableChuaDangKyList.clear();
        if (idTKBDaDangKys.isEmpty())
            diemRepository.findAllIdThoiKhoaBieuByIdKyHocAndIdSinhVienNotInAndNganh(kyhoc.getId(), sinhVien.getNganh().getId()).forEach(integer -> {
                ThoiKhoaBieu thoiKhoaBieu = thoiKhoaBieuRepository.findById(integer).get();
                observableChuaDangKyList.add(new ThoiKhoaBieuDTO(0, thoiKhoaBieu));
            });
        else
            diemRepository.findAllIdThoiKhoaBieuByIdKyHocAndIdSinhVienNotInAndNganh(kyhoc.getId(), sinhVien.getNganh().getId(), idTKBDaDangKys).forEach(integer -> {
                ThoiKhoaBieu thoiKhoaBieu = thoiKhoaBieuRepository.findById(integer).get();
                observableChuaDangKyList.add(new ThoiKhoaBieuDTO(0, thoiKhoaBieu));
            });
    }

    private void refeshBothTable() {
        monHocDaDangKy.refresh();
        monHocChuaDangKy.refresh();

    }

    private void updateSlMonHoc() {
        int slTin = 0;
        for (ThoiKhoaBieuDTO dto : monHocDaDangKy.getItems()) {
            slTin += dto.getTinChi();
        }
        sinhVienController.setSlMonHhoc(sinhVien.getId(), monHocDaDangKy.getItems().size(), slTin);
    }

    @FXML
    public void handleDeleteOption(ActionEvent actionEvent) {
        ThoiKhoaBieuDTO thoiKhoaBieuDTO = monHocDaDangKy.getSelectionModel().getSelectedItem();
        Diem diem = diemRepository.findByIdSvAndIdTKB(sinhVien.getId(), thoiKhoaBieuDTO.getId());
        diemRepository.delete(diem);
        observableDaDangKyList.remove(thoiKhoaBieuDTO);
        observableChuaDangKyList.add(thoiKhoaBieuDTO);
        refeshBothTable();
        updateSlMonHoc();
    }

    @FXML
    public void handleAddOption(ActionEvent actionEvent) {
        ThoiKhoaBieuDTO thoiKhoaBieuDTO = monHocChuaDangKy.getSelectionModel().getSelectedItem();
        Diem diem = new Diem();
        diem.setSinhVien(sinhVien);
        diem.setThoiKhoaBieu(thoiKhoaBieuDTO.getThoiKhoaBieu());
        diemRepository.save(diem);
        observableChuaDangKyList.remove(thoiKhoaBieuDTO);
        observableDaDangKyList.add(thoiKhoaBieuDTO);
        refeshBothTable();
        updateSlMonHoc();
    }
}
