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
 * Methods that are common between RegExFactory, RegExBuilder, and
 * CharClassBuilder
 * 
 * @author Chris Topher
 * @version 0.0, Sep 5, 2009
 */
public interface CommonBuilder<T extends CommonBuilder<T>> {
	// ==========
	// Characters
	// ==========

	/**
	 * Appends <tt>\a</tt> to the regular expression.
	 * <p>
	 * <tt>\a</tt> matches the alert (bell) character ({@code'\u0007'}).
	 * </p>
	 * 
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	T bell();

	/**
	 * Appends <tt>\t</tt> to the regular expression.
	 * <p>
	 * <tt>\t</tt> matches the tab character (<tt>'\u0009'</tt>).
	 * </p>
	 * 
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	T tab();

	/**
	 * Appends <tt>\n</tt> to the regular expression.
	 * <p>
	 * <tt>\n</tt> matches the newline (line feed) character (<tt>'\u000A'</tt>).
	 * </p>
	 * 
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	T lineFeed();

	/**
	 * Appends <tt>\v</tt> to the regular expression.
	 * <p>
	 * <tt>\t</tt> matches the tab character (<tt>'\u000B'</tt>).
	 * </p>
	 * 
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	T verticalTab();

	/**
	 * Appends <tt>\f</tt> to the regular expression.
	 * <p>
	 * <tt>\f</tt> matches the form-feed character (<tt>'\u000C'</tt>).
	 * </p>
	 * 
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	T formFeed();

	/**
	 * Appends <tt>\r</tt> to the regular expression.
	 * <p>
	 * <tt>\r</tt> matches the carriage-return character (<tt>'\u000D'</tt>).
	 * </p>
	 * 
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	T carriageReturn();

	/**
	 * Appends <tt>\e</tt> to the regular expression.
	 * <p>
	 * <tt>\e</tt> matches the escape character ({@code'\u001B'}).
	 * </p>
	 * 
	 * @return {@code this} RegExBuilder or a {@code new} one
	 */
	T escape();

