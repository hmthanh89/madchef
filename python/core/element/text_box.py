from core.element.base_element import BaseElement
from core.util.wait_helper import wait_until


class TextBox(BaseElement):

    def __init__(self, locator, parent=None):
        super().__init__(locator, parent)

    @property
    def value(self):
        return self.get_attribute("value")

    def wait_for_value_contains(self, value, timeout=None):
        wait_until(lambda: value in self.value,
                   "Element '%s' did not get value '%s' in <TIMEOUT>." % (self._locator, value),
                   timeout)

    def wait_for_value_not_contains(self, value, timeout=None):
        wait_until(lambda: value not in self.value,
                   "Element '%s' still had value '%s' after <TIMEOUT>." % (self._locator, value),
                   timeout)

    def enter(self, text):
        self.clear()
        self.send_keys(text)

    def append(self, text):
        self.send_keys(text)
