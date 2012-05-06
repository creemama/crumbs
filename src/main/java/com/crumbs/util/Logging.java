/**
 * Copyright 2009 Creemama
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.crumbs.util;

import static java.text.MessageFormat.format;
import static java.util.ResourceBundle.getBundle;

import java.text.ParseException;
import java.util.ResourceBundle;

/**
 * @author Chris Topher
 * @version 0.0, Aug 29, 2009
 */
public class Logging {
	private static final LoggingUtil logging = new LoggingUtil(getBundle("com.crumbs.util.crumbs"));

	private Logging() {
		// prevent instantiation
	}

	public static String getString(String patternKey, Object... arguments) {
		return logging.getString(patternKey, arguments);
	}

	public static IllegalArgumentException illegalNullArg(Class<?> argType, String argName) {
		return logging.illegalNullArg(argType, argName);
	}

	public static IllegalArgumentException illegalNullArrayItemArg(Class<?> argType, String argName) {
		return logging.illegalNullArrayItemArg(argType, argName);
	}

	public static IllegalArgumentException illegalOutsideSetArg(Class<?> argType, String argName,
			Object invalidArgValue, String validSetOfValues) {
		return logging.illegalOutsideSetArg(argType, argName, invalidArgValue, validSetOfValues);
	}

	public static IllegalArgumentException illegalEmptyStringArg(String argName) {
		return logging.illegalEmptyStringArg(argName);
	}

	public static IllegalArgumentException illegalArg(String patternKey, Object... arguments) {
		return logging.illegalArg(patternKey, arguments);
	}

	public static ParseException parseException(String patternKey, Object... arguments) {
		return logging.parseException(patternKey, arguments);
	}

	public static Error error() {
		return logging.error();
	}

	static class LoggingUtil {
		private final ResourceBundle bundle;

		LoggingUtil(ResourceBundle bundle) {
			this.bundle = bundle;
		}

		String getString(String patternKey, Object... arguments) {
			return format(this.bundle.getString(patternKey), arguments);
		}

		IllegalArgumentException illegalNullArg(Class<?> argType, String argName) {
			return illegalArg("illegal.argument.null", argType.getSimpleName(), argName);
		}

		IllegalArgumentException illegalNullArrayItemArg(Class<?> argType, String argName) {
			return illegalArg("illegal.argument.null.array.item", argType.getSimpleName(), argName);
		}

		IllegalArgumentException illegalOutsideSetArg(Class<?> argType, String argName, Object invalidArgValue,
				String validSetOfValues) {
			return illegalArg("illegal.argument.outside.set", argType.getSimpleName(), argName, invalidArgValue,
					validSetOfValues);
		}

		IllegalArgumentException illegalEmptyStringArg(String argName) {
			return illegalArg("illegal.argument.string.empty", argName);
		}

		IllegalArgumentException illegalArg(String patternKey, Object... arguments) {
			return new IllegalArgumentException(getString(patternKey, arguments));
		}

		ParseException parseException(String patternKey, Object... arguments) {
			return new ParseException(getString(patternKey, arguments), 0);
		}

		Error error() {
			return new Error(this.bundle.getString("error"));
		}
	}
}
