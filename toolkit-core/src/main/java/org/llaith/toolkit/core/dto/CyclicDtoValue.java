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

/**
 * Note: Could not think of a better way to do this, so forced to do a nasty
 * hack to deal with cycles without passing any objects down the interface
 * (yuck). This means it's a tad fragile. I moved it into it's own wrapper
 * class in case I need to drop it in a hurry. Obviously not threadsafe in any
 * way.
 */
public class CyclicDtoValue<T> extends DtoValue<T> {

    private transient boolean checkDirty = false;
    private transient boolean checkStale = false;
    private transient boolean checkConflicted = false;
    private transient boolean skipDirty = false;
    private transient boolean skipStale = false;
    private transient boolean skipConflicted = false;
    private transient boolean skipAccept = false;
    private transient boolean skipCancel = false;

    public CyclicDtoValue(DtoField<T> dtoField) {
        super(dtoField);
    }

    public CyclicDtoValue(DtoValue<T> dtoValue) {
        super(dtoValue);
    }

    @Override
    public boolean isDirty() {
        // short circuit
        if (this.skipDirty) {
            this.skipDirty = false;
            return this.checkDirty;
        }

        // eval
        this.skipDirty = true;
        try {
            this.checkDirty = super.isDirty();
            return this.checkDirty;
        } finally {
            this.skipDirty = false;
        }
    }

    @Override
    public boolean isStale() {
        // short circuit
        if (this.skipStale) {
            this.skipStale = false;
            return this.checkStale;
        }

        // eval
        this.skipStale = true;
        try {
            this.checkStale = super.isStale();
            return this.checkStale;
        } finally {
            this.skipStale = false;
        }
    }

    @Override
    public boolean isConflicted() {
        // short circuit
        if (this.skipConflicted) {
            this.skipConflicted = false;
            return this.checkConflicted;
        }

        // eval
        this.skipConflicted = true;
        try {
            this.checkConflicted = super.isConflicted();
            return this.checkConflicted;
        } finally {
            this.skipConflicted = false;
        }
    }

    @Override
    public CyclicDtoValue<T> acceptChanges() {
        // short circuit
        if (this.skipAccept) {
            this.skipAccept = false;
            return this;
        }

        // eval
        this.skipAccept = true;
        try {
            super.acceptChanges();
        } finally {
            this.skipAccept = false;
        }
        return this;
    }

    @Override
    public CyclicDtoValue<T> cancelChanges() {
        // short circuit
        if (this.skipCancel) {
            this.skipCancel = false;
            return this;
        }

        // eval
        this.skipCancel = true;
        try {
            super.cancelChanges();
        } finally {
            this.skipCancel = false;
        }
        return this;
    }

    @Override
    public <T> T addMetadata(T metadata) {
        return super.addMetadata(metadata);
    }

    @Override
    public <T> T replaceMetadata(T metadata) {
        return super.replaceMetadata(metadata);
    }

    @Override
    public <T> T metadata(Class<T> metadataClass) {
        return super.metadata(metadataClass);
    }

    @Override
    public boolean hasMetadata(Class<?> metadataClass) {
        return super.hasMetadata(metadataClass);
    }

    @Override
    public <T> T removeMetadata(Class<T> metadataClass) {
        return super.removeMetadata(metadataClass);
    }

}
