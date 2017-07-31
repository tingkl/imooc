import org.junit.Test;

/**
 * Created by tingkl on 2017/7/30.
 */
// 泛型只是给检查器使用的，编译通过后，类型约束就没有了
public class Generic {


    // <T> 表示这是一个泛型方法
    private static <T> void swap(T[] a, int i, int j) {
        T tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
        for (T t : a) {
            System.out.println(t.toString());
        }
    }

    private static <T> T autoConvert(Object obj) {
        return (T)obj;
    }

    @Test
    public void testConvert() {
        Object obj = "abc";
        // 根据指定的返回值类型String， 自动识别T
        String x3 = autoConvert(obj);
        System.out.printf("x3");
    }
    @Test
    public void testSwap() {
        swap(new String[] {"abc", "xyz", "itcast"}, 1, 2);
    }
}
