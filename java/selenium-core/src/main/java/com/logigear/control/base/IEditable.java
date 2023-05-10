package com.logigear.control.base;

public interface IEditable extends IClickable {

	void setText(String value);

	void setValue(String value);

	void enter(CharSequence... value);

	void clear();
}
