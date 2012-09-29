/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fenghm.event4j.utils;


/**
 * <code>null</code> safe {@link String} utility
 */
public class Strings {

	public static final String	EMPTY	        = "";
	public static final String	COMMA	        = ",";

	protected Strings() {

	}

	// Fomatting
	// -----------------------------------------------------------------------
	public static String format(String template, Object... args) {
		if (isEmpty(template)) {
			return EMPTY;
		}

		char[] templateChars = template.toCharArray();

		int templateLength = templateChars.length;
		int length = 0;
		int tokenCount = args.length;
		for (int i = 0; i < tokenCount; i++) {
			Object sourceString = args[i];
			if (sourceString != null) {
				length += sourceString.toString().length();
			}
		}

		// The following buffer size is just an initial estimate. It is legal for
		// any given pattern, such as {0}, to occur more than once, in which case
		// the buffer size will expand automatically if need be.
		StringBuilder buffer = new StringBuilder(length + templateLength);

		int lastStart = 0;
		for (int i = 0; i < templateLength; i++) {
			char ch = templateChars[i];
			if (ch == '{') {
				// Only check for single digit patterns that have an associated token.
				if (i + 2 < templateLength && templateChars[i + 2] == '}') {
					int tokenIndex = templateChars[i + 1] - '0';
					if (tokenIndex >= 0 && tokenIndex < tokenCount) {
						buffer.append(templateChars, lastStart, i - lastStart);
						Object sourceString = args[tokenIndex];
						if (sourceString != null)
							buffer.append(sourceString.toString());

						i += 2;
						lastStart = i + 1;
					}
				}
			}
			// ELSE: Do nothing. The character will be added in later.
		}

		buffer.append(templateChars, lastStart, templateLength - lastStart);

		return new String(buffer);
	}

	// Empty checks
	// -----------------------------------------------------------------------
	public static boolean isEmpty(CharSequence string) {
		return string == null || string.length() == 0;
	}

	public static boolean isEmpty(Object string) {
		if (null == string) {
			return true;
		} else if (string instanceof CharSequence) {
			return isEmpty((CharSequence) string);
		}
		return false;
	}

	public static boolean isNotEmpty(CharSequence string) {
		return null != string && string.length() > 0;
	}

	public static boolean isNotEmpty(Object string) {
		if (null == string) {
			return false;
		} else if (string instanceof CharSequence) {
			return isNotEmpty((CharSequence) string);
		}
		return false;
	}

	// Trim
	//-----------------------------------------------------------------------
	public static String trim(String string) {
		return string == null ? EMPTY : string.trim();
	}

	public static String trimOrNull(String string) {
		return string == null ? null : string.trim();
	}

	public static String trimToNull(String string) {
		String ts = trimOrNull(string);
		return isEmpty(ts) ? null : ts;
	}
}
