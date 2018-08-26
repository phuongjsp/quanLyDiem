package doan.zera.jsp.controller.capNhatUser;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import doan.zera.jsp.configuration.AlertMaker;
import doan.zera.jsp.controller.FXMLController;
import doan.zera.jsp.model.User;
import doan.zera.jsp.repositories.UsersRepository;
import doan.zera.jsp.util.FXMLname;
import doan.zera.jsp.util.HelperUlti;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

@Component
@FXMLname(value = "capNhatUser/doiMatKhau", title = "Đổi mật khẩu",
        prefWidth = 500)
public class DoiMatKhauController extends FXMLController implements Initializable {
    @FXML
    public StackPane rootPane;
    @FXML
    public JFXTextField oldPassword;
    @FXML
    public JFXTextField newPassword;
    @FXML
    public JFXTextField confirmPassword;
    @FXML
    public VBox vbox;
    @FXML
    public Label lblNewPw;
    @FXML
    public Label lblCfmPw;
    @FXML
    public JFXButton btnSave;
    private UsersRepository usersRepository;
    private boolean theFist = false;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public DoiMatKhauController(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void reMoveOldPassword() {
        vbox.getChildren().remove(oldPassword);
        theFist = true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSave.setDisable(true);
        confirmPassword.setDisable(true);
        newPassword.setOnKeyReleased(event -> {
            if (!HelperUlti.validatePasswordHeight(newPassword.getText())) {
                lblNewPw.setText("Mật khẩu phải lớn hơn 6 ký tự gồm chữ hoa chữ thường và số");
                confirmPassword.clear();
                confirmPassword.setDisable(true);
                btnSave.setDisable(true);
            } else {
                lblNewPw.setText("Mật khẩu hợp lệ");
                confirmPassword.setDisable(false);
            }
        });
        confirmPassword.setOnKeyReleased(event -> {
            if (confirmPassword.getText().length() == newPassword.getText().length()) {
                if (confirmPassword.getText().equals(newPassword.getText())) {
                    lblCfmPw.setText("Mật khẩu hợp lệ");
                    btnSave.setDisable(false);
                } else {
                    lblCfmPw.setText("Mật khẩu không trùng khớp");
                    btnSave.setDisable(true);
                }
            }
        });
    }

    private void clear() {
        oldPassword.clear();
        newPassword.clear();
        confirmPassword.clear();
    }

    @FXML
    public void onSave(ActionEvent actionEvent) {
        if (!newPassword.getText().equals(confirmPassword.getText())) {
            List<String> stringList = new Stack<>();
            stringList.add("Xác nhận mật khẩu mới không chính sác vui lòng nhập lại");
            confirmPassword.clear();
            HelperUlti.showDialog(rootPane, "Lỗi", stringList);
            return;
        }

        String getUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (theFist || bCryptPasswordEncoder.matches(oldPassword.getText(),
                usersRepository.getPasswordByUsername(getUsername))) {
            User user = usersRepository.findUsersByUsername(getUsername);
            user.setPassword(bCryptPasswordEncoder.encode(confirmPassword.getText()));
            usersRepository.save(user);
            AlertMaker.showTrayMessage("Đổi mật khẩu thành công", "Mật khẩu của bạn đã được thay đổi");
            newPassword.clear();
            confirmPassword.clear();
            theFist = false;
            HelperUlti.close(rootPane);
        } else {
            List<String> stringList = new Stack<>();
            stringList.add("Mật khẩu cũ không chính sác vui lòng nhập lại");
            HelperUlti.showDialog(rootPane, "Lỗi", stringList);
            clear();
            return;
        }
    }

    @FXML
    public void onCancel(ActionEvent actionEvent) {
        HelperUlti.close(rootPane);
    }


}