	/**
	 * Appends <tt>\c</tt> followed by the specified character to the regular
	 * expression.
	 * <p>
	 * <tt>\c</tt><em>x</em> matches the control character corresponding to
	 * <em>x</em>. If a lowercase letter is specified, it is capitalized before
	 * appending it to the regular expression. The following table lists the
	 * regular expressions (including the octal, ascii, and unicode versions)
	 * that correspond to each control character.
	 * </p>
	 * <table>
	 * <tr>
	 * <td>Equivalent Regular Expressions</td>
	 * <td>Matching Control Character</td>
	 * </tr>
	 * <tr>
	 * <td><tt>\cA</tt> <tt>\01</tt> <tt>\x01</tt> <tt>\u0001</tt></td>
	 * <td><tt>SOH</tt> (start of heading)</td>
	 * </tr>
	 * <tr>
	 * <td><tt>\cB</tt> <tt>\02</tt> <tt>\x02</tt> <tt>\u0002</tt></td>
	 * <td><tt>STX</tt> (start of text)</td>
	 * </tr>
	 * <tr>
	 * <td><tt>\cC</tt> <tt>\03</tt> <tt>\x03</tt> <tt>\u0003</tt></td>
	 * <td><tt>ETX</tt> (end of text)</td>
	 * </tr>
	 * <tr>
	 * <td><tt>\cD</tt> <tt>\04</tt> <tt>\x04</tt> <tt>\u0004</tt></td>
	 * <td><tt>EOT</tt> (end of transmission)</td>
	 * </tr>
	 * <tr>
	 * <td><tt>\cE</tt> <tt>\05</tt> <tt>\x05</tt> <tt>\u0005</tt></td>
	 * <td><tt>ENQ</tt> (enquiry)</td>
	 * </tr>
	 * <tr>
	 * <td><tt>\cF</tt> <tt>\06</tt> <tt>\x06</tt> <tt>\u0006</tt></td>
	 * <td><tt>ACK</tt> (acknowledge)</td>
	 * </tr>
	 * <tr>
	 * <td><tt>\cG</tt> <tt>\07</tt> <tt>\x07</tt> <tt>\u0007</tt></td>
	 * <td><tt>BEL</tt> (bell)</td>
	 * </tr>
	 * <tr>
	 * <td><tt>\cH</tt> <tt>\010</tt> <tt>\x08</tt> <tt>\u0008</tt></td>
	 * <td><tt>BS</tt> (backspace)</td>
	 * </tr>
	 * <tr>
	 * <td><tt>\cI</tt> <tt>\011</tt> <tt>\x09</tt> <tt>\u0009</tt></td>
	 * <td><tt>TAB</tt> (horizontal tab)</td>
	 * </tr>
	 * <tr>
	 * <td><tt>\cJ</tt> <tt>\012</tt> <tt>\x0A</tt> <tt>\u000A</tt></td>
	 * <td><tt>LF</tt> (NL line feed, new line)</td>
	 * </tr>
	 * <tr>
	 * <td><tt>\cK</tt> <tt>\013</tt> <tt>\x0B</tt> <tt>\u000B</tt></td>
	 * <td><tt>VT</tt> (vertical tab)</td>
	 * </tr>
	 * <tr>
	 * <td><tt>\cL</tt> <tt>\014</tt> <tt>\x0C</tt> <tt>\u000C</tt></td>
	 * <td><tt>FF</tt> (NP form feed, new page)</td>
	 * </tr>
	 * <tr>
	 * <td><tt>\cM</tt> <tt>\015</tt> <tt>\x0D</tt> <tt>\u000D</tt></td>
	 * <td><tt>CR</tt> (carriage return)</td>
	 * </tr>
	 * <tr>
	 * <td><tt>\cN</tt> <tt>\016</tt> <tt>\x0E</tt> <tt>\u000E</tt></td>
	 * <td><tt>SO</tt> (shift out)</td>
	 * </tr>
	 * <tr>
	 * <td><tt>\cO</tt> <tt>\017</tt> <tt>\x0F</tt> <tt>\u000F</tt></td>
	 * <td><tt>SI</tt> (shift in)</td>
	 * </tr>
	 * <tr>
	 * <td><tt>\cP</tt> <tt>\020</tt> <tt>\x10</tt> <tt>\u0010</tt></td>
	 * <td><tt>DLE</tt> (data link escape)</td>
	 * </tr>
	 * <tr>
	 * <td><tt>\cQ</tt> <tt>\021</tt> <tt>\x11</tt> <tt>\u0011</tt></td>
	 * <td><tt>DC1</tt> (device control 1)</td>
	 * </tr>
	 * <tr>
	 * <td><tt>\cR</tt> <tt>\022</tt> <tt>\x12</tt> <tt>\u0012</tt></td>
	 * <td><tt>DC2</tt> (device control 2)</td>
	 * </tr>
	 * <tr>
	 * <td><tt>\cS</tt> <tt>\023</tt> <tt>\x13</tt> <tt>\u0013</tt></td>
	 * <td><tt>DC3</tt> (device control 3)</td>
	 * </tr>
	 * <tr>
	 * <td><tt>\cT</tt> <tt>\024</tt> <tt>\x14</tt> <tt>\u0014</tt></td>
	 * <td><tt>DC4</tt> (device control 4)</td>
	 * </tr>
	 * <tr>
	 * <td><tt>\cU</tt> <tt>\025</tt> <tt>\x15</tt> <tt>\u0015</tt></td>
	 * <td><tt>NAK</tt> (negative acknowledge)</td>
	 * </tr>
	 * <tr>
	 * <td><tt>\cV</tt> <tt>\026</tt> <tt>\x16</tt> <tt>\u0016</tt></td>
	 * <td><tt>SYN</tt> (synchronous idle)</td>
	 * </tr>
	 * <tr>
	 * <td><tt>\cW</tt> <tt>\027</tt> <tt>\x17</tt> <tt>\u0017</tt></td>
	 * <td><tt>ETB</tt> (end of trans. block)</td>
	 * </tr>
	 * <tr>
	 * <td><tt>\cX</tt> <tt>\030</tt> <tt>\x18</tt> <tt>\u0018</tt></td>
	 * <td><tt>CAN</tt> (cancel)</td>
	 * </tr>
	 * <tr>
	 * <td><tt>\cY</tt> <tt>\031</tt> <tt>\x19</tt> <tt>\u0019</tt></td>
	 * <td><tt>EM</tt> (end of medium)</td>
	 * </tr>
	 * <tr>
	 * <td><tt>\cZ</tt> <tt>\032</tt> <tt>\x1A</tt> <tt>\u001A</tt></td>
	 * <td><tt>SUB</tt> (substitute)</td>
	 * </tr>
	 * </table>
	 * 
	 * @param x
	 *            character literal between <tt>A</tt> ({@code'\u0041'}) through
	 *            <tt>Z</tt> ({@code'\u005A'}) inclusive or between <tt>a</tt> (
	 *            {@code'\u0061'}) through <tt>z</tt> ({@code'\u007A'})
	 *            inclusive
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @throws IllegalArgumentException
	 *             if {@code x} is not in the range defined above
	 * @see #octal(int)
	 * @see #ascii(int)
	 * @see #unicode(int)
	 */
	T control(char x);

