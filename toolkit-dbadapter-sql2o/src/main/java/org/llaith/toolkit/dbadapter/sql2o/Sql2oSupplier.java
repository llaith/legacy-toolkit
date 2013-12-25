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
