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

import static com.crumbs.regex.BaseRegExBuilder.isOneGrouping;
import static com.crumbs.regex.BaseRegExBuilder.isRegExUnit;
import static java.text.MessageFormat.format;
import static java.util.regex.Pattern.DOTALL;
import static java.util.regex.Pattern.UNIX_LINES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Chris Topher
 * @version 0.0, Aug 11, 2009
 */
public class RegExBuilderImplTest {
	@Test
	public void tTest() {
		JRegExBuilder regEx = new JRegExBuilder();

		regEx.t("ABC123*&%^");
		assertEquals("ABC123*&%^", regEx.toString());

		regEx.t((String) null);
		assertEquals("ABC123*&%^null", regEx.toString());

		regEx.t(new Object() {
			@Override
			public String toString() {
				return "\u1234+555";
			}
		});
		assertEquals("ABC123*&%^null\u1234+555", regEx.toString());

		regEx.t(new Object() {
			@Override
			public String toString() {
				return null;
			}
		});
		assertEquals("ABC123*&%^null\u1234+555null", regEx.toString());

		try {
			regEx.t((Object[]) null);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}
		assertEquals("ABC123*&%^null\u1234+555null", regEx.toString());

		regEx.t(new Integer(Integer.MIN_VALUE));
		assertEquals("ABC123*&%^null\u1234+555null-2147483648", regEx.toString());

		regEx.t(new Character('&'));
		assertEquals("ABC123*&%^null\u1234+555null-2147483648&", regEx.toString());

		regEx.t(new Character('Q'), new Object[] {});
		assertEquals("ABC123*&%^null\u1234+555null-2147483648&Q", regEx.toString());

		regEx.t("Reg3x R0cks", new Object[] { new Character('A'), new Integer(1) });
		assertEquals("ABC123*&%^null\u1234+555null-2147483648&QReg3x R0cksA1", regEx.toString());
	}

	@Test
	public void tObjectObjectTest() {
		JRegExBuilder regEx = new JRegExBuilder();

		try {
			regEx.t(null, null);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}

		try {
			regEx.t(null, new Object[] {});
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}

		try {
			regEx.t(null, new Object[] { new Character('A'), null, new Integer(1) });
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}

		try {
			regEx.t(null, new Object[] { new Character('A'), new Integer(1) });
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}

		try {
			regEx.t("Reg3x R0cks", null);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}

		try {
			regEx.t("Reg3x R0cks", new Object[] { new Character('A'), null, new Integer(1) });
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}
	}

	@Test
	public void isOneOuterGroupingTest() {
		assertTrue(isOneGrouping("()", '(', ')'));
		assertTrue(isOneGrouping("(a)", '(', ')'));
		assertTrue(isOneGrouping("(())", '(', ')'));
		assertFalse(isOneGrouping("()\\)", '(', ')'));
		assertFalse(isOneGrouping("()abcd\\)", '(', ')'));
		assertTrue(isOneGrouping("((a)((b(ddde(342()dd)d)))d)", '(', ')'));
		assertFalse(isOneGrouping("(abc)(abde)", '(', ')'));
		assertFalse(isOneGrouping("(abc)abcde(abde)", '(', ')'));
		assertFalse(isOneGrouping("(\\)\\)\\(\\)\\()()\\)", '(', ')'));
		assertFalse(isOneGrouping("[][][]", '[', ']'));
		assertTrue(isOneGrouping("[abc]", '[', ']'));
	}

