package doan.zera.jsp;

import doan.zera.jsp.util.HelperUlti;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@SuppressWarnings("restriction")
public class AppPreloader extends Preloader {
    private Stage preloaderStage;
    private Scene scene;
    @Override
    public void init() throws Exception {

        Parent root1 = FXMLLoader.load(getClass().getResource("/doan/zera/jsp/views/splashScreen.fxml"));

        root1.setStyle("-fx-background-image: url(icon.png)");
        scene = new Scene(root1);
        //scene = new Scene(root1, 700, 400, Color.TRANSPARENT);


    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.preloaderStage = primaryStage;
        HelperUlti.initSecurity();
        // preloaderStage.initStyle(StageStyle.TRANSPARENT);
        // Set preloader scene and show stage.
        preloaderStage.setScene(scene);
        preloaderStage.initStyle(StageStyle.UNDECORATED);

        preloaderStage.getIcons().add(new Image("/icon.png"));
        preloaderStage.show();


    }

    @Override
    public void handleStateChangeNotification(Preloader.StateChangeNotification info) {

        StateChangeNotification.Type type = info.getType();
        switch (type) {

            case BEFORE_START:
                // Called after MyApplication#init and before MyApplication#start is called.
                preloaderStage.hide();
                break;
        }


    }

}
