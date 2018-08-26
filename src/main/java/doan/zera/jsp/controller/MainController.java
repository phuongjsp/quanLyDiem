package doan.zera.jsp.controller;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import doan.zera.jsp.MainApp;
import doan.zera.jsp.controller.capNhatUser.DoiMatKhauController;
import doan.zera.jsp.controller.giaoVien.DanhSachMonHocQuanLyController;
import doan.zera.jsp.controller.phanQuyen.ListController;
import doan.zera.jsp.controller.phongDaoTao.diem.DiemController;
import doan.zera.jsp.controller.phongDaoTao.diem.ThongKeDiemController;
import doan.zera.jsp.controller.phongDaoTao.giaoVien.GiaovienController;
import doan.zera.jsp.controller.phongDaoTao.khoa.KhoaController;
import doan.zera.jsp.controller.phongDaoTao.khoaHoc.KhoaHocController;
import doan.zera.jsp.controller.phongDaoTao.kyHoc.KyHocController;
import doan.zera.jsp.controller.phongDaoTao.monHoc.MonHocController;
import doan.zera.jsp.controller.phongDaoTao.nganh.NganhController;
import doan.zera.jsp.controller.phongDaoTao.sinhVien.SinhVienController;
import doan.zera.jsp.controller.phongDaoTao.thoiKhoaBieu.ThoiKhoaBieuController;
import doan.zera.jsp.repositories.*;
import doan.zera.jsp.util.FXMLname;
import doan.zera.jsp.util.HelperUlti;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

interface setMainBy {
    void doSome();
}

@Component
@FXMLname(value = "main", title = "Giao dien chinh")
public class MainController extends FXMLController implements Initializable {
    @FXML
    public HBox hboxMain;
    @FXML
    public VBox side_nav;
    @FXML
    public Button btnRefreshMain;
    @FXML
    public HBox rootpane;
    @FXML
    public HBox authoritiesBox;
    @FXML
    public HBox hboxThongKeDiemSo;
    @Autowired
    private KyhocRepository kyhocRepository;
    @Autowired
    private ThoiKhoaBieuRepository thoiKhoaBieuRepository;
    @Autowired
    private DiemRepository diemRepository;
    @Autowired
    private KhoaHocRepository khoaHocRepository;
    @Autowired
    private KhoaRepository khoaRepository;
    @Autowired
    private NganhRepository nganhRepository;
    @Autowired
    private MonHocRepository monHocRepository;
    @Autowired
    private ApplicationContext context;

    public void setSize() {
        Window window = rootpane.getScene().getWindow();
        window.setHeight(720);
        window.setWidth(800);
        window.centerOnScreen();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setInitMain();
    }

