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
 * Interface for regular-expression builders
 * 
 * @author Chris Topher
 * @version 0.0, 07/20/2009
 */
// TODO Add examples for matches
// TODO Replacement Syntax
public interface RegExBuilder extends CommonBuilder<RegExBuilder> {

	/**
	 * Appends each regular expression specified to the regular expression.
	 * 
	 * @param regEx
	 *            regular expression to append to this regular expression
	 *            builder
	 * @param regExN
	 *            other regular expressions to be appended to this regular
	 *            expression builder
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @throws IllegalArgumentException
	 *             if {@code regEx}, {@code regExN}, or one of the items in
	 *             {@code regExN} is {@code null}
	 * 
	 */
	RegExBuilder re(RegExBuilder regEx, RegExBuilder... regExN);

	/**
	 * Appends {@code obj.toString()} to the regular expression, escaping all
	 * special characters as needed.
	 * <p>
	 * The special characters are:
	 * </p>
	 * <p>
	 * <tt>\ [ ^ $ . | ? * + ( ) {</tt>
	 * </p>
	 * <p>
	 * Java regular expressions consider <tt>{</tt> a special character; other
	 * regular expression engines may not. If {@code objN} is specified, for
	 * each object in {@code objN} where 0 &le; <em>i</em> &lt;
	 * {@code objN.length}, <tt>objN[</tt><em>i</em><tt>].toString()</tt> is
	 * appended to the regular expression, escaping special characters as
	 * needed.
	 * </p>
	 * 
	 * @param obj
	 *            object whose string representation is to be incorporated into
	 *            this regular expression
	 * @param objN
	 *            array of other objects whose string representations are to be
	 *            incorporated into this regular expression
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @throws IllegalArgumentException
	 *             if any argument or any item in {@code objN} is {@code null}
	 */
	RegExBuilder re(Object obj, Object... objN);

	/**
	 * Calls {@code regEx(Byte.valueOf(number))}.
	 * <p>
	 * This is a convenience method.
	 * </p>
	 * 
	 * @param number
	 *            {@code byte} to be converted into a {@code String}
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @see #regEx(Object)
	 */
	RegExBuilder re(byte number);

	/**
	 * Calls {@code regEx(Short.valueOf(number))}.
	 * <p>
	 * This is a convenience method.
	 * </p>
	 * 
	 * @param number
	 *            {@code short} to be converted into a {@code String}
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @see #regEx(Object)
	 */
	RegExBuilder re(short number);

	/**
	 * Calls {@code regEx(Integer.valueOf(integer))}.
	 * <p>
	 * This is a convenience method.
	 * </p>
	 * 
	 * @param integer
	 *            {@code int} to be converted into a {@code String}
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @see #regEx(Object)
	 */
	RegExBuilder re(int integer);

	/**
	 * Calls {@code regEx(Long.valueOf(number))}.
	 * <p>
	 * This is a convenience method.
	 * </p>
	 * 
	 * @param number
	 *            {@code long} to be converted into a {@code String}
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @see #regEx(Object)
	 */
	RegExBuilder re(long number);

	/**
	 * Calls {@code regEx(Float.valueOf(number))}.
	 * <p>
	 * This is a convenience method. If the decimal separator {@code .} is part
	 * of the number, it is escaped.
	 * </p>
	 * 
	 * @param number
	 *            {@code float} to be converted into a {@code String}
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @see #regEx(Object)
	 */
	RegExBuilder re(float number);

	/**
	 * Calls {@code regEx(Double.valueOf(number))}.
	 * <p>
	 * This is a convenience method. If the decimal separator {@code .} is part
	 * of the number, it is escaped.
	 * </p>
	 * 
	 * @param number
	 *            {@code double} to be converted into a {@code String}
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @see #regEx(Object)
	 */
	RegExBuilder re(double number);

	/**
	 * Calls {@code regEx(Boolean.valueOf(primitive))}.
	 * <p>
	 * This is a convenience method.
	 * </p>
	 * 
	 * @param primitive
	 *            {@code boolean} to be converted into a {@code String}
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @see #regEx(Object)
	 */
	RegExBuilder re(boolean primitive);

