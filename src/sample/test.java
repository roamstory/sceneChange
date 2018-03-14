package sample;

public class test {
    public static void main(String[] args) {
        String phone = "01012345678";
        String j = phone.substring(0,3);
        String k = phone.substring(3,7);
        String l = phone.substring(7);
        System.out.println(j + "-" + k + "-" + l);
    }
}
