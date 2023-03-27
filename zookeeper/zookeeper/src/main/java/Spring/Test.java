package Spring;

import org.springframework.stereotype.Component;

@Component
public class Test  {
    String name="123";

    public Test(String name) {
        this.name = name;
    }

    public Test() {
    }
}