	@Test
	public void isRegExUnitTest() {

		try {
			isRegExUnit((String) null);
			fail();
		} catch (NullPointerException e) {
			// expected
		}

		assertTrue(isRegExUnit(""));
		assertTrue(isRegExUnit("\u1234"));
		assertTrue(isRegExUnit("(ade\\()"));
		assertFalse(isRegExUnit("(ade\\()\\)"));
		assertFalse(isRegExUnit("(ade\\()(abc)a\\)"));
		assertTrue(isRegExUnit("[ade\\]]"));
		assertFalse(isRegExUnit("[ade\\()\\]"));
		assertFalse(isRegExUnit("[ade\\[][abc]a\\]"));
		assertTrue(isRegExUnit("\\*"));
		assertTrue(isRegExUnit("\\0377"));
		assertFalse(isRegExUnit("\\0378"));
		assertTrue(isRegExUnit("\\037"));
		assertTrue(isRegExUnit("\\00"));
		assertTrue(isRegExUnit("\\x00"));
		assertTrue(isRegExUnit("\\x0A"));
		assertTrue(isRegExUnit("\\xff"));
		// TODO Test some of these in the integration test
		assertTrue(isRegExUnit("\\xA0"));
		assertFalse(isRegExUnit("\\xG0"));
		assertFalse(isRegExUnit("\\xG0A"));
		assertTrue(isRegExUnit("\\uFFFF"));
		assertTrue(isRegExUnit("\\u1234"));
		assertFalse(isRegExUnit("\\u12345"));
		assertFalse(isRegExUnit("\\uFZFF"));
		assertTrue(isRegExUnit("\\cA"));
		assertTrue(isRegExUnit("\\cM"));
		assertFalse(isRegExUnit("\\c0"));
		assertFalse(isRegExUnit("\\c05"));
		assertTrue(isRegExUnit("\\p{Latin}"));
		assertTrue(isRegExUnit("\\P{Latin}"));
		assertTrue(isRegExUnit("\\p{A}"));
		assertTrue(isRegExUnit("\\P{A}"));
		assertFalse(isRegExUnit("\\p{}"));
		assertFalse(isRegExUnit("\\P{}"));
		assertFalse(isRegExUnit("\\P{}a"));
	}

	@Test
	public void tGroupExceptionTest() {
		JRegExBuilder regEx = new JRegExBuilder();
		JRegExBuilder regExA = new JRegExBuilder();
		JRegExBuilder regExB = new JRegExBuilder();
		JRegExBuilder regExC = new JRegExBuilder();

		try {
			regEx.tGroup((JRegExBuilder) null, (JRegExBuilder[]) null);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}

		try {
			regEx.tGroup((JRegExBuilder) null, new JRegExBuilder[] {});
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}

		try {
			regEx.tGroup((JRegExBuilder) null, new JRegExBuilder[] { regExA, null, regExB });
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}

		try {
			regEx.tGroup((JRegExBuilder) null, new JRegExBuilder[] { regExA, regExB, regExC });
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}

		try {
			regEx.tGroup(regExA, (JRegExBuilder[]) null);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}

		try {
			regEx.tGroup(regExA, new JRegExBuilder[] { regExA, null, regExB });
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}
	}

	@Test
	public void tGroupTest() {
		JRegExBuilder regExA = new JRegExBuilder();
		regExA.re("abdefa");
		JRegExBuilder regExB = new JRegExBuilder();
		regExB.ascii(0xA0);
		JRegExBuilder regExC = new JRegExBuilder();
		regExC.re("\\xG0");

		// TODO integration test, does \\xA0+ actually do \\xA0 one or more
		// times or just 0 one or more times?

		// TODO when you combine, can a combination form something that is
		// considered a group (but shouldn't be)

		assertEquals("(?:abdefa)", new JRegExBuilder().tGroup(regExA, new JRegExBuilder[] {}).toString());
		assertEquals("\\xA0", new JRegExBuilder().tGroup(regExB, new JRegExBuilder[] {}).toString());
		assertEquals("(?:\\\\xG0)", new JRegExBuilder().tGroup(regExC, new JRegExBuilder[] {}).toString());
		assertEquals("(?:abdefa\\xA0\\\\xG0)",
				new JRegExBuilder().tGroup(regExA, new JRegExBuilder[] { regExB, regExC }).toString());
		assertEquals(".", new JRegExBuilder().tGroup(new JRegExBuilder().anyChar(), new JRegExBuilder[] {}).toString());
		assertEquals("\\b", new JRegExBuilder().tGroup(new JRegExBuilder().wordBoundary(), new JRegExBuilder[] {})
				.toString());
	}

