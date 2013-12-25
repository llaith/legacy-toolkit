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
