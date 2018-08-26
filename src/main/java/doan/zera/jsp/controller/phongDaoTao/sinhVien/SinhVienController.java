package doan.zera.jsp.controller.phongDaoTao.sinhVien;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import doan.zera.jsp.DTO.SinhVienDTO;
import doan.zera.jsp.controller.FXMLController;
import doan.zera.jsp.model.KhoaHoc;
import doan.zera.jsp.model.Kyhoc;
import doan.zera.jsp.model.Nganh;
import doan.zera.jsp.model.SinhVien;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.*;

@Component
@FXMLname(value = "phongDaoTao/sinhVien/list", prefWidth = 1118, title = "Quản lý sinh viên")
public class SinhVienController extends FXMLController implements Initializable {
    @FXML
    public TableColumn<SinhVienDTO, String> maSv;
    @FXML
    public TableColumn<SinhVienDTO, String> ho;
    @FXML
    public TableColumn<SinhVienDTO, String> ten;
    @FXML
    public TableColumn<SinhVienDTO, String> gioiTinhString;
    @FXML
    public TableColumn<SinhVienDTO, Date> ngaySinhString;
    @FXML
    public TableColumn<SinhVienDTO, String> queQuan;
    @FXML
    public TableColumn<SinhVienDTO, String> maKhoaHoc;
    @FXML
    public TableColumn<SinhVienDTO, String> maNganh;
    @FXML
    public TableView<SinhVienDTO> sinhVienTable;
    @FXML
    public JFXComboBox<Label> cbbKhoaHoc;
    @FXML
    public JFXComboBox<Label> cbbNganhHoc;
    @FXML
    public StackPane rootPane;
    @FXML
    public JFXButton jfxBtnTimKiem;
    @FXML
    public JFXComboBox<Label> cbbKyHoc;
    @FXML
    public TableColumn<SinhVienDTO, String> slMonDangKyTrongKy;
    @FXML
    public TableColumn<SinhVienDTO, String> slTinDangKyTrongKy;
    List<KhoaHoc> khoaHocs = new ArrayList<>();
    List<Nganh> nganhs = new ArrayList<>();
    private KyhocRepository kyhocRepository;
    private NganhRepository nganhRepository;
    private KhoaHocRepository khoaHocRepository;
    private SinhVienRepository sinhVienRepository;
    private ApplicationContext context;
    private ThoiKhoaBieuRepository thoiKhoaBieuRepository;
    private DiemRepository diemRepository;

    public SinhVienController(KyhocRepository kyhocRepository, NganhRepository nganhRepository, KhoaHocRepository khoaHocRepository, SinhVienRepository sinhVienRepository, ApplicationContext context, ThoiKhoaBieuRepository thoiKhoaBieuRepository, DiemRepository diemRepository) {
        this.kyhocRepository = kyhocRepository;
        this.nganhRepository = nganhRepository;
        this.khoaHocRepository = khoaHocRepository;
        this.sinhVienRepository = sinhVienRepository;
        this.context = context;
        this.thoiKhoaBieuRepository = thoiKhoaBieuRepository;
        this.diemRepository = diemRepository;
    }

    private void setCbbKyHoc() {
        cbbKyHoc.getItems().clear();
        kyhocRepository.findAll().forEach(kyhoc ->
                cbbKyHoc.getItems().add(HelperUlti.newLabel(kyhoc.getMaKyHoc(), kyhoc.getTenKyHoc())));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        setKhoasAndNganhs();
        setCbbKyHoc();
    }

    @Override
    public void onRefreshMain(ActionEvent actionEvent) {
        setKhoasAndNganhs();
        setCbbKyHoc();
    }


    void setDataSinhVien(KhoaHoc khoaHoc, Nganh nganh) {
        sinhVienTable.getItems().clear();
        jfxBtnTimKiem.setDisable(true);
        if (nganh.getId() == 0 && khoaHoc.getId() == 0)
            for (int i = 0; i < sinhVienRepository.count(); i++)
                addItemSinhVien(sinhVienRepository.findAll(PageRequest.of(i, 1)).getContent());
        else if (nganh.getId() == 0)
            for (int i = 0; i < sinhVienRepository.countAllByKhoaHoc(khoaHoc); i++)
                addItemSinhVien(sinhVienRepository.findAllByKhoaHoc(khoaHoc, PageRequest.of(i, 1)));
        else if (khoaHoc.getId() == 0)
            for (int i = 0; i < sinhVienRepository.countAllByNganh(nganh); i++)
                addItemSinhVien(sinhVienRepository.findAllByNganh(nganh, PageRequest.of(i, 1)));
        else for (int i = 0; i < sinhVienRepository.countAllByKhoaHocAndNganh(khoaHoc, nganh); i++)
                addItemSinhVien(sinhVienRepository.findAllByKhoaHocAndNganh(khoaHoc, nganh, PageRequest.of(i, 1)));
        jfxBtnTimKiem.setDisable(false);
    }

