/**
 * Created by tingkl on 2017/8/7.
 */
public class GenericClass <E>{
    /*
    *  类中有多个方法需要使用方向，使用类级别的泛型，静态方法不需要类的创建，所以不知道具体的泛型类型，所以静态方法不属于泛型类控制，需要独自定义为泛型方法
    *
    * */

    public E findById(int id) {
        return null;
    }

    public void update(E obj) {}

    public static <T> void update2(T obj) {

    }

}
