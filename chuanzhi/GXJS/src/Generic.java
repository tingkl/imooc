import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Vector;

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

    /*
    * java中的泛型类型类似于C++中的模板。但是这种相似性仅限于表面，java语言中的泛型基本上完全是在编译器中实现，由于编译器执行类型检查和类型推断，然后生成普通的非泛型的字节码，
    * 这种实现技术成为擦除（编译器使用泛型类型信息保证类型安全，然后在生成字节码之前将其清除）。
    * */


    // 用反射的方式获取泛型的实际参数类型

    @Test
    public void test() {
        try {
            Method applyMethod = Generic.class.getMethod("applyVector", Vector.class);
            Type[] types = applyMethod.getGenericParameterTypes();
            ParameterizedType pType = (ParameterizedType) types[0];
            System.out.println(pType.getRawType());
            // HashMap<K,V>可能有多个泛型类型K，V，所以是一个数组
            System.out.println(pType.getActualTypeArguments()[0]);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }
    public static void applyVector(Vector<Date> v1) {
        // v1本身是无法得知Date类型，因为编译过后，已经成为普通的字节码
        // 但是当我们把变量交给一个方法去使用的时候，这个方法提供了一些方法获得它的参数列表
    }
}
