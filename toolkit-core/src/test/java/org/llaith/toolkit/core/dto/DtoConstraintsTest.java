package org.llaith.toolkit.core.dto;

import org.junit.Test;
import org.llaith.toolkit.core.dto.instances.ContactDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assume.assumeThat;


/**
 * Test the basic change behaviour and builder transitions.
 */
public class DtoConstraintsTest {

    @Test(expected = IllegalStateException.class)
    public void clearingRequiredValuesWillErrorInIdentifiedMode() throws Exception {
        newContact().acceptChanges().setEmail(null);
    }

    @Test
    public void clearingRequiredValuesIsOkWhenNew() throws Exception {
        assertThat(
                newContact().setEmail(null).getEmail(),
                is(nullValue()));
    }

    @Test(expected = IllegalStateException.class)
    public void identifiedInstancesDontAllowContantsToChange() throws Exception {
        newContact().acceptChanges().setName("New Name");
    }

    @Test
    public void builderInstancesDoAllowConstantsToChange() throws Exception {
        assertThat(
                newContact().setName("New Name").getName(),
                is(equalTo("New Name")));
    }

    @Test
    public void changingCurrentValuesWorksAsExpected() throws Exception {
        assumeThat(
                newContact().setName("New Name").getName(),
                is(equalTo("New Name")));
    }

    private ContactDto newContact() throws Exception {
        return ContactDto.unsavedInstance("NOS01","Name","email@address.com","555-1234",true,40l,null,null);
    }

}
