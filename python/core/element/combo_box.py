from selenium.webdriver.support.ui import Select
from core.element.base_element import BaseElement
from core.util.decorator import staleness_of


class ComboBox(BaseElement):

    def __init__(self, locator, parent=None):
        super().__init__(locator, parent)

    @staleness_of
    def select_by_value(self, value):
        Select(self.element).select_by_value(value)

    @staleness_of
    def select_by_index(self, idx):
        Select(self.element).select_by_index(idx)

    @staleness_of
    def select_by_visible_text(self, text):
        Select(self.element).select_by_visible_text(text)

    @property
    @staleness_of
    def first_selected_text(self):
        return Select(self.element).first_selected_option.text
