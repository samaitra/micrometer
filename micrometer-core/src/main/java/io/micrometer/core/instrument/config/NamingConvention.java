/**
 * Copyright 2017 Pivotal Software, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micrometer.core.instrument.config;

import io.micrometer.core.instrument.Meter;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Monitoring systems make different recommendations regarding naming convention.
 *
 * Also, many systems have constraints on valid characters that may appear
 * in a tag key/value or metric name. While it is recommended to choose tag
 * keys/values that are absent special characters that are invalid on any
 * common metrics backend, sometimes this is hard to avoid (as in the format
 * of the URI template for parameterized URIs like /api/person/{id} emanating
 * from Spring Web).
 *
 * @author Jon Schneider
 */
public interface NamingConvention {
    NamingConvention identity = (name, type, baseUnit) -> name;

    /**
     * This maps to identity because we suggest using dot notation everywhere in Micrometer-instrumented code
     */
    NamingConvention dot = identity;

    NamingConvention snakeCase = new NamingConvention() {
        @Override
        public String name(String name, Meter.Type type, String baseUnit) {
            return toSnakeCase(name);
        }

        @Override
        public String tagKey(String key) {
            return toSnakeCase(key);
        }

        private String toSnakeCase(String value) {
            return Arrays.stream(value.split("\\.")).filter(Objects::nonNull).collect(Collectors.joining("_"));
        }
    };

    NamingConvention camelCase = new NamingConvention() {
        @Override
        public String name(String name, Meter.Type type, String baseUnit) {
            return toCamelCase(name);
        }

        @Override
        public String tagKey(String key) {
            return toCamelCase(key);
        }

        private String toCamelCase(String value) {
            String[] parts = value.split("\\.");
            StringBuilder conventionName = new StringBuilder();
            for (int i = 0; i < parts.length; i++) {
                String str = parts[i];
                if (str == null || str.length() == 0)
                    continue;

                if (i == 0) {
                    conventionName.append(str);
                } else {
                    final char firstChar = str.charAt(0);
                    if (Character.isTitleCase(firstChar)) {
                        conventionName.append(str); // already capitalized
                    } else {
                        conventionName.append(String.valueOf(Character.toTitleCase(firstChar))).append(str.substring(1));
                    }
                }
            }
            return conventionName.toString();
        }
    };

    NamingConvention upperCamelCase = new NamingConvention() {
        @Override
        public String name(String name, Meter.Type type, String baseUnit) {
            String lowerCamel = camelCase.name(name, type, baseUnit);
            return capitalize(lowerCamel);
        }

        private String capitalize(String name) {
            if (name == null || name.length() == 0) {
                return name;
            }
            char chars[] = name.toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            return new String(chars);
        }
    };

    default String name(String name, Meter.Type type) {
        return name(name, type, null);
    }
    String name(String name, Meter.Type type, String baseUnit);

    default String tagKey(String key) { return key; }
    default String tagValue(String value) { return value; }
}