	@Test
	public void regExTest() {
		JRegExBuilder regEx = new JRegExBuilder();

		try {
			regEx.re(null);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}

		regEx.re("\\[Regul*r+expre$$|ons ^are^ awesome?{!}]()");
		assertEquals("\\\\\\[Regul\\*r\\+expre\\$\\$\\|ons \\^are\\^ awesome\\?\\{!}]\\(\\)", regEx.toString());

		regEx = new JRegExBuilder();
		regEx.re("\\QI use \\Q...\\E in my regular expressions.\\E");
		assertEquals("\\\\QI use \\\\Q\\.\\.\\.\\\\E in my regular expressions\\.\\\\E", regEx.toString());

		regEx = new JRegExBuilder();
		regEx.re(new Object() {
			@Override
			public String toString() {
				return "Hola. ^_^";
			}
		});
		assertEquals("Hola\\. \\^_\\^", regEx.toString());

		regEx = new JRegExBuilder();

		try {
			regEx.re(new Object() {
				@Override
				public String toString() {
					return null;
				}
			});
			fail();
		} catch (NullPointerException e) {
			// expected
		}

		String str;

		str = "\\[Regul*r+expre$$|ons }]^are^{0,5} awesome.\t?{!}]()";
		testMatch(Pattern.compile(new JRegExBuilder().re(str).toString()), str);
		str = "\\QI use \\Q...\\E in my regular expressions.\\E";
		testMatch(Pattern.compile(new JRegExBuilder().re(str).toString()), str);
	}

	@Test
	public void regExOthersTest() {
		RegExBuilder regEx;

		byte b = -128;
		regEx = new JRegExBuilder().re(b);
		assertEquals("-128", regEx.toString());

		short s = 32767;
		regEx = new JRegExBuilder().re(s);
		assertEquals("32767", regEx.toString());

		int i = -2147483648;
		regEx = new JRegExBuilder().re(i);
		assertEquals("-2147483648", regEx.toString());

		long l = -9223372036854775808l;
		regEx = new JRegExBuilder().re(l);
		assertEquals("-9223372036854775808", regEx.toString());

		float f = Float.NaN;
		regEx = new JRegExBuilder().re(f);
		assertEquals("NaN", regEx.toString());

		f = Float.POSITIVE_INFINITY;
		regEx = new JRegExBuilder().re(f);
		assertEquals("Infinity", regEx.toString());

		f = Float.NEGATIVE_INFINITY;
		regEx = new JRegExBuilder().re(f);
		assertEquals("-Infinity", regEx.toString());

		f = -3.1492E20f;
		regEx = new JRegExBuilder().re(f);
		assertEquals("-3\\.1492E20", regEx.toString());

		f = -0;
		regEx = new JRegExBuilder().re(f);
		assertEquals("0\\.0", regEx.toString());

		double d = Double.NaN;
		regEx = new JRegExBuilder().re(d);
		assertEquals("NaN", regEx.toString());

		d = Double.POSITIVE_INFINITY;
		regEx = new JRegExBuilder().re(d);
		assertEquals("Infinity", regEx.toString());

		d = Double.NEGATIVE_INFINITY;
		regEx = new JRegExBuilder().re(d);
		assertEquals("-Infinity", regEx.toString());

		d = 3.1415926535898E-310;
		regEx = new JRegExBuilder().re(d);
		assertEquals("3\\.1415926535898E-310", regEx.toString());

		d = -0;
		regEx = new JRegExBuilder().re(d);
		assertEquals("0\\.0", regEx.toString());

		boolean bool = true;
		regEx = new JRegExBuilder().re(bool);
		assertEquals("true", regEx.toString());

		char c = '^';
		regEx = new JRegExBuilder().re(c);
		assertEquals("\\^", regEx.toString());

		c = '\u1234';
		regEx = new JRegExBuilder().re(c);
		assertEquals("\u1234", regEx.toString());

		c = '\u000C';
		regEx = new JRegExBuilder().re(c);
		assertEquals("\f", regEx.toString());

		c = '\u002A';
		regEx = new JRegExBuilder().re(c);
		assertEquals("\\*", regEx.toString());
	}

