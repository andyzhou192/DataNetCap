package com.common.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
 
/**
 * 反射工具类
 *
 * @author liujiduo
 *
 */
public class ReflectUtil {
 
    /**
     * 反射调用指定构造方法创建对象
     *
     * @param clazz
     *            对象类型
     * @param argTypes
     *            参数类型
     * @param args
     *            构造参数
     * @return 返回构造后的对象
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InstantiationException
     *
     */
    public static <T> T invokeConstructor(Class<T> clazz, Class<?>[] argTypes,
            Object[] args) throws NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Constructor<T> constructor = clazz.getConstructor(argTypes);
        return constructor.newInstance(args);
    }
    
    /**
     * 调用指定方法
     * @param target 目标对象（即方法所属类的对象）
     * @param methodName 方法名称
     * @return
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static <T> Object invokeMethod(T target, String methodName, Method method, Object...args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
    	if(null == args)
    		return method.invoke(target);
        return method.invoke(target, args);
    }
    
    /**
     * 获取method对象
     * @param clazz
     * @param methodName
     * @param parameterTypes
     * @return
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    public static Method getMethod(Class<?> clazz, String methodName, Class<?>...parameterTypes) throws NoSuchMethodException, SecurityException{
    	Method method = null;
    	if(null != parameterTypes)
    		method = clazz.getMethod(methodName, parameterTypes);
    	else
    		method = clazz.getMethod(methodName);
    	return method;
    }
 
    /**
     * 反射调用指定对象属性的getter方法
     *
     * @param <T>
     *            泛型
     * @param target
     *            指定对象
     * @param fieldName
     *            属性名
     * @return 返回调用后的值
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     *
     */
    public static <T> Object invokeGetter(T target, String fieldName)
            throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        // 如果属性名为xxx，则方法名为getXxx
        String methodName = "get" + StringUtil.firstCharUpperCase(fieldName);
        Method method = ReflectUtil.getMethod(target.getClass(), methodName);
        return ReflectUtil.invokeMethod(target, methodName, method);
    }
 
    /**
     * 反射调用指定对象属性的setter方法
     *
     * @param <T>
     *            泛型
     * @param target
     *            指定对象
     * @param fieldName
     *            属性名
     * @param argTypes
     *            参数类型
     * @param args
     *            参数列表
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     *
     */
    public static <T> void invokeSetter(T target, String fieldName, Object args)
            throws NoSuchFieldException, SecurityException,
            NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        // 如果属性名为xxx，则方法名为setXxx
        String methodName = "set" + StringUtil.firstCharUpperCase(fieldName);
        Class<?> clazz = target.getClass();
        Field field = clazz.getDeclaredField(fieldName);
//        Method method = clazz.getMethod(methodName, field.getType());
//        method.invoke(target, args);
        Method method = ReflectUtil.getMethod(target.getClass(), methodName, field.getType());
        ReflectUtil.invokeMethod(target, methodName, method, args);
    }
 
}

