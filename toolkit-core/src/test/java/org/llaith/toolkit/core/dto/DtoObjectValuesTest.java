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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

/**
 *
 */
public class DtoObjectValuesTest {

    @Test
    public void testRelations() throws Exception {

        final ContactDto inner = this.newContact1();
        final ContactDto outer = this.newContact2().setPartner(inner);

        assumeThat(inner.isDirty(),is(true));
        assertThat(outer.isDirty(),is(true));

        outer.acceptChanges();

        assumeThat(inner.isDirty(),is(false));
        assertThat(outer.isDirty(),is(false));

        inner.setEmail("newemail@email.com");

        assumeThat(inner.isDirty(),is(true));
        assertThat(outer.isDirty(),is(true));

    }

    @Test
    public void testCycles() throws Exception {

        /*
         * TODO: Test fails. No cycle detection at the moment. Not clear how to
         * add it either! Needs to also be added to DtoCollections too. Perhaps
         * it can be added to the DtoValue instead of the DtoObject/Collection?
         *
         * What you would be saying is that a given DtoValue would only pass a
         * command down once per 'run'. The question is how do you reset it per
         * run? You'd have to cache the value also to avoid the return vals
         * being screwy.
         */

        final ContactDto inner = this.newContact1();
        final ContactDto outer = this.newContact2();

        outer.setPartner(inner);
        inner.setPartner(outer);

        outer.acceptChanges();

        assumeThat(inner.isDirty(),is(false));
        assertThat(outer.isDirty(),is(false));

        inner.setEmail("newemail@email.com");

        assumeThat(inner.isDirty(),is(true));
        assertThat(outer.isDirty(),is(true));

    }

    private ContactDto newContact1() throws Exception {
        return ContactDto.unsavedInstance("LUK03","Name","luk@address.com","555-3234",true,10l,null,null);
    }

    private ContactDto newContact2() throws Exception {
        return ContactDto.unsavedInstance("LAN04","Name","lan@address.com","555-4234",true,5l,null,null);
    }

}
