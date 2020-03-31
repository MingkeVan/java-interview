package design_pattern;

/**
 * @program: interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription:
 * @date: 2020/3/1 23:09
 */
public class Singleton {

    //volatile可以保证有序性和可见性
    private volatile static Singleton instance = null;

    private Singleton() {

    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    //volatile禁止指令重排序
                    //下边步骤2和步骤3不会重排序，
                    //另外一个线程不会访问到一个未初始化的对象
                    //1.分配内存
                    //2.初始化
                    //3.变量赋值
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
