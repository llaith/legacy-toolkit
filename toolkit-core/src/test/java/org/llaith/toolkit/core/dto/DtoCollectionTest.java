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
package org.llaith.toolkit.core.dto;

import org.junit.Test;
import org.llaith.toolkit.core.dto.instances.ContactDto;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

/**
 *
 */
public class DtoCollectionTest {

    @Test
    public void removingElementsThenAcceptingShrinksAndDirtiesTheCollection() throws Exception {

        final ContactDto one = newChild1().acceptChanges();
        final ContactDto two = newChild2().acceptChanges();

        assumeThat(one.isDirty() || two.isDirty(),is(false));

        final DtoCollection<ContactDto> children = new DtoCollection<>(Arrays.<ContactDto>asList(
                one,
                two)).remove(one);

        assertThat(children.isDirty(),is(true));
        assertThat(children.size(),is(1));
        assertThat(children.contains(two),is(true));

        children.acceptChanges();

        assertThat(children.isDirty(),is(false));
        assertThat(children.size(),is(1));
        assertThat(children.contains(two),is(true));
    }

    @Test
    public void addingElementsThenAcceptingExpandsAndDirtiesTheCollection() throws Exception {

        final ContactDto one = newChild1().acceptChanges();
        final ContactDto two = newChild2().acceptChanges();

        assumeThat(one.isDirty() || two.isDirty(),is(false));

        final DtoCollection<ContactDto> children = new DtoCollection<>(Arrays.<ContactDto>asList(one)).add(two);

        assertThat(children.isDirty(),is(true));
        assertThat(children.size(),is(2));
        assertThat(children.contains(two),is(true));

        children.acceptChanges();

        assertThat(children.isDirty(),is(false));
        assertThat(children.size(),is(2));
        assertThat(children.contains(two),is(true));
    }

    @Test
    public void removingElementThenCancellingDoesNotChangeOrDirtyCollection() throws Exception {

        final ContactDto one = newChild1().acceptChanges();
        final ContactDto two = newChild2().acceptChanges();

        assumeThat(one.isDirty() || two.isDirty(),is(false));

        final DtoCollection<ContactDto> children = new DtoCollection<>(Arrays.<ContactDto>asList(
                one,
                two)).remove(one);

        assertThat(children.isDirty(),is(true));
        assertThat(children.size(),is(1));
        assertThat(children.contains(two),is(true));

        children.cancelChanges();

        assertThat(children.isDirty(),is(false));
        assertThat(children.size(),is(2));
        assertThat(children.contains(two),is(true));
        assertThat(children.contains(one),is(true));
    }

    @Test
    public void addingElementThenCancellingDoesNotChangeOrDirtyCollection() throws Exception {

        final ContactDto one = newChild1().acceptChanges();
        final ContactDto two = newChild2().acceptChanges();

        assumeThat(one.isDirty() || two.isDirty(),is(false));

        final DtoCollection<ContactDto> children = new DtoCollection<>(Arrays.<ContactDto>asList(one)).add(two);

        assertThat(children.isDirty(),is(true));
        assertThat(children.size(),is(2));
        assertThat(children.contains(two),is(true));

        children.cancelChanges();

        assertThat(children.isDirty(),is(false));
        assertThat(children.size(),is(1));
        assertThat(children.contains(two),is(false));
    }

    @Test
    public void changingNestedDtoThenAcceptingDirtiesCollection() throws Exception {

        final ContactDto one = newChild1().acceptChanges();

        assumeThat(one.isDirty(),is(false));

        final DtoCollection<ContactDto> children = new DtoCollection<>(Arrays.<ContactDto>asList(one));

        one.setEmail("changed@email.com");

        assertThat(children.isDirty(),is(true));
        assertThat(children.size(),is(1));

        children.acceptChanges();

        assertThat(children.isDirty(),is(false));
        assertThat(children.size(),is(1));
        assertThat(one.getEmail(),is(equalTo("changed@email.com")));
    }

    @Test
    public void changingNestedDtoThenCancellingDoesNotChangeOrDirtyCollection() throws Exception {

        final ContactDto one = newChild1().acceptChanges();

        assumeThat(one.isDirty(),is(false));

        final DtoCollection<ContactDto> children = new DtoCollection<>(Arrays.<ContactDto>asList(one));

        one.setEmail("changed@email.com");

        assertThat(children.isDirty(),is(true));
        assertThat(children.size(),is(1));

        children.cancelChanges();

        assertThat(children.isDirty(),is(false));
        assertThat(children.size(),is(1));
        assertThat(one.getEmail(),is(equalTo("luk@address.com")));
    }

    @Test
    public void mutatingThenAcceptingNestedCollectionDirtiesParentDto() throws Exception {

        final ContactDto one = newChild1().acceptChanges();

        final ContactDto master = newContact1(new DtoCollection<>(Arrays.<ContactDto>asList(one))).acceptChanges();

        assumeThat(master.isDirty(),is(false));

        one.setEmail("changed@email.com");

        assertThat(master.isDirty(),is(true));
        assertThat(master.acceptChanges().isDirty(),is(false));
    }

    @Test
    public void mutatingThenCancellingNestedCollectionDoesNotDirtiesParentDto() throws Exception {

        final ContactDto one = newChild1().acceptChanges();

        final ContactDto master = newContact1(new DtoCollection<>(Arrays.<ContactDto>asList(one))).acceptChanges();

        assumeThat(master.isDirty(),is(false));

        one.setEmail("changed@email.com");

        assertThat(master.isDirty(),is(true));
        assertThat(master.cancelChanges().isDirty(),is(false));
    }

    private ContactDto newContact1(final DtoCollection<ContactDto> children) {
        return ContactDto.unsavedInstance("LAU02","Name","lua@address.com","555-2234",true,30l,
                null,
                children);
    }

    private ContactDto newChild1() {
        return ContactDto.unsavedInstance("LUK03","Name","luk@address.com","555-3234",true,10l,null,null);
    }

    private ContactDto newChild2() {
        return ContactDto.unsavedInstance("LAN04","Name","lan@address.com","555-4234",true,5l,null,null);
    }

}
