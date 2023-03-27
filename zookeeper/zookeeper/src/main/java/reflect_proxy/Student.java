package reflect_proxy;

public class Student implements Person {

    String name;

    public void giveMoney() {
        System.out.println(name + "上交了50 班费");
    }

    public Student(String name) {
        this.name = name;
    }
}
