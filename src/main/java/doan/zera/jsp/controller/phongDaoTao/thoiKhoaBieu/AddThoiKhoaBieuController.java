package doan.zera.jsp.controller.phongDaoTao.thoiKhoaBieu;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import doan.zera.jsp.DTO.PhongHoc;
import doan.zera.jsp.DTO.ThoiKhoaBieuDTO;
import doan.zera.jsp.controller.FXMLController;
import doan.zera.jsp.model.*;
import doan.zera.jsp.repositories.*;
import doan.zera.jsp.util.FXMLname;
import doan.zera.jsp.util.HelperUlti;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.StackPane;
import javafx.util.converter.IntegerStringConverter;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@FXMLname(value = "phongDaoTao/thoiKhoaBieu/addThoiKhoaBieu", title = "Cập nhật thời khóa biểu")
public class AddThoiKhoaBieuController extends FXMLController implements Initializable {
    @FXML
    public JFXComboBox<Label> cbbKhoa;
    @FXML
    public JFXComboBox<Label> cbbNganh;
    @FXML
    public JFXComboBox<Label> cbbMonHoc;
    @FXML
    public JFXComboBox<Label> cbbGiaoVien;
    @FXML
    public JFXComboBox<Label> cbbThuTrongTuan;
    @FXML
    public JFXComboBox<Label> cbbTietHoc;
    @FXML
    public JFXComboBox<Label> cbbPhongHoc;

    @FXML
    public JFXTextField tongSoTietHoc;
    @FXML
    public JFXComboBox<Label> cbbSoTiet;
    @FXML
    public Label lblNgayBatDau;
    @FXML
    public Label lblNgayKetThuc;
    @FXML
    public JFXButton btnSave;
    @FXML
    public StackPane rootPane;
    @FXML
    public JFXTextField tinChi;
    List<PhongDangDung> listPhongHocDangSuDung = new Stack<>();
    String[][] labels = new String[10][6];
    Map<GiaoVien, String[][]> tkbOfGv = new WeakHashMap<>();
    private ThoiKhoaBieuController thoiKhoaBieuController;
    private Kyhoc kyhoc;
    @Autowired
    private KhoaRepository khoaRepository;
    @Autowired
    private NganhRepository nganhRepository;
    @Autowired
    private MonHocRepository monHocRepository;
    @Autowired
    private GiaoVienRepository giaoVienRepository;
    @Autowired
    private MonHocGiaoVienRepository monHocGiaoVienRepository;

    private ThoiKhoaBieuDTO thoiKhoaBieuDTO;
    private List<ThoiKhoaBieuDTO> thoiKhoaBieus;
    private Map<String, List<String>> tietTrongTuan = new TreeMap<>();

    public void setThoiKhoaBieuController(ThoiKhoaBieuController thoiKhoaBieuController, Kyhoc kyhoc,
                                          List<ThoiKhoaBieuDTO> thoiKhoaBieus) {
        this.thoiKhoaBieuController = thoiKhoaBieuController;
        this.kyhoc = kyhoc;
        this.thoiKhoaBieus = thoiKhoaBieus;
        if (thoiKhoaBieus == null) this.thoiKhoaBieus = new Stack<>();
        setMapPhongHoc();
    }

    public ThoiKhoaBieuDTO getThoiKhoaBieuDTO() {
        if (thoiKhoaBieuDTO == null) thoiKhoaBieuDTO = new ThoiKhoaBieuDTO();
        if (thoiKhoaBieuDTO.getKyhoc() == null) thoiKhoaBieuDTO.setKyhoc(this.kyhoc);
        return thoiKhoaBieuDTO;
    }

