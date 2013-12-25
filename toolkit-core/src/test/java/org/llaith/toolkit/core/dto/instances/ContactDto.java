package org.llaith.toolkit.core.dto.instances;

import com.google.common.reflect.TypeToken;
import org.llaith.toolkit.core.dto.DtoCollection;
import org.llaith.toolkit.core.dto.DtoField;
import org.llaith.toolkit.core.dto.DtoObject;
import org.llaith.toolkit.core.dto.DtoType;
import org.llaith.toolkit.common.lang.Primitive;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
@SuppressWarnings("unchecked")
public class ContactDto extends DtoObject<ContactDto> {

    public static interface Fields {
        public static final DtoField<String> ID = DtoField.newReference("id",String.class,true,true,true);
        public static final DtoField<String> NAME = DtoField.newReference("name",String.class,true,true,false);
        public static final DtoField<String> EMAIL = DtoField.newReference("email",String.class,true,false,false);
        public static final DtoField<String> PHONE = DtoField.newReference("phone",String.class,false,false,false);

        public static final DtoField<Boolean> CONTACTABLE = DtoField.newPrimitive("contactable",Primitive.BOOLEAN);
        public static final DtoField<Long> AGE = DtoField.newPrimitive("age",Primitive.LONG);

        public static final DtoField<ContactDto> PARTNER = DtoField.newDtoObject("partner",ContactDto.class,false);
        public static final DtoField<DtoCollection<ContactDto>> RELATIONS = DtoField.newDtoCollection("relations",new TypeToken<DtoCollection<ContactDto>>() {
        });
    }

    public static final List<DtoField<?>> fields = Arrays.asList(
            Fields.ID,
            Fields.NAME,
            Fields.EMAIL,
            Fields.PHONE,
            Fields.CONTACTABLE,
            Fields.AGE,
            Fields.PARTNER,
            Fields.RELATIONS);


    public static ContactDto unsavedInstance() {
        return new ContactDto();
    }

    public static ContactDto unsavedInstance(final String name, final String email, final String phone,
                                             final Boolean contact, final Long age,
                                             final ContactDto partner, final DtoCollection<ContactDto> relations) {
        return new ContactDto(name,email,phone,contact,age,partner,relations);
    }

    public static ContactDto unsavedInstance(final String id, final String name, final String email, final String phone,
                                             final Boolean contact, final Long age,
                                             final ContactDto partner, final DtoCollection<ContactDto> relations) {
        return new ContactDto(false,id,name,email,phone,contact,age,partner,relations);
    }

    public static ContactDto savedInstance(final String id, final String name, final String email, final String phone,
                                           final Boolean contact, final Long age,
                                           final ContactDto partner, final DtoCollection<ContactDto> relations) {
        return new ContactDto(true,id,name,email,phone,contact,age,partner,relations);
    }

    private ContactDto() {
        super(new DtoType<>(ContactDto.class,fields));
    }

    private ContactDto(final String name, final String email, final String phone,
                      final Boolean contact, final Long age,
                      final ContactDto partner, final DtoCollection<ContactDto> relations) {

        super(new DtoType<>(ContactDto.class,fields));

        this.setName(name);
        this.setEmail(email);
        this.setPhone(phone);
        this.setContact(contact);
        this.setAge(age);
        this.setPartner(partner);
        this.setRelations(relations);
    }

    private ContactDto(final boolean accept, final String id,
                      final String name, final String email, final String phone,
                      final Boolean contact, final Long age,
                      final ContactDto partner, final DtoCollection<ContactDto> relations) {

        super(new DtoType<>(ContactDto.class,fields),new FieldInit(accept)
                .initField(Fields.ID,id)
                .initField(Fields.NAME,name)
                .initField(Fields.EMAIL,email)
                .initField(Fields.PHONE,phone)
                .initField(Fields.CONTACTABLE,contact)
                .initField(Fields.AGE,age)
                .initField(Fields.PARTNER,partner)
                .initField(Fields.RELATIONS,relations));
    }

    public String getId() {
        return this.get(Fields.ID);
    }

    public ContactDto setId(final String id) {
        this.set(Fields.ID,id);
        return this;
    }

    public String getName() {
        return this.get(Fields.NAME);
    }

    public ContactDto setName(final String name) {
        this.set(Fields.NAME,name);
        return this;
    }

    public String getEmail() {
        return this.get(Fields.EMAIL);
    }

    public ContactDto setEmail(final String email) {
        this.set(Fields.EMAIL,email);
        return this;
    }

    public String getPhone() {
        return this.get(Fields.PHONE);
    }

    public ContactDto setPhone(final String phone) {
        this.set(Fields.PHONE,phone);
        return this;
    }

    public Boolean getContact() {
        return this.get(Fields.CONTACTABLE);
    }

    public ContactDto setContact(final Boolean contact) {
        this.set(Fields.CONTACTABLE,contact);
        return this;
    }

    public Long getAge() {
        return this.get(Fields.AGE);
    }

    public ContactDto setAge(final Long age) {
        this.set(Fields.AGE,age);
        return this;
    }

    public ContactDto getPartner() {
        return this.get(Fields.PARTNER);
    }

    public ContactDto setPartner(final ContactDto partner) {
        this.set(Fields.PARTNER,partner);
        return this;
    }

    public DtoCollection<ContactDto> getRelations() {
        return this.get(Fields.RELATIONS);
    }

    public ContactDto setRelations(final DtoCollection<ContactDto> relations) {
        this.set(Fields.RELATIONS,relations);
        return this;
    }

    @Override
    public ContactDto getThis() {
        return this;
    }

}
