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

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;


/**
 * Test the basic change behaviour and builder transitions.
 */
public class DtoFlagsTest {

    @Test
    public void settingValuesOnNewFlagsAsDirty() throws Exception {
        assertTrue(newContact()
                .setEmail("newemail@address.com")
                .isDirty());
    }

    @Test
    public void newInstancesStayDirtyWithAnyEdit() throws Exception {
        final ContactDto target = newContact();

        assumeTrue(target.setEmail("newemail@address.com").isDirty());

        // because it was never accepted, original is still null
        assertTrue(target.setEmail("email@address.com").isDirty());
    }

    @Test
    public void settingNewValuesOnExistingFlagsItAsDirty() throws Exception {
        assertTrue(newContact()
                .acceptChanges()
                .setEmail("newemail@address.com")
                .isDirty());
    }

    @Test
    public void existingInstancesUnflagAsDirtyIfSetBackToOldValue() throws Exception {
        final ContactDto target = newContact().acceptChanges();

        assumeTrue(target.setEmail("newemail@address.com").isDirty());

        assertFalse(target.setEmail("email@address.com").isDirty());
    }

    @Test
    public void settingNewValuesDoesNotFlagAsStale() throws Exception {
        assertFalse(newContact().acceptChanges().setEmail("newemail@address.com").isStale());
    }

    @Test
    public void resettingNewValuesDoesNotFlagAsDirty() throws Exception {
        assertFalse(newContact().acceptChanges().reset("email","newemail@address.com").isDirty());
    }

    @Test
    public void resettingNewValuesFlagsItAsStale() throws Exception {
        assertTrue(newContact().acceptChanges().reset("email","newemail@address.com").isStale());
    }

    @Test
    public void resettingNewValuesSameAsOriginalUnflagsItAsStale() throws Exception {

        final ContactDto target = newContact().acceptChanges();

        assumeTrue(target.reset("email","newemail@address.com").isStale());

        assertTrue(target.reset("email","email@address.com").isStale());
    }

    @Test
    public void settingAndResettingWillFlagAsConflicted() throws Exception {
        assertTrue(newContact()
                .acceptChanges()
                .set("email","newemail@address.com")
                .reset("email","neweremail@address.com")
                .isStale());
    }

    @Test
    public void acceptingConflictedInstancesWillResolveConflict() throws Exception {
        assertFalse(newContact()
                .acceptChanges()
                .set("email","newemail@address.com")
                .reset("email","neweremail@address.com")
                .acceptChanges()
                .isStale());
    }

    @Test
    public void cancellingConflictedInstancesWillResolveConflict() throws Exception {
        assertFalse(newContact()
                .acceptChanges()
                .set("email","newemail@address.com")
                .reset("email","neweremail@address.com")
                .cancelChanges()
                .isStale());
    }

    private ContactDto newContact() throws Exception {
        return ContactDto.unsavedInstance("NOS01","Name","email@address.com","555-1234",true,40l,null,null);
    }

}
