/*
 * Copyright 2002-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package fenghm.event4j.utils;

public abstract class Assert {

	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new IllegalArgumentException(message);
		}
	}
	
	public static void isFalse(boolean expression, String message) {
		if (expression) {
			throw new IllegalArgumentException(message);
		}
	}
	
	public static void isFalse(boolean expression, String template,Object... args) {
		if (expression) {
			throw new IllegalArgumentException(Strings.format(template, args));
		}
	}

	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new IllegalArgumentException(message);
		}
	}
	
	public static void isNull(Object object, String template,Object... args) {
		if (object != null) {
			throw new IllegalArgumentException(Strings.format(template, args));
		}
	}
	
	public static <T> T notNull(T object) {
		return notNull(object, "[Assertion failed] - this argument is required; it must not be null");
	}
	
	public static <T> T notNull(T object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
		return object;
	}
	
	public static <T> T notNull(T object, String template,Object... args) {
		if (object == null) {
			throw new IllegalArgumentException(Strings.format(template, args));
		}
		return object;
	}
}