	/**
	 * Calls {@code regEx(Character.valueOf(character))}.
	 * <p>
	 * This is a convenience method.
	 * </p>
	 * 
	 * @param character
	 *            {@code char} to be converted into a {@code String}
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @see #regEx(Object)
	 */
	RegExBuilder re(char character);

	/**
	 * Appends <tt>\Q</tt><em>obj</em><tt>\E</tt> to the regular expression,
	 * where <em>obj</em> is the string returned by {@code obj.toString()}.
	 * <p>
	 * Unlike {@link #regEx(Object)}, special characters are not escaped. If
	 * <em>obj</em> contains the string {@code "\\E"}, it is replaced with
	 * {@code "\\\\\\E\\QE"} so that the {@code "\\E"} is matched.
	 * </p>
	 * 
	 * @param obj
	 *            object whose string representation is to be incorporated into
	 *            this regular expression
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @throws IllegalArgumentException
	 *             if {@code obj} is {@code null}
	 */
	RegExBuilder quote(Object obj);

	/**
	 * Appends <em>X</em>|<em>Y</em> to the regular expression where <em>X</em>
	 * is {@code regEx0} and <em>Y</em> is {@code regEx1}.
	 * 
	 * @param regEx0
	 * @param regEx1
	 * @param regExN
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder orNoCaptureGroup(RegExBuilder regEx0, RegExBuilder regEx1, RegExBuilder... regExN);

	RegExBuilder orGroup(RegExBuilder regEx0, RegExBuilder regEx1, RegExBuilder... regExN);

	/**
	 * Appends <tt>#</tt> <em>space</em> <em>X</em> <em>newLine</em> to the
	 * regular expression where <em>X</em> is the {@code comment}.
	 * <p>
	 * Java uses this convention. Other regular expression engines use
	 * <tt>(#</tt><em>X</em><tt>)</tt>.
	 * </p>
	 * 
	 * @param comment
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	// TODO Discuss COMMENT flag
	RegExBuilder comment(String comment);

	// ===============
	// Character Class
	// ===============

	/**
	 * Appends <tt>\P{M}\p{M}*</tt> to the regular expression.
	 * <p>
	 * Since Java does not support the use of <tt>\X</tt>, <tt>\P{M}\p{M}*</tt>
	 * is used instead. <tt>\P{M}\p{M}*</tt> matches a single Unicode grapheme.
	 * A grapheme most closely resembles the everyday concept of a "character".
	 * </p>
	 * 
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder unicodeGrapheme();

	/**
	 * Appends a dot <tt>.</tt> to the regular expression.
	 * <p>
	 * The dot matches any character (except perhaps line terminators). That is,
	 * the dot matches any character except a line terminator unless the
	 * {@link java.util.regex.Pattern#DOTALL} flag is specified. By default, the
	 * {@code DOTALL} flag is not specified. A <em>line terminator</em> is a
	 * one- or two-character sequence that marks the end of a line of the input
	 * character sequence. The following are recognized as line terminators:
	 * </p>
	 * <ul>
	 * <li>A newline (line feed) character (<tt>'\n'</tt>),</li>
	 * <li>A carriage-return character followed immediately by a newline</li>
	 * character (<tt>"\r\n"</tt>),</li>
	 * <li>A standalone carriage-return character (<tt>'\r'</tt>),</li>
	 * <li>A next-line character (<tt>'&#92;u0085'</tt>),</li>
	 * <li>A line-separator character (<tt>'&#92;u2028'</tt>), or</li>
	 * <li>A paragraph-separator character (<tt>'&#92;u2029</tt>).</li>
	 * </ul>
	 * <p>
	 * If {@link java.util.regex.Pattern#UNIX_LINES} mode is activated, then the
	 * only line terminators recognized are newline characters. By default, the
	 * {@code UNIX_LINES} flag is not specified.
	 * </p>
	 * <p>
	 * Note that the <tt>.</tt> by itself does not match the line separator (
	 * <tt>"\r\n"</tt>). The regular expression <tt>..</tt> does match it when
	 * {@code DOTALL} is specified.
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td>{@code DOTALL} set</td>
	 * <td>{@code UNIX_LINES} set</td>
	 * <td>matches {@code '\r'}</td>
	 * <td>matches {@code '\n'}</td>
	 * </tr>
	 * <tr>
	 * <td>false</td>
	 * <td>false</td>
	 * <td>false</td>
	 * <td>false</td>
	 * </tr>
	 * <tr>
	 * <td>false</td>
	 * <td>true</td>
	 * <td>true</td>
	 * <td>false</td>
	 * </tr>
	 * <tr>
	 * <td>true</td>
	 * <td>false</td>
	 * <td>true</td>
	 * <td>true</td>
	 * </tr>
	 * <tr>
	 * <td>true</td>
	 * <td>true</td>
	 * <td>true</td>
	 * <td>true</td>
	 * </tr>
	 * </table>
	 * 
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder anyChar();

	/**
	 * Appends the character class <tt>[</tt><em>min</em><tt>-</tt><em>max</em>
	 * <tt>]</tt> to the regular expression.
	 * <p>
	 * <tt>[</tt><em>min</em><tt>-</tt><em>max</em> <tt>]</tt> matches any
	 * character in the range <em>min</em> to <em>max</em> inclusive.
	 * <em>min</em> or <em>max</em> can be one of many representations; for
	 * example, if <em>min</em> were <tt>A</tt>, it is perfectly valid for
	 * <em>min</em> to also be <tt>\0101</tt>, <tt>\x41</tt>, or <tt>\u0041</tt>
	 * since these are equivalent regular expressions to the regular expression
	 * <tt>A</tt>. It is up to implementations of this class to determine which
	 * representation to output.
	 * </p>
	 * 
	 * @param min
	 *            lower boundary of the range
	 * @param max
	 *            upper boundary of the range
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @throws IllegalArgumentException
	 *             if the numeric value of {@code min} is greater than
	 *             {@code max}
	 */
	RegExBuilder rangeClass(char min, char max);

