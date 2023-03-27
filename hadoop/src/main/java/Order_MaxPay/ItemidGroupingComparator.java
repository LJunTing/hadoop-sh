package Order_MaxPay;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;


/**
 * 利用reduce端的GroupingComparator来实现  将一组bean看成相同的key
 * 分组用
 */
public class ItemidGroupingComparator extends WritableComparator {

    //更好的是用二进制比较 (最优选择)
    public ItemidGroupingComparator() {
        //父类需要反射  序列化 比较
        super(OrderBean.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {

        OrderBean abean = (OrderBean) a;
        OrderBean bbean = (OrderBean) b;

        //比较订单id
        return abean.getItemid().compareTo(bbean.getItemid());
    }
}