    private void setInitMain() {
        Label text = new Label("Ngày nay ứng dụng của công nghệ thông tin(CNTT) càng trở nên đa dạng và phong phú,nó đang dần trở thành công cụ hữu ích đối với tất cả các lĩnh vực trong xã hội.\n" +
                "\tỨng dụng CNTT trong công tác quản lý nói chung và trong công tác quản lý điểm của sinh viên trong một khoa của một trường đại học nói riêng là thật sự cần thiết.Nó không chỉ giúp tiết kiệm về mặt thời gian,nhân sự mà quan trọng hơn là đáp ứng được nhu cầu truy cập thông tin về kết quả học tập của sinh viên,giáo viên trong khoa.Bên cạnh đó cũng có nhiều vấn đề được đặt ra là làm sao thiết kế hệ thống quản lý một cách chính xác và đơn giản nhất có thể nhằm mục đích giúp cho việc lưu trữ dữ liệu,quản lí và báo cáo nhanh chóng, hiệu quả.\n" +
                "\tVấn đề trên được giải quyết thông qua việc phân tích thiết kế hệ thống thông tin,một bộ môn quan trọng trong lĩnh vực công nghệ thông tin.\n");
        Label text2 = new Label("\tXuất phát từ những yêu cầu trên chúng em đã chọn và thực hiện đề tài “Phân tích thiết kế hệ" +
                " thống quản lý điểm của trường Đại học Việt Bắc”." +
                "Với những kiến thức được học trên lớp cùng với quá" +
                " trình tham khảo các tài liệu liên quan đặc biệt là " +
                "sự hướng dẫn tận tình của Cô em đã hoàn thành đề tài này." +
                "Trong quá trình phân tích thiết kế có thể còn nhiều chổ thiếu " +
                "sót mong nhận được từ Cô những lời góp ý chân thành để chúng em " +
                "rút kinh nghiệm và dần hoàn thiện kĩ năng phân tích thiết kế một");
        Label text3 = new Label(" bước quan trọng chuẩn bị cho hành trang bước vào đời.\n" + "\tEm xin gửi đến Cô lời cảm ơn chân thành nhất!\n");
        text.setStyle("-fx-font-size: 16.0");
        text.setWrapText(true);
        text2.setStyle("-fx-font-size: 16.0");
        text2.setWrapText(true);
        hboxMain.setStyle("-fx-background-color: #eeeeee");
        text3.setStyle("-fx-font-size: 16.0");
        text3.setWrapText(true);
        VBox vBox = new VBox();
        vBox.getChildren().add(text);
        vBox.getChildren().add(text2);
        vBox.getChildren().add(text3);
        HBox.setMargin(vBox, new Insets(100, 10, 10, 10));
        vBox.setPrefHeight(720);
        hboxMain.getChildren().add(vBox);

    }

    private void addSetThongKeDiemSo() {
        JFXButton jfxButton = new JFXButton("Thống kê điểm số");
        jfxButton.setOnAction(this::thongKeDiemSo);
        hboxThongKeDiemSo.getChildren().add(jfxButton);
    }

    private void addAuthoririesbox() {
        JFXButton jfxButton = new JFXButton("Authorities");
        MaterialDesignIconView iconView = new MaterialDesignIconView();
        iconView.setGlyphName("ACCOUNT");
        jfxButton.setGraphic(iconView);
        authoritiesBox.getChildren().add(jfxButton);
        jfxButton.setOnAction(event -> {
            setMainByClass(context.getBean(ListController.class));
            for (Node node : side_nav.getChildren()) {
                node.setStyle("");
            }
        });
    }

    public void setSide_nav() {
        addListMenu();
    }

    private void addListMenu() {

        List<String> authorities = new LinkedList<>();
        SecurityContextHolder.getContext().
                getAuthentication().getAuthorities().forEach(o -> authorities.add(o.getAuthority()));
        if (authorities.contains("ROLE_ADMIN")) {
            addAuthoririesbox();
            addSetThongKeDiemSo();
        }

        if (authorities.contains("ROLE_QLSV") || authorities.contains("ROLE_ADMIN"))
            setSide_nav_Item(sideNavItem("CHILD", "Sinh Viên"), () ->
                    setMainByClass(context.getBean(SinhVienController.class)));
        if (authorities.contains("ROLE_QLGV") || authorities.contains("ROLE_ADMIN"))
            setSide_nav_Item(sideNavItem("MALE", "Giáo Viên"), () ->
                    setMainByClass(context.getBean(GiaovienController.class)));
        if (authorities.contains("ROLE_QLKH") || authorities.contains("ROLE_ADMIN"))
            setSide_nav_Item(sideNavItem("ITALIC", "Khóa học"), () ->
                    setMainByClass(context.getBean(KhoaHocController.class)));
        if (authorities.contains("ROLE_QLKYH") || authorities.contains("ROLE_ADMIN"))
            setSide_nav_Item(sideNavItem("FOURSQUARE", "Kỳ học"), () ->
                    setMainByClass(context.getBean(KyHocController.class)));
        if (authorities.contains("ROLE_QLK") || authorities.contains("ROLE_ADMIN"))
            setSide_nav_Item(sideNavItem("FLAG", "Khoa"), () ->
                    setMainByClass(context.getBean(KhoaController.class)));
        if (authorities.contains("ROLE_QLNH") || authorities.contains("ROLE_ADMIN"))
            setSide_nav_Item(sideNavItem("FLAG", "Ngành học"), () ->
                    setMainByClass(context.getBean(NganhController.class)));
        if (authorities.contains("ROLE_QLMH") || authorities.contains("ROLE_ADMIN"))
            setSide_nav_Item(sideNavItem("BOOK", "Môn học"), () ->
                    setMainByClass(context.getBean(MonHocController.class)));
        if (authorities.contains("ROLE_QLTKB") || authorities.contains("ROLE_ADMIN"))
            setSide_nav_Item(sideNavItem("CLIPBOARD", "Thời khóa biểu"), () ->
                    setMainByClass(context.getBean(ThoiKhoaBieuController.class)));
        if (authorities.contains("ROLE_QLDS") || authorities.contains("ROLE_ADMIN"))
            setSide_nav_Item(sideNavItem("ARCHIVE", "Quản lý điểm số"), () ->
                    setMainByClass(context.getBean(DiemController.class)));
        if (authorities.contains("ROLE_DSMH") || authorities.contains("ROLE_ADMIN"))
            setSide_nav_Item(sideNavItem("TABLE", "DS môn học đang dạy"), () ->
                    setMainByClass(context.getBean(DanhSachMonHocQuanLyController.class)));
    }