	/**
	 * Appends the character class <tt>[^</tt><em>min</em><tt>-</tt><em>max</em>
	 * <tt>]</tt> to the regular expression.
	 * <p>
	 * <tt>[</tt><em>min</em><tt>-</tt><em>max</em> <tt>]</tt> matches any
	 * character not in the range <em>min</em> to <em>max</em> inclusive.
	 * <em>min</em> or <em>max</em> can be one of many representations; for
	 * example, if <em>min</em> were <tt>A</tt>, it is perfectly valid for
	 * <em>min</em> to also be <tt>\0101</tt>, <tt>\x41</tt>, or <tt>\u0041</tt>
	 * since these are equivalent regular expressions to the regular expression
	 * <tt>A</tt>. It is up to implementations of this class to determine which
	 * representation to output.
	 * </p>
	 * 
	 * @param min
	 *            lower boundary of the range
	 * @param max
	 *            upper boundary of the range
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @throws IllegalArgumentException
	 *             if the numeric value of {@code min} is greater than
	 *             {@code max}
	 */
	RegExBuilder notRangeClass(char min, char max);

	/**
	 * Appends the character class <tt>[</tt><em>c</em><sub>0</sub><em>c</em>
	 * <sub>1</sub>...<em>c<sub>n</sub></em><tt>]</tt> to the regular
	 * expression.
	 * 
	 * @param c0
	 *            first character literal
	 * @param c1
	 *            second character literal
	 * @param cN
	 *            if specified, an array containing the third character literal
	 *            to the (<em>n</em> + 1)th character literal
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder charClass(char c0, char c1, char... cN);

	/**
	 * Appends the character class <tt>[^</tt><em>c</em><sub>0</sub><em>c</em>
	 * <sub>1</sub>...<em>c<sub>n</sub></em><tt>]</tt> to the regular
	 * expression.
	 * 
	 * @param c0
	 *            first character literal
	 * @param c1
	 *            second character literal
	 * @param cN
	 *            if specified, an array containing the third character literal
	 *            to the (<em>n</em> + 1)th character literal
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder notCharClass(char c0, char c1, char... cN);

	/**
	 * Appends the character class <tt>[</tt>{@code str}<tt>]</tt> to the
	 * regular expression escaping any characters in {@code str} as needed.
	 * 
	 * @param str
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	// TODO What to do about duplicates in the string?
	RegExBuilder charClass(String str);

	/**
	 * Appends the character class <tt>[^</tt>{@code str}</tt>]</tt> to the
	 * regular expression escaping any characters in {@code str} as needed.
	 * 
	 * @param str
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	// TODO What to do about duplicates in the string?
	RegExBuilder notCharClass(String str);

	/**
	 * Appends the character class <tt>[</tt><em>C</em><sub>0</sub>...
	 * <em>C<sub>n</sub></em><tt>]</tt> to the regular expression.
	 * 
	 * @param c0
	 * @param cN
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder charClass(CharClassBuilder charClass);

	RegExBuilder notCharClass(CharClassBuilder charClass);

	// TODO documentation
	RegExBuilder union(CharClassBuilder charClass0, CharClassBuilder charClass1);

	RegExBuilder intersection(CharClassBuilder charClass0, CharClassBuilder charClass1);

	// =================
	// Boundary Matchers
	// =================

	/**
	 * Appends <tt>^</tt> to the regular expression.
	 * 
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	// TODO discuss MULTILINE and UNIX_LINES flag
	RegExBuilder matchLineStart();

	/**
	 * Appends <tt>$</tt> to the regular expression.
	 * 
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	// TODO discuss MULTILINE and UNIX_LINES flag
	RegExBuilder matchLineEnd();

	/**
	 * Appends <tt>\A</tt> to the regular expression.
	 * 
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder matchInputStart();

	/**
	 * Appends <tt>\Z</tt> to the regular expression.
	 * 
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder matchInputEnd();

	/**
	 * Appends <tt>\z</tt> to the regular expression.
	 * 
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder matchInputEndStrict();

	/**
	 * Appends <tt>\b</tt> to the regular expression.
	 * 
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder wordBoundary();

	/**
	 * Appends <tt>\B</tt> to the regular expression.
	 * 
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder notWordBoundary();

	/**
	 * Appends <tt>\G</tt> to the regular expression.
	 * 
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder previousMatchEnd();

	// ==================
	// Greedy Quantifiers
	// ==================

	/**
	 * Appends <em>X</em><tt>?</tt> to the regular expression.
	 * 
	 * @param regEx
	 * @param regExN
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder optional(RegExBuilder regEx, RegExBuilder... regExN);

	/**
	 * Appends <em>X</em><tt>*</tt> to the regular expression.
	 * 
	 * @param regEx
	 * @param regExN
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder zeroOrMore(RegExBuilder regEx, RegExBuilder... regExN);

	/**
	 * Appends <em>X</em>+ to the regular expression.
	 * 
	 * @param regEx
	 * @param regExN
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder oneOrMore(RegExBuilder regEx, RegExBuilder... regExN);

	/**
	 * Appends <em>X</em><tt>{</tt><em>n</em><tt>}</tt> to the regular
	 * expressions where <em>n</em> is {@code nTimes}.
	 * 
	 * @param nTimes
	 * @param regEx
	 * @param regExN
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder repeat(int nTimes, RegExBuilder regEx, RegExBuilder... regExN);

	/**
	 * Appends <em>X</em><tt>{</tt><em>n</em><tt>,}</tt> to the regular
	 * expression where <em>n</em> is {@code nTimes}.
	 * 
	 * @param nTimes
	 * @param regEx
	 * @param regExN
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder repeatAtLeast(int nTimes, RegExBuilder regEx, RegExBuilder... regExN);

	/**
	 * Appends <em>X</em><tt>{</tt><em>n</em><tt>,</tt><em>m</em><tt>}</tt> to
	 * the regular expression where <em>n</em> is {@code atLeastNTimes} and
	 * <em>m</em> is {@code atMostMTimes}.
	 * 
	 * @param atLeastNTimes
	 * @param atMostMTimes
	 * @param regEx
	 * @param regExN
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder repeat(int atLeastNTimes, int atMostMTimes, RegExBuilder regEx, RegExBuilder... regExN);

	// ================
	// Lazy Quantifiers
	// ================

	/**
	 * Appends <em>X</em><tt>??</tt> to the regular expression.
	 * 
	 * @param regEx
	 * @param regExN
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder optionalLazy(RegExBuilder regEx, RegExBuilder... regExN);

	/**
	 * Appends <em>X</em><tt>*?</tt> to the regular expression.
	 * 
	 * @param regEx
	 * @param regExN
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder zeroOrMoreLazy(RegExBuilder regEx, RegExBuilder... regExN);

	/**
	 * Appends <em>X</em><tt>+?</tt> to the regular expression.
	 * 
	 * @param regEx
	 * @param regExN
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @return
	 */
	RegExBuilder oneOrMoreLazy(RegExBuilder regEx, RegExBuilder... regExN);

