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
package org.llaith.toolkit.core.fault;

/**
 *
 */
public class FaultSuppression {

    private final FaultLocation location;
    private final SuppressionToken token;

    public FaultSuppression(final FaultLocation location, final SuppressionToken token) {
        this.location = location;
        this.token = token;
    }

    public FaultLocation location() {
        return this.location;
    }

    public SuppressionToken token() {
        return this.token;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        final FaultSuppression that = (FaultSuppression) o;

        if (this.location != null ? !this.location.equals(that.location) : that.location != null) return false;
        if (this.token != null ? !this.token.equals(that.token) : that.token != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = this.location != null ? this.location.hashCode() : 0;
        result = 31 * result + (this.token != null ? this.token.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FaultSuppression{" +
                "location=" + this.location +
                ", token=" + this.token +
                '}';
    }

}
