package reflect_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class StuINvocationHandler<T> implements InvocationHandler {
    T target;

    public StuINvocationHandler(T target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("代理执行"+method.getName()+"方法");

        Object invoke = method.invoke(target, args);


        return invoke;
    }
}