	/**
	 * Appends <em>X</em><tt>{</tt><em>n</em><tt>}?</tt> to the regular
	 * expressions where <em>n</em> is {@code nTimes}.
	 * 
	 * @param nTimes
	 * @param regEx
	 * @param regExN
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder repeatLazy(int nTimes, RegExBuilder regEx, RegExBuilder... regExN);

	/**
	 * Appends <em>X</em><tt>{</tt><em>n</em><tt>,}?</tt> to the regular
	 * expression where <em>n</em> is {@code nTimes}.
	 * 
	 * @param nTimes
	 * @param regEx
	 * @param regExN
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder repeatAtLeastLazy(int nTimes, RegExBuilder regEx, RegExBuilder... regExN);

	/**
	 * Appends <em>X</em><tt>{</tt><em>n</em><tt>,</tt><em>m</em><tt>}?</tt> to
	 * the regular expression where <em>n</em> is {@code atLeastNTimes} and
	 * <em>m</em> is {@code atMostMTimes}.
	 * 
	 * @param atLeastNTimes
	 * @param atMostMTimes
	 * @param regEx
	 * @param regExN
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder repeatLazy(int atLeastNTimes, int atMostMTimes, RegExBuilder regEx, RegExBuilder... regExN);

	// ======================
	// Possessive Quantifiers
	// ======================

	/**
	 * Appends <em>X</em><tt>?+</tt> to the regular expression.
	 * 
	 * @param regEx
	 * @param regExN
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder optionalPossessive(RegExBuilder regEx, RegExBuilder... regExN);

	/**
	 * Appends <em>X</em><tt>*+</tt> to the regular expression.
	 * 
	 * @param regEx
	 * @param regExN
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder zeroOrMorePossessive(RegExBuilder regEx, RegExBuilder... regExN);

	/**
	 * Appends <em>X</em><tt>++</tt> to the regular expression.
	 * 
	 * @param regEx
	 * @param regExN
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder oneOrMorePossessive(RegExBuilder regEx, RegExBuilder... regExN);

	/**
	 * Appends <em>X</em><tt>{</tt><em>n</em><tt>}+</tt> to the regular
	 * expressions where <em>n</em> is {@code nTimes}.
	 * 
	 * @param nTimes
	 * @param regEx
	 * @param regExN
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder repeatPossessive(int nTimes, RegExBuilder regEx, RegExBuilder... regExN);

	/**
	 * Appends <em>X</em><tt>{</tt><em>n</em><tt>,}+</tt> to the regular
	 * expression where <em>n</em> is {@code nTimes}.
	 * 
	 * @param nTimes
	 * @param regEx
	 * @param regExN
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder repeatAtLeastPossessive(int nTimes, RegExBuilder regEx, RegExBuilder... regExN);

	/**
	 * Appends <em>X</em><tt>{</tt><em>n</em><tt>,</tt><em>m</em><tt>}+</tt> to
	 * the regular expression where <em>n</em> is {@code atLeastNTimes} and
	 * <em>m</em> is {@code atMostMTimes}.
	 * 
	 * @param atLeastNTimes
	 * @param atMostMTimes
	 * @param regEx
	 * @param regExN
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder repeatPossessive(int atLeastNTimes, int atMostMTimes, RegExBuilder regEx, RegExBuilder... regExN);

	// ======
	// Groups
	// ======

	/**
	 * Appends <tt>(</tt><em>X</em><tt>)</tt> to the regular expression.
	 * 
	 * @param regEx
	 * @param regExN
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder group(RegExBuilder regEx, RegExBuilder... regExN);

	/**
	 * Appends <tt>(?:</tt><em>X</em><tt>)</tt> to the regular expression.
	 * 
	 * @param regEx
	 * @param regExN
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder noCaptureGroup(RegExBuilder regEx, RegExBuilder... regExN);

	/**
	 * Appends <tt>(?idmsux-idmsux:</tt><em>X</em><tt>)</tt> to the regular
	 * expression.
	 * 
	 * @param regEx
	 * @param flag
	 * @param flagN
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder noCaptureGroup(RegExBuilder regEx, RegExMatchFlag flag, RegExMatchFlag... flagN);

	/**
	 * Appends <tt>(?idmsux-idmsux)</tt> to the regular expression.
	 * 
	 * @param flag
	 * @param flagN
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	// what happens when multiple references are in the list
	RegExBuilder turnOnOffMatchFlags(RegExMatchFlag flag, RegExMatchFlag... flagN);

	/**
	 * Appends <tt>(?&gt;</tt><em>X</em><tt>)</tt> to the regular expression.
	 * 
	 * @param regEx
	 * @param regExN
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	// also known as an independent group
	RegExBuilder atomicGroup(RegExBuilder regEx, RegExBuilder... regExN);

	/**
	 * Appends <tt>\</tt><em>n</em> to the regular expression where <em>n</em>
	 * is the <em>n</em>th {@code captureGroup}.
	 * 
	 * @param captureGroup
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	RegExBuilder backReference(int captureGroup);

	// ==========
	// Lookaround
	// ==========

	/**
	 * Appends <tt>(?=</tt><em>X</em><tt>)</tt> to the regular expression.
	 * 
	 * @param lookahead
	 * @param lookaheadN
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @throws IllegalArgumentException
	 *             if {@code lookahead}, {@code lookaheadN}, or one of the items
	 *             in {@code lookaheadN} is {@code null}
	 */
	RegExBuilder positiveLookahead(RegExBuilder lookahead, RegExBuilder... lookaheadN);