    private void addItemSinhVien(List<SinhVien> sinhViens) {
        sinhVienTable.getItems().add(new SinhVienDTO(sinhViens.get(0)));
    }


    void initCol() {
        maSv.setCellValueFactory(new PropertyValueFactory<>("maSv"));
        ho.setCellValueFactory(new PropertyValueFactory<>("ho"));
        ten.setCellValueFactory(new PropertyValueFactory<>("ten"));
        queQuan.setCellValueFactory(new PropertyValueFactory<>("queQuan"));
        ngaySinhString.setCellValueFactory(new PropertyValueFactory<>("ngaySinhString"));
        maKhoaHoc.setCellValueFactory(new PropertyValueFactory<>("maKhoaHoc"));
        maNganh.setCellValueFactory(new PropertyValueFactory<>("maNganh"));
        gioiTinhString.setCellValueFactory(new PropertyValueFactory<>("gioiTinhString"));
        slMonDangKyTrongKy.setCellValueFactory(new PropertyValueFactory<>("slMonDangKyTrongKy"));
        slTinDangKyTrongKy.setCellValueFactory(new PropertyValueFactory<>("slTinDangKyTrongKy"));
    }

//    @FXML
//    public void handleRefresh(ActionEvent actionEvent) {
//        sinhVienTable.getItems().clear();
//        sinhVienTable.refresh();
//    }

    @FXML
    public void handleShowDetails(ActionEvent actionEvent) {
    }

    private void setKhoasAndNganhs() {
        cbbKhoaHoc.getItems().clear();
        cbbNganhHoc.getItems().clear();
        khoaHocs = khoaHocRepository.findAll();
        nganhs = nganhRepository.findAll();
        setAllKhoaOrNganhInCbb(cbbKhoaHoc, cbbNganhHoc);
        setDataCbbKhoaHocAndCbbNganhHoc(cbbKhoaHoc, cbbNganhHoc);
    }

    public void setAllKhoaOrNganhInCbb(JFXComboBox<Label> _cbbKhoaHoc, JFXComboBox<Label> _cbbNganhHoc) {
        _cbbKhoaHoc.getItems().add(HelperUlti.newLabel("all", "Tất cả"));
        _cbbNganhHoc.getItems().add(HelperUlti.newLabel("all", "Tất cả"));
    }

    public KhoaHoc getKhoaFormCbb(JFXComboBox<Label> _cbbKhoaHoc) {
        Label khoaSelect = _cbbKhoaHoc.getSelectionModel().getSelectedItem();
        if (khoaSelect.getId().equals("all"))
            return new KhoaHoc();
        for (KhoaHoc k : khoaHocs)
            if (k.getMaKhoa().equals(khoaSelect.getId()))
                return k;
        return null;
    }

    public Nganh getNganhFromCbb(JFXComboBox<Label> cbbNganhHoc) {
        Label nganhSelect = cbbNganhHoc.getSelectionModel().getSelectedItem();
        if (nganhSelect.getId().equals("all")) return new Nganh();
        for (Nganh n : nganhs)
            if (n.getMaNganh().equals(nganhSelect.getId()))
                return n;
        return null;
    }

    public void setDataCbbKhoaHocAndCbbNganhHoc(JFXComboBox<Label> _cbbKhoaHoc, JFXComboBox<Label> _cbbNganhHoc) {
        khoaHocs.forEach(khoa ->
                _cbbKhoaHoc.getItems().add(HelperUlti.newLabel(khoa.getMaKhoa(), khoa.getTenKhoa())));
        nganhs.forEach(nganh ->
                _cbbNganhHoc.getItems().add(HelperUlti.newLabel(nganh.getMaNganh(), nganh.getTenNganh())));
    }

    public void setSlMonHhoc(int idSv, int slMonHhoc, int slTinChi) {
        sinhVienTable.getItems().forEach(sinhVienDTO -> {
            if (idSv == sinhVienDTO.getId()) {
                sinhVienDTO.setSlMonDangKyTrongKy(slMonHhoc);
                sinhVienDTO.setSlTinDangKyTrongKy(slTinChi);
            }
        });
        sinhVienTable.refresh();
    }

