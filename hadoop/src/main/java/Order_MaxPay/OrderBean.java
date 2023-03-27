package Order_MaxPay;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class OrderBean implements WritableComparable<OrderBean> {

    public OrderBean() {
    }

    private Text itemid;
    private DoubleWritable amuount;

    public OrderBean(Text itemid, DoubleWritable amuount) {
        set(itemid, amuount);
    }

    public void set(Text itemid, DoubleWritable amuount) {
        this.itemid = itemid;
        this.amuount = amuount;
    }

    public Text getItemid() {
        return itemid;
    }

    public void setItemid(Text itemid) {
        this.itemid = itemid;
    }

    public DoubleWritable getAmuount() {
        return amuount;
    }

    public void setAmuount(DoubleWritable amuount) {
        this.amuount = amuount;
    }

    public int compareTo(OrderBean o) {
        if (this.itemid == null) {
            System.out.println("itemid==null");
        }
        if (o.getItemid() == null) {
            System.out.println("o==null");
        }
        int cmp = this.itemid.compareTo(o.getItemid());
        //如果id相同 则 比金额大小
        if (cmp == 0) {
            cmp = -this.amuount.compareTo(o.getAmuount());
        }
        return cmp;
    }

    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(itemid.toString());
        dataOutput.writeDouble(amuount.get());
    }

    public void readFields(DataInput dataInput) throws IOException {
        String s = dataInput.readUTF();
        double v = dataInput.readDouble();

        this.itemid = new Text(s);
        this.amuount = new DoubleWritable(v);
    }

    @Override
    public String toString() {
        return itemid.toString() + "\t" + amuount.toString();
    }
}
