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

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

/**
 * @author Chris Topher
 * @version 0.0, Aug 11, 2009
 */
public class RegExBuilderImplTest {
	private RegExBuilderImpl newRegEx() {
		return new RegExBuilderImpl(new StringBuilder());
	}

	private Method getPrivateMethod(String name, Class<?>... parameterTypes)
			throws Exception {
		Method method = RegExBuilderImpl.class.getDeclaredMethod(name,
				parameterTypes);
		method.setAccessible(true);
		return method;
	}

	@Test
	public void tTest() throws Exception {
		RegExBuilderImpl regEx = new RegExBuilderImpl(new StringBuilder());

		regEx.t("ABC123*&%^"); //$NON-NLS-1$
		assertEquals("ABC123*&%^", regEx.toString()); //$NON-NLS-1$

		regEx.t((String) null);
		assertEquals("ABC123*&%^null", regEx.toString()); //$NON-NLS-1$

		regEx.t(new Object() {
			@Override
			public String toString() {
				return "\u1234+555"; //$NON-NLS-1$
			}
		});
		assertEquals("ABC123*&%^null\u1234+555", regEx.toString()); //$NON-NLS-1$

		regEx.t(new Object() {
			@Override
			public String toString() {
				return null;
			}
		});
		assertEquals("ABC123*&%^null\u1234+555null", regEx.toString()); //$NON-NLS-1$

		try {
			regEx.t((Object[]) null);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}
		assertEquals("ABC123*&%^null\u1234+555null", regEx.toString()); //$NON-NLS-1$

		regEx.t(new Integer(Integer.MIN_VALUE));
		assertEquals(
				"ABC123*&%^null\u1234+555null-2147483648", regEx.toString()); //$NON-NLS-1$

		regEx.t(new Character('&'));
		assertEquals(
				"ABC123*&%^null\u1234+555null-2147483648&", regEx.toString()); //$NON-NLS-1$

		getPrivateMethod("t", Object.class, Object[].class).invoke(regEx, new Character('Q'), new Object[] {}); //$NON-NLS-1$
		assertEquals(
				"ABC123*&%^null\u1234+555null-2147483648&Q", regEx.toString()); //$NON-NLS-1$

		getPrivateMethod("t", Object.class, Object[].class).invoke(regEx, "Reg3x R0cks", new Object[] { new Character('A'), new Integer(1) }); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(
				"ABC123*&%^null\u1234+555null-2147483648&QReg3x R0cksA1", regEx.toString()); //$NON-NLS-1$
	}

	@Test
	public void tObjectObjectTest() throws Exception {
		RegExBuilderImpl regEx = new RegExBuilderImpl(new StringBuilder());
		Method method = getPrivateMethod("t", Object.class, Object[].class); //$NON-NLS-1$

		try {
			method.invoke(regEx, null, null);
			fail();
		} catch (InvocationTargetException e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
		}

		try {
			method.invoke(regEx, null, new Object[] {});
			fail();
		} catch (InvocationTargetException e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
		}

		try {
			method.invoke(regEx, null, new Object[] { new Character('A'), null,
					new Integer(1) });
			fail();
		} catch (InvocationTargetException e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
		}

		try {
			method.invoke(regEx, null, new Object[] { new Character('A'),
					new Integer(1) });
			fail();
		} catch (InvocationTargetException e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
		}

		try {
			method.invoke(regEx, "Reg3x R0cks", null); //$NON-NLS-1$ 
			fail();
		} catch (InvocationTargetException e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
		}

		try {
			method.invoke(regEx, "Reg3x R0cks", new Object[] { //$NON-NLS-1$ 
					new Character('A'), null, new Integer(1) });
			fail();
		} catch (InvocationTargetException e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
		}
	}

	@Test
	public void isOneOuterGroupingTest() throws Exception {
		RegExBuilderImpl regEx = new RegExBuilderImpl(new StringBuilder());

		Method method = getPrivateMethod(
				"isOneGrouping", String.class, char.class, char.class); //$NON-NLS-1$

		assertEquals(TRUE, method.invoke(regEx,
				"()", new Character('('), new Character(')'))); //$NON-NLS-1$
		assertEquals(TRUE, method.invoke(regEx,
				"(a)", new Character('('), new Character(')'))); //$NON-NLS-1$
		assertEquals(TRUE, method.invoke(regEx,
				"(())", new Character('('), new Character(')'))); //$NON-NLS-1$
		assertEquals(FALSE, method.invoke(regEx,
				"()\\)", new Character('('), new Character(')'))); //$NON-NLS-1$
		assertEquals(FALSE, method.invoke(regEx,
				"()abcd\\)", new Character('('), new Character(')'))); //$NON-NLS-1$
		assertEquals(TRUE, method.invoke(regEx, "((a)((b(ddde(342()dd)d)))d)", //$NON-NLS-1$
				new Character('('), new Character(')')));
		assertEquals(FALSE, method.invoke(regEx,
				"(abc)(abde)", new Character('('), new Character(')'))); //$NON-NLS-1$
		assertEquals(FALSE, method.invoke(regEx,
				"(abc)abcde(abde)", new Character('('), new Character(')'))); //$NON-NLS-1$
		assertEquals(FALSE, method.invoke(regEx, "(\\)\\)\\(\\)\\()()\\)", //$NON-NLS-1$
				new Character('('), new Character(')')));
		assertEquals(FALSE, method.invoke(regEx, "[][][]", //$NON-NLS-1$
				new Character('['), new Character(']')));
		assertEquals(TRUE, method.invoke(regEx, "[abc]", //$NON-NLS-1$
				new Character('['), new Character(']')));
	}

