package ActiveMQ;

import java.util.Random;

public class ProductTest {

    public static void main(String[] args) throws Exception {
        ProductTool productTool = new ProductTool();
        Random random = new Random();
        for(int i = 0; i < 20; i++) {
          Thread.sleep(random.nextInt(10)*1000);
          productTool.sendMessage("hello,world!---"+i);
          productTool.close();
        }

    }

}
