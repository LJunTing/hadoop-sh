package hbase;

import org.junit.Test;

public class test {

    public static void main(String[] args) {
        test_one();
    }
    static String tableName1 = "t_student";
    static String tableName2 = "t_student_info";
    static String[] columnFamily1 = {"st1", "st2"};
    static String[] columnFamily2 = {"stf1", "stf2"};
    public static void test_one() {

        HBaseUtil.creatTable(tableName1, columnFamily1);
        HBaseUtil.creatTable(tableName2, columnFamily2);



        for(int i = 0; i < 100; i++) {

            HBaseUtil.insert(tableName1, "1001", columnFamily1[0], "name", "zhangsan"+i);
            HBaseUtil.insert(tableName1, "1002", columnFamily1[0], "name", "lisi"+i);
            HBaseUtil.insert(tableName1, "1001", columnFamily1[1], "age", "18"+i);
            HBaseUtil.insert(tableName1, "1002", columnFamily1[1], "age", "20"+i);
        }

//        HBaseUtil.insert(tableName1, "1001", columnFamily1[0], "name", "zhangsan");
//        HBaseUtil.insert(tableName1, "1002", columnFamily1[0], "name", "lisi");
//        HBaseUtil.insert(tableName1, "1001", columnFamily1[1], "age", "18");
//        HBaseUtil.insert(tableName1, "1002", columnFamily1[1], "age", "20");
////
//        HBaseUtil.insert(tableName2, "1001", columnFamily2[0], "phone", "123456");
//        HBaseUtil.insert(tableName2, "1002", columnFamily2[0], "phone", "234567");
//        HBaseUtil.insert(tableName2, "1001", columnFamily2[1], "mail", "123@163.com");
//        HBaseUtil.insert(tableName2, "1002", columnFamily2[1], "mail", "234@163.com");


    }


    @Test
    public void search_table() {
        HBaseUtil.select(tableName2); //查询该表所有数据
    }


}
