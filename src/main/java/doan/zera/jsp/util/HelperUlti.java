package doan.zera.jsp.util;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Log4j2
public class HelperUlti {

    public static final String ICON_IMAGE_LOC = "/icon.png";
    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    public static void setStageIcon(Stage stage) {
        stage.getIcons().add(new Image(ICON_IMAGE_LOC));
    }


    public static String formatDateTimeString(Date date) {
        return DATE_TIME_FORMAT.format(date);
    }

    public static String formatDateTimeString(Long time) {
        return DATE_TIME_FORMAT.format(new Date(time));
    }

    public static String getDateString(Date date) {
        return DATE_FORMAT.format(date);
    }

    public static boolean validateEmailAddress(String emailID) {
        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(emailID).matches();
    }

    public static boolean validatePasswordHeight(String pasword) {
        String regex = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}";
        Pattern pattern = Pattern.compile(regex);

        return pattern.matcher(pasword).matches();
    }

    /***
     * Iniciar seguridad MODE_GLOBAL establece un unico contexto de seguridad.
     * usemos MODE_GLOBAL solo en apliaciones no web.
     */
    public static void initSecurity() {
        SecurityContextHolder.setStrategyName("MODE_GLOBAL");
        initAnonymous();
    }

    /***
     * Agregar un usuario anonimo
     */
    public static void initAnonymous() {
        AnonymousAuthenticationToken auth = new AnonymousAuthenticationToken(
                "anonymous", "anonymous",
                AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    /***
     * cerrar la session de usuario
     */
    public static void logout() {
        SecurityContextHolder.clearContext();
        initAnonymous();
    }


    public static void showDialog(StackPane rootPane, String text, List<String> list) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text(text));
        StringBuilder body = new StringBuilder();
        list.forEach(s -> body.append("\n" + s));
        content.setBody(new Text(body.toString()));
        JFXDialog jfxDialog = new JFXDialog(rootPane, content, JFXDialog.DialogTransition.TOP);
        jfxDialog.show();
    }

    public static void close(Node node) {
        ((Stage) node.getScene().getWindow()).close();
    }

    public static Label newLabel(String id, String text) {
        javafx.scene.control.Label label = new javafx.scene.control.Label();
        label.setId(id);
        label.setText(text);
        label.setTextFill((javafx.scene.paint.Color.valueOf("black")));
        FontAwesomeIconView faiv = new FontAwesomeIconView();
        faiv.setGlyphName("STAR");
        faiv.setFill(Color.valueOf("yellow"));
        label.setGraphic(faiv);
        return label;
    }
}
