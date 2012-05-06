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
 * See {@link CommonBuilder#unicode(UnicodeCharacterProperty)}.
 * 
 * @author Chris Topher
 * @version 0.0, Jul 21, 2009
 */
public enum UnicodeCharacterProperty {

	// http://www.unicode.org/book/ch04.pdf Table 4-5

	L,
	Letter,
	Ll,
	Lowercase_Letter,
	Lu,
	Uppercase_Letter,
	Lt,
	Titlecase_Letter,
	LAmpersand("L&"),
	LetterAmpersand("Letter&"),
	Lm,
	Modifier_Letter,
	Lo,
	Other_Letter,
	M,
	Mark,
	Mn,
	Non_Spacing_Mark,
	Mc,
	Spacing_Combining_Mark,
	Me,
	Enclosing_Mark,
	Z,
	Separator,
	Zs,
	Space_Separator,
	Zl,
	Line_Separator,
	Zp,
	Paragraph_Separator,
	S,
	Symbol,
	Sm,
	Math_Symbol,
	Sc,
	Currency_Symbol,
	Sk,
	Modifier_Symbol,
	So,
	Other_Symbol,
	N,
	Number,
	Nd,
	Decimal_Digit_Number,
	Nl,
	Letter_Number,
	No,
	Other_Number,
	P,
	Punctuation,
	Pd,
	Dash_Punctuation,
	Ps,
	Open_Punctuation,
	Pe,
	Close_Punctuation,
	Pi,
	Initial_Punctuation,
	Pf,
	Final_Punctuation,
	Pc,
	Connector_Punctuation,
	Po,
	Other_Punctuation,
	C,
	Other,
	Cc,
	Control,
	Cf,
	Format,
	Co,
	Private_Use,
	Cs,
	Surrogate,
	Cn,
	Unassigned;

	private final String name;

	private UnicodeCharacterProperty() {
		this(null);
	}

	private UnicodeCharacterProperty(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name == null ? name() : this.name;
	}
}