    public void setThoiKhoaBieuDTO(ThoiKhoaBieuDTO thoiKhoaBieuDTO) {

        this.thoiKhoaBieuDTO = thoiKhoaBieuDTO;
        if (thoiKhoaBieuDTO.getId() == 0) return;
        this.thoiKhoaBieus.remove(thoiKhoaBieuDTO);
        setCbbKhoa();
        Khoa khoa = thoiKhoaBieuDTO.getMonHoc().getNganh().getKhoa();
        cbbKhoa.getItems().forEach(label -> {
            if (label.getId().equals(khoa.getMaKhoa()))
                cbbKhoa.getSelectionModel().select(label);
        });
        setCbbNganh(khoa);
        Nganh nganh = thoiKhoaBieuDTO.getMonHoc().getNganh();
        cbbNganh.getItems().forEach(label -> {
            if (label.getId().equals(nganh.getMaNganh()))
                cbbNganh.getSelectionModel().select(label);
        });
        setCbbMonHoc(nganh);
        MonHoc monHoc = thoiKhoaBieuDTO.getMonHoc();
        cbbMonHoc.getItems().forEach(label -> {
            if (label.getId().equals(monHoc.getMaMonHoc()))
                cbbMonHoc.getSelectionModel().select(label);
        });
        setCbbGiaoVien((List<GiaoVien>) monHocGiaoVienRepository.findGiaoVienByMonHoc(monHoc));
        cbbGiaoVien.getItems().forEach(label -> {
            if (label.getId().equals(thoiKhoaBieuDTO.getGiaoVien().getMaGv()))
                cbbGiaoVien.getSelectionModel().select(label);
        });

        cbbSoTiet.getItems().forEach(label -> {
            if (label.getText().equals(thoiKhoaBieuDTO.getSoTiet() + ""))
                cbbSoTiet.getSelectionModel().select(label);
        });
        tongSoTietHoc.setText(thoiKhoaBieuDTO.getTongSoTiet() + "");
        setCbbThuTrongTuan();
        cbbThuTrongTuan.getItems().forEach(label -> {
            int ttt = Integer.parseInt(String.valueOf(label.getText().charAt(label.getText().length() - 1)));
            if (ttt == thoiKhoaBieuDTO.getThuTrongTuan())
                cbbThuTrongTuan.getSelectionModel().select(label);
        });

        String selectThuTrongTuan = cbbThuTrongTuan.getSelectionModel().getSelectedItem().getId();
        setCbbTietHoc(tietTrongTuan.get
                (selectThuTrongTuan));
        cbbTietHoc.getItems().forEach(label -> {
            if (label.getText().equals(thoiKhoaBieuDTO.getTietDay()))
                cbbTietHoc.getSelectionModel().select(label);
        });
        tinChi.setText(thoiKhoaBieuDTO.getTinChi() + "");
        lblNgayBatDau.setText("Ngày bắt đầu " + thoiKhoaBieuDTO.getNgayBatDauString());
        lblNgayKetThuc.setText("Ngầy kết thúc " + thoiKhoaBieuDTO.getNgayBatDauString());
        listPhongHocDangSuDung = new Stack<>();
        List<String> phongDangHoc = new LinkedList<>();
        String[] tietHoc = cbbTietHoc.getSelectionModel().getSelectedItem().getText().split("-");
        setPhongDangHocAndTietHoc(phongDangHoc, tietHoc);
        setCbbPhongHoc(phongDangHoc);
        cbbPhongHoc.getItems().forEach(label -> {
            if (label.getText().equals(thoiKhoaBieuDTO.getPhongHoc()))
                cbbPhongHoc.getSelectionModel().select(label);
        });
    }

    private void setPhongDangHocAndTietHoc(List<String> phongDangHoc, String[] tietHoc) {
        for (PhongDangDung phongDangDung : listPhongHocDangSuDung)
            if (phongDangDung.getThuTrongTuan() == this.thoiKhoaBieuDTO.getThuTrongTuan())
                for (int i = 0; i < phongDangDung.getTiet().length; i++)
                    for (String aTietHoc : tietHoc)
                        if (phongDangDung.getTiet()[i].equals(aTietHoc))
                            if (!phongDangHoc.contains(phongDangDung.getPhong()))
                                phongDangHoc.add(phongDangDung.getPhong());
    }


    private void thoiGianTrong(int sc, int soTiet) {
        for (int i = 0; i < 6; i++) {
            List<String> thu = new LinkedList<>();
            for (int j = sc; j < sc + 5; j++) {
                if ((j + 1) <= sc + 5) if (soTiet == 1) if (labels[j][i] == null) {
                    thu.add((j + 1) + "");
                }
                if ((j + 2) <= sc + 5) if (soTiet == 2) if (labels[j][i] == null
                        && labels[j + 1][i] == null) {
                    thu.add((j + 1) + "-" + (j + 2));
                }
                if ((j + 3) <= sc + 5) if (soTiet == 3) if (labels[j][i] == null
                        && labels[j + 1][i] == null
                        && labels[j + 2][i] == null) {
                    thu.add((j + 1) + "-" + (j + 2) + "-" + (j + 3));
                }
                if ((j + 4) <= sc + 5) if (soTiet == 4) if (labels[j][i] == null
                        && labels[j + 1][i] == null
                        && labels[j + 2][i] == null
                        && labels[j + 3][i] == null) {
                    thu.add((j + 1) + "-" + (j + 2) + "-" + (j + 3) + "-" + (j + 4));
                }
            }
            if (sc == 0) tietTrongTuan.put("Sáng thứ" + (i + 2), thu);
            else tietTrongTuan.put("Chiều thứ " + (i + 2), thu);
        }
    }

