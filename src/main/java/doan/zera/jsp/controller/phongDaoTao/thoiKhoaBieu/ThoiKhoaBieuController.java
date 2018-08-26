package doan.zera.jsp.controller.phongDaoTao.thoiKhoaBieu;

import com.jfoenix.controls.JFXComboBox;
import doan.zera.jsp.DTO.ThoiKhoaBieuDTO;
import doan.zera.jsp.controller.FXMLController;
import doan.zera.jsp.model.Kyhoc;
import doan.zera.jsp.model.ThoiKhoaBieu;
import doan.zera.jsp.repositories.DiemRepository;
import doan.zera.jsp.repositories.KyhocRepository;
import doan.zera.jsp.repositories.ThoiKhoaBieuRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

@Component
@FXMLname(value = "phongDaoTao/thoiKhoaBieu/list", title = "Quản lý thời khóa biểu")
public class ThoiKhoaBieuController extends FXMLController implements Initializable {
    @FXML
    public TableView<ThoiKhoaBieuDTO> thoiKhoaBieuTable;
    @FXML
    public TableColumn<ThoiKhoaBieuDTO, String> tenMonHoc;
    @FXML
    public TableColumn<ThoiKhoaBieuDTO, String> heSoTinChi;
    @FXML
    public TableColumn<ThoiKhoaBieuDTO, String> tenNganh;
    @FXML
    public TableColumn<ThoiKhoaBieuDTO, String> phongHoc;
    @FXML
    public TableColumn<ThoiKhoaBieuDTO, String> tiet;
    @FXML
    public TableColumn<ThoiKhoaBieuDTO, String> tenGiaoVien;
    @FXML
    public TableColumn<ThoiKhoaBieuDTO, String> ngayBatDauString;
    @FXML
    public TableColumn<ThoiKhoaBieuDTO, String> ngayKetThucString;
    @FXML
    public JFXComboBox<Label> cbbKyHoc;
    @FXML
    public TableColumn<ThoiKhoaBieuDTO, String> thuTrongTuan;
    @FXML
    public StackPane rootPane;
    public TableColumn<ThoiKhoaBieuDTO, Integer> soLuongSV;
    private List<ThoiKhoaBieuDTO> thoiKhoaBieus;
    @Autowired
    private KyhocRepository kyhocRepository;
    @Autowired
    private ThoiKhoaBieuRepository thoiKhoaBieuRepository;
    @Autowired
    private DiemRepository diemRepository;
    @Autowired
    private ApplicationContext context;
    private Kyhoc kyhocScope;

    void initCol() {
        tenMonHoc.setCellValueFactory(new PropertyValueFactory<>("tenMonHoc"));
        heSoTinChi.setCellValueFactory(new PropertyValueFactory<>("tinChi"));
        tenNganh.setCellValueFactory(new PropertyValueFactory<>("tenNganh"));
        phongHoc.setCellValueFactory(new PropertyValueFactory<>("phongHoc"));
        tiet.setCellValueFactory(new PropertyValueFactory<>("tietDay"));
        tenGiaoVien.setCellValueFactory(new PropertyValueFactory<>("tenGiaoVien"));
        ngayBatDauString.setCellValueFactory(new PropertyValueFactory<>("ngayBatDauString"));
        ngayKetThucString.setCellValueFactory(new PropertyValueFactory<>("ngayKetThucString"));
        thuTrongTuan.setCellValueFactory(new PropertyValueFactory<>("thuTrongTuan"));
        soLuongSV.setCellValueFactory(new PropertyValueFactory<>("soLuongSV"));
    }

    public void setDataKyHoc() {
        cbbKyHoc.getItems().clear();
        kyhocRepository.findAll(new Sort(Sort.Direction.DESC, "maKyHoc")).forEach(kyhoc ->
                cbbKyHoc.getItems().add(HelperUlti.newLabel(kyhoc.getMaKyHoc(), kyhoc.getTenKyHoc())));
    }


