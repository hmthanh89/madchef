package com.logigear.control.common.imp;

import org.openqa.selenium.By;

import com.logigear.control.base.imp.BaseControl;
import com.logigear.control.base.imp.Editable;
import com.logigear.control.common.IElement;

public class Element extends Editable implements IElement {

	public Element(String locator) {
		super(locator);
	}

	public Element(By locator) {
		super(locator);
	}

	public Element(String locator, Object... value) {
		super(locator, value);
	}

	public Element(BaseControl parent, String locator) {
		super(parent, locator);
	}

	public Element(BaseControl parent, By locator) {
		super(parent, locator);
	}

	public Element(BaseControl parent, String locator, Object... value) {
		super(parent, locator, value);
	}
}
