package com.fanmk.design_pattern;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * @program: com.fanmk.interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription:
 * @date: 2020/3/1 23:17
 */
public class Singleton_innerclass implements Serializable {
static ThreadLocal threadLocal = new ThreadLocal();

    private Singleton_innerclass() {
    threadLocal.set("111");
    }

    private static class SingletonHolder {
        private static final Singleton_innerclass instance = new Singleton_innerclass();

    }

    public static Singleton_innerclass getInstance() {
        return SingletonHolder.instance;
    }
    /**
     * 访问权限为private/protected
     * 返回值必须是Object
     * 异常可以不抛
     *
     * @return
     * @throws ObjectStreamException
     */
    protected Object readResolve() throws ObjectStreamException {
        return SingletonHolder.instance;
    }
}
