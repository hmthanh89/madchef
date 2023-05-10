from core.element.base_element import BaseElement
from core.util.wait_helper import wait_until
from core.util.decorator import staleness_of


class CheckBox(BaseElement):

    def __init__(self, locator, parent=None):
        super().__init__(locator, parent)

    @staleness_of
    def is_checked(self):
        return self.element.is_selected()

    @staleness_of
    def check(self):
        if not self.is_checked():
            self.click()

    @staleness_of
    def un_check(self):
        if self.is_checked():
            self.click()

    def wait_for_checked(self, timeout=None):
        wait_until(lambda: self.is_checked(),
                   "Element '%s' was not checked in <TIMEOUT>." % self._locator,
                   timeout)

    def wait_for_unchecked(self, timeout=None):
        wait_until(lambda: not self.is_checked(),
                   "Element '%s' was not un-checked in <TIMEOUT>." % self._locator,
                   timeout)