	@Test
	public void quoteTest() {
		JRegExBuilder regEx = new JRegExBuilder();

		try {
			regEx.quote(null);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}

		regEx.quote("\\[Regul*r+expre$$|ons ^are^ awesome?{!}]()");
		assertEquals("\\Q\\[Regul*r+expre$$|ons ^are^ awesome?{!}]()\\E", regEx.toString());

		regEx = new JRegExBuilder();
		regEx.quote("\\QI use \\Q...\\E in my regular expressions.\\E");
		assertEquals("\\Q\\QI use \\Q...\\\\E\\QE in my regular expressions.\\\\E\\QE\\E", regEx.toString());

		String str;

		str = "\\[Regul*r+expre$$|ons }]^are^{0,5} awesome.\t?{!}]()";
		testMatch(Pattern.compile(new JRegExBuilder().quote(str).toString()), str);
		str = "\\QI use \\Q...\\E in my regular expressions.\\E";
		testMatch(Pattern.compile(new JRegExBuilder().quote(str).toString()), str);
	}

	@Test
	public void charactersTest() {
		Pattern pattern;

		pattern = Pattern.compile(new JRegExBuilder().bell().toString());
		testMatch(pattern, "\u0007");

		pattern = Pattern.compile(new JRegExBuilder().tab().toString());
		testMatch(pattern, "\t");
		testMatch(pattern, "\u0009");

		// It is illegal to use

		pattern = Pattern.compile(new JRegExBuilder().lineFeed().toString());
		testMatch(pattern, "\n");
		testMatch(pattern, "\12");

		pattern = Pattern.compile(new JRegExBuilder().verticalTab().toString());
		testMatch(pattern, "\u000B");

		pattern = Pattern.compile(new JRegExBuilder().formFeed().toString());
		testMatch(pattern, "\f");
		testMatch(pattern, "\u000C");

		// It is illegal to use
		pattern = Pattern.compile(new JRegExBuilder().carriageReturn().toString());
		testMatch(pattern, "\r");
		testMatch(pattern, "\15");

		pattern = Pattern.compile(new JRegExBuilder().escape().toString());
		testMatch(pattern, "\u001B");
	}

	@Test
	public void controlCharactersTest() {
		char i = 'A';
		int j = 0;
		for (; i < 'Z'; i++, j++) {
			assertTrue(Pattern.compile(new JRegExBuilder().control(i).toString()).matcher("" + (char) ('\1' + j))
					.matches());
		}
	}

	@Test
	public void asciiTest() {
		Pattern pattern;
		char i = 0x00;
		for (; i < 0xFF + 1; i++) {
			pattern = Pattern.compile(new JRegExBuilder().ascii(i).toString());
			testMatch(pattern, Character.toString(i));
		}
	}

	@Test
	public void digitsTest() {
		Pattern pattern = Pattern.compile(new JRegExBuilder().digit().toString());
		for (int i = 0; i < 10; i++) {
			testMatch(pattern, Integer.toString(i));
		}
		testNonMatch(pattern, "A");
		testNonMatch(pattern, " ");
	}

	static private void testMatch(Pattern pattern, String str) {
		String msg = "Expected \"{0}\" to match \"{1}\"";
		assertTrue(format(msg, pattern, str), pattern.matcher(str).matches());
	}

	static private void testNonMatch(Pattern pattern, String str) {
		String msg = "Did not expect \"{0}\" to match \"{1}\"";
		assertFalse(format(msg, pattern, str), pattern.matcher(str).matches());
	}

	@Test
	public void nonDigitsTest() {
		Pattern pattern = Pattern.compile(new JRegExBuilder().notDigit().toString());
		for (int i = 0; i < 10; i++) {
			testNonMatch(pattern, Integer.toString(i));
		}
		testMatch(pattern, "A");
		testMatch(pattern, " ");
	}