	/**
	 * Appends <tt>\x0</tt><em>n</em> where <em>n</em> is the octal
	 * representation of the specified argument {@code n}.
	 * <p>
	 * <tt>\x0</tt><em>n</em> matches the ASCII character with octal value
	 * {@code n}.
	 * </p>
	 * 
	 * @param n
	 *            integer between (0)<sub>8</sub> to (377)<sub>8</sub>
	 *            inclusive; this range is equivalent to (0)<sub>10</sub> to
	 *            (255)<sub>10</sub> inclusive
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @throws IllegalArgumentException
	 *             if {@code n} is not in the range defined above
	 */
	T octal(int n);

	/**
	 * Appends <tt>\x</tt><em>hh</em> to the regular expression.
	 * <p>
	 * <tt>\x</tt><em>hh</em> matches the ASCII character with hexadecimal value
	 * 0x<em>hh</em>. <em>h</em> can be:
	 * </p>
	 * <p>
	 * <tt>0 1 2 3 4 5 6 7 8 9 A B C D E F a b c d e f</tt>
	 * </p>
	 * 
	 * @param hex
	 *            integer between 0 (0x00) to 255 (0xFF) inclusive
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @throws IllegalArgumentException
	 *             if {@code hex} is not in the range defined above
	 */
	T ascii(int hex);

	// ===============
	// Unicode Support
	// ===============

	/**
	 * Appends <tt>&#92;u</tt><em>hhhh</em> to the regular expression.
	 * <p>
	 * <tt>&#92;u</tt><em>hhhh</em> matches the Unicode character with
	 * hexadecimal value 0x<em>hhhh</em>. <em>h</em> can be:
	 * </p>
	 * <p>
	 * <tt>0 1 2 3 4 5 6 7 8 9 A B C D E F a b c d e f</tt>
	 * </p>
	 * 
	 * @param hex
	 *            integer between 0 (0x0000) to 65535 (0xFFFF) inclusive
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @throws IllegalArgumentException
	 *             if {@code hex} is not in the range defined above
	 */
	T unicode(int hex);