    @FXML
    public void handleEditOption(ActionEvent actionEvent) {
        SinhVienDTO sinhVienDTO = (SinhVienDTO) sinhVienTable.getSelectionModel().getSelectedItem();
        AddSinhVienController addSinhVienController = (AddSinhVienController) showNewScene(context.getBean(AddSinhVienController.class),
                "Cập nhật thong tin sinh viên " + sinhVienDTO.getMaSv() + " : " + sinhVienDTO.getHo() + " " + sinhVienDTO.getTen());
        addSinhVienController.setSinhVienController(this);
        addSinhVienController.setSinhVien(sinhVienDTO.getSinhVien());
//TODO khi update co nen huy update khoahoc hay khong
    }

    public void save(SinhVien sinhVien) {
        if (sinhVien.getId() == 0) {
            SinhVien s = sinhVienRepository.save(sinhVien);
            sinhVienTable.getItems().add(new SinhVienDTO(s));
        } else {
            SinhVien s = sinhVienRepository.save(sinhVien);
            SinhVienDTO sinhVienDTO = new SinhVienDTO(s);
            for (int i = 0; i < sinhVienTable.getItems().size(); i++) {
                if (((SinhVienDTO) sinhVienTable.getItems().get(i)).getId() == sinhVienDTO.getId())
                    sinhVienTable.getItems().set(i, sinhVienDTO);
            }
        }
        sinhVienTable.refresh();
    }

    @FXML
    public void handleDeleteOption(ActionEvent actionEvent) {
        SinhVienDTO sinhVienDTO = (SinhVienDTO) sinhVienTable.getSelectionModel().getSelectedItem();
        sinhVienRepository.deleteById(sinhVienDTO.getId());
        sinhVienTable.getItems().remove(sinhVienDTO);
        sinhVienTable.refresh();
    }

    @FXML
    public void onThemSv(ActionEvent actionEvent) {
        AddSinhVienController addSinhVienController = (AddSinhVienController) showNewScene(context.getBean(AddSinhVienController.class),
                "Thêm mới sinh viên");
        addSinhVienController.setSinhVienController(this);
        addSinhVienController.setSinhVien(new SinhVien());
    }

    @FXML
    public void onTimKiemSV(ActionEvent actionEvent) {
        List<String> error = new ArrayList<>();
        if (cbbKhoaHoc.getSelectionModel().getSelectedItem() == null)
            error.add("Khóa học");
        if (cbbNganhHoc.getSelectionModel().getSelectedItem() == null)
            error.add("Ngành học");
        if (!error.isEmpty()) {
            HelperUlti.showDialog(rootPane, "bạn chưa chọn", error);
            return;
        }
        setDataSinhVien(getKhoaFormCbb(this.cbbKhoaHoc), getNganhFromCbb(this.cbbNganhHoc));
    }

    @FXML
    public void onActionCbbKyHoc(ActionEvent actionEvent) {
        setSlMHAndTinDKy();
    }

    private boolean checkKyHocSelect() {
        if (cbbKyHoc.getSelectionModel().getSelectedItem() == null) {
            List<String> stringList = new Stack<>();
            stringList.add("Bạn vui lòng chọn học kỳ trước");
            HelperUlti.showDialog(rootPane, "Vui lòng chọn học kỳ", stringList);
            return true;
        }
        return false;
    }

    private void setSlMHAndTinDKy() {
        if (checkKyHocSelect()) return;
        Kyhoc kyhoc = kyhocRepository.findByMaKyHoc(cbbKyHoc.getSelectionModel().getSelectedItem().getId());
        sinhVienTable.getItems().forEach(svdto -> {
            List<Integer> idMonHocs = diemRepository.findIdMonHocByIdKyHocAndIdSv(kyhoc.getId(), svdto.getId());
            svdto.setSlMonDangKyTrongKy(idMonHocs.size());
            int slTin = 0;
            for (Integer integer : idMonHocs) {
                slTin += thoiKhoaBieuRepository.findTinChiByMonHocAndHocKy(integer, kyhoc.getId());
            }
            svdto.setSlTinDangKyTrongKy(slTin);
        });
        sinhVienTable.refresh();
    }

    public void refreshKyHoc(MouseEvent mouseEvent) {
        setSlMHAndTinDKy();
    }

    @FXML
    public void handleDangKyHocPhan(ActionEvent actionEvent) {
        SinhVien sinhVien = sinhVienTable.getSelectionModel().getSelectedItem().getSinhVien();
        if (checkKyHocSelect()) return;
        HocPhanDangKyController hocPhanDangKyController = (HocPhanDangKyController) showNewScene(context.getBean(HocPhanDangKyController.class),
                "Đăng ký học phần cho sinh viên " + sinhVien.getMaSv() + " : " + sinhVien.getHo() + " " + sinhVien.getTen());
        hocPhanDangKyController.setSinhVienAndKyhoc(this, sinhVien, kyhocRepository.findByMaKyHoc(cbbKyHoc.getSelectionModel().getSelectedItem().getId()));
    }
}
