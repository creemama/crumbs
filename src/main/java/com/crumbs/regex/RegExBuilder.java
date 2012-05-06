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
package com.crumbs.regex;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author Chris Topher
 * @version 0.0, 07/20/2009
 */
// TODO Describe being careful in calling something of these methods multiple
// times.
public interface RegExBuilder extends RegExCommon {
	/**
	 * Compiles the regular expression built thusfar into a pattern.
	 * 
	 * @throws PatternSyntaxException
	 *             if the expression's syntax is invalid
	 * @see Pattern#compile(String)
	 */
	Pattern compile() throws PatternSyntaxException;

	/**
	 * Compiles the regular expression built thusfar into a pattern with the
	 * given flags.
	 * 
	 * @param flags
	 *            Match flags, a bit mask that may include
	 *            {@link Pattern#CASE_INSENSITIVE}, {@link Pattern#MULTILINE},
	 *            {@link Pattern#DOTALL} , {@link Pattern#UNICODE_CASE},
	 *            {@link Pattern#CANON_EQ}, {@link Pattern#UNIX_LINES},
	 *            {@link Pattern#LITERAL} and {@link Pattern#COMMENTS}
	 * @throws IllegalArgumentException
	 *             if bit values other than those corresponding to the defined
	 *             match flags are set in {@code flags}
	 * @throws PatternSyntaxException
	 *             if the expression's syntax is invalid
	 * @see Pattern#compile(String, int)
	 */
	Pattern compile(int flags) throws PatternSyntaxException;

	/**
	 * Tells whether the regular expression built thusfar matches the given
	 * {@code str}.
	 * 
	 * @param regex
	 *            the regular expression to which this string is to be matched
	 * @return {@code true} if, and only if, this string matches the given
	 *         regular expression
	 * @throws IllegalArgumentException
	 *             if {@code str} is {@code null}
	 * @throws PatternSyntaxException
	 *             if the regular expression's syntax is invalid
	 */
	boolean matches(String str);

	/**
	 * Returns the regular expression built thusfar as a string.
	 * 
	 * @return non-{@code null} regular expression
	 */
	String toString();
}
