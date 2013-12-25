/*******************************************************************************

  * Copyright (c) 2005 - 2013 Nos Doughty
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *     http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.

 ******************************************************************************/
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
