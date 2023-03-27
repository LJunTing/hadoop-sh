package flowsum;

public class test {
    public static void main(String[] args) {
        String dfa = "1363157984040\t13602846565\t\t5C-0E-8B-8B-B6-00:CMCC\t120.197.40.4\t\t\t2063.flash2-http.com\t综合门户\t15\t12\t1938\t2910\t200";
        String[] split = dfa.split("\t");
        System.out.println("length=" + split.length);
        String s = split[1];
        String s1 = split[split.length - 3];
        System.out.println("s=" + s + "  s1=" + s1);
    }
}
