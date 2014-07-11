package org.blaise.firestarter;

/*
 * #%L
 * Firestarter
 * --
 * Copyright (C) 2009 - 2014 Elisha Peterson
 * --
 * Licensed under the Apache License, Version 2.0.
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.google.common.base.Predicate;
import java.beans.PropertyDescriptor;

/**
 * Encodes possible filters for bean patterns.
 */
public enum BeanFilterRule implements Predicate<PropertyDescriptor> {

    /** Matches non-expert, non-hidden patterns with read and write methods */
    STANDARD {
        public boolean apply(PropertyDescriptor pd) {
            return pd.getWriteMethod() != null && pd.getReadMethod() != null && !pd.isExpert() && !pd.isHidden();
        }
        @Override public String toString() { return "standard properties (read and write)"; }
    },
    
    /** Matches non-expert, non-hidden patterns with read or write methods */
    STANDARD_READ_OR_WRITE {
        public boolean apply(PropertyDescriptor pd) {
            return (pd.getWriteMethod() != null || pd.getReadMethod() != null) && !pd.isExpert() && !pd.isHidden();
        }
        @Override public String toString() { return "standard properties (read or write)"; }
    },
    
    /** Matches preferred patterns */
    PREFERRED {
        public boolean apply(PropertyDescriptor pd) {
            return pd.isPreferred();
        }
        @Override public String toString() { return "preferred properties"; }
    },
    
    /** Matches expert patterns */
    EXPERT {
        public boolean apply(PropertyDescriptor pd) {
            return pd.isExpert();
        }
        @Override public String toString() { return "expert properties"; }
    },
    
    /** Matches all patterns. */
    ALL {
        public boolean apply(PropertyDescriptor pd) {
            return true;
        }
        @Override public String toString() { return "all properties"; }
    },
    
    /** Matches all patterns without a write method */
    READ_ONLY {
        public boolean apply(PropertyDescriptor pd) {
            return pd.getWriteMethod() == null;
        }
        @Override public String toString() { return "read-only properties"; }
    },
    
    /** Matches bound patterns with a write method */
    BOUND {
        public boolean apply(PropertyDescriptor pd) {
            return pd.isBound() && pd.getWriteMethod() != null;
        }
        @Override public String toString() { return "bound properties"; }
    },
    
    /** Matches constrained patterns with a write method */
    CONSTRAINED {
        public boolean apply(PropertyDescriptor pd) {
            return pd.isConstrained() && pd.getWriteMethod() != null;
        }
        @Override public String toString() { return "constrained properties"; }
    },
    
    /** Matches hidden patterns */
    HIDDEN {
        public boolean apply(PropertyDescriptor pd) {
            return pd.isHidden();
        }
        @Override public String toString() { return "hidden properties"; }
    };


    /** Converts string filter to a property filter, using the property name. */
    public static Predicate<PropertyDescriptor> byName(final Predicate<String> propertyFilter) {
        return new Predicate<PropertyDescriptor>(){
            public boolean apply(PropertyDescriptor input) {
                return propertyFilter == null || propertyFilter.apply(input.getName());
            }
        };
    }
    
}
