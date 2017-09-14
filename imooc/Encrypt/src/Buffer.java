import org.junit.Test;

/**
 * Created by tingkl on 2017/9/13.
 */
public class Buffer {

    @Test
    public void smallThan128() {
        String a = "abcdefghijklmn";
        byte[] bytes = a.getBytes();
        for (byte bt : bytes) {
            System.out.println(bt);
        }
        /*
        97
        98
        99
        100
        101
        102
        103
        104
        105
        106
        107
        108
        109
        110
        * */
    }

    @Test
    public void zw() {
        String a = "丁国梁";
        byte[] bytes = a.getBytes();
        for (byte bt : bytes) {
            System.out.println(bt);
        }
        /*
        -72
        -127
        -27
        -101
        -67
        -26
        -94
        -127
        */
    }
}
