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
package org.llaith.toolkit.common.depends;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DependencyFactoryTest {
    @Test
    public void testCalculate() throws Exception {

    }

    public static void main(final String[] args) {
        try {

            final List<Dependency<String>> strings = new ArrayList<>();
            strings.add(new Dependency<>("leaf",new String[]{"branch1","branch2"}));
            strings.add(new Dependency<>("branch1",new String[]{"trunk"}));
            strings.add(new Dependency<>("branch2",new String[]{"trunk"}));
            strings.add(new Dependency<>("trunk"));

            strings.add(new Dependency<>("branch1", new String[]{"branch2"}));
            strings.add(new Dependency<>("branch2", new String[]{"branch3"}));
            strings.add(new Dependency<>("branch3", new String[]{"branch1"}));

            final DependencyManager<String> depends = new DependencyFactory<String>().calculate(strings);

            System.out.println("all->base->: " + depends.generateOrderedList(DependencyManager.Direction.TOWARDS_BASE, DependencyManager.Order.BASE_FIRST));
            System.out.println("all->base<-: " + depends.generateOrderedList(DependencyManager.Direction.TOWARDS_BASE, DependencyManager.Order.TIP_FIRST));
            System.out.println("all->tip<-: " + depends.generateOrderedList(DependencyManager.Direction.TOWARDS_TIP, DependencyManager.Order.BASE_FIRST));
            System.out.println("all->tip->: " + depends.generateOrderedList(DependencyManager.Direction.TOWARDS_TIP, DependencyManager.Order.TIP_FIRST));

            System.out.println("sub->base->: " + depends.generateOrderedListSubset("branch1",true, DependencyManager.Direction.TOWARDS_BASE, DependencyManager.Order.BASE_FIRST));
            System.out.println("sub->base<-: " + depends.generateOrderedListSubset("branch1",true, DependencyManager.Direction.TOWARDS_BASE, DependencyManager.Order.TIP_FIRST));
            System.out.println("sub->tip<-: " + depends.generateOrderedListSubset("branch1",true, DependencyManager.Direction.TOWARDS_TIP, DependencyManager.Order.BASE_FIRST));
            System.out.println("sub->tip->: " + depends.generateOrderedListSubset("branch1",true, DependencyManager.Direction.TOWARDS_TIP, DependencyManager.Order.TIP_FIRST));

        } catch (CircularDependencyException e) {
            System.err.println(String.format("Circular: %s.",e.getCircular()));
        } catch (UnsatisfiedDependencyException e) {
            System.err.println("Unsatisfied: "+e.getUnsatisfied());
        } catch (MissingReverseDependencyException e) {
            System.err.println("There is no dependency information for: " + e.getMissing());
        }
    }




}
