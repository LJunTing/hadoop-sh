package Spring;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})  //注解用在接口上
@Retention(RetentionPolicy.RUNTIME)//vm将在运行期保留注释,因此可以通过反射读取注解
@Component
public @interface zhujie {

    String value();
}
