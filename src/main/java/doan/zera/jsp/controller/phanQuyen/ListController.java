package doan.zera.jsp.controller.phanQuyen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import doan.zera.jsp.DTO.ListAuthorities;
import doan.zera.jsp.controller.FXMLController;
import doan.zera.jsp.model.Authorities;
import doan.zera.jsp.repositories.AuthoritiesRepository;
import doan.zera.jsp.repositories.UsersRepository;
import doan.zera.jsp.util.FXMLname;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

@Component
@FXMLname(value = "phanQuyen/list", title = "Quản lý điểm",
        prefHeight = 768, prefWidth = 1000)
public class ListController extends FXMLController implements Initializable {
    @FXML
    public TableColumn<ListAuthorities, String> username;
    @FXML
    public TableColumn<ListAuthorities, Boolean> admin;
    @FXML
    public TableColumn<ListAuthorities, Boolean> giaoVien;
    @FXML
    public TableColumn<ListAuthorities, Boolean> qlDiem;
    @FXML
    public TableColumn<ListAuthorities, Boolean> qlGiaoVien;
    @FXML
    public TableColumn<ListAuthorities, Boolean> qlKhoa;
    @FXML
    public TableColumn<ListAuthorities, Boolean> qlKyHoc;
    @FXML
    public TableColumn<ListAuthorities, Boolean> qlMonHoc;
    @FXML
    public TableColumn<ListAuthorities, Boolean> qlNganh;
    @FXML
    public TableColumn<ListAuthorities, Boolean> qlSinhVien;
    @FXML
    public TableColumn<ListAuthorities, Boolean> qlTKB;
    @FXML
    public TableView<ListAuthorities> authoritiesTable;
    @FXML
    public TableColumn<ListAuthorities, Boolean> qlKhoaHoc;
    @FXML
    public JFXCheckBox cbadmin;
    @FXML
    public JFXCheckBox cbgiaoVien;
    @FXML
    public JFXCheckBox cbqlDiem;
    @FXML
    public JFXCheckBox cbqlGiaoVien;
    @FXML
    public JFXCheckBox cbqlKhoa;
    @FXML
    public JFXCheckBox cbqlKhoaHoc;
    @FXML
    public JFXCheckBox cbqlKyHoc;
    @FXML
    public JFXCheckBox cbqlMonHoc;
    @FXML
    public JFXCheckBox cbqlNganh;
    @FXML
    public JFXCheckBox cbqlSinhVien;
    @FXML
    public JFXCheckBox cbqlTKB;
    @FXML
    public VBox vboxUpdate;
    private ListAuthorities authoritiesScope;
    private List<ListAuthorities> listAuthorities = new Stack<>();
    private ObservableList<ListAuthorities> observableList = FXCollections.observableArrayList(listAuthorities);
    private UsersRepository usersRepository;
    private AuthoritiesRepository authoritiesRepository;

    public ListController(UsersRepository usersRepository, AuthoritiesRepository authoritiesRepository) {
        this.usersRepository = usersRepository;
        this.authoritiesRepository = authoritiesRepository;
    }

    @FXML
    public void onUpdateRole(ActionEvent actionEvent) {
        authoritiesScope = authoritiesTable.getSelectionModel().getSelectedItem();
        cbadmin.setSelected(authoritiesScope.isAdmin());
        cbgiaoVien.setSelected(authoritiesScope.isGiaoVien());
        cbqlDiem.setSelected(authoritiesScope.isQlDiem());
        cbqlGiaoVien.setSelected(authoritiesScope.isQlGiaoVien());
        cbqlKhoa.setSelected(authoritiesScope.isQlKhoa());
        cbqlKhoaHoc.setSelected(authoritiesScope.isQlKhoaHoc());
        cbqlKyHoc.setSelected(authoritiesScope.isQlKyHoc());
        cbqlMonHoc.setSelected(authoritiesScope.isQlMonHoc());
        cbqlNganh.setSelected(authoritiesScope.isQlNganh());
        cbqlSinhVien.setSelected(authoritiesScope.isQlSinhVien());
        cbqlTKB.setSelected(authoritiesScope.isQlTKB());
        vboxUpdate.setDisable(false);
    }

    private void clearRole() {
        authoritiesScope = new ListAuthorities();
        cbadmin.setSelected(false);
        cbgiaoVien.setSelected(false);
        cbqlDiem.setSelected(false);
        cbqlGiaoVien.setSelected(false);
        cbqlKhoa.setSelected(false);
        cbqlKhoaHoc.setSelected(false);
        cbqlKyHoc.setSelected(false);
        cbqlMonHoc.setSelected(false);
        cbqlNganh.setSelected(false);
        cbqlSinhVien.setSelected(false);
        cbqlTKB.setSelected(false);
    }

