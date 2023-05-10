package com.logigear.control.base;

public interface IClickable extends IBaseControl {

	void click();

	void click(int x, int y);

	void clickByJs();

	void doubleClick();
	
	void waitForElementClickable();
	
	void waitForElementClickable(int timeOutInSecond);
}
