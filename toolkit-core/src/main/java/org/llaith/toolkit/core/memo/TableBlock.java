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
package org.llaith.toolkit.core.memo;

/**
 *
 */
public class TableBlock extends Block {

    private final String headers;
    private final String rowFormat;
    private final Object[][] rowData;

    public TableBlock(String headers, String rowFormat, Object[][] rowData) {
        super();
        this.headers = headers;
        this.rowFormat = rowFormat;
        this.rowData = rowData;
    }

    public TableBlock(String title, String headers, String rowFormat, Object[][] rowData) {
        super(title);
        this.headers = headers;
        this.rowFormat = rowFormat;
        this.rowData = rowData;
    }

    public String headers() {
        return headers;
    }

    public String rowFormat() {
        return rowFormat;
    }

    public Object[][] rowData() {
        return rowData;
    }

}
