package doan.zera.jsp;


import doan.zera.jsp.controller.FXMLController;
import doan.zera.jsp.controller.HelloController;
import doan.zera.jsp.util.HelperUlti;
import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Lazy
@SpringBootApplication
@EnableJpaRepositories(basePackages = "doan.zera.jsp.repositories")
@EntityScan(basePackages = "doan.zera.jsp.model")
public class MainApp extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launchApp(MainApp.class, args);
    }
    @Autowired
   private ApplicationContext context;

    @Override
    public void start(Stage stage) {
        notifyPreloader(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_START));
        System.out.println("Start in Main");
        FXMLController hello = context.getBean(HelloController.class);
        stage.setScene(new Scene(hello.getRoot()));
        stage.setTitle(hello.fxmLname.title());
        stage.getIcons().add(new Image(getClass().getResourceAsStream(hello.fxmLname.iconPath())));
        stage.show();
        stage.setHeight(420);
        stage.setWidth(850);
        stage.centerOnScreen();
    }

}