	@Test
	public void isRegExUnitTest() throws Exception {
		RegExBuilderImpl regEx = new RegExBuilderImpl(new StringBuilder());

		Method method = getPrivateMethod("isRegExUnit", String.class); //$NON-NLS-1$

		try {
			method.invoke(regEx, (String) null);
			fail();
		} catch (InvocationTargetException e) {
			assertTrue(e.getCause() instanceof NullPointerException);
			// expected
		}

		assertEquals(TRUE, method.invoke(regEx, "")); //$NON-NLS-1$
		assertEquals(TRUE, method.invoke(regEx, "\u1234")); //$NON-NLS-1$
		assertEquals(TRUE, method.invoke(regEx, "(ade\\()")); //$NON-NLS-1$
		assertEquals(FALSE, method.invoke(regEx, "(ade\\()\\)")); //$NON-NLS-1$
		assertEquals(FALSE, method.invoke(regEx, "(ade\\()(abc)a\\)")); //$NON-NLS-1$
		assertEquals(TRUE, method.invoke(regEx, "[ade\\]]")); //$NON-NLS-1$
		assertEquals(FALSE, method.invoke(regEx, "[ade\\()\\]")); //$NON-NLS-1$
		assertEquals(FALSE, method.invoke(regEx, "[ade\\[][abc]a\\]")); //$NON-NLS-1$
		assertEquals(TRUE, method.invoke(regEx, "\\*")); //$NON-NLS-1$
		assertEquals(TRUE, method.invoke(regEx, "\\0377")); //$NON-NLS-1$
		assertEquals(FALSE, method.invoke(regEx, "\\0378")); //$NON-NLS-1$
		assertEquals(TRUE, method.invoke(regEx, "\\037")); //$NON-NLS-1$
		assertEquals(TRUE, method.invoke(regEx, "\\00")); //$NON-NLS-1$
		assertEquals(TRUE, method.invoke(regEx, "\\x00")); //$NON-NLS-1$
		assertEquals(TRUE, method.invoke(regEx, "\\x0A")); //$NON-NLS-1$
		assertEquals(TRUE, method.invoke(regEx, "\\xff")); //$NON-NLS-1$
		// TODO Test some of these in the integration test
		assertEquals(TRUE, method.invoke(regEx, "\\xA0")); //$NON-NLS-1$
		assertEquals(FALSE, method.invoke(regEx, "\\xG0")); //$NON-NLS-1$
		assertEquals(FALSE, method.invoke(regEx, "\\xG0A")); //$NON-NLS-1$
		assertEquals(TRUE, method.invoke(regEx, "\\uFFFF")); //$NON-NLS-1$
		assertEquals(TRUE, method.invoke(regEx, "\\u1234")); //$NON-NLS-1$
		assertEquals(FALSE, method.invoke(regEx, "\\u12345")); //$NON-NLS-1$
		assertEquals(FALSE, method.invoke(regEx, "\\uFZFF")); //$NON-NLS-1$
		assertEquals(TRUE, method.invoke(regEx, "\\cA")); //$NON-NLS-1$
		assertEquals(TRUE, method.invoke(regEx, "\\cM")); //$NON-NLS-1$
		assertEquals(FALSE, method.invoke(regEx, "\\c0")); //$NON-NLS-1$
		assertEquals(FALSE, method.invoke(regEx, "\\c05")); //$NON-NLS-1$
		assertEquals(TRUE, method.invoke(regEx, "\\p{Latin}")); //$NON-NLS-1$
		assertEquals(TRUE, method.invoke(regEx, "\\P{Latin}")); //$NON-NLS-1$
		assertEquals(TRUE, method.invoke(regEx, "\\p{A}")); //$NON-NLS-1$
		assertEquals(TRUE, method.invoke(regEx, "\\P{A}")); //$NON-NLS-1$
		assertEquals(FALSE, method.invoke(regEx, "\\p{}")); //$NON-NLS-1$
		assertEquals(FALSE, method.invoke(regEx, "\\P{}")); //$NON-NLS-1$
		assertEquals(FALSE, method.invoke(regEx, "\\P{}a")); //$NON-NLS-1$
	}