	/**
	 * Appends <tt>\p{</tt><em>block</em><tt>}</tt> to the regular expression.
	 * <p>
	 * <tt>\p{</tt><em>block</em><tt>}</tt> matches any character in the
	 * specified block. The supported blocks are those found in <a
	 * href="http://www.unicode.org/book/u2.html">
	 * <em>The Unicode Standard, Version 3.0</em></a>. The block names are those
	 * defined in Chapter 14 and in the file <a
	 * href="http://www.unicode.org/Public/3.0-Update/Blocks-3.txt"
	 * >Blocks-3.txt</a> of the Unicode Character Database (reproduced below for
	 * convenience) except that the spaces are removed; <tt>"Basic Latin"</tt>,
	 * for example, becomes <tt>"BasicLatin"</tt>.
	 * </p>
	 * 
	 * <pre>
	 * # Start Code; End Code; Block Name
	 * 0000; 007F; Basic Latin
	 * 0080; 00FF; Latin-1 Supplement
	 * 0100; 017F; Latin Extended-A
	 * 0180; 024F; Latin Extended-B
	 * 0250; 02AF; IPA Extensions
	 * 02B0; 02FF; Spacing Modifier Letters
	 * 0300; 036F; Combining Diacritical Marks
	 * 0370; 03FF; Greek
	 * 0400; 04FF; Cyrillic
	 * 0530; 058F; Armenian
	 * 0590; 05FF; Hebrew
	 * 0600; 06FF; Arabic
	 * 0700; 074F; Syriac  
	 * 0780; 07BF; Thaana
	 * 0900; 097F; Devanagari
	 * 0980; 09FF; Bengali
	 * 0A00; 0A7F; Gurmukhi
	 * 0A80; 0AFF; Gujarati
	 * 0B00; 0B7F; Oriya
	 * 0B80; 0BFF; Tamil
	 * 0C00; 0C7F; Telugu
	 * 0C80; 0CFF; Kannada
	 * 0D00; 0D7F; Malayalam
	 * 0D80; 0DFF; Sinhala
	 * 0E00; 0E7F; Thai
	 * 0E80; 0EFF; Lao
	 * 0F00; 0FFF; Tibetan
	 * 1000; 109F; Myanmar 
	 * 10A0; 10FF; Georgian
	 * 1100; 11FF; Hangul Jamo
	 * 1200; 137F; Ethiopic
	 * 13A0; 13FF; Cherokee
	 * 1400; 167F; Unified Canadian Aboriginal Syllabics
	 * 1680; 169F; Ogham
	 * 16A0; 16FF; Runic
	 * 1780; 17FF; Khmer
	 * 1800; 18AF; Mongolian
	 * 1E00; 1EFF; Latin Extended Additional
	 * 1F00; 1FFF; Greek Extended
	 * 2000; 206F; General Punctuation
	 * 2070; 209F; Superscripts and Subscripts
	 * 20A0; 20CF; Currency Symbols
	 * 20D0; 20FF; Combining Marks for Symbols
	 * 2100; 214F; Letterlike Symbols
	 * 2150; 218F; Number Forms
	 * 2190; 21FF; Arrows
	 * 2200; 22FF; Mathematical Operators
	 * 2300; 23FF; Miscellaneous Technical
	 * 2400; 243F; Control Pictures
	 * 2440; 245F; Optical Character Recognition
	 * 2460; 24FF; Enclosed Alphanumerics
	 * 2500; 257F; Box Drawing
	 * 2580; 259F; Block Elements
	 * 25A0; 25FF; Geometric Shapes
	 * 2600; 26FF; Miscellaneous Symbols
	 * 2700; 27BF; Dingbats
	 * 2800; 28FF; Braille Patterns
	 * 2E80; 2EFF; CJK Radicals Supplement
	 * 2F00; 2FDF; Kangxi Radicals
	 * 2FF0; 2FFF; Ideographic Description Characters
	 * 3000; 303F; CJK Symbols and Punctuation
	 * 3040; 309F; Hiragana
	 * 30A0; 30FF; Katakana
	 * 3100; 312F; Bopomofo
	 * 3130; 318F; Hangul Compatibility Jamo
	 * 3190; 319F; Kanbun
	 * 31A0; 31BF; Bopomofo Extended
	 * 3200; 32FF; Enclosed CJK Letters and Months
	 * 3300; 33FF; CJK Compatibility
	 * 3400; 4DB5; CJK Unified Ideographs Extension A
	 * 4E00; 9FFF; CJK Unified Ideographs
	 * A000; A48F; Yi Syllables
	 * A490; A4CF; Yi Radicals
	 * AC00; D7A3; Hangul Syllables
	 * D800; DB7F; High Surrogates
	 * DB80; DBFF; High Private Use Surrogates
	 * DC00; DFFF; Low Surrogates
	 * E000; F8FF; Private Use
	 * F900; FAFF; CJK Compatibility Ideographs
	 * FB00; FB4F; Alphabetic Presentation Forms
	 * FB50; FDFF; Arabic Presentation Forms-A
	 * FE20; FE2F; Combining Half Marks
	 * FE30; FE4F; CJK Compatibility Forms
	 * FE50; FE6F; Small Form Variants
	 * FE70; FEFE; Arabic Presentation Forms-B
	 * FEFF; FEFF; Specials
	 * FF00; FFEF; Halfwidth and Fullwidth Forms
	 * FFF0; FFFD; Specials
	 * </pre>
	 * 
	 * @param block
	 *            a block of Unicode characters
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @throws IllegalArgumentException
	 *             if {@code block} is {@code null}
	 * @see #notUnicode(UnicodeBlock)
	 */
	T unicode(UnicodeBlock block);

