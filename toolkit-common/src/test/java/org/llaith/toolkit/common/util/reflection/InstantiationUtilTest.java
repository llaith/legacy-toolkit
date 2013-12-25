package org.llaith.toolkit.common.util.reflection;

/**
 *
 */
public class InstantiationUtilTest {



    // TODO: turn into test in toolkit
    public static void main(final String[] args) {

        try {
            output(int.class.getName());
            output(int[].class.getName());
            output(Integer[].class.getName());
            output(Integer[][].class.getName());

            output(InstanceUtil.getReflectionName(int.class));
            output(InstanceUtil.getReflectionName(int[].class));
            output(InstanceUtil.getReflectionName(Integer[][].class));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public static void output(final String name) throws ClassNotFoundException {
        System.out.println(String.format("For the name '%s' we get the class '%s'.",name, InstanceUtil.forReflectionName(name).getName()));
    }

}