    private void addChildKTKB(String tMH, int soTiet, int thu, int tietND) {
        thu -= 2;
        setNewLabel(tMH, tietND - 1, thu);
        if (soTiet >= 2) setNewLabel(tMH, tietND, thu);
        if (soTiet >= 3) setNewLabel(tMH, tietND + 1, thu);
        if (soTiet >= 4) setNewLabel(tMH, tietND + 2, thu);
    }

    private void setNewLabel(String tMH, int i, int j) {
        labels[i][j] = tMH;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCbbKhoa();
        setInitData();
        tongSoTietHoc.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        tinChi.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        tinChi.setOnKeyPressed(event -> {
            if (tinChi.getText().length()>3 ){
                HelperUlti.showDialog(rootPane,"Tín chỉ không nên quá lớn",new Stack<>());
            }
        });
    }

    private void setInitData() {
        cbbKhoa.setOnAction(this::onSetCbbKhoa);
        cbbNganh.setOnAction(this::onSetCbbNganh);
        cbbMonHoc.setOnAction(this::onSetCbbMonHoc);
        cbbGiaoVien.setOnAction(this::onSetCbbGiaoVien);
        tongSoTietHoc.setOnAction(this::onSetTongSoTietHoc);
        cbbSoTiet.setDisable(true);
        cbbSoTiet.setOnAction(this::onSetSoTiet);
        cbbThuTrongTuan.setOnAction(this::onSetCbbThuTrongTuan);
        cbbTietHoc.setOnAction(this::onSetTietHoc);
        cbbPhongHoc.setOnAction(this::onSetPhongHoc);
        btnSave.setDisable(true);
    }

    private void onSetCbbKhoa(ActionEvent actionEvent) {
        setDisableField(1);
        setCbbNganh(khoaRepository.findByMaKhoa
                (cbbKhoa.getSelectionModel().getSelectedItem().getId()));

    }

    private void onSetCbbNganh(ActionEvent actionEvent) {
        setDisableField(2);
        if (cbbNganh.getSelectionModel().getSelectedItem() != null) {
            setCbbMonHoc(nganhRepository.findByMaNganh
                    (cbbNganh.getSelectionModel().getSelectedItem().getId()));
        }

    }

    private void onSetCbbMonHoc(ActionEvent actionEvent) {
        setDisableField(3);
        if (cbbMonHoc.getSelectionModel().getSelectedItem() != null) {
            MonHoc monHoc = monHocRepository.findByMaMonHoc
                    (cbbMonHoc.getSelectionModel().getSelectedItem().getId());
            getThoiKhoaBieuDTO().setMonHoc(monHoc);
            setCbbGiaoVien(monHocGiaoVienRepository.findGiaoVienByMonHoc(monHoc));
            cbbSoTiet.setDisable(false);
        }
    }

    private void onSetCbbGiaoVien(ActionEvent actionEvent) {
        setDisableField(4);
        if (cbbGiaoVien.getSelectionModel().getSelectedItem() == null) return;
        GiaoVien giaoVien = giaoVienRepository.findByMaGv(cbbGiaoVien.getSelectionModel().getSelectedItem().getId());
        getThoiKhoaBieuDTO().setGiaoVien(giaoVien);
        for (int i = 1; i < 6; i++) {
            cbbSoTiet.getItems().add(HelperUlti.newLabel(i + "", i + ""));
        }
    }

    private void onSetPhongHoc(ActionEvent actionEvent) {
        if (this.cbbPhongHoc.getSelectionModel().getSelectedItem() == null) {
            btnSave.setDisable(true);
            return;
        }
        this.thoiKhoaBieuDTO.setPhongHoc(this.cbbPhongHoc.getSelectionModel().getSelectedItem().getText());
        btnSave.setDisable(false);
    }

    private void setCbbPhongHoc(List<String> phongDangHoc) {
        if (!cbbPhongHoc.getItems().isEmpty()) cbbPhongHoc.getItems().clear();
        for (PhongHoc p : PhongHoc.values()) {
            boolean khongTrung = true;
            for (String s : phongDangHoc)
                if (p.name().equals(s)) khongTrung = false;
            if (khongTrung)
                cbbPhongHoc.getItems().add(HelperUlti.newLabel(p.name(), p.name()));
        }
    }
    //TietDay == tietHoc