	/**
	 * Appends <tt>\P{</tt><em>block</em><tt>}</tt> to the regular expression.
	 * <p>
	 * <tt>\P{</tt><em>block</em><tt>}</tt> matches any character except one in
	 * the specified block or category. The supported blocks are those found in
	 * <a href="http://www.unicode.org/book/u2.html">
	 * <em>The Unicode Standard, Version 3.0</em></a>. The block names are those
	 * defined in Chapter 14 and in the file <a
	 * href="http://www.unicode.org/Public/3.0-Update/Blocks-3.txt"
	 * >Blocks-3.txt</a> of the Unicode Character Database (reproduced below for
	 * convenience) except that the spaces are removed; <tt>"Basic Latin"</tt>,
	 * for example, becomes <tt>"BasicLatin"</tt>.
	 * </p>
	 * 
	 * <pre>
	 * # Start Code; End Code; Block Name
	 * 0000; 007F; Basic Latin
	 * 0080; 00FF; Latin-1 Supplement
	 * 0100; 017F; Latin Extended-A
	 * 0180; 024F; Latin Extended-B
	 * 0250; 02AF; IPA Extensions
	 * 02B0; 02FF; Spacing Modifier Letters
	 * 0300; 036F; Combining Diacritical Marks
	 * 0370; 03FF; Greek
	 * 0400; 04FF; Cyrillic
	 * 0530; 058F; Armenian
	 * 0590; 05FF; Hebrew
	 * 0600; 06FF; Arabic
	 * 0700; 074F; Syriac  
	 * 0780; 07BF; Thaana
	 * 0900; 097F; Devanagari
	 * 0980; 09FF; Bengali
	 * 0A00; 0A7F; Gurmukhi
	 * 0A80; 0AFF; Gujarati
	 * 0B00; 0B7F; Oriya
	 * 0B80; 0BFF; Tamil
	 * 0C00; 0C7F; Telugu
	 * 0C80; 0CFF; Kannada
	 * 0D00; 0D7F; Malayalam
	 * 0D80; 0DFF; Sinhala
	 * 0E00; 0E7F; Thai
	 * 0E80; 0EFF; Lao
	 * 0F00; 0FFF; Tibetan
	 * 1000; 109F; Myanmar 
	 * 10A0; 10FF; Georgian
	 * 1100; 11FF; Hangul Jamo
	 * 1200; 137F; Ethiopic
	 * 13A0; 13FF; Cherokee
	 * 1400; 167F; Unified Canadian Aboriginal Syllabics
	 * 1680; 169F; Ogham
	 * 16A0; 16FF; Runic
	 * 1780; 17FF; Khmer
	 * 1800; 18AF; Mongolian
	 * 1E00; 1EFF; Latin Extended Additional
	 * 1F00; 1FFF; Greek Extended
	 * 2000; 206F; General Punctuation
	 * 2070; 209F; Superscripts and Subscripts
	 * 20A0; 20CF; Currency Symbols
	 * 20D0; 20FF; Combining Marks for Symbols
	 * 2100; 214F; Letterlike Symbols
	 * 2150; 218F; Number Forms
	 * 2190; 21FF; Arrows
	 * 2200; 22FF; Mathematical Operators
	 * 2300; 23FF; Miscellaneous Technical
	 * 2400; 243F; Control Pictures
	 * 2440; 245F; Optical Character Recognition
	 * 2460; 24FF; Enclosed Alphanumerics
	 * 2500; 257F; Box Drawing
	 * 2580; 259F; Block Elements
	 * 25A0; 25FF; Geometric Shapes
	 * 2600; 26FF; Miscellaneous Symbols
	 * 2700; 27BF; Dingbats
	 * 2800; 28FF; Braille Patterns
	 * 2E80; 2EFF; CJK Radicals Supplement
	 * 2F00; 2FDF; Kangxi Radicals
	 * 2FF0; 2FFF; Ideographic Description Characters
	 * 3000; 303F; CJK Symbols and Punctuation
	 * 3040; 309F; Hiragana
	 * 30A0; 30FF; Katakana
	 * 3100; 312F; Bopomofo
	 * 3130; 318F; Hangul Compatibility Jamo
	 * 3190; 319F; Kanbun
	 * 31A0; 31BF; Bopomofo Extended
	 * 3200; 32FF; Enclosed CJK Letters and Months
	 * 3300; 33FF; CJK Compatibility
	 * 3400; 4DB5; CJK Unified Ideographs Extension A
	 * 4E00; 9FFF; CJK Unified Ideographs
	 * A000; A48F; Yi Syllables
	 * A490; A4CF; Yi Radicals
	 * AC00; D7A3; Hangul Syllables
	 * D800; DB7F; High Surrogates
	 * DB80; DBFF; High Private Use Surrogates
	 * DC00; DFFF; Low Surrogates
	 * E000; F8FF; Private Use
	 * F900; FAFF; CJK Compatibility Ideographs
	 * FB00; FB4F; Alphabetic Presentation Forms
	 * FB50; FDFF; Arabic Presentation Forms-A
	 * FE20; FE2F; Combining Half Marks
	 * FE30; FE4F; CJK Compatibility Forms
	 * FE50; FE6F; Small Form Variants
	 * FE70; FEFE; Arabic Presentation Forms-B
	 * FEFF; FEFF; Specials
	 * FF00; FFEF; Halfwidth and Fullwidth Forms
	 * FFF0; FFFD; Specials
	 * </pre>
	 * 
	 * @param block
	 *            a block of Unicode characters
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @throws IllegalArgumentException
	 *             if {@code block} is {@code null}
	 * @see #unicode(UnicodeBlock)
	 */
	T notUnicode(UnicodeBlock block);

