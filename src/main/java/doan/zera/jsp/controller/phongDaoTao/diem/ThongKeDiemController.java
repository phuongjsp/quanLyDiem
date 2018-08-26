package doan.zera.jsp.controller.phongDaoTao.diem;

import com.jfoenix.controls.JFXComboBox;
import doan.zera.jsp.controller.FXMLController;
import doan.zera.jsp.model.*;
import doan.zera.jsp.repositories.*;
import doan.zera.jsp.util.FXMLname;
import doan.zera.jsp.util.HelperUlti;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.springframework.data.domain.Sort;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;


@FXMLname("phongDaoTao/diem/thongKeDiem")
public class ThongKeDiemController extends FXMLController implements Initializable {
    @FXML
    public JFXComboBox<Label> cbbKyHoc;
    @FXML
    public JFXComboBox<Label> cbbKhoa;
    @FXML
    public JFXComboBox<Label> cbbNganhHoc;
    @FXML
    public JFXComboBox<Label> cbbKhoaHoc;
    @FXML
    public JFXComboBox<Label> cbbMonHoc;
    @FXML
    public HBox hboxChart;

    public PieChart pieChart;
    ObservableList<PieChart.Data> pieChartData;
    private KyhocRepository kyhocRepository;
    private ThoiKhoaBieuRepository thoiKhoaBieuRepository;
    private DiemRepository diemRepository;
    private KhoaHocRepository khoaHocRepository;
    private KhoaRepository khoaRepository;
    private NganhRepository nganhRepository;
    private MonHocRepository monHocRepository;
    private Kyhoc kyhocScope;
    private MonHoc monHocScope;
    private KhoaHoc khoaHocScope;
    private Khoa khoaScope;
    private Nganh nganhScope;

    public ThongKeDiemController(KyhocRepository kyhocRepository,
                                 ThoiKhoaBieuRepository thoiKhoaBieuRepository,
                                 DiemRepository diemRepository,
                                 KhoaHocRepository khoaHocRepository,
                                 KhoaRepository khoaRepository,
                                 NganhRepository nganhRepository,
                                 MonHocRepository monHocRepository) {
        this.kyhocRepository = kyhocRepository;
        this.thoiKhoaBieuRepository = thoiKhoaBieuRepository;
        this.diemRepository = diemRepository;
        this.khoaHocRepository = khoaHocRepository;
        this.khoaRepository = khoaRepository;
        this.nganhRepository = nganhRepository;
        this.monHocRepository = monHocRepository;
    }

    public Kyhoc getKyhocScope() {
        if (kyhocScope == null) kyhocScope = new Kyhoc();
        return kyhocScope;
    }

    public MonHoc getMonHocScope() {
        if (monHocScope == null) monHocScope = new MonHoc();
        return monHocScope;
    }

    public KhoaHoc getKhoaHocScope() {
        if (khoaHocScope == null) khoaHocScope = new KhoaHoc();
        return khoaHocScope;
    }

    public Khoa getKhoaScope() {
        if (khoaScope == null) khoaScope = new Khoa();
        return khoaScope;
    }

    public Nganh getNganhScope() {
        if (nganhScope == null) nganhScope = new Nganh();
        return nganhScope;
    }

    public void setDataKyHoc() {
        cbbKyHoc.getItems().clear();
        kyhocRepository.findAll(new Sort(Sort.Direction.DESC, "maKyHoc")).forEach(kyhoc ->
                cbbKyHoc.getItems().add(HelperUlti.newLabel(kyhoc.getMaKyHoc(), kyhoc.getTenKyHoc())));
    }

    private void setCbbKhoaHoc() {
        cbbKhoaHoc.getItems().clear();
        khoaHocRepository.findAll().forEach(khoaHoc ->
                cbbKhoaHoc.getItems().add(HelperUlti.newLabel(khoaHoc.getMaKhoa(), khoaHoc.getTenKhoa())));
    }

    private void setCbbKhoa() {
        cbbKhoa.getItems().clear();
        khoaRepository.findAll().forEach(khoa ->
                cbbKhoa.getItems().add(HelperUlti.newLabel(khoa.getMaKhoa(), khoa.getTenKhoa())));
    }

    private void setCbbNganhHoc(Khoa khoa) {
        cbbNganhHoc.getItems().clear();
        nganhRepository.findAllByKhoa(khoa).forEach(nganh ->
                cbbNganhHoc.getItems().add(HelperUlti.newLabel(nganh.getMaNganh(), nganh.getTenNganh())));
    }

