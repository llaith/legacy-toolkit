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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Allows multiple errors/exceptions, useful when compiling
 * Allows warnings to be ignored.
 *
 * Rules:
 * 1) Only one validation per location
 * 2) Skips a validation if the subset of the location has already failed.
 *
 */
public class FaultManager {

    private static final Logger logger = LoggerFactory.getLogger(Fault.class);

    // to help some logic it's neater if we get an exception
    public static class ErrorException extends Exception {
        public ErrorException() {
            super();
        }
    }

    private final Map<FaultLocation,Set<SuppressionToken>> suppressions = new HashMap<>();

    private final Map<FaultLocation,Fault> errors = new HashMap<>();
    private final Map<FaultLocation,Fault> warnings = new HashMap<>();

    public FaultManager(final Collection<FaultSuppression> suppressions) {
        this.suppressions.putAll(this.indexSuppressions(suppressions));
    }

    private Map<FaultLocation,Set<SuppressionToken>> indexSuppressions(final Collection<FaultSuppression> suppressions) {
        final Map<FaultLocation,Set<SuppressionToken>> m = new HashMap<>();
        for (FaultSuppression suppression : suppressions) {
            if (!m.containsKey(suppression.location())) m.put(suppression.location(),new HashSet<SuppressionToken>());
            m.get(suppression.location()).add(suppression.token());
        }
        return m;
    }

    public boolean addFault(final Fault fault) {
        if (this.isLocationOk(fault.location())) {
            if (this.isSuppressed(fault)) {
                this.storeWarning(fault);
                return true;
            } else this.storeError(fault);
        }
        return false;
    }

    public void addFaultAndThrow(final Fault fault) throws FaultManager.ErrorException {
        if (!this.addFault(fault)) throw new FaultManager.ErrorException();
    }

    public boolean hasWarnings() {
        return !this.warnings.isEmpty();
    }


    public List<Fault> warnings() {
        return new ArrayList<>(this.warnings.values());
    }

    public boolean hasErrors() {
        return !this.errors.isEmpty();
    }


    public List<Fault> errors() {
        return new ArrayList<>(this.errors.values());
    }

    public boolean isLocationOk(final FaultLocation location) {
        for (FaultLocation chk : this.errors.keySet()) {
            if (chk.includes(location)) return false;
        }
        return true;
    }

    public void displayErrors(final FaultRenderer display) {

        for (final Fault error : this.errors.values()) {
            display.displayFault(error);
        }

    }

    public void displayWarnings(final FaultRenderer display) {

        for (final Fault error : this.warnings.values()) {
            display.displayFault(error);
        }

    }

    private boolean isSuppressed(final Fault fault) {
        return this.suppressions.containsKey(fault.location()) &&
                this.suppressions.get(fault.location()).contains(fault.suppressionToken());

    }

    private void storeWarning(final Fault fault) {
        this.warnings.put(
                fault.location(),
                fault);
    }

    private void storeError(final Fault fault) {
        this.errors.put(
                fault.location(),
                fault);
    }

}