	/**
	 * Appends <tt>\p{</tt><em>category</em><tt>}</tt> to the regular
	 * expression.
	 * <p>
	 * <tt>\p{</tt><em>category</em><tt>}</tt> matches any character in the
	 * specified category. The supported categories are those found in <a
	 * href="http://www.unicode.org/book/u2.html">
	 * <em>The Unicode Standard, Version 3.0</em></a>. The category names are
	 * those defined in table 4-5 of the Standard (p.88), both normative and
	 * informative (reproduced below for convenience).
	 * </p>
	 * 
	 * <pre>
	 * Table 4-5. General Category
	 * 
	 * Normative
	 * Lu = Letter, uppercase
	 * Ll = Letter, lowercase
	 * Lt = Letter, titlecase
	 * 
	 * Mn = Mark, nonspacing
	 * Mc = Mark, spacing combining
	 * Me = Mark, enclosing
	 * 
	 * Nd = Number, decimal digit
	 * Nl = Number, letter
	 * No = Number, other
	 * 
	 * Zs = Separator, space
	 * Zl = Separator, line
	 * Zp = Separator, paragraph
	 * 
	 * Cc = Other, control
	 * Cf = Other, format
	 * Cs = Other, surrogate
	 * Co = Other, private use
	 * Cn = Other, not assigned
	 * 
	 * Informative
	 * Lm = Letter, modifier
	 * Lo = Letter, other
	 * 
	 * Pc = Punctuation, connector
	 * Pd = Punctuation, dash
	 * Ps = Punctuation, open
	 * Pe = Punctuation, close
	 * Pi = Punctuation, initial quote (may behave like Ps or Pe depending on usage)
	 * Pf = Punctuation, final quote (may behanve like Ps or Pe depending on usage)
	 * Po = Punctuation, other
	 * 
	 * Sm = Symbol, math
	 * Sc = Symbol, currency
	 * Sk = Symbol, modifier
	 * So = Symbol, other
	 * </pre>
	 * 
	 * @param category
	 *            a category of Unicode characters
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @throws IllegalArgumentException
	 *             if {@code category} is {@code null}
	 * @see #notUnicode(UnicodeCharacterProperty)
	 */
	T unicode(UnicodeCharacterProperty category);

	/**
	 * Appends <tt>\P{</tt><em>category</em><tt>}</tt> to the regular
	 * expression.
	 * <p>
	 * <tt>\P{</tt><em>category</em><tt>}</tt> matches any character except one
	 * in the specified category. The supported categories are those found in <a
	 * href="http://www.unicode.org/book/u2.html">
	 * <em>The Unicode Standard, Version 3.0</em></a>. The category names are
	 * those defined in table 4-5 of the Standard (p.88), both normative and
	 * informative (reproduced below for convenience).
	 * </p>
	 * 
	 * <pre>
	 * Table 4-5. General Category
	 * 
	 * Normative
	 * Lu = Letter, uppercase
	 * Ll = Letter, lowercase
	 * Lt = Letter, titlecase
	 * 
	 * Mn = Mark, nonspacing
	 * Mc = Mark, spacing combining
	 * Me = Mark, enclosing
	 * 
	 * Nd = Number, decimal digit
	 * Nl = Number, letter
	 * No = Number, other
	 * 
	 * Zs = Separator, space
	 * Zl = Separator, line
	 * Zp = Separator, paragraph
	 * 
	 * Cc = Other, control
	 * Cf = Other, format
	 * Cs = Other, surrogate
	 * Co = Other, private use
	 * Cn = Other, not assigned
	 * 
	 * Informative
	 * Lm = Letter, modifier
	 * Lo = Letter, other
	 * 
	 * Pc = Punctuation, connector
	 * Pd = Punctuation, dash
	 * Ps = Punctuation, open
	 * Pe = Punctuation, close
	 * Pi = Punctuation, initial quote (may behave like Ps or Pe depending on usage)
	 * Pf = Punctuation, final quote (may behanve like Ps or Pe depending on usage)
	 * Po = Punctuation, other
	 * 
	 * Sm = Symbol, math
	 * Sc = Symbol, currency
	 * Sk = Symbol, modifier
	 * So = Symbol, other
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param category
	 *            a category of Unicode characters
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @throws IllegalArgumentException
	 *             if {@code property} is {@code null}
	 * @see #unicode(UnicodeCharacterProperty)
	 */
	T notUnicode(UnicodeCharacterProperty category);

