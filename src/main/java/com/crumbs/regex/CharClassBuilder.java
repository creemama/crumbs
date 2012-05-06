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

/**
 * Regular expression part that can only exist inside of a character class
 * 
 * @author Chris Topher
 * @version 0.0, Jul 20, 2009
 */
public interface CharClassBuilder extends CommonBuilder<CharClassBuilder> {

	/**
	 * Appends <em>min</em><tt>-</tt><em>max</em> to this character class
	 * 
	 * @param min
	 * @param max
	 * @return
	 * @throws IllegalArgumentException
	 *             if {@code min} is greater than {@code max}
	 */
	CharClassBuilder range(char min, char max);

	/**
	 * Appends <em>c</em><sub>0</sub><em>c</em><sub>1</sub>...
	 * <em>c<sub>n</sub></em> to the character class escaping any special
	 * characters as needed.
	 * 
	 * @param c0
	 *            character to append to this character class
	 * @param cN
	 *            array of other characters to append to this character class
	 * @return
	 */
	// TODO what is done with duplicates
	CharClassBuilder c(char c0, char... cN);

	/**
	 * Appends {@code str} to the character class escaping any special
	 * characters in {@code str} as needed.
	 * 
	 * @param str
	 * @return {@code this} CharClassBuilder
	 */
	CharClassBuilder str(String str);
}