    private void onSetSoTiet(ActionEvent actionEvent) {
        if (cbbSoTiet.getSelectionModel().getSelectedItem() == null) return;
        getThoiKhoaBieuDTO().setSoTiet(Integer.parseInt(cbbSoTiet.getSelectionModel().getSelectedItem().getText()));
        setDisableField(5);
    }

    private void setCbbThuTrongTuan() {
        int soTietNgay = 0;
        List<ThoiKhoaBieuDTO> tkbOfGv = new Stack<>();
        labels = new String[10][6];
        for (ThoiKhoaBieuDTO dto : thoiKhoaBieus)
            if (dto.getGiaoVien().getId() == thoiKhoaBieuDTO.getGiaoVien().getId()) tkbOfGv.add(dto);
        for (ThoiKhoaBieuDTO t : tkbOfGv)
            addChildKTKB(t.getTenMonHoc(), t.getSoTiet(),
                    t.getThuTrongTuan(), t.getTietBatDau());
        soTietNgay = Integer.valueOf(cbbSoTiet.getSelectionModel().getSelectedItem().getText());
        tietTrongTuan = new TreeMap<>();
        thoiGianTrong(0, soTietNgay);
        thoiGianTrong(5, soTietNgay);
        tietTrongTuan.forEach((s, list) -> cbbThuTrongTuan.getItems().add(HelperUlti.newLabel(s, s)));

    }

    private void setCbbTietHoc(List<String> list) {
        list.forEach(s -> cbbTietHoc.getItems().add(HelperUlti.newLabel(s, s)));
    }

    private void onSetCbbThuTrongTuan(ActionEvent actionEvent) {
        if (cbbThuTrongTuan.getSelectionModel().getSelectedItem() != null) {
            setDisableField(7);
            String selectThuTrongTuan = cbbThuTrongTuan.getSelectionModel().getSelectedItem().getId();
            getThoiKhoaBieuDTO().setThuTrongTuan(Integer.parseInt(selectThuTrongTuan.charAt(selectThuTrongTuan.length() - 1) + ""));
            setCbbTietHoc(tietTrongTuan.get
                    (selectThuTrongTuan));
        }
    }

    private void setCbbKhoa() {
        khoaRepository.findAll().forEach(khoa -> cbbKhoa.getItems().add(HelperUlti.newLabel(khoa.getMaKhoa(), khoa.getTenKhoa())));
    }

    private void setCbbNganh(Khoa khoa) {
        nganhRepository.findAllByKhoa(khoa).forEach(nganh -> cbbNganh.getItems()
                .add(HelperUlti.newLabel(nganh.getMaNganh(), nganh.getTenNganh())));
    }

    private void setCbbMonHoc(Nganh nganh) {
        monHocRepository.findAllByNganh(nganh).forEach(monHoc ->
                cbbMonHoc.getItems().add(HelperUlti.newLabel(monHoc.getMaMonHoc(), monHoc.getTenMonHoc())));
    }

    private void setCbbGiaoVien(List<GiaoVien> giaoViens) {
        giaoViens.forEach(giaoVien -> cbbGiaoVien.getItems()
                .add(HelperUlti.newLabel(giaoVien.getMaGv(), giaoVien.getHo() + " " + giaoVien.getTen())));
    }