	// ===========================
	// Shorthand Character Classes
	// ===========================

	/**
	 * Appends <tt>\p{</tt><em>posixCharClass</em><tt>}</tt> to the regular
	 * expression.
	 * <p>
	 * <tt>\p{</tt><em>posixCharClass</em><tt>}</tt> matches any character in
	 * the specified POSIX character class. The supported POSIX character
	 * classes are:
	 * </p>
	 * <table>
	 * <tr align="left">
	 * <th colspan="2">POSIX character classes</b> (US-ASCII only)<b></th>
	 * </tr>
	 * <tr>
	 * <td><tt>\p{Lower}</tt></td>
	 * <td>A lower-case alphabetic character: <tt>[a-z]</tt></td>
	 * </tr>
	 * <tr>
	 * <td><tt>\p{Upper}</tt></td>
	 * <td>An upper-case alphabetic character:<tt>[A-Z]</tt></td>
	 * </tr>
	 * <tr>
	 * <td><tt>\p{ASCII}</tt></td>
	 * <td>All ASCII:<tt>[\x00-\x7F]</tt></td>
	 * </tr>
	 * <tr>
	 * <td><tt>\p{Alpha}</tt></td>
	 * <td>An alphabetic character: <tt>[\p{Lower}\p{Upper}]</tt></td>
	 * </tr>
	 * <tr>
	 * <td><tt>\p{Digit}</tt></td>
	 * <td>A decimal digit: <tt>[0-9]</tt></td>
	 * </tr>
	 * <tr>
	 * <td><tt>\p{Alnum}</tt></td>
	 * <td>An alphanumeric character: <tt>[\p{Alpha}\p{Digit}]</tt></td>
	 * </tr>
	 * <tr>
	 * <td><tt>\p{Punct}</tt></td>
	 * <td>Punctuation: One of <tt>!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~</tt></td>
	 * </tr>
	 * <!-- <tt>[\!"#\$%&'\(\)\*\+,\-\./:;\<=\>\?@\[\\\]\^_`\{\|\}~]</tt> <tt>[\X21-\X2F\X31-\X40\X5B-\X60\X7B-\X7E]</tt> -->
	 * <tr>
	 * <td><tt>\p{Graph}</tt></td>
	 * <td>A visible character: <tt>[\p{Alnum}\p{Punct}]</tt></td>
	 * </tr>
	 * <tr>
	 * <td><tt>\p{Print}</tt></td>
	 * <td>A printable character: <tt>[\p{Graph}\x20]</tt></td>
	 * </tr>
	 * <tr>
	 * <td><tt>\p{Blank}</tt></td>
	 * <td>A space or a tab: <tt>[ \t]</tt></td>
	 * </tr>
	 * <tr>
	 * <td><tt>\p{Cntrl}</tt></td>
	 * <td>A control character: <tt>[\x00-\x1F\x7F]</td>
	 * </tr>
	 * <tr>
	 * <td><tt>\p{XDigit}</tt></td>
	 * <td>A hexadecimal digit: <tt>[0-9a-fA-F]</tt></td>
	 * </tr>
	 * <tr>
	 * <td><tt>\p{Space}</tt></td>
	 * <td>A whitespace character: <tt>[ \t\n\x0B\f\r]</tt></td>
	 * </tr>
	 * </table>
	 * 
	 * @param posixCharClass
	 *            a POSIX character class
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @throws IllegalArgumentException
	 *             if {@code propertyOrBlock} is {@code null}
	 */
	T posix(POSIXCharacterClass posixCharClass);

