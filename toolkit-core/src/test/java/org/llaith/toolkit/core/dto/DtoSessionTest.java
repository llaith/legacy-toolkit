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

import com.google.common.base.Supplier;
import org.junit.Before;
import org.junit.Test;
import org.llaith.toolkit.core.dto.instances.ContactDto;
import org.llaith.toolkit.core.dto.session.DtoBus;
import org.llaith.toolkit.core.dto.session.DtoSession;
import org.llaith.toolkit.core.dto.session.impl.GuavaDtoBus;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 *
 */
public class DtoSessionTest {

    private DtoSession sessions;

    @Before
    public void setUpSession() throws Exception {
        this.sessions = new DtoSession(new Supplier<DtoBus>() {
            @Override
            public DtoBus get() {
                return new GuavaDtoBus();
            }
        });
    }

    @Test
    public void simplePropertyShouldSyncThroughSession() throws Exception {

        final ContactDto a = sessions.addDto(newContact1().acceptChanges());
        final ContactDto b = sessions.addDto(newContact1().acceptChanges());

        a.setEmail("none").acceptChanges();

        assertThat(b.getEmail(),is(equalTo("none")));
    }

    @Test(expected = IllegalStateException.class)
    public void builderInstancesCannotBeAddedToSessions() throws Exception {
        this.sessions.addDto(newContact1());
    }

    private ContactDto newContact1() throws Exception {
        return ContactDto.unsavedInstance(
                "NOS01","Name","nos@address.com","555-1234",true,40l,
                newContact2(),
                null);
    }

    private ContactDto newContact2() throws Exception {
        return ContactDto.unsavedInstance("LAU02","Name","lua@address.com","555-2234",true,30l,
                null,
                new DtoCollection<>(Arrays.<ContactDto>asList(
                        newContact3(),
                        newContact4())));
    }

    private ContactDto newContact3() throws Exception {
        return ContactDto.unsavedInstance("LUK03","Name","luk@address.com","555-3234",true,10l,null,null);
    }

    private ContactDto newContact4() throws Exception {
        return ContactDto.unsavedInstance("LAN04","Name","lan@address.com","555-4234",true,5l,null,null);
    }

}
