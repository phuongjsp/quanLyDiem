package doan.zera.jsp.controller;

import doan.zera.jsp.util.FXMLname;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Created by Carmelo Marin Abrego on 01/03/2016.
 */
public class FXMLController {
    public FXMLname fxmLname = this.getClass().getAnnotation(FXMLname.class);
    protected String prefix = "doan/zera/jsp/views/";
    protected String suffix = ".fxml";
    protected Parent root;

    public void onRefreshMain(ActionEvent actionEvent) {

    }


    public FXMLLoader fxmlLoader() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource(getFxmlName()));
        loader.setControllerFactory(clz -> this);
        return loader;
    }

    public Parent load() {
        try {

            return fxmlLoader().load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected String getFxmlName() {
        return prefix + fxmLname.value() + suffix;
    }

    public Parent getRoot() {
        if (root == null) {
            root = load();
        }
        return root;
    }

    protected FXMLController showNewScene(FXMLController fxmlController, String title) {
        FXMLLoader loader = fxmlController.fxmlLoader();
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle(title);
        stage.getIcons().add(new Image(getClass().getResourceAsStream(fxmLname.iconPath())));
        stage.setScene(new Scene(parent));
        stage.show();
        return loader.getController();
    }


}