    private void setNgayBatDauAndKetThuc() {
        if (tongSoTietHoc.getText().equals("")) return;
        if (cbbThuTrongTuan.getSelectionModel().getSelectedItem() == null) return;
        if (cbbTietHoc.getSelectionModel().getSelectedItem() == null) return;
        thoiKhoaBieuDTO.setTongSoTiet(Integer.parseInt(tongSoTietHoc.getText()));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date tuanBatDau = kyhoc.getNgayBatDauHoc();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tuanBatDau);
        String cbbTTT = cbbThuTrongTuan.getSelectionModel().getSelectedItem().getText();
        int dow = Integer.parseInt(String.valueOf(cbbTTT.charAt(cbbTTT.length() - 1)));
        calendar.set(Calendar.DAY_OF_WEEK, dow);
        Date ngayBatDau = calendar.getTime();
        if (ngayBatDau.before(tuanBatDau)) ngayBatDau.setDate(ngayBatDau.getDate() + 7);
        lblNgayBatDau.setText("Ngày bất đầu " + sdf.format(ngayBatDau));
        int soBuoiHoc = thoiKhoaBieuDTO.getTongSoTiet();
        int soTuan = soBuoiHoc / thoiKhoaBieuDTO.getSoTiet();
        int soNgay = soTuan * 7;
        if (soTuan % 7 != 0) soNgay += 7;
        Date ngayKetThuc = new Date(ngayBatDau.getTime());
        ngayKetThuc.setDate(ngayKetThuc.getDate() + soNgay);
        lblNgayKetThuc.setText("Ngày kết tuh́c " + sdf.format(ngayKetThuc));
        thoiKhoaBieuDTO.setThoiGianBatDau(ngayBatDau);
        thoiKhoaBieuDTO.setThoiGianKetThuc(ngayKetThuc);
        thoiKhoaBieuDTO.setKyhoc(kyhoc);
    }

    @FXML
    public void onSave(ActionEvent actionEvent) {
        List<String> tinChiIsEmpty = new LinkedList<>();
        tinChiIsEmpty.add("Vui lòng nhập tín chỉ");
        if (tinChi.getText().isEmpty()) HelperUlti.showDialog(rootPane, "Bạn phải nhập tín chỉ", tinChiIsEmpty);
        thoiKhoaBieuDTO.setTinChi(Integer.parseInt(tinChi.getText()));
        thoiKhoaBieuController.saveTKB(this.thoiKhoaBieuDTO.getThoiKhoaBieu());
        HelperUlti.close(rootPane);
    }

    @FXML
    public void onCancel(ActionEvent actionEvent) {
        HelperUlti.close(rootPane);
    }

    private void onSetTongSoTietHoc(ActionEvent actionEvent) {
        checkThoiGian();
    }

    private void checkThoiGian() {
        if (tongSoTietHoc.getText().equals("")) return;
        try {
            thoiKhoaBieuDTO.setTongSoTiet(Integer.parseInt(tongSoTietHoc.getText()));
            setDisableField(6);
            setCbbThuTrongTuan();
        } catch (NumberFormatException e) {
            return;
        }
    }

    private void setDisableField(int tang) {
        if (tang <= 9) btnSave.setDisable(true);
        if (tang <= 8) cbbPhongHoc.getItems().clear();
        if (tang <= 7) cbbTietHoc.getItems().clear();
        if (tang <= 6) cbbThuTrongTuan.getItems().clear();
        if (tang <= 5) {
            tinChi.setText("");
            tongSoTietHoc.setText("");
            lblNgayBatDau.setText("Ngày bắt đầu ");
            lblNgayKetThuc.setText("Ngày kết thúc ");
        }
        if (tang <= 4) cbbSoTiet.getItems().clear();
        if (tang <= 3) cbbGiaoVien.getItems().clear();
        if (tang <= 2) cbbMonHoc.getItems().clear();
        if (tang <= 1) cbbNganh.getItems().clear();
    }


    private void setMapPhongHoc() {
        this.thoiKhoaBieus.forEach(dto -> {
            PhongDangDung phongDangDung = new PhongDangDung();
            phongDangDung.setThuTrongTuan(dto.getThuTrongTuan());
            phongDangDung.setPhong(dto.getPhongHoc());
            phongDangDung.setTiet(dto.getTietDay().split("-"));
            listPhongHocDangSuDung.add(phongDangDung);
        });
    }

    private void onSetTietHoc(ActionEvent actionEvent) {
        if (cbbTietHoc.getSelectionModel().getSelectedItem() == null) return;
        setNgayBatDauAndKetThuc();
        List<String> phongDangHoc = new LinkedList<>();
        String[] tietHoc = cbbTietHoc.getSelectionModel().getSelectedItem().getText().split("-");
        this.thoiKhoaBieuDTO.setTietBatDau(Integer.parseInt(tietHoc[0]));
        setPhongDangHocAndTietHoc(phongDangHoc, tietHoc);
        setDisableField(8);
        setCbbPhongHoc(phongDangHoc);
    }

    @FXML
    public void onCheckThoiGian(ActionEvent actionEvent) {
        checkThoiGian();
    }

    @FXML
    public void onSetTinChi(ActionEvent actionEvent) {
        this.thoiKhoaBieuDTO.setTinChi(Integer.parseInt(tinChi.getText()));
    }
}

@Data
class PhongDangDung {
    private int thuTrongTuan;
    private String phong;
    private String[] tiet;
}