	/**
	 * Appends <tt>\P{</tt><em>posixCharClass</em><tt>}</tt> to the regular
	 * expression.
	 * <p>
	 * <tt>\P{</tt><em>posixCharClass</em><tt>}</tt> matches any character
	 * except one in the specified POSIX character class. The supported POSIX
	 * character classes are:
	 * </p>
	 * <table>
	 * <tr align="left">
	 * <th colspan="2">POSIX character classes</b> (US-ASCII only)<b></th>
	 * </tr>
	 * <tr>
	 * <td><tt>Lower</tt></td>
	 * <td>A lower-case alphabetic character: <tt>[a-z]</tt></td>
	 * </tr>
	 * <tr>
	 * <td><tt>Upper</tt></td>
	 * <td>An upper-case alphabetic character:<tt>[A-Z]</tt></td>
	 * </tr>
	 * <tr>
	 * <td><tt>ASCII</tt></td>
	 * <td>All ASCII:<tt>[\x00-\x7F]</tt></td>
	 * </tr>
	 * <tr>
	 * <td><tt>Alpha</tt></td>
	 * <td>An alphabetic character: <tt>[\p{Lower}\p{Upper}]</tt></td>
	 * </tr>
	 * <tr>
	 * <td><tt>Digit</tt></td>
	 * <td>A decimal digit: <tt>[0-9]</tt></td>
	 * </tr>
	 * <tr>
	 * <td><tt>Alnum</tt></td>
	 * <td>An alphanumeric character: <tt>[\p{Alpha}\p{Digit}]</tt></td>
	 * </tr>
	 * <tr>
	 * <td><tt>Punct</tt></td>
	 * <td>Punctuation: One of <tt>!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~</tt></td>
	 * </tr>
	 * <!-- <tt>[\!"#\$%&'\(\)\*\+,\-\./:;\<=\>\?@\[\\\]\^_`\{\|\}~]</tt> <tt>[\X21-\X2F\X31-\X40\X5B-\X60\X7B-\X7E]</tt> -->
	 * <tr>
	 * <td><tt>Graph</tt></td>
	 * <td>A visible character: <tt>[\p{Alnum}\p{Punct}]</tt></td>
	 * </tr>
	 * <tr>
	 * <td><tt>Print</tt></td>
	 * <td>A printable character: <tt>[\p{Graph}\x20]</tt></td>
	 * </tr>
	 * <tr>
	 * <td><tt>Blank</tt></td>
	 * <td>A space or a tab: <tt>[ \t]</tt></td>
	 * </tr>
	 * <tr>
	 * <td><tt>Cntrl</tt></td>
	 * <td>A control character: <tt>[\x00-\x1F\x7F]</td>
	 * </tr>
	 * <tr>
	 * <td><tt>XDigit</tt></td>
	 * <td>A hexadecimal digit: <tt>[0-9a-fA-F]</tt></td>
	 * </tr>
	 * <tr>
	 * <td><tt>Space</tt></td>
	 * <td>A whitespace character: <tt>[ \t\n\x0B\f\r]</tt></td>
	 * </tr>
	 * </table>
	 * 
	 * @param posixCharClass
	 *            a POSIX character class
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @throws IllegalArgumentException
	 *             if {@code propertyOrBlock} is {@code null}
	 */
	T notPOSIX(POSIXCharacterClass posixCharClass);

	/**
	 * Appends <tt>\d</tt> to the regular expression.
	 * <p>
	 * <tt>\d</tt> is a shorthand character class for <tt>[0-9]</tt>.
	 * </p>
	 * 
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @see #notDigit()
	 */
	T digit();

	/**
	 * Appends <tt>\D</tt> to the regular expression.
	 * <p>
	 * <tt>\D</tt> is a shorthand character class for <tt>[^0-9]</tt>.
	 * </p>
	 * 
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @see #digit()
	 */
	T notDigit();

	/**
	 * Appends <tt>\s</tt> to the regular expression.
	 * <p>
	 * <tt>\s</tt> is a shorthand character class for <tt>[ \t\n\x0B\f\r]</tt>.
	 * </p>
	 * 
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @see #notWhitespace()
	 */
	T whitespace();

	/**
	 * Appends <tt>\S</tt> to the regular expression.
	 * <p>
	 * <tt>\S</tt> is a shorthand character class for <tt>[^\s]</tt>.
	 * </p>
	 * 
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @see #whitespace()
	 */
	T notWhitespace();

	/**
	 * Appends <tt>\w</tt> to the regular expression.
	 * <p>
	 * <tt>\w</tt> is a shorthand character class for <tt>[a-zA-Z_0-9]</tt>.
	 * </p>
	 * 
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @see #notWordCharacter()
	 */
	T wordCharacter();

	/**
	 * Appends <tt>\W</tt> to the regular expression.
	 * <p>
	 * <tt>\W</tt> is a shorthand character class for <tt>[^\w]</tt>.
	 * </p>
	 * 
	 * @return {@code this} RegExBuilder or a {@code new} one
	 * @see #wordCharacter()
	 */
	T notWordCharacter();
}
