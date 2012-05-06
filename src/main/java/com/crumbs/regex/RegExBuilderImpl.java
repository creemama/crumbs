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

import static com.crumbs.util.Logging.illegalNullArg;
import static com.crumbs.util.Logging.illegalNullArrayItemArg;
import static com.crumbs.util.Logging.illegalOutsideSetArg;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Implementation of RegExBuilder.
 * <p>
 * If an exception is thrown by one of the methods of this class, the regular
 * expression stored by this class is suspect. That is, the regular expression
 * may or may not be valid.
 * </p>
 * 
 * @author Chris Topher
 * @version 0.0, 07/20/2009
 */
// TODO Check that RegExBuilderImpl is necessary as a return argument
class RegExBuilderImpl extends AbstractBuilder<RegExBuilderImpl> implements
		RegExBuilder {

	private static final String[] specialCharacters = //
	new String[] { "\\", //$NON-NLS-1$ // must be first in the list because latter characters are escaped, and we don't want this to escape again
			"[", //$NON-NLS-1$
			"^", //$NON-NLS-1$
			"$", //$NON-NLS-1$
			".", //$NON-NLS-1$
			"|", //$NON-NLS-1$
			"?", //$NON-NLS-1$
			"*", //$NON-NLS-1$
			"+", //$NON-NLS-1$
			"(", //$NON-NLS-1$
			")", //$NON-NLS-1$
			"{" //$NON-NLS-1$ // not really a special character, but Java thinks so
	};

	/**
	 * Constructs a {@code RegExBuilderImpl} with the specified backing {@code
	 * StringBuilder}.
	 * <p>
	 * {@code regEx} is assumed to be non-<tt>null</tt>.
	 * </p>
	 * 
	 * @param regEx
	 *            backing string buffer used to build the regular expression
	 */
	RegExBuilderImpl(StringBuilder regEx) {
		super(regEx);
	}

	@Override
	protected RegExBuilderImpl thiz() {
		return this;
	}

	/**
	 * Appends first {@code obj.toString()} and subsequently the string
	 * representation of each object in {@code objN} to the regular expression.
	 * 
	 * @param obj
	 *            object whose string representation is to be appended to this
	 *            regular expression
	 * @param objN
	 *            array of objects where each object's string representation is
	 *            to be appended to this regular expression
	 * @return this {@code RegExBuilder}
	 * @throws IllegalArgumentException
	 *             if any argument is {@code null} or if any item in {@code
	 *             objN} is {@code null}
	 */
	private RegExBuilderImpl t(Object obj, Object[] objN) {
		// #t(Object} throws an IllegalArgumentException if obj is null
		t(obj);
		if (objN == null) {
			throw illegalNullArg(Object[].class, "objN"); //$NON-NLS-1$
		}
		for (Object o : objN) {
			t(o);
		}
		return this;
	}

	/**
	 * Appends {@code regEx.toString()} and subsequently the string
	 * representation of each object in {@code regExN} to this regular
	 * expression builder (wrapping the just appended text in a no-capture group
	 * if necessary).
	 * <p>
	 * This method is only used by quantifier methods like
	 * {@link #optional(RegExBuilder, RegExBuilder...)}. The regular expression
	 * arguments combined are to be treated as one grouping so that methods like
	 * {@link #optional(RegExBuilder, RegExBuilder...)} can apply the quantifier
	 * to the entire grouping. If it is determined that the regular expression
	 * arguments combined cannot be treated as one grouping, the regular
	 * expression appended to this builder is surrounded by a no-capture group,
	 * <tt>(?</tt><em>X</em><tt>)</tt> where <em>X</em> is the regular
	 * expression arguments combined.
	 * </p>
	 * 
	 * @param regEx
	 *            regular expression to be appended to this regular expression
	 * @param regExN
	 *            array of regular expressions to be appended to this regular
	 *            expression
	 * @return
	 * @throws IllegalArgumentException
	 *             if any argument is {@code null} or if an item in {@code
	 *             regExN} is {@code null}
	 */
	// TODO Give examples
	private RegExBuilderImpl tGroup(RegExBuilder regEx, RegExBuilder[] regExN) {
		if (regEx == null) {
			throw illegalNullArg(RegExBuilder.class, "regEx"); //$NON-NLS-1$
		}
		if (regExN == null) {
			throw illegalNullArg(RegExBuilder[].class, "regExN"); //$NON-NLS-1$
		}

		String str;

		if (regExN.length == 0) {
			str = regEx.toString();
		} else {
			StringBuilder builder = new StringBuilder(regEx.toString());
			for (RegExBuilder item : regExN) {
				if (item == null) {
					throw illegalNullArrayItemArg(RegExBuilder[].class,
							"regExN"); //$NON-NLS-1$
				}
				builder.append(item.toString());
			}
			str = builder.toString();
		}
		if (isRegExUnit(str)) {
			t(str);
		} else {
			t("(?:").t(str).t(")"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return this;
	}

	/**
	 * Returns {@code true} is the specified {@code str} is one regular
	 * expression unit.
	 * <p>
	 * A regular expression unit is a regular expression such that, when the
	 * regular expression is followed by a quantifer metacharacter, it does not
	 * have to be surrounded by a no-capture group so that the quantifier
	 * applies to the whole regular expression. Valid regular expression units
	 * are:
	 * </p>
	 * <ul>
	 * <li>one character in one of its many manifestations (e.g., <tt>A</tt>,
	 * octal <tt>\0101</tt>, ascii <tt>\x41</tt>, unicode <tt>\u0041</tt>, or
	 * control character <tt>\cJ</tt>)</li>
	 * <li>one group, <tt>(</tt><em>X</em><tt>)</tt></li>
	 * <li>one character class (<tt>[</tt><em>X</em><tt>]</tt>, <tt>\p{</tt>
	 * <em>blockOrCategory</em><tt>}</tt>, or <tt>\P{</tt>
	 * <em>blockOrCategory</em><tt>}</tt>)</li>
	 * <li>backreferences <tt>\0</tt> to <tt>\9</tt></li>
	 * </ul>
	 * 
	 * @param str
	 *            string to examine
	 * @return true if {@code str} is one regular expression unit
	 * @throws NullPointerException
	 *             if {@code str} is {@code null} (although this should never
	 *             happen)
	 */
	private boolean isRegExUnit(String str) {
		if (str.length() <= 1) {
			return true;
		} else if (isOneGroup(str)) {
			return true;
		} else if (str.charAt(0) == '[' && str.charAt(str.length() - 1) == ']') {
			return isOneGrouping(str, '[', ']');
		} else if (str.charAt(0) == '\\') {
			if (str.length() == 2) {
				// numbered backreferences \0 to \9 are covered here
				return true;
			}
			switch (str.charAt(1)) {
			case '0':
				return str.matches("\\\\0[0-3]?[0-7]?[0-7]"); //$NON-NLS-1$
			case 'x':
				return str.matches("\\\\x[0-9A-Fa-f]{2}"); //$NON-NLS-1$
			case 'u':
				return str.matches("\\\\u[0-9A-Fa-f]{4}"); //$NON-NLS-1$
			case 'c':
				if (str.length() > 3) {
					return false;
				}
				char c = str.charAt(2);
				return ('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z');
			case 'p':
				return str.matches("\\\\p\\{[^}]+\\}"); //$NON-NLS-1$
			case 'P':
				return str.matches("\\\\P\\{[^}]+\\}"); //$NON-NLS-1$
			}
		}
		// numbered backreferences aren't included (if therey're more than two
		// digits, then we'll just surround it by parenthesis anyway)
		return false;
	}

	private boolean isOneGroup(String str) {
		if (str.length() > 1 && str.charAt(0) == '('
				&& str.charAt(str.length() - 1) == ')') {
			return isOneGrouping(str, '(', ')');
		}
		return false;
	}

	/**
	 * Returns true if {@code str} represents one group (within parenthesis) or
	 * one character class (within brackets).
	 * <p>
	 * Nested groupings are disregarded. In other words, this method will return
	 * {@code true} if the one group has nested groups or the one character
	 * class has nested character classes. This method assumes that {@code
	 * str.charAt(0) == startChar} and {@code str.charAt(str.length - 1) ==
	 * endChar}. This method also assumes that {@code str} is a valid regular
	 * expression. In a valid regular expression, all non-escaped left
	 * parentheses have matching non-escaped right parentheses. Similarly, all
	 * non-escaped left brackets have matching non-escaped right brackets.
	 * </p>
	 * 
	 * @param str
	 *            the string to examine
	 * @param startChar
	 *            either {@code '('} for a group or {@code '['} for a character
	 *            class
	 * @param endChar
	 *            either {@code ')'} for a group or {@code ']'} for a character
	 *            class
	 * @return {@code true} if {@code str} is one group and {@code false}
	 *         otherwise
	 */
	// TODO Give examples
	private boolean isOneGrouping(String str, char startChar, char endChar) {

		// numGroupings includes nested groupings.
		// numOuterGroupings does not include nested groupings.
		// In the for-loop below, we skip over the first character; therefore we
		// start each counter at 1.
		int numGroupings = 1;
		int numOuterGroupings = 1;
		boolean idxMinusOneEscaped = false;

		if (str.charAt(str.length() - 2) == '\\') {
			// Assuming the str is a valid regular expression, we know the
			// string is not a grouping because the final character is escaped.
			return false;
		}
		for (int i = 1; i < str.length(); i++) {
			if (str.charAt(i) == startChar && !idxMinusOneEscaped) {
				if (numGroupings == 0) {
					numOuterGroupings++;
				}
				numGroupings++;
			} else if (str.charAt(i) == endChar && !idxMinusOneEscaped) {
				numGroupings--;
			}
			idxMinusOneEscaped = str.charAt(i) == '\\' && !idxMinusOneEscaped;
		}
		// If numGroups does not equal zero then something is wrong, we'll just
		// return false in that case.
		return numOuterGroupings == 1 && numGroupings == 0;
	}

	public String compileStr() {
		String str = toString();
		if (isOneGroup(str)) {
			if (str.startsWith("(?")) { //$NON-NLS-1$
				if (str.charAt(2) == ':') {
					return str.substring(3, str.length() - 1);
				}
			}
			return str.substring(1, str.length() - 1);
		}
		return str;
	}

	@Override
	public Pattern compile() throws PatternSyntaxException {
		return Pattern.compile(compileStr());
	}

	@Override
	public Pattern compile(int flags) throws PatternSyntaxException {
		return Pattern.compile(compileStr(), flags);
	}

	@Override
	public boolean matches(String str) {
		if (str == null) {
			throw illegalNullArg(String.class, "str"); //$NON-NLS-1$
		}
		return str.matches(toString());
	}

	public RegExBuilderImpl regEx(RegExBuilder e, RegExBuilder... expressions) {
		return t(e, expressions);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IllegalArgumentException
	 *             {@inheritDoc}
	 * @throws NullPointerException
	 *             if {@code obj.toString()} return {@code null}
	 */
	public RegExBuilder regEx(Object obj, Object... objN) {
		if (obj == null) {
			throw illegalNullArg(Object.class, "obj"); //$NON-NLS-1$
		}
		if (objN == null) {
			throw illegalNullArg(Object[].class, "objN"); //$NON-NLS-1$
		}
		String newString;
		if (objN.length > 0) {
			StringBuilder builder = new StringBuilder(obj.toString());
			for (Object obji : objN) {
				if (obji == null) {
					throw illegalNullArrayItemArg(Object[].class, "objN"); //$NON-NLS-1$
				}
				builder.append(obji.toString());
			}
			newString = builder.toString();
		} else {
			newString = obj.toString();
		}
		for (String specialChar : specialCharacters) {
			newString = newString.replace(specialChar, "\\" + specialChar); //$NON-NLS-1$
		}
		return t(newString);
	}

	public RegExBuilder regEx(byte obj) {
		return re(Byte.valueOf(obj));
	}

	public RegExBuilder regEx(short obj) {
		return re(Short.valueOf(obj));
	}

	public RegExBuilder regEx(int obj) {
		return re(Integer.valueOf(obj));
	}

	public RegExBuilder regEx(long obj) {
		return re(Long.valueOf(obj));
	}

	public RegExBuilder regEx(float obj) {
		return re(Float.valueOf(obj));
	}

	public RegExBuilder regEx(double obj) {
		return re(Double.valueOf(obj));
	}

	public RegExBuilder regEx(boolean obj) {
		return re(Boolean.valueOf(obj));
	}

	public RegExBuilder regEx(char obj) {
		return re(Character.valueOf(obj));
	}

	private RegExBuilder re(Object obj) {
		return regEx(obj);
	}

	public RegExBuilder quote(Object obj) {
		if (obj == null) {
			throw illegalNullArg(Object.class, "obj"); //$NON-NLS-1$
		}
		return t("\\Q" + obj.toString().replace("\\E", "\\\\E\\QE") + "\\E"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

	public RegExBuilder comment(String str) {
		return t("# ").t(str).t(System.getProperty("line.separator")); //$NON-NLS-1$//$NON-NLS-2$
	}

	// ==========
	// Characters
	// ==========

	public RegExBuilder unicodeGrapheme() {
		return notUnicode(UnicodeCharacterProperty.M).zeroOrMore(
				new RegExBuilderImpl(new StringBuilder())
						.unicode(UnicodeCharacterProperty.M));
	}

	public RegExBuilder anyChar() {
		return t("."); //$NON-NLS-1$
	}

	@Override
	public RegExBuilder rangeClass(char min, char max) {
		if (min > max) {
			throw illegalOutsideSetArg( //
					char.class, "min", new Character(min), //$NON-NLS-1$//
					"[0, max]=[0," + max + "]"); //$NON-NLS-1$//$NON-NLS-2$
		}
		return t("[").t(min).t("-").t(max).t("]"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	}

	@Override
	public RegExBuilder notRangeClass(char min, char max) {
		if (min > max) {
			throw illegalOutsideSetArg( //
					char.class, "min", new Character(min), //$NON-NLS-1$//
					"[0, max]=[0," + max + "]"); //$NON-NLS-1$//$NON-NLS-2$
		}
		return t("[^").t(min).t("-").t(max).t("]"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	}

	public RegExBuilder charClass(char c0, char c1, char... charN) {
		if (charN == null) {
			throw illegalNullArg(char[].class, "charN"); //$NON-NLS-1$
		}
		t("["); //$NON-NLS-1$
		charClass(c0);
		charClass(c1);
		for (char c : charN) {
			charClass(c);
		}
		return t("]"); //$NON-NLS-1$
	}

	public RegExBuilder notCharClass(char c0, char c1, char... charN) {
		if (charN == null) {
			throw illegalNullArg(char[].class, "charN"); //$NON-NLS-1$
		}
		t("[^"); //$NON-NLS-1$
		charClass(c0);
		charClass(c1);
		for (char c : charN) {
			charClass(c);
		}
		return t("]"); //$NON-NLS-1$
	}

	public RegExBuilder charClass(String str) {
		if (str == null) {
			throw illegalNullArg(String.class, "str"); //$NON-NLS-1$
		}
		if (str.isEmpty()) {
			// TODO should we throw an exception here
		}
		t("["); //$NON-NLS-1$
		for (char c : str.toCharArray()) {
			charClass(c);
		}
		return t("]"); //$NON-NLS-1$
	}

	public RegExBuilder notCharClass(String str) {
		if (str == null) {
			throw illegalNullArg(String.class, "str"); //$NON-NLS-1$
		}
		if (str.isEmpty()) {
			// TODO should we throw an exception here
		}
		t("[^"); //$NON-NLS-1$
		for (char c : str.toCharArray()) {
			charClass(c);
		}
		return t("]"); //$NON-NLS-1$
	}

	public RegExBuilder charClass(CharClassBuilder charClass) {
		return t("[").t(charClass).t("]"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public RegExBuilder intersection(CharClassBuilder c0, CharClassBuilder c1) {
		return t("[").t(c0).t("&&[").t(c1).t("]]"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	}

	@Override
	public RegExBuilder union(CharClassBuilder c0, CharClassBuilder c1) {
		return t("[").t(c0).t("[").t(c1).t("]]"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	}

	public RegExBuilder matchLineStart() {
		return t("^"); //$NON-NLS-1$
	}

	public RegExBuilder matchLineEnd() {
		return t("$"); //$NON-NLS-1$
	}

	public RegExBuilder matchInputStart() {
		return t("\\A"); //$NON-NLS-1$
	}

	public RegExBuilder matchInputEnd() {
		return t("\\Z"); //$NON-NLS-1$
	}

	public RegExBuilder matchInputEndStrict() {
		return t("\\z"); //$NON-NLS-1$
	}

	public RegExBuilder wordBoundary() {
		return t("\\b"); //$NON-NLS-1$
	}

	public RegExBuilder notWordBoundary() {
		return t("\\B"); //$NON-NLS-1$
	}

	public RegExBuilder previousMatchEnd() {
		return t("\\G"); //$NON-NLS-1$
	}

	public RegExBuilder optional(RegExBuilder expression,
			RegExBuilder... expressions) {
		return tGroup(expression, expressions).t("?"); //$NON-NLS-1$
	}

	public RegExBuilder zeroOrMore(RegExBuilder expression,
			RegExBuilder... expressions) {
		return tGroup(expression, expressions).t("*"); //$NON-NLS-1$
	}

	public RegExBuilder oneOrMore(RegExBuilder expression,
			RegExBuilder... expressions) {
		return tGroup(expression, expressions).t("+"); //$NON-NLS-1$
	}

	private RegExBuilderImpl repeat(int nTimes) {
		if (nTimes < 0) {
			throw illegalOutsideSetArg( //
					int.class, "nTimes", //$NON-NLS-1$
					new Integer(nTimes), "[0,\u221E)"); //$NON-NLS-1$
		}
		return t("{").t(nTimes).t("}"); //$NON-NLS-1$//$NON-NLS-2$
	}

	@Override
	public RegExBuilder repeat(int nTimes, RegExBuilder expression,
			RegExBuilder... expressions) {
		return tGroup(expression, expressions).repeat(nTimes);
	}

	private RegExBuilderImpl repeatAtLeast(int nTimes) {
		return t("{").t(nTimes).t(",}"); //$NON-NLS-1$//$NON-NLS-2$
	}

	@Override
	public RegExBuilder repeatAtLeast(int nTimes, RegExBuilder expression,
			RegExBuilder... expressions) {
		return tGroup(expression, expressions).repeatAtLeast(nTimes);
	}

	private RegExBuilderImpl repeat(int nTimes, int toMTimes) {
		if (nTimes < 0) {
			throw illegalOutsideSetArg( //
					int.class, "nTimes", //$NON-NLS-1$
					new Integer(nTimes), "[0,\u221E)"); //$NON-NLS-1$
		}
		if (toMTimes < 0) {
			throw illegalOutsideSetArg( //
					int.class, "toMTimes", //$NON-NLS-1$
					new Integer(toMTimes), "[0,\u221E)"); //$NON-NLS-1$
		}
		if (nTimes > toMTimes) {
			throw illegalOutsideSetArg( //
					int.class, "nTimes", new Integer(nTimes), //$NON-NLS-1$
					"[0,toMTimes]=[0," + toMTimes + "]"); //$NON-NLS-1$//$NON-NLS-2$
		}
		return t("{").t(nTimes).t(",").t(toMTimes).t("}"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	}

	@Override
	public RegExBuilder repeat(int nTimes, int mTimes, RegExBuilder expression,
			RegExBuilder... expressions) {
		return tGroup(expression, expressions).repeat(nTimes, mTimes);
	}

	public RegExBuilder optionalLazy(RegExBuilder expression,
			RegExBuilder... expressions) {
		return tGroup(expression, expressions).t("??"); //$NON-NLS-1$
	}

	public RegExBuilder zeroOrMoreLazy(RegExBuilder expression,
			RegExBuilder... expressions) {
		return tGroup(expression, expressions).t("*?"); //$NON-NLS-1$
	}

	public RegExBuilder oneOrMoreLazy(RegExBuilder expression,
			RegExBuilder... expressions) {
		return tGroup(expression, expressions).t("+?"); //$NON-NLS-1$
	}

	private RegExBuilder repeatLazy(int nTimes) {
		return repeat(nTimes).t("?"); //$NON-NLS-1$
	}

	@Override
	public RegExBuilder repeatLazy(int nTimes, RegExBuilder expression,
			RegExBuilder... expressions) {
		return tGroup(expression, expressions).repeatLazy(nTimes);
	}

	private RegExBuilder repeatAtLeastLazy(int nTimes) {
		return repeatAtLeast(nTimes).t("?"); //$NON-NLS-1$
	}

	@Override
	public RegExBuilder repeatAtLeastLazy(int nTimes, RegExBuilder expression,
			RegExBuilder... expressions) {
		return tGroup(expression, expressions).repeatAtLeastLazy(nTimes);
	}

	private RegExBuilder repeatLazy(int nTimes, int toMTimes) {
		return repeat(nTimes, toMTimes).t("?"); //$NON-NLS-1$
	}

	@Override
	public RegExBuilder repeatLazy(int nTimes, int mTimes,
			RegExBuilder expression, RegExBuilder... expressions) {
		return tGroup(expression, expressions).repeatLazy(nTimes, mTimes);
	}

	@Override
	public RegExBuilder optionalPossessive(RegExBuilder expression,
			RegExBuilder... expressions) {
		return tGroup(expression, expressions).t("?+"); //$NON-NLS-1$
	}

	@Override
	public RegExBuilder zeroOrMorePossessive(RegExBuilder expression,
			RegExBuilder... expressions) {
		return tGroup(expression, expressions).t("*+"); //$NON-NLS-1$
	}

	@Override
	public RegExBuilder oneOrMorePossessive(RegExBuilder expression,
			RegExBuilder... expressions) {
		return tGroup(expression, expressions).t("++"); //$NON-NLS-1$
	}

	private RegExBuilder repeatPossessive(int nTimes) {
		return repeat(nTimes).t("+"); //$NON-NLS-1$
	}

	@Override
	public RegExBuilder repeatPossessive(int nTimes, RegExBuilder expression,
			RegExBuilder... expressions) {
		return tGroup(expression, expressions).repeatPossessive(nTimes);
	}

	private RegExBuilder repeatAtLeastPossessive(int nTimes) {
		return repeatAtLeast(nTimes).t("+"); //$NON-NLS-1$
	}

	@Override
	public RegExBuilder repeatAtLeastPossessive(int nTimes,
			RegExBuilder expression, RegExBuilder... expressions) {
		return tGroup(expression, expressions).repeatAtLeastPossessive(nTimes);
	}

	private RegExBuilder repeatPossessive(int nTimes, int toMTimes) {
		return repeat(nTimes, toMTimes).t("+"); //$NON-NLS-1$
	}

	@Override
	public RegExBuilder repeatPossessive(int nTimes, int mTimes,
			RegExBuilder expression, RegExBuilder... expressions) {
		return tGroup(expression, expressions).repeatPossessive(nTimes, mTimes);
	}

	public RegExBuilder group(RegExBuilder expression,
			RegExBuilder... expressions) {
		String str = new RegExBuilderImpl(new StringBuilder()).t(expression,
				expressions).toString();
		if (isOneGroup(str)) {
			// If str is a no-capture group, do not include the no-capture
			// symbology in the output.
			if (str.startsWith("(?:")) { //$NON-NLS-1$
				str = str.substring(3, str.length() - 1);
			}
		}
		return t("(").t(str).t(")"); //$NON-NLS-1$//$NON-NLS-2$
	}

	public RegExBuilder noCaptureGroup(RegExBuilder expression,
			RegExBuilder... expressions) {
		String str = new RegExBuilderImpl(new StringBuilder()).t(expression,
				expressions).toString();
		if (isOneGroup(str)) {
			// If str is already a group, there is no need to surround it with a
			// no-capture group.
			return t(str);
		}
		return t("(?:").t(str).t(")"); //$NON-NLS-1$//$NON-NLS-2$
	}

	public RegExBuilder turnOnOffMatchFlags(RegExMatchFlag flag,
			RegExMatchFlag... flagN) {
		return t("(?").t(flagString(getFlags(flag, flagN))).t(")"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private List<RegExMatchFlag> getFlags(RegExMatchFlag flag,
			RegExMatchFlag... flagN) {
		if (flag == null) {
			throw illegalNullArg(RegExMatchFlag.class, "flag"); //$NON-NLS-1$
		}
		if (flagN == null) {
			throw illegalNullArg(RegExMatchFlag[].class, "flagN"); //$NON-NLS-1$
		}
		List<RegExMatchFlag> flags = new ArrayList<RegExMatchFlag>(
				flagN.length + 1);
		addFlag(flag, flags);
		for (RegExMatchFlag f : flagN) {
			addFlag(f, flags);
		}
		return flags;
	}

	private String flagString(List<RegExMatchFlag> flags) {
		StringBuilder builder = new StringBuilder();
		boolean hasOffFlag = false;
		for (RegExMatchFlag flag : flags) {
			if (flag.isOnFlag()) {
				builder.append(flag.toChar());
			} else {
				hasOffFlag = true;
			}
		}
		if (hasOffFlag) {
			builder.append('-');
			for (RegExMatchFlag flag : flags) {
				if (!flag.isOnFlag()) {
					builder.append(flag.toChar());
				} else {
					hasOffFlag = true;
				}
			}
		}
		return builder.toString();
	}

	private void addFlag(RegExMatchFlag f, List<RegExMatchFlag> flags) {
		if (!flags.contains(f)) {
			flags.add(f);
		}
		flags.remove(f.getOppositeFlag());
	}

	public RegExBuilder atomicGroup(RegExBuilder expression,
			RegExBuilder... expressions) {
		return t("(?>").t(expression, expressions).t(")"); //$NON-NLS-1$//$NON-NLS-2$
	}

	public RegExBuilder noCaptureGroup( //
			RegExBuilder regEx, //
			RegExMatchFlag flag, //
			RegExMatchFlag... flagN) {
		if (regEx == null) {
			throw illegalNullArg(RegExBuilder.class, "regEx"); //$NON-NLS-1$
		}
		return t("(?").t(flagString(getFlags(flag, flagN))).t(":").t(regEx).t(")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	// TODO Group these regular expressions
	@Override
	public RegExBuilder orNoCaptureGroup(RegExBuilder expression0,
			RegExBuilder expression1, RegExBuilder... expressions) {
		t("(?:").t(expression0).t("|").t(expression1); //$NON-NLS-1$ //$NON-NLS-2$
		for (RegExBuilder expression : expressions) {
			t("|").t(expression); //$NON-NLS-1$
		}
		return t(")"); //$NON-NLS-1$
	}

	// TODO Group these regular expressions
	@Override
	public RegExBuilder orGroup(RegExBuilder expression0,
			RegExBuilder expression1, RegExBuilder... expressions) {
		t("(").t(expression0).t("|").t(expression1); //$NON-NLS-1$//$NON-NLS-2$
		for (RegExBuilder expression : expressions) {
			t("|").t(expression); //$NON-NLS-1$
		}
		return t(")"); //$NON-NLS-1$
	}

	public RegExBuilder backReference(int group) {
		if (group < 0) {
			throw illegalOutsideSetArg(int.class,
					"group", new Integer(group), "[0,\u221E)"); //$NON-NLS-1$//$NON-NLS-2$
		}
		if (group < 10) {
			return t("\\").t(Integer.toString(group)); //$NON-NLS-1$
		}
		// Put in no-capture group when group >= 10 so that the backReference is
		// treated as a group in #isOneOuterGrouping
		return t("(?:\\").t(Integer.toString(group)).t(")"); //$NON-NLS-1$//$NON-NLS-2$
	}

	public RegExBuilder positiveLookahead(RegExBuilder lookahead,
			RegExBuilder... lookaheadN) {
		return t("(?=").t(lookahead, lookaheadN).t(")"); //$NON-NLS-1$//$NON-NLS-2$
	}

	public RegExBuilder negativeLookahead(RegExBuilder lookahead,
			RegExBuilder... lookaheadN) {
		return t("(?!").t(lookahead, lookaheadN).t(")"); //$NON-NLS-1$//$NON-NLS-2$
	}

	public RegExBuilder positiveLookbehind(RegExBuilder lookbehind,
			RegExBuilder... lookbehindN) {
		return t("(?<=").t(lookbehind, lookbehindN).t(")"); //$NON-NLS-1$//$NON-NLS-2$
	}

	public RegExBuilder negativeLookbehind(RegExBuilder lookbehind,
			RegExBuilder... lookbehindN) {
		return t("(?<!").t(lookbehind, lookbehindN).t(")"); //$NON-NLS-1$//$NON-NLS-2$
	}
}
