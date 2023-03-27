package Spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class myservice implements ApplicationContextAware {

    public static void main(String[] args) {


    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(zhujie.class);
        for (Object serviceBean : beansWithAnnotation.values()) {
            try {
                String value = serviceBean.getClass().getAnnotation(zhujie.class).value();
                Method hello = serviceBean.getClass().getMethod("hello", new Class[]{String.class});
                Object bbb = hello.invoke(serviceBean, "bbb");
                System.out.println(bbb);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }


}
