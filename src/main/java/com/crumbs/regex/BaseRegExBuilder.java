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

import static com.crumbs.util.Logging.illegalEmptyStringArg;
import static com.crumbs.util.Logging.illegalNullArg;
import static com.crumbs.util.Logging.illegalNullArrayItemArg;
import static com.crumbs.util.Logging.illegalOutsideSetArg;

import java.util.ArrayList;
import java.util.List;

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
abstract class BaseRegExBuilder extends BaseCommonBuilder<RegExBuilder> implements RegExBuilder {

	private static final String[] specialCharacters = //
	new String[] { "\\", // must be first in the list because latter characters
							// are escaped, and we don't want this to escape
							// again
			"[", "^", "$", ".", "|", "?", "*", "+", "(", ")", //
			"{" // not really a special character, but Java thinks so
	};

	protected abstract BaseRegExBuilder newInstance();

	@Override
	protected BaseRegExBuilder u(String str) {
		return (BaseRegExBuilder) t(str);
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
	 *             if any argument is {@code null} or if any item in
	 *             {@code objN} is {@code null}
	 */
	RegExBuilder t(Object obj, Object[] objN) {
		// #t(Object} throws an IllegalArgumentException if obj is null
		t(obj);
		if (objN == null) {
			throw illegalNullArg(Object[].class, "objN");
		}
		for (Object o : objN) {
			t(o);
		}
		return thiz();
	}

	BaseRegExBuilder u(Object obj, Object[] objN) {
		return (BaseRegExBuilder) t(obj, objN);
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
	 *             if any argument is {@code null} or if an item in
	 *             {@code regExN} is {@code null}
	 */
	// TODO Give examples
	RegExBuilder tGroup(RegExBuilder regEx, RegExBuilder[] regExN) {
		if (regEx == null) {
			throw illegalNullArg(RegExBuilder.class, "regEx");
		}
		if (regExN == null) {
			throw illegalNullArg(RegExBuilder[].class, "regExN");
		}

		String str;

		if (regExN.length == 0) {
			str = regEx.toString();
		} else {
			StringBuilder builder = new StringBuilder(regEx.toString());
			for (RegExBuilder item : regExN) {
				if (item == null) {
					throw illegalNullArrayItemArg(RegExBuilder[].class, "regExN");
				}
				builder.append(item.toString());
			}
			str = builder.toString();
		}
		if (isRegExUnit(str)) {
			t(str);
		} else {
			u("(?:").u(str).t(")");
		}
		return thiz();
	}

	BaseRegExBuilder uGroup(RegExBuilder regEx, RegExBuilder[] regExN) {
		return (BaseRegExBuilder) tGroup(regEx, regExN);
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
	static boolean isRegExUnit(String str) {
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
				return str.matches("\\\\0[0-3]?[0-7]?[0-7]");
			case 'x':
				return str.matches("\\\\x[0-9A-Fa-f]{2}");
			case 'u':
				return str.matches("\\\\u[0-9A-Fa-f]{4}");
			case 'c':
				if (str.length() > 3) {
					return false;
				}
				char c = str.charAt(2);
				return ('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z');
			case 'p':
				return str.matches("\\\\p\\{[^}]+\\}");
			case 'P':
				return str.matches("\\\\P\\{[^}]+\\}");
			}
		}
		// numbered backreferences aren't included (if therey're more than two
		// digits, then we'll just surround it by parenthesis anyway)
		return false;
	}

	static protected boolean isOneGroup(String str) {
		if (str.length() > 1 && str.charAt(0) == '(' && str.charAt(str.length() - 1) == ')') {
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
	 * class has nested character classes. This method assumes that
	 * {@code str.charAt(0) == startChar} and
	 * {@code str.charAt(str.length - 1) ==
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
	static boolean isOneGrouping(String str, char startChar, char endChar) {

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

	@Override
	public RegExBuilder re(RegExBuilder regEx, RegExBuilder... regExN) {
		return t(regEx, regExN);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IllegalArgumentException
	 *             {@inheritDoc}
	 * @throws NullPointerException
	 *             if {@code obj.toString()} return {@code null}
	 */
	@Override
	public RegExBuilder re(Object obj, Object... objN) {
		if (obj == null) {
			throw illegalNullArg(Object.class, "obj");
		}
		if (objN == null) {
			throw illegalNullArg(Object[].class, "objN");
		}
		String newString;
		if (objN.length > 0) {
			StringBuilder builder = new StringBuilder(obj.toString());
			for (Object obji : objN) {
				if (obji == null) {
					throw illegalNullArrayItemArg(Object[].class, "objN");
				}
				builder.append(obji.toString());
			}
			newString = builder.toString();
		} else {
			newString = obj.toString();
		}
		for (String specialChar : specialCharacters) {
			newString = newString.replace(specialChar, "\\" + specialChar);
		}
		return t(newString);
	}

	@Override
	public RegExBuilder re(byte number) {
		return regEx(Byte.valueOf(number));
	}

	@Override
	public RegExBuilder re(short number) {
		return regEx(Short.valueOf(number));
	}

	@Override
	public RegExBuilder re(int integer) {
		return regEx(Integer.valueOf(integer));
	}

	@Override
	public RegExBuilder re(long number) {
		return regEx(Long.valueOf(number));
	}

	@Override
	public RegExBuilder re(float number) {
		return regEx(Float.valueOf(number));
	}

	@Override
	public RegExBuilder re(double number) {
		return regEx(Double.valueOf(number));
	}

	@Override
	public RegExBuilder re(boolean primitive) {
		return regEx(Boolean.valueOf(primitive));
	}

	@Override
	public RegExBuilder re(char character) {
		return regEx(Character.valueOf(character));
	}

	private RegExBuilder regEx(Object obj) {
		return re(obj);
	}

	@Override
	public RegExBuilder quote(Object obj) {
		if (obj == null) {
			throw illegalNullArg(Object.class, "obj");
		}
		return t("\\Q" + obj.toString().replace("\\E", "\\\\E\\QE") + "\\E");
	}

	@Override
	public RegExBuilder comment(String comment) {
		return u("# ").u(comment).t(System.getProperty("line.separator"));
	}

	@Override
	public RegExBuilder unicodeGrapheme() {
		return notUnicode(UnicodeCharacterProperty.M).zeroOrMore(newInstance().unicode(UnicodeCharacterProperty.M));
	}

	@Override
	public RegExBuilder anyChar() {
		return t(".");
	}

	@Override
	public RegExBuilder rangeClass(char min, char max) {
		if (min > max) {
			throw illegalOutsideSetArg( //
					char.class, "min", new Character(min), //
					"[0, max]=[0," + max + "]");
		}
		return u("[").u(min).u("-").u(max).t("]");
	}

	@Override
	public RegExBuilder notRangeClass(char min, char max) {
		if (min > max) {
			throw illegalOutsideSetArg( //
					char.class, "min", new Character(min), //
					"[0, max]=[0," + max + "]");
		}
		return u("[^").u(min).u("-").u(max).t("]");
	}

	@Override
	public RegExBuilder charClass(char c0, char c1, char... cN) {
		if (cN == null) {
			throw illegalNullArg(char[].class, "cN");
		}
		t("[");
		charClass(c0);
		charClass(c1);
		for (char c : cN) {
			charClass(c);
		}
		return t("]");
	}

	@Override
	public RegExBuilder notCharClass(char c0, char c1, char... cN) {
		if (cN == null) {
			throw illegalNullArg(char[].class, "cN");
		}
		t("[^");
		charClass(c0);
		charClass(c1);
		for (char c : cN) {
			charClass(c);
		}
		return t("]");
	}

	@Override
	public RegExBuilder charClass(String str) {
		if (str == null) {
			throw illegalNullArg(String.class, "str");
		}
		if (str.isEmpty()) {
			throw illegalEmptyStringArg("str");
		}
		t("[");
		for (char c : str.toCharArray()) {
			charClass(c);
		}
		return t("]");
	}

	@Override
	public RegExBuilder notCharClass(String str) {
		if (str == null) {
			throw illegalNullArg(String.class, "str");
		}
		if (str.isEmpty()) {
			throw illegalEmptyStringArg("str");
		}
		t("[^");
		for (char c : str.toCharArray()) {
			charClass(c);
		}
		return t("]");
	}

	@Override
	public RegExBuilder charClass(CharClassBuilder charClass) {
		return u("[").u(charClass).t("]");
	}

	@Override
	public RegExBuilder notCharClass(CharClassBuilder charClass) {
		return u("[^").u(charClass).t("]");
	}

	@Override
	public RegExBuilder intersection(CharClassBuilder charClass0, CharClassBuilder charClass1) {
		return u("[").u(charClass0).u("&&[").u(charClass1).t("]]");
	}

	@Override
	public RegExBuilder union(CharClassBuilder charClass0, CharClassBuilder charClass1) {
		return u("[").u(charClass0).u("[").u(charClass1).t("]]");
	}

	@Override
	public RegExBuilder matchLineStart() {
		return t("^");
	}

	@Override
	public RegExBuilder matchLineEnd() {
		return t("$");
	}

	@Override
	public RegExBuilder matchInputStart() {
		return t("\\A");
	}

	@Override
	public RegExBuilder matchInputEnd() {
		return t("\\Z");
	}

	@Override
	public RegExBuilder matchInputEndStrict() {
		return t("\\z");
	}

	@Override
	public RegExBuilder wordBoundary() {
		return t("\\b");
	}

	@Override
	public RegExBuilder notWordBoundary() {
		return t("\\B");
	}

	@Override
	public RegExBuilder previousMatchEnd() {
		return t("\\G");
	}

	@Override
	public RegExBuilder optional(RegExBuilder regEx, RegExBuilder... regExN) {
		return uGroup(regEx, regExN).t("?");
	}

	@Override
	public RegExBuilder zeroOrMore(RegExBuilder regEx, RegExBuilder... regExN) {
		return uGroup(regEx, regExN).t("*");
	}

	@Override
	public RegExBuilder oneOrMore(RegExBuilder regEx, RegExBuilder... regExN) {
		return uGroup(regEx, regExN).t("+");
	}

	protected RegExBuilder repeat(int nTimes) {
		if (nTimes < 0) {
			throw illegalOutsideSetArg( //
					int.class, "nTimes", new Integer(nTimes), "[0,\u221E)");
		}
		return u("{").u(nTimes).t("}");
	}

	private BaseRegExBuilder uRepeat(int nTimes) {
		return (BaseRegExBuilder) repeat(nTimes);
	}

	@Override
	public RegExBuilder repeat(int nTimes, RegExBuilder regEx, RegExBuilder... regExN) {
		return uGroup(regEx, regExN).repeat(nTimes);
	}

	protected RegExBuilder repeatAtLeast(int nTimes) {
		return u("{").u(nTimes).t(",}");
	}

	private BaseRegExBuilder uRepeatAtLeast(int nTimes) {
		return (BaseRegExBuilder) repeatAtLeast(nTimes);
	}

	@Override
	public RegExBuilder repeatAtLeast(int nTimes, RegExBuilder regEx, RegExBuilder... regExN) {
		return uGroup(regEx, regExN).repeatAtLeast(nTimes);
	}

	protected RegExBuilder repeat(int nTimes, int toMTimes) {
		if (nTimes < 0) {
			throw illegalOutsideSetArg( //
					int.class, "nTimes", new Integer(nTimes), "[0,\u221E)");
		}
		if (toMTimes < 0) {
			throw illegalOutsideSetArg( //
					int.class, "toMTimes", new Integer(toMTimes), "[0,\u221E)");
		}
		if (nTimes > toMTimes) {
			throw illegalOutsideSetArg( //
					int.class, "nTimes", new Integer(nTimes), "[0,toMTimes]=[0," + toMTimes + "]");
		}
		return u("{").u(nTimes).u(",").u(toMTimes).t("}");
	}

	private BaseRegExBuilder uRepeat(int nTimes, int toMTimes) {
		return (BaseRegExBuilder) repeat(nTimes, toMTimes);
	}

	@Override
	public RegExBuilder repeat(int atLeastNTimes, int atMostMTimes, RegExBuilder regEx, RegExBuilder... regExN) {
		return uGroup(regEx, regExN).repeat(atLeastNTimes, atMostMTimes);
	}

	@Override
	public RegExBuilder optionalLazy(RegExBuilder regEx, RegExBuilder... regExN) {
		return uGroup(regEx, regExN).t("??");
	}

	@Override
	public RegExBuilder zeroOrMoreLazy(RegExBuilder regEx, RegExBuilder... regExN) {
		return uGroup(regEx, regExN).t("*?");
	}

	@Override
	public RegExBuilder oneOrMoreLazy(RegExBuilder regEx, RegExBuilder... regExN) {
		return uGroup(regEx, regExN).t("+?");
	}

	private RegExBuilder repeatLazy(int nTimes) {
		return uRepeat(nTimes).t("?");
	}

	@Override
	public RegExBuilder repeatLazy(int nTimes, RegExBuilder regEx, RegExBuilder... regExN) {
		return uGroup(regEx, regExN).repeatLazy(nTimes);
	}

	private RegExBuilder repeatAtLeastLazy(int nTimes) {
		return uRepeatAtLeast(nTimes).t("?");
	}

	@Override
	public RegExBuilder repeatAtLeastLazy(int nTimes, RegExBuilder regEx, RegExBuilder... regExN) {
		return uGroup(regEx, regExN).repeatAtLeastLazy(nTimes);
	}

	private RegExBuilder repeatLazy(int nTimes, int toMTimes) {
		return uRepeat(nTimes, toMTimes).t("?");
	}

	@Override
	public RegExBuilder repeatLazy(int atLeastNTimes, int atMostMTimes, RegExBuilder regEx, RegExBuilder... regExN) {
		return uGroup(regEx, regExN).repeatLazy(atLeastNTimes, atMostMTimes);
	}

	@Override
	public RegExBuilder optionalPossessive(RegExBuilder regEx, RegExBuilder... regExN) {
		return uGroup(regEx, regExN).t("?+");
	}

	@Override
	public RegExBuilder zeroOrMorePossessive(RegExBuilder regEx, RegExBuilder... regExN) {
		return uGroup(regEx, regExN).t("*+");
	}

	@Override
	public RegExBuilder oneOrMorePossessive(RegExBuilder regEx, RegExBuilder... regExN) {
		return uGroup(regEx, regExN).t("++");
	}

	private RegExBuilder repeatPossessive(int nTimes) {
		return uRepeat(nTimes).t("+");
	}

	@Override
	public RegExBuilder repeatPossessive(int nTimes, RegExBuilder regEx, RegExBuilder... regExN) {
		return uGroup(regEx, regExN).repeatPossessive(nTimes);
	}

	private RegExBuilder repeatAtLeastPossessive(int nTimes) {
		return uRepeatAtLeast(nTimes).t("+");
	}

	@Override
	public RegExBuilder repeatAtLeastPossessive(int nTimes, RegExBuilder regEx, RegExBuilder... regExN) {
		return uGroup(regEx, regExN).repeatAtLeastPossessive(nTimes);
	}

	private RegExBuilder repeatPossessive(int nTimes, int toMTimes) {
		return uRepeat(nTimes, toMTimes).t("+");
	}

	@Override
	public RegExBuilder repeatPossessive(int atLeastNTimes, int atMostMTimes, RegExBuilder regEx,
			RegExBuilder... regExN) {
		return uGroup(regEx, regExN).repeatPossessive(atLeastNTimes, atMostMTimes);
	}

	@Override
	public RegExBuilder group(RegExBuilder regEx, RegExBuilder... regExN) {
		String str = newInstance().t(regEx, regExN).toString();
		if (isOneGroup(str)) {
			// If str is a no-capture group, do not include the no-capture
			// symbology in the output.
			if (str.startsWith("(?:")) {
				str = str.substring(3, str.length() - 1);
			}
		}
		return u("(").u(str).t(")");
	}

	@Override
	public RegExBuilder noCaptureGroup(RegExBuilder regEx, RegExBuilder... regExN) {
		String str = newInstance().t(regEx, regExN).toString();
		if (isOneGroup(str)) {
			// If str is already a group, there is no need to surround it with a
			// no-capture group.
			return t(str);
		}
		return u("(?:").u(str).t(")");
	}

	@Override
	public RegExBuilder turnOnOffMatchFlags(RegExMatchFlag flag, RegExMatchFlag... flagN) {
		return u("(?").u(flagString(getFlags(flag, flagN))).t(")");
	}

	static private List<RegExMatchFlag> getFlags(RegExMatchFlag flag, RegExMatchFlag... flagN) {
		if (flag == null) {
			throw illegalNullArg(RegExMatchFlag.class, "flag");
		}
		if (flagN == null) {
			throw illegalNullArg(RegExMatchFlag[].class, "flagN");
		}
		List<RegExMatchFlag> flags = new ArrayList<RegExMatchFlag>(flagN.length + 1);
		addFlag(flag, flags);
		for (RegExMatchFlag f : flagN) {
			addFlag(f, flags);
		}
		return flags;
	}

	static private String flagString(List<RegExMatchFlag> flags) {
		StringBuilder builder = new StringBuilder();
		boolean hasOffFlag = false;
		for (RegExMatchFlag flag : flags) {
			if (flag.isOn()) {
				builder.append(flag.toChar());
			} else {
				hasOffFlag = true;
			}
		}
		if (hasOffFlag) {
			builder.append('-');
			for (RegExMatchFlag flag : flags) {
				if (!flag.isOn()) {
					builder.append(flag.toChar());
				} else {
					hasOffFlag = true;
				}
			}
		}
		return builder.toString();
	}

	static private void addFlag(RegExMatchFlag f, List<RegExMatchFlag> flags) {
		if (!flags.contains(f)) {
			flags.add(f);
		}
		flags.remove(f.getOppositeFlag());
	}

	@Override
	public RegExBuilder atomicGroup(RegExBuilder regEx, RegExBuilder... regExN) {
		return u("(?>").u(regEx, regExN).t(")");
	}

	@Override
	public RegExBuilder noCaptureGroup( //
			RegExBuilder regEx, //
			RegExMatchFlag flag, //
			RegExMatchFlag... flagN) {
		if (regEx == null) {
			throw illegalNullArg(RegExBuilder.class, "regEx");
		}
		return u("(?").u(flagString(getFlags(flag, flagN))).u(":").u(regEx).t(")");
	}

	@Override
	public RegExBuilder orNoCaptureGroup(RegExBuilder regEx0, RegExBuilder regEx1, RegExBuilder... regExN) {
		u("(?:").u(regEx0).u("|").t(regEx1);
		for (RegExBuilder expression : regExN) {
			u("|").t(expression);
		}
		return t(")");
	}

	@Override
	public RegExBuilder orGroup(RegExBuilder regEx0, RegExBuilder regEx1, RegExBuilder... regExN) {
		u("(").u(regEx0).u("|").t(regEx1);
		for (RegExBuilder expression : regExN) {
			u("|").t(expression);
		}
		return t(")");
	}

	@Override
	public RegExBuilder backReference(int captureGroup) {
		if (captureGroup < 0) {
			throw illegalOutsideSetArg(int.class, "group", new Integer(captureGroup), "[0,\u221E)");
		}
		if (captureGroup < 10) {
			return u("\\").t(Integer.toString(captureGroup));
		}
		// Put in no-capture group when group >= 10 so that the backReference is
		// treated as a group in #isOneOuterGrouping
		return u("(?:\\").u(Integer.toString(captureGroup)).t(")");
	}

	@Override
	public RegExBuilder positiveLookahead(RegExBuilder lookahead, RegExBuilder... lookaheadN) {
		return u("(?=").u(lookahead, lookaheadN).t(")");
	}

	@Override
	public RegExBuilder negativeLookahead(RegExBuilder lookahead, RegExBuilder... lookaheadN) {
		return u("(?!").u(lookahead, lookaheadN).t(")");
	}

	@Override
	public RegExBuilder positiveLookbehind(RegExBuilder lookbehind, RegExBuilder... lookbehindN) {
		return u("(?<=").u(lookbehind, lookbehindN).t(")");
	}

	@Override
	public RegExBuilder negativeLookbehind(RegExBuilder lookbehind, RegExBuilder... lookbehindN) {
		return u("(?<!").u(lookbehind, lookbehindN).t(")");
	}
}
