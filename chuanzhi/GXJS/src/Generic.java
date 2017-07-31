import org.junit.Test;

/**
 * Created by tingkl on 2017/7/30.
 */
public class Generic {

    private static <T> void swap(T[] a, int i, int j) {
        T tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
        for (T t : a) {
            System.out.println(t.toString());
        }
    }

    @Test
    public void testSwap() {
        swap(new String[] {"abc", "xyz", "itcast"}, 1, 2);
    }
}