    @FXML
    public void onSave(ActionEvent actionEvent) {
        authoritiesScope.setAdmin(cbadmin.isSelected());
        authoritiesScope.setGiaoVien(cbgiaoVien.isSelected());
        authoritiesScope.setQlDiem(cbqlDiem.isSelected());
        authoritiesScope.setQlGiaoVien(cbqlGiaoVien.isSelected());
        authoritiesScope.setQlKhoa(cbqlKhoa.isSelected());
        authoritiesScope.setQlKhoaHoc(cbqlKhoaHoc.isSelected());
        authoritiesScope.setQlKyHoc(cbqlKyHoc.isSelected());
        authoritiesScope.setQlMonHoc(cbqlMonHoc.isSelected());
        authoritiesScope.setQlNganh(cbqlNganh.isSelected());
        authoritiesScope.setQlSinhVien(cbqlSinhVien.isSelected());
        authoritiesScope.setQlTKB(cbqlTKB.isSelected());
        for (ListAuthorities la : observableList) {
            if (la.getUsername().equals(authoritiesScope.getUsername())) {
                la.setListAuthorities(authoritiesScope);
                List<String> roles = new Stack<>();
                authoritiesRepository.findAllByUsername(authoritiesScope.getUsername()).forEach(authorities ->
                        roles.add(authorities.getAuthority()));
                roles.forEach(s -> {
                    if (!la.getRoles().contains(s))
                        authoritiesRepository.deleteByUsernameAndAuthority(la.getUsername(), s);
                });
                la.getRoles().forEach(s -> {
                    if (!roles.contains(s))
                        authoritiesRepository.save(new Authorities(la.getUsername(), s));

                });

            }
        }
        clearRole();
        authoritiesTable.refresh();
        vboxUpdate.setDisable(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        setData();
        vboxUpdate.setDisable(true);
    }

    private void setData() {
        usersRepository.findAll().forEach(user -> {
            List<String> roles = new Stack<>();
            authoritiesRepository.findAllByUsername(user.getUsername()).forEach(authorities ->
                    roles.add(authorities.getAuthority()));
            observableList.add(new ListAuthorities(user.getUsername(), roles));
        });

        authoritiesTable.setItems(observableList);
    }

    private void initCol() {
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        admin.setCellValueFactory(new PropertyValueFactory<>("admin"));
        giaoVien.setCellValueFactory(new PropertyValueFactory<>("giaoVien"));
        qlDiem.setCellValueFactory(new PropertyValueFactory<>("qlDiem"));
        qlGiaoVien.setCellValueFactory(new PropertyValueFactory<>("qlGiaoVien"));
        qlKhoa.setCellValueFactory(new PropertyValueFactory<>("qlKhoa"));
        qlKhoaHoc.setCellValueFactory(new PropertyValueFactory<>("qlKhoaHoc"));
        qlKyHoc.setCellValueFactory(new PropertyValueFactory<>("qlKyHoc"));
        qlMonHoc.setCellValueFactory(new PropertyValueFactory<>("qlMonHoc"));
        qlNganh.setCellValueFactory(new PropertyValueFactory<>("qlNganh"));
        qlSinhVien.setCellValueFactory(new PropertyValueFactory<>("qlSinhVien"));
        qlTKB.setCellValueFactory(new PropertyValueFactory<>("qlTKB"));
        admin.setCellFactory(this::call);
        giaoVien.setCellFactory(this::call);
        qlDiem.setCellFactory(this::call);
        qlGiaoVien.setCellFactory(this::call);
        qlKhoa.setCellFactory(this::call);
        qlKhoaHoc.setCellFactory(this::call);
        qlKyHoc.setCellFactory(this::call);
        qlMonHoc.setCellFactory(this::call);
        qlNganh.setCellFactory(this::call);
        qlSinhVien.setCellFactory(this::call);
        qlTKB.setCellFactory(this::call);
    }

    private TableCell<ListAuthorities, Boolean> call(TableColumn<ListAuthorities, Boolean> param) {
        return new TableCell<ListAuthorities, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty && getItem()) {
                    JFXButton jfxButton = new JFXButton();
                    MaterialDesignIconView iconView = new MaterialDesignIconView();
                    iconView.setGlyphName(MaterialDesignIcon.ACCOUNT.name());
                    setGraphic(jfxButton);
                    setAlignment(Pos.CENTER);
                    setStyle("-fx-background-color: green");
                    jfxButton.prefWidth(80);
                    jfxButton.prefHeight(20);
                    jfxButton.setGraphic(iconView);
                }
            }
        };
    }


}