    private void setCbbMonHoc(Kyhoc kyhoc) {
        cbbMonHoc.getItems().clear();
        thoiKhoaBieuRepository.findAllIdMonHoc(kyhoc)
                .forEach(monHoc ->
                        cbbMonHoc.getItems().add(HelperUlti.newLabel(monHoc.getMaMonHoc(), monHoc.getTenMonHoc())));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDataKyHoc();
        initSetPieChart();
        setCbbKhoa();
        setCbbKhoaHoc();
        initDisable();
    }

    private void initSetPieChart() {
        pieChart = new PieChart();
        pieChartData = FXCollections.observableArrayList();
        pieChart.setPrefWidth(800);
        pieChart.setLegendSide(Side.TOP);
        hboxChart.getChildren().add(pieChart);
    }

    private void initDisable() {
        cbbKhoa.setDisable(true);
        cbbNganhHoc.setDisable(true);
        cbbKhoaHoc.setDisable(true);
        cbbMonHoc.setDisable(true);
    }

    private void clearOtherSelection(JFXComboBox jfxComboBox) {
        if (jfxComboBox != cbbMonHoc)
            cbbMonHoc.getSelectionModel().clearSelection();
        if (jfxComboBox != cbbKhoaHoc)
            cbbKhoaHoc.getSelectionModel().clearSelection();
        if (jfxComboBox != cbbNganhHoc) {
            cbbNganhHoc.getSelectionModel().clearSelection();
            if (jfxComboBox != cbbKhoa)
                cbbKhoa.getSelectionModel().clearSelection();
        }


    }

    @FXML
    public void onActionCbbKyHoc(ActionEvent actionEvent) {
        kyhocScope = kyhocRepository.findByMaKyHoc(cbbKyHoc.getSelectionModel().getSelectedItem().getId());
        Map<String, Long> mapData = new TreeMap<>();
        mapData.put("A", diemRepository.countAllByKyHoc(kyhocScope.getId(), 8.5, 10.0));
        mapData.put("B", diemRepository.countAllByKyHoc(kyhocScope.getId(), 7.0, 8.4));
        mapData.put("C", diemRepository.countAllByKyHoc(kyhocScope.getId(), 5.5, 6.9));
        mapData.put("D", diemRepository.countAllByKyHoc(kyhocScope.getId(), 4.1, 5.4));
        mapData.put("F", diemRepository.countAllByKyHoc(kyhocScope.getId(), 0, 4.0));
        refreshDataPie(mapData, "Kỳ học " + kyhocScope.getTenKyHoc());
        setCbbMonHoc(kyhocScope);
        cbbKhoa.setDisable(false);
        cbbKhoaHoc.setDisable(false);
        cbbMonHoc.setDisable(false);
    }

    @FXML
    public void onActionCbbMonHoc(ActionEvent actionEvent) {
        if (cbbMonHoc.getSelectionModel().getSelectedItem() == null)
            return;
        monHocScope = monHocRepository.findByMaMonHoc(cbbMonHoc.getSelectionModel().getSelectedItem().getId());
        Map<String, Long> mapData = new TreeMap<>();
        mapData.put("A", diemRepository.countAllByKyHocAndMonHoc(kyhocScope.getId(), monHocScope.getId(), 8.5, 10.0));
        mapData.put("B", diemRepository.countAllByKyHocAndMonHoc(kyhocScope.getId(), monHocScope.getId(), 7.0, 8.4));
        mapData.put("C", diemRepository.countAllByKyHocAndMonHoc(kyhocScope.getId(), monHocScope.getId(), 5.5, 6.9));
        mapData.put("D", diemRepository.countAllByKyHocAndMonHoc(kyhocScope.getId(), monHocScope.getId(), 4.1, 5.4));
        mapData.put("F", diemRepository.countAllByKyHocAndMonHoc(kyhocScope.getId(), monHocScope.getId(), 0, 4.0));
        refreshDataPie(mapData, "Môn học " + monHocScope.getTenMonHoc());
        clearOtherSelection(cbbMonHoc);
    }