    private void setDataTKBTable(Kyhoc kyhoc) {
        if (thoiKhoaBieus != null) thoiKhoaBieus.clear();
        else thoiKhoaBieus = new Stack<>();
        thoiKhoaBieuTable.getItems().clear();
        for (int i = 0; i < thoiKhoaBieuRepository.countAllByKyhoc(kyhoc); i++) {
            ThoiKhoaBieu tkb = thoiKhoaBieuRepository
                    .findAllByKyhoc(kyhoc, PageRequest.of(i, 1)).get(0);
            ThoiKhoaBieuDTO thoiKhoaBieuDTO = new ThoiKhoaBieuDTO(
                    diemRepository.findAllSinhVienByIdTKB(tkb.getId()).size(),
                    tkb
            );
            thoiKhoaBieuTable.getItems().add(thoiKhoaBieuDTO);
            thoiKhoaBieus.add(thoiKhoaBieuDTO);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        setDataKyHoc();
    }

    @Override
    public void onRefreshMain(ActionEvent actionEvent) {
        setDataKyHoc();
    }

    public void saveTKB(ThoiKhoaBieu tkb) {
        if (tkb.getId() == 0) {
            tkb = thoiKhoaBieuRepository.save(tkb);
            thoiKhoaBieuTable.getItems().add(new ThoiKhoaBieuDTO(0, tkb));
        } else {
            tkb = thoiKhoaBieuRepository.save(tkb);
            for (int i = 0; i < thoiKhoaBieuTable.getItems().size(); i++) {
                if (thoiKhoaBieuTable.getItems().get(i).getId() == tkb.getId())
                    thoiKhoaBieuTable.getItems().set(i, new ThoiKhoaBieuDTO(
                            diemRepository.findAllSinhVienByIdTKB(tkb.getId()).size(), tkb));
            }
        }
        thoiKhoaBieuTable.refresh();
    }

    @FXML
    public void onTimKiemByKyHoc(ActionEvent actionEvent) {
        if (cbbKyHocIsSelect()) {
            kyhocScope = kyhocRepository.
                    findByMaKyHoc(cbbKyHoc.getSelectionModel().
                            getSelectedItem().getId());
            setDataTKBTable(kyhocScope);
        }

    }

    private boolean cbbKyHocIsSelect() {
        if (cbbKyHoc.getSelectionModel().
                getSelectedItem() == null) {
            List<String> dialog = new ArrayList<>();
            dialog.add("Bạn phải chọn kỳ học trước");
            HelperUlti.showDialog(rootPane, "Lỗi", dialog);
            return false;
        }
        return true;
    }

    @FXML
    public void onThemThoiKhoaBieu(ActionEvent actionEvent) {
        if (!cbbKyHocIsSelect()) return;
        AddThoiKhoaBieuController addThoiKhoaBieuController =
                (AddThoiKhoaBieuController) showNewScene(context.getBean(AddThoiKhoaBieuController.class),
                        "Thêm mới TKB");
        addThoiKhoaBieuController.setThoiKhoaBieuController(this, kyhocRepository.
                findByMaKyHoc(cbbKyHoc.getSelectionModel().
                        getSelectedItem().getId()), this.thoiKhoaBieus);
        addThoiKhoaBieuController.setThoiKhoaBieuDTO(new ThoiKhoaBieuDTO());
    }

//    @FXML
//    public void handleRefresh(ActionEvent actionEvent) {
//        if (kyhocScope == null) return;
//        thoiKhoaBieuTable.getItems().clear();
//        setDataTKBTable(kyhocScope);
//        thoiKhoaBieuTable.refresh();
//    }

    @FXML
    public void handleEditOption(ActionEvent actionEvent) {
        ThoiKhoaBieuDTO thoiKhoaBieuDTO = thoiKhoaBieuTable.getSelectionModel().getSelectedItem();
        AddThoiKhoaBieuController addThoiKhoaBieuController = (AddThoiKhoaBieuController) showNewScene(context.getBean(AddThoiKhoaBieuController.class),
                "Cập nhật TKB ");
        addThoiKhoaBieuController.setThoiKhoaBieuController(this, kyhocRepository.
                findByMaKyHoc(cbbKyHoc.getSelectionModel().
                        getSelectedItem().getId()), this.thoiKhoaBieus);
        addThoiKhoaBieuController.setThoiKhoaBieuDTO(thoiKhoaBieuDTO);
    }

    @FXML
    public void handleDeleteOption(ActionEvent actionEvent) {
        ThoiKhoaBieuDTO thoiKhoaBieuDTO = thoiKhoaBieuTable.getSelectionModel().getSelectedItem();
        thoiKhoaBieuRepository.deleteById(thoiKhoaBieuDTO.getId());
        thoiKhoaBieuTable.getItems().remove(thoiKhoaBieuDTO);
        thoiKhoaBieuTable.refresh();
    }

    //    @FXML
//    public void onRefreshKyHoc(MouseEvent mouseEvent) {
//        cbbKyHoc.getItems().clear();
//        setDataKyHoc();
//    }
    @FXML
    public void handleAddSinhVien(ActionEvent actionEvent) {
        SinhVienDangKyHocPhanController sinhVienDangKyHocPhanController = (SinhVienDangKyHocPhanController) showNewScene(
                context.getBean(SinhVienDangKyHocPhanController.class), "Dang ky hoc phan");
        sinhVienDangKyHocPhanController.setTKBDTO(this,
                thoiKhoaBieuTable.getSelectionModel().getSelectedItem());
    }

    public void updateSLSVDangKyHocPhan(int idTKB, boolean update) {
        thoiKhoaBieuTable.getItems().forEach(dto -> {
            if (dto.getId() == idTKB) {
                if (update) dto.setSoLuongSV(dto.getSoLuongSV() + 1);
                else dto.setSoLuongSV(dto.getSoLuongSV() - 1);
            }
        });
        thoiKhoaBieuTable.refresh();
    }
}
