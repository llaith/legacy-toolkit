package org.llaith.toolkit.dbadapter.sql2o;

import com.google.common.base.Supplier;
import org.sql2o.Sql2o;

import javax.sql.DataSource;

/**
 *
 */
public class Sql2oSupplier implements Supplier<Sql2o> {

    private final Supplier<DataSource> dataSourceSupplier;

    public Sql2oSupplier(final Supplier<DataSource> dataSourceSupplier) {
        this.dataSourceSupplier = dataSourceSupplier;
    }

    @Override
    public Sql2o get() {
        return new Sql2o(this.dataSourceSupplier.get());
    }

}