	@Test
	public void whitespaceTest() {
		Pattern pattern = Pattern.compile(new JRegExBuilder().whitespace().toString());

		// space
		testMatch(pattern, " ");
		testMatch(pattern, "\40");

		// tab
		testMatch(pattern, "\t");
		testMatch(pattern, "\11");

		// new line
		testMatch(pattern, "\n");
		testMatch(pattern, "\12");

		// vertical tab
		testMatch(pattern, "\13");

		// form feed
		testMatch(pattern, "\f");
		testMatch(pattern, "\14");

		// carriage return
		testMatch(pattern, "\r");
		testMatch(pattern, "\15");

		testNonMatch(pattern, "a");
		testNonMatch(pattern, "0");
	}

	@Test
	public void nonWhitespaceTest() {
		Pattern pattern = Pattern.compile(new JRegExBuilder().notWhitespace().toString());

		// space
		testNonMatch(pattern, " ");
		testNonMatch(pattern, "\40");

		// tab
		testNonMatch(pattern, "\t");
		testNonMatch(pattern, "\11");

		// new line
		testNonMatch(pattern, "\n");
		testNonMatch(pattern, "\12");

		// vertical tab
		testNonMatch(pattern, "\13");

		// form feed
		testNonMatch(pattern, "\f");
		testNonMatch(pattern, "\14");

		// carriage return
		testNonMatch(pattern, "\r");
		testNonMatch(pattern, "\15");

		testMatch(pattern, "a");
		testMatch(pattern, "0");
	}

	@Test
	public void wordCharactersTest() {
		Pattern pattern = Pattern.compile(new JRegExBuilder().wordCharacter().toString());
		for (int i = 0; i < 10; i++) {
			testMatch(pattern, Integer.toString(i));
		}
		for (char i = 'A'; i < 'Z'; i++) {
			testMatch(pattern, Character.toString(i));
		}
		for (char i = 'a'; i < 'z'; i++) {
			testMatch(pattern, Character.toString(i));
		}
		testMatch(pattern, "_");
		testNonMatch(pattern, "\t");
		testNonMatch(pattern, "+");
	}

	@Test
	public void nonWordCharactersTest() {
		Pattern pattern = Pattern.compile(new JRegExBuilder().notWordCharacter().toString());
		for (int i = 0; i < 10; i++) {
			testNonMatch(pattern, Integer.toString(i));
		}
		for (char i = 'A'; i < 'Z'; i++) {
			testNonMatch(pattern, Character.toString(i));
		}
		for (char i = 'a'; i < 'z'; i++) {
			testNonMatch(pattern, Character.toString(i));
		}
		testNonMatch(pattern, "_");
		testMatch(pattern, "\t");
		testMatch(pattern, "+");
	}

	@Test
	public void anyCharTest() {
		// do not match recognized line terminators
		Pattern pattern = Pattern.compile(new JRegExBuilder().anyChar().toString());
		testMatch(pattern, "\t");
		testNonMatch(pattern, "\n");
		testNonMatch(pattern, "\r");
		testNonMatch(pattern, "\r\n");

		// match everything
		pattern = Pattern.compile(new JRegExBuilder().anyChar().toString(), DOTALL);
		testMatch(pattern, "\t");
		testMatch(pattern, "\n");
		testMatch(pattern, "\r");
		testNonMatch(pattern, "\r\n");

		// do not match \n
		pattern = Pattern.compile(new JRegExBuilder().anyChar().toString(), UNIX_LINES);
		testMatch(pattern, "\t");
		testNonMatch(pattern, "\n");
		testMatch(pattern, "\r");
		testNonMatch(pattern, "\r\n");

		pattern = Pattern.compile(new JRegExBuilder().anyChar().anyChar().toString());
		testNonMatch(pattern, "\r\n");

		pattern = Pattern.compile(new JRegExBuilder().anyChar().anyChar().toString(), DOTALL);
		testMatch(pattern, "\r\n");
	}

	@Test
	public void test() {
		Assert.assertTrue("\\".matches("[+-^]"));
	}
}