	@Test
	public void tGroupExceptionTest() throws Exception {
		RegExBuilderImpl regEx = new RegExBuilderImpl(new StringBuilder());
		RegExBuilderImpl regExA = new RegExBuilderImpl(new StringBuilder());
		RegExBuilderImpl regExB = new RegExBuilderImpl(new StringBuilder());
		RegExBuilderImpl regExC = new RegExBuilderImpl(new StringBuilder());

		Method method = getPrivateMethod(
				"tGroup", RegExBuilder.class, RegExBuilder[].class); //$NON-NLS-1$

		try {
			method.invoke(regEx, (RegExBuilder) null, (RegExBuilder[]) null);
			fail();
		} catch (InvocationTargetException e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
			// expected
		}

		try {
			method.invoke(regEx, (RegExBuilder) null, new RegExBuilder[] {});
			fail();
		} catch (InvocationTargetException e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
			// expected
		}

		try {
			method.invoke(regEx, (RegExBuilder) null, new RegExBuilder[] {
					regExA, null, regExB });
			fail();
		} catch (InvocationTargetException e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
			// expected
		}

		try {
			method.invoke(regEx, (RegExBuilder) null, new RegExBuilder[] {
					regExA, regExB, regExC });
			fail();
		} catch (InvocationTargetException e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
			// expected
		}

		try {
			method.invoke(regEx, regExA, (RegExBuilder[]) null);
			fail();
		} catch (InvocationTargetException e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
			// expected
		}

		try {
			method.invoke(regEx, regExA, new RegExBuilder[] { regExA, null,
					regExB });
			fail();
		} catch (InvocationTargetException e) {
			assertTrue(e.getCause() instanceof IllegalArgumentException);
			// expected
		}
	}

	@Test
	public void tGroupTest() throws Exception {
		RegExBuilderImpl regExA = new RegExBuilderImpl(new StringBuilder());
		regExA.regEx("abdefa");
		RegExBuilderImpl regExB = new RegExBuilderImpl(new StringBuilder());
		regExB.ascii(0xA0);
		RegExBuilderImpl regExC = new RegExBuilderImpl(new StringBuilder());
		regExC.regEx("\\xG0");

		// TODO integration test, does \\xA0+ actually do \\xA0 one or more
		// times or just 0 one or more times?

		Method method = getPrivateMethod(
				"tGroup", RegExBuilder.class, RegExBuilder[].class); //$NON-NLS-1$

		// TODO when you combine, can a combination form something that is
		// considered a group (but shouldn't be)

		assertEquals("(?:abdefa)", method.invoke(newRegEx(), regExA,
				new RegExBuilder[] {}).toString());
		assertEquals("\\xA0", method.invoke(newRegEx(), regExB,
				new RegExBuilder[] {}).toString());
		assertEquals("(?:\\\\xG0)", method.invoke(newRegEx(), regExC,
				new RegExBuilder[] {}).toString());
		assertEquals("(?:abdefa\\xA0\\\\xG0)", method.invoke(newRegEx(),
				regExA, new RegExBuilder[] { regExB, regExC }).toString());
		assertEquals(".", method.invoke(newRegEx(), newRegEx().anyChar(),
				new RegExBuilder[] {}).toString());
		assertEquals("\\b", method.invoke(newRegEx(),
				newRegEx().wordBoundary(), new RegExBuilder[] {}).toString());
	}

	@Test
	public void regExTest() {
		RegExBuilder regEx = new RegExBuilderImpl(new StringBuilder());

		try {
			regEx.regEx(null);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}

		regEx.regEx("\\[Regul*r+expre$$|ons ^are^ awesome?{!}]()"); //$NON-NLS-1$
		assertEquals(
				"\\\\\\[Regul\\*r\\+expre\\$\\$\\|ons \\^are\\^ awesome\\?\\{!}]\\(\\)", regEx.toString()); //$NON-NLS-1$

		regEx = new RegExBuilderImpl(new StringBuilder());
		regEx.regEx("\\QI use \\Q...\\E in my regular expressions.\\E"); //$NON-NLS-1$
		assertEquals(
				"\\\\QI use \\\\Q\\.\\.\\.\\\\E in my regular expressions\\.\\\\E", regEx.toString()); //$NON-NLS-1$

		regEx = new RegExBuilderImpl(new StringBuilder());
		regEx.regEx(new Object() {
			@Override
			public String toString() {
				return "Hola. ^_^"; //$NON-NLS-1$
			}
		});
		assertEquals("Hola\\. \\^_\\^", regEx.toString()); //$NON-NLS-1$

		regEx = new RegExBuilderImpl(new StringBuilder());

		try {
			regEx.regEx(new Object() {
				@Override
				public String toString() {
					return null;
				}
			});
			fail();
		} catch (NullPointerException e) {
			// expected
		}
	}