	/**
	 * Appends <tt>(?!</tt><em>X</em><tt>)</tt> to the regular expression.
	 * 
	 * @param lookahead
	 * @param lookaheadN
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @throws IllegalArgumentException
	 *             if {@code lookahead}, {@code lookaheadN}, or one of the items
	 *             in {@code lookaheadN} is {@code null}
	 */
	RegExBuilder negativeLookahead(RegExBuilder lookahead, RegExBuilder... lookaheadN);

	/**
	 * Appends <tt>(?&lt;=</tt><em>X</em><tt>)</tt> to the regular expression.
	 * 
	 * @param lookbehind
	 * @param lookbehindN
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @throws IllegalArgumentException
	 *             if {@code lookbehind}, {@code lookbehindN}, or one of the
	 *             items in {@code lookbehindN} is {@code null}
	 */
	RegExBuilder positiveLookbehind(RegExBuilder lookbehind, RegExBuilder... lookbehindN);

	/**
	 * Appends <tt>(?!</tt><em>X</em><tt>)</tt> to the regular expression.
	 * 
	 * @param lookbehind
	 * @param lookbehindN
	 * @return {@code this} T or a {@code new} one
	 * @throws IllegalArgumentException
	 *             if {@code lookbehind}, {@code lookbehindN}, or one of the
	 *             items in {@code lookbehindN} is {@code null}
	 */
	RegExBuilder negativeLookbehind(RegExBuilder lookbehind, RegExBuilder... lookbehindN);

	/**
	 * Returns the regular expression built thusfar as a string.
	 * 
	 * @return non-{@code null} regular expression
	 */
	@Override
	String toString();

	/**
	 * Returns the regular expression built thusfar as code in the flavor of
	 * this regular-expression builder.
	 * 
	 * @return non-{@code null} code snippet
	 */
	String asCode();
}
