package doan.zera.jsp.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FXMLname {

    String value();

    String title() default "Quản lý điểm  -  Đại học việt bắc ";

    String iconPath() default "/icon.png";

    double prefWidth() default 1080.0;

    double prefHeight() default 720.0;

    boolean fullScreen() default false;

}
