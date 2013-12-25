package org.llaith.toolkit.common.lang;

import org.llaith.toolkit.common.guard.Guard;

import javax.annotation.Nullable;

/**
 * Don't forget there is also the Guava Primitives class.
 */
public class Primitive<T> {

    // http://stackoverflow.com/questions/1040868/java-syntax-and-meaning-behind-b1ef9157-binary-address
    public static Primitive<Void> VOID = new Primitive<>(void.class,Void.class,null,null);
    public static Primitive<Boolean> BOOLEAN = new Primitive<>(boolean.class,Boolean.class,"Z",false);
    public static Primitive<Byte> BYTE = new Primitive<>(byte.class,Byte.class,"B",(byte)0);
    public static Primitive<Character> CHAR = new Primitive<>(char.class,Character.class,"C",(char)0);
    public static Primitive<Short> SHORT = new Primitive<>(short.class,Short.class,"S",(short)0);
    public static Primitive<Integer> INT = new Primitive<>(int.class,Integer.class,"I",0);
    public static Primitive<Long> LONG = new Primitive<>(long.class,Long.class,"J",0l);
    public static Primitive<Float> FLOAT = new Primitive<>(float.class,Float.class,"F",0.0f);
    public static Primitive<Double> DOUBLE = new Primitive<>(double.class,Double.class,"D",0.0);

    public static Primitive<?>[] primitives = {VOID,BOOLEAN,BYTE,CHAR,SHORT,INT,LONG,FLOAT,DOUBLE};

    private final Class<?> primitiveClass;
    private final Class<T> wrapperClass;
    private final String arrayClassSymbol;
    private final T nullValue;

    Primitive(final Class<?> primitiveClass, final Class<T> wrapperClass, final String arrayClassSymbol, final T nullValue) {
        this.primitiveClass = Guard.notNull(primitiveClass);
        this.wrapperClass = Guard.notNull(wrapperClass);
        this.arrayClassSymbol = arrayClassSymbol;
        this.nullValue = nullValue;
    }

    public Class<?> primitiveClass() {
        return primitiveClass;
    }

    public Class<T> wrapperClass() {
        return wrapperClass;
    }

    public String arrayClassSymbol() {
        return arrayClassSymbol;
    }

    public T nullValue() {
        return nullValue;
    }

    public static boolean isPrimitiveType(final String className) {
        for (final Primitive<?> p : Primitive.primitives) {
            if (p.primitiveClass().getName().equals(className)) return true;
        }
        return false;
    }

    @Nullable
    public static Primitive<?> primitiveFor(final Class<?> type) {
        return primitiveForName(type.getName());
    }

    @Nullable
    public static Primitive<?> primitiveForName(final String className) {
        for (final Primitive<?> p : Primitive.primitives) {
            if (p.primitiveClass().getName().equals(className)) return p;
        }
        return null; // Expect() if you care
    }

    public static boolean isWrapperType(final String className) {
        for (final Primitive<?> p : Primitive.primitives) {
            if (p.wrapperClass().getName().equals(className)) return true;
        }
        return false;
    }

    public static boolean isWrapperType(final Class<?> clazz) {
        for (final Primitive<?> p : Primitive.primitives) {
            if (p.wrapperClass().getName().equals(clazz.getName())) return true;
        }
        return false;
    }

}