    private void setMainByClass(Object byClass) {
        FXMLController fxml = (FXMLController) byClass;
        hboxMain.getChildren().removeAll(hboxMain.getChildren());
        hboxMain.getChildren().add(fxml.getRoot());
        btnRefreshMain.setOnAction(fxml::onRefreshMain);
        FXMLname fxmLname = fxml.getClass().getAnnotation(FXMLname.class);
        Stage stage = (Stage) rootpane.getScene().getWindow();
        stage.setHeight(fxmLname.prefHeight());
        stage.setWidth(fxmLname.prefWidth());
        stage.setTitle("Quản lý điểm - " + fxmLname.title());
        stage.centerOnScreen();
    }

    void setSide_nav_Item(HBox hbox, setMainBy s) {
        side_nav.getChildren().add(hbox);
        hbox.setOnMouseClicked(event -> {
            s.doSome();
            for (Node node : side_nav.getChildren()) {
                node.setStyle("");
            }
            hbox.setStyle("-fx-background-color: #1976D2");
        });

    }

    private HBox sideNavItem(String iconName, String name) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPrefWidth(100);
        FontAwesomeIconView faiv = new FontAwesomeIconView();
        faiv.setFill(Paint.valueOf("WHITE"));
        faiv.setGlyphName(iconName);
        faiv.setSize("1.2em");
        faiv.setFill(Paint.valueOf("blue"));
        hBox.getChildren().add(faiv);
        Text text = new Text();
        text.setStrokeType(StrokeType.valueOf("OUTSIDE"));
        text.setStrokeWidth(0.0);
        text.setStyle("-fx-fill: white; -fx-font-size: 1.2em;");
        text.setText(name);
        text.setWrappingWidth(100);
        HBox.setMargin(text, new Insets(0, 10, 0, 10.0));
        hBox.getChildren().add(text);
        hBox.setPadding(new Insets(20, 10, 20, 40));
        HBox.setMargin(hBox, new Insets(8, 0, 0, 0));
        return hBox;
    }

    @FXML
    public void onLogOut(ActionEvent actionEvent) {
        HelperUlti.logout();
        context = new AnnotationConfigApplicationContext(MainApp.class);
        FXMLController hello = context.getBean(HelloController.class);
        rootpane.getScene().setRoot(hello.getRoot());
        ((HelloController) hello).setSize();
        HelperUlti.initSecurity();

    }

    @FXML
    public void onChangePassword() {
        showNewScene(context.getBean(DoiMatKhauController.class), "Thay đổi mật khẩu");
    }

    public void thongKeDiemSo(ActionEvent actionEvent) {
        showNewScene(new ThongKeDiemController(kyhocRepository, thoiKhoaBieuRepository, diemRepository,
                khoaHocRepository, khoaRepository, nganhRepository, monHocRepository), "Biểu đồ thống kê");
    }
}