    private void refreshDataPie(Map<String, Long> mapData, String title) {
        pieChartData = FXCollections.observableArrayList();
        pieChart.setTitle("Biêu đồ " + title);
        int total = 0;
        for (String s : mapData.keySet()) {
            if (mapData.get(s) != 0) pieChartData.add(new PieChart.Data(s, mapData.get(s)));
            total += mapData.get(s);
        }
        pieChart.setData(pieChartData);
        for (PieChart.Data data : pieChart.getData()) {
            double person = Math.round((data.getPieValue() / total) * 100);

            data.nameProperty().bind(Bindings.concat(data.getName(),
                    " [", data.getPieValue(), "] ", person, "%"));
        }

    }

    @FXML
    public void onActionCbbKhoaHoc(ActionEvent actionEvent) {
        if (cbbKhoaHoc.getSelectionModel().getSelectedItem() == null) return;
        khoaHocScope = khoaHocRepository.findByMaKhoa(cbbKhoaHoc.getSelectionModel().getSelectedItem().getId());
        Map<String, Long> mapData = new TreeMap<>();
        mapData.put("A", diemRepository.countAllByKyHocAndKhoaHoc(kyhocScope.getId(), khoaHocScope.getId(), 8.5, 10.0));
        mapData.put("B", diemRepository.countAllByKyHocAndKhoaHoc(kyhocScope.getId(), khoaHocScope.getId(), 7.0, 8.4));
        mapData.put("C", diemRepository.countAllByKyHocAndKhoaHoc(kyhocScope.getId(), khoaHocScope.getId(), 5.5, 6.9));
        mapData.put("D", diemRepository.countAllByKyHocAndKhoaHoc(kyhocScope.getId(), khoaHocScope.getId(), 4.1, 5.4));
        mapData.put("F", diemRepository.countAllByKyHocAndKhoaHoc(kyhocScope.getId(), khoaHocScope.getId(), 0, 4.0));
        refreshDataPie(mapData, "Khóa học " + khoaHocScope.getTenKhoa());
        clearOtherSelection(cbbKhoaHoc);

    }

    @FXML
    public void onActionCbbKhoa(ActionEvent actionEvent) {
        if (cbbKhoa.getSelectionModel().getSelectedItem() == null) return;
        khoaScope = khoaRepository.findByMaKhoa(cbbKhoa.getSelectionModel().getSelectedItem().getId());
        Map<String, Long> mapData = new TreeMap<>();
        mapData.put("A", diemRepository.countAllByKyHocAndKhoa(kyhocScope.getId(), khoaScope.getId(), 8.5, 10.0));
        mapData.put("B", diemRepository.countAllByKyHocAndKhoa(kyhocScope.getId(), khoaScope.getId(), 7.0, 8.4));
        mapData.put("C", diemRepository.countAllByKyHocAndKhoa(kyhocScope.getId(), khoaScope.getId(), 5.5, 6.9));
        mapData.put("D", diemRepository.countAllByKyHocAndKhoa(kyhocScope.getId(), khoaScope.getId(), 4.1, 5.4));
        mapData.put("F", diemRepository.countAllByKyHocAndKhoa(kyhocScope.getId(), khoaScope.getId(), 0, 4.0));
        refreshDataPie(mapData, "Khoa " + khoaScope.getTenKhoa());
        setCbbNganhHoc(khoaScope);
        clearOtherSelection(cbbKhoa);
        cbbNganhHoc.setDisable(false);
    }

    @FXML
    public void onActionCbbNganhHoc(ActionEvent actionEvent) {
        if (cbbNganhHoc.getSelectionModel().getSelectedItem() == null) return;
        nganhScope = nganhRepository.findByMaNganh(cbbNganhHoc.getSelectionModel().getSelectedItem().getId());
        Map<String, Long> mapData = new TreeMap<>();
        mapData.put("A", diemRepository.countAllByKyHocAndNganh(kyhocScope.getId(), nganhScope.getId(), 8.5, 10.0));
        mapData.put("B", diemRepository.countAllByKyHocAndNganh(kyhocScope.getId(), nganhScope.getId(), 7.0, 8.4));
        mapData.put("C", diemRepository.countAllByKyHocAndNganh(kyhocScope.getId(), nganhScope.getId(), 5.5, 6.9));
        mapData.put("D", diemRepository.countAllByKyHocAndNganh(kyhocScope.getId(), nganhScope.getId(), 4.1, 5.4));
        mapData.put("F", diemRepository.countAllByKyHocAndNganh(kyhocScope.getId(), nganhScope.getId(), 0, 4.0));
        refreshDataPie(mapData, "Ngành " + nganhScope.getTenNganh());
        clearOtherSelection(cbbNganhHoc);
    }
}
