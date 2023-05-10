from core.element.base_element import BaseElement
from core.util.decorator import staleness_of


class IFrame(BaseElement):

    def __init__(self, locator, parent=None):
        super().__init__(locator, parent)

    @staleness_of
    def select(self):
        self._driver.switch_to.frame(self.element)

    def deselect(self):
        self._driver.switch_to.default_content()