	@Test
	public void regExOthersTest() {
		RegExBuilder regEx;

		byte b = -128;
		regEx = new RegExBuilderImpl(new StringBuilder()).regEx(b);
		assertEquals("-128", regEx.toString()); //$NON-NLS-1$

		short s = 32767;
		regEx = new RegExBuilderImpl(new StringBuilder()).regEx(s);
		assertEquals("32767", regEx.toString()); //$NON-NLS-1$

		int i = -2147483648;
		regEx = new RegExBuilderImpl(new StringBuilder()).regEx(i);
		assertEquals("-2147483648", regEx.toString()); //$NON-NLS-1$

		long l = -9223372036854775808l;
		regEx = new RegExBuilderImpl(new StringBuilder()).regEx(l);
		assertEquals("-9223372036854775808", regEx.toString()); //$NON-NLS-1$

		float f = Float.NaN;
		regEx = new RegExBuilderImpl(new StringBuilder()).regEx(f);
		assertEquals("NaN", regEx.toString()); //$NON-NLS-1$

		f = Float.POSITIVE_INFINITY;
		regEx = new RegExBuilderImpl(new StringBuilder()).regEx(f);
		assertEquals("Infinity", regEx.toString()); //$NON-NLS-1$

		f = Float.NEGATIVE_INFINITY;
		regEx = new RegExBuilderImpl(new StringBuilder()).regEx(f);
		assertEquals("-Infinity", regEx.toString()); //$NON-NLS-1$

		f = -3.1492E20f;
		regEx = new RegExBuilderImpl(new StringBuilder()).regEx(f);
		assertEquals("-3\\.1492E20", regEx.toString()); //$NON-NLS-1$

		f = -0;
		regEx = new RegExBuilderImpl(new StringBuilder()).regEx(f);
		assertEquals("0\\.0", regEx.toString()); //$NON-NLS-1$

		double d = Double.NaN;
		regEx = new RegExBuilderImpl(new StringBuilder()).regEx(d);
		assertEquals("NaN", regEx.toString()); //$NON-NLS-1$

		d = Double.POSITIVE_INFINITY;
		regEx = new RegExBuilderImpl(new StringBuilder()).regEx(d);
		assertEquals("Infinity", regEx.toString()); //$NON-NLS-1$

		d = Double.NEGATIVE_INFINITY;
		regEx = new RegExBuilderImpl(new StringBuilder()).regEx(d);
		assertEquals("-Infinity", regEx.toString()); //$NON-NLS-1$

		d = 3.1415926535898E-310;
		regEx = new RegExBuilderImpl(new StringBuilder()).regEx(d);
		assertEquals("3\\.1415926535898E-310", regEx.toString()); //$NON-NLS-1$

		d = -0;
		regEx = new RegExBuilderImpl(new StringBuilder()).regEx(d);
		assertEquals("0\\.0", regEx.toString()); //$NON-NLS-1$

		boolean bool = true;
		regEx = new RegExBuilderImpl(new StringBuilder()).regEx(bool);
		assertEquals("true", regEx.toString()); //$NON-NLS-1$

		char c = '^';
		regEx = new RegExBuilderImpl(new StringBuilder()).regEx(c);
		assertEquals("\\^", regEx.toString()); //$NON-NLS-1$

		c = '\u1234';
		regEx = new RegExBuilderImpl(new StringBuilder()).regEx(c);
		assertEquals("\u1234", regEx.toString()); //$NON-NLS-1$

		c = '\u000C';
		regEx = new RegExBuilderImpl(new StringBuilder()).regEx(c);
		assertEquals("\f", regEx.toString()); //$NON-NLS-1$

		c = '\u002A';
		regEx = new RegExBuilderImpl(new StringBuilder()).regEx(c);
		assertEquals("\\*", regEx.toString()); //$NON-NLS-1$
	}

	@Test
	public void quoteTest() {
		RegExBuilder regEx = new RegExBuilderImpl(new StringBuilder());

		try {
			regEx.quote(null);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}

		regEx.quote("\\[Regul*r+expre$$|ons ^are^ awesome?{!}]()"); //$NON-NLS-1$
		assertEquals(
				"\\Q\\[Regul*r+expre$$|ons ^are^ awesome?{!}]()\\E", regEx.toString()); //$NON-NLS-1$

		regEx = new RegExBuilderImpl(new StringBuilder());
		regEx.quote("\\QI use \\Q...\\E in my regular expressions.\\E"); //$NON-NLS-1$
		assertEquals(
				"\\Q\\QI use \\Q...\\\\E\\QE in my regular expressions.\\\\E\\QE\\E", regEx.toString()); //$NON-NLS-1$
	}



}
