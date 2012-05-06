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
 * @author Chris Topher
 * @version 0.0, Aug 23, 2009
 */
public enum RegExMatchFlag {

	TURN_ON_UNIX_LINES(true, 'd'),
	TURN_OFF_UNIX_LINES(false, 'd'),
	TURN_ON_CASE_INSENSITIVE(true, 'i'),
	TURN_OFF_CASE_INSENSITIVE(false, 'i'),
	TURN_ON_COMMENTS(true, 'x'),
	TURN_OFF_COMMENTS(false, 'x'),
	TURN_ON_MULTILINE(true, 'm'),
	TURN_OFF_MULTILINE(false, 'm'),
	TURN_ON_DOTALL(true, 's'),
	TURN_OFF_DOTALL(false, 's'),
	TURN_ON_UNICODE_CASE(true, 'u'),
	TURN_OFF_UNICODE_CASE(false, 'u');

	static {
		TURN_ON_UNIX_LINES.opposite = TURN_OFF_UNIX_LINES;
		TURN_OFF_UNIX_LINES.opposite = TURN_ON_UNIX_LINES;
		TURN_ON_CASE_INSENSITIVE.opposite = TURN_OFF_CASE_INSENSITIVE;
		TURN_OFF_CASE_INSENSITIVE.opposite = TURN_ON_CASE_INSENSITIVE;
		TURN_ON_COMMENTS.opposite = TURN_OFF_COMMENTS;
		TURN_OFF_COMMENTS.opposite = TURN_ON_COMMENTS;
		TURN_ON_MULTILINE.opposite = TURN_OFF_MULTILINE;
		TURN_OFF_MULTILINE.opposite = TURN_ON_MULTILINE;
		TURN_ON_DOTALL.opposite = TURN_OFF_DOTALL;
		TURN_OFF_DOTALL.opposite = TURN_ON_DOTALL;
		TURN_ON_UNICODE_CASE.opposite = TURN_OFF_UNICODE_CASE;
		TURN_OFF_UNICODE_CASE.opposite = TURN_ON_UNICODE_CASE;
	}

	private RegExMatchFlag opposite;

	private final char character_;

	private final boolean isOnFlag_;

	RegExMatchFlag(boolean isOnFlag, char character) {
		this.isOnFlag_ = isOnFlag;
		this.character_ = character;
	}

	public RegExMatchFlag getOppositeFlag() {
		return this.opposite;
	}

	public boolean isOnFlag() {
		return this.isOnFlag_;
	}

	public char toChar() {
		return this.character_;
	}
}
