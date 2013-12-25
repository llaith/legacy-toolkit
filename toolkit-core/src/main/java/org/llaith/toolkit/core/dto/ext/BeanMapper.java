package org.llaith.toolkit.core.dto.ext;


import org.llaith.toolkit.core.dto.DtoObject;
import org.llaith.toolkit.common.util.reflection.PropertyFieldUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 14-Mar-2011
 * Time: 10:40:58
 */
public class BeanMapper<D extends DtoObject<D>,T> {

    public interface PropertyMapper<S,D> {

        public S toSource(D dest);

        public D toDest(S source);

    }

    private static Logger log = LoggerFactory.getLogger(BeanMapper.class);

    private final Map<String,PropertyMapper<Object,Object>> propertyMappers = new HashMap<>();

    public BeanMapper() {
        super();
    }

    public BeanMapper(final Map<String,PropertyMapper<Object,Object>> propertyMappers) {
        this.propertyMappers.putAll(propertyMappers);
    }

    public D toDto(T bean, D dto) {
        return this.toDto(
                bean,
                dto,
                dto.dtoType().names());
    }

    public D toDto(T bean, D dto, Iterable<String> fields) {
        for (String field : fields) {

            // call getter
            Object val = PropertyFieldUtil.fieldGet(bean,field);

            // remap if necessary
            if ((val != null) && (this.propertyMappers.containsKey(field))) {
                val = this.propertyMappers.get(field).toSource(val);
            }

            // add
            dto.set(field,val);

        }
        return dto;
    }

    public T toBean(D dto, T bean) {
        return this.toBean(
                dto,
                bean,
                dto.dtoType().names());
    }

    public T toBean(D dto, T bean, Iterable<String> fields) {
        for (String field : fields) {

            // get from map
            Object val = dto.get(field);

            if ((val != null) && (this.propertyMappers.containsKey(field))) {
                val = this.propertyMappers.get(field).toDest(val);
            }

            // call getter
            PropertyFieldUtil.fieldSet(bean,field,val);

        }
        return bean;
    }

}
