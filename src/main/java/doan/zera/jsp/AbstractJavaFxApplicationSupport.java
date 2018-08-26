package doan.zera.jsp;

import javafx.application.Application;
import javafx.application.Preloader;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.sun.javafx.application.LauncherImpl;
@SuppressWarnings("restriction")
public abstract class AbstractJavaFxApplicationSupport extends Application {
    private static String[] savedArgs;
    private ConfigurableApplicationContext applicationContext;

    static void launchApp(Class<? extends AbstractJavaFxApplicationSupport> appClass, String... args) {
        AbstractJavaFxApplicationSupport.savedArgs = args;
        LauncherImpl.launchApplication(appClass, AppPreloader.class, args);
    }
    @Override
    public void init() throws Exception {
        Thread.currentThread().setName("main");
        applicationContext = SpringApplication.run(getClass(), savedArgs);
        applicationContext.getAutowireCapableBeanFactory().autowireBean(this);

    }


    @Override
    public void stop() throws Exception {
        super.stop();
        applicationContext.close();
    }
}
