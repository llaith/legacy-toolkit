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

import org.llaith.toolkit.common.exception.ext.UncheckedException;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;


public class ClasspathUtil {


    public static void loadClassFromFile(final String name) {
        loadClassFromFile(new File(name));
    }

    public static void loadClassFromFile(final File file) {
        try {
            loadClassFromUrl(file.toURI().toURL());
        } catch (MalformedURLException e) {
            throw UncheckedException.wrap(e);
        }
    }

    public static void loadClassFromUrl(final URL url) {
        try {
            final URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            final Method method = URLClassLoader.class.getDeclaredMethod("addURL",URL.class);
            method.setAccessible(true);
            method.invoke(sysloader,url);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw UncheckedException.wrap(e);
        }
    }

}

