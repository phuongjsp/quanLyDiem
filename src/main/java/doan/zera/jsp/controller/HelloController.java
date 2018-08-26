package doan.zera.jsp.controller;

import doan.zera.jsp.util.FXMLname;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Window;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@FXMLname(value = "hello", title = "Đại học việt bắc - Quản lý điểm ")
public class HelloController extends FXMLController {

    @FXML
    public TextField username;
    @FXML
    public PasswordField password;
    @FXML
    public Text lblError;
    @FXML
    public Button btnContactMe;
    @FXML
    public VBox mainVbox;
    private ApplicationContext context;
    private AuthenticationManager authManager;

    public HelloController(ApplicationContext context, AuthenticationManager authManager) {
        this.context = context;
        this.authManager = authManager;
    }

    public void setSize() {
        Window window = mainVbox.getScene().getWindow();
        window.setHeight(420);
        window.setWidth(850);
        window.centerOnScreen();
        username.setText("");
        password.setText("");
    }

    @FXML
    public void onAuthClick(ActionEvent event) throws IOException {
        try {
            final String usuario = username.getText().trim();
            final String contrasena = password.getText().trim();
            Authentication request = new UsernamePasswordAuthenticationToken(usuario, contrasena);
            Authentication result = authManager.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(result);
            loadMain(btnContactMe.getScene());
        } catch (AuthenticationException e) {
            lblError.setText("Tài khoản hoặc mật khẩu không hợp lệ");
        }
    }

    private void loadMain(Scene scene) {
        FXMLController hello = context.getBean(MainController.class);
        scene.setRoot(hello.getRoot());
        ((MainController) hello).setSide_nav();
        ((MainController) hello).setSize();
    }

    @FXML
    public void onQuenMatKhau(MouseEvent mouseEvent) {
        lblError.setText("Chức năng này đang được xây dựng.vui lòng đợi các bản cập nhật sau");
    }
}
