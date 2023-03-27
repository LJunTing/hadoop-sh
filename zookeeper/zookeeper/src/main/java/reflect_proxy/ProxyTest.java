package reflect_proxy;

import java.lang.reflect.Proxy;

public class ProxyTest {
    public static void main(String[] args) {
        Student lisi = new Student("李四");

        //代理对象的生成
        //1. ClassLoader，用于加载代理类的 Loader 类，通常这个 Loader 和被代理的类是同一个 Loader 类。
        //2. Interfaces，是要被代理的那些那些接口。
        //3. InvocationHandler，就是用于执行除了被代理接口中方法之外的用户自定义的操作，
        // 他也是用户需要代理的最终目的。用户调用目标方法都被代理到 InvocationHandler 类中定义的唯一方法 invoke 中。
        Person o = (Person) Proxy.newProxyInstance(Person.class.getClassLoader(),
                new Class<?>[]{Person.class}, new StuINvocationHandler<Person>(lisi));

        o.giveMoney();
    }
}
