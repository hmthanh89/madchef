from selenium.webdriver.common.by import By
from core.util.decorator import staleness_of
from core import selenium
from core.util.wait_helper import wait_until
from core.config import config
from selenium.common.exceptions import NoSuchElementException
import logging
import traceback
import rootpath


class CachedElement:

    def __init__(self, driver, element):
        self._element = element
        self._driver = driver

    @property
    def element(self):
        return self._element

    @element.setter
    def element(self, value):
        self._element = value

    @property
    def driver(self):
        return self._driver

    @driver.setter
    def driver(self, value):
        self._driver = value


class BaseElement:

    def __init__(self, locator, parent=None):
        self._strategies = {
            'id': self.__find_by_id,
            'name': self.__find_by_name,
            'xpath': self.__find_by_xpath,
            'css': self.__find_by_css_selector,
            'class': self.__find_by_class_name,
            'accessibility_id': self.__find_by_accessibility_id
        }

        self._locator = locator
        self._dynamic_locator = locator
        self._cached_element = None
        self._parent = parent
        self._is_xpath_warning = False

    def format(self, *args):
        self._locator = self._dynamic_locator % args
        self._cached_element = None

    @property
    def element(self):
        return self.get_element()

    @property
    def text(self):
        return self.element.text

    @property
    def tag_name(self):
        return self.element.tag_name

    @staleness_of
    def get_attribute(self, name):
        return self.element.get_attribute(name)

    def get_elements(self):
        return self.__find(False)

    def get_element(self, cached=True):
        if config.element_caching() and self._cached_element is not None \
                and self._cached_element.driver is self._driver and cached:
            return self._cached_element.element
        try:
            self._cached_element = CachedElement(self._driver, self.__find())
            if self.__is_xpath() and not self._is_xpath_warning and config.xpath_warning():
                attribute_name = "id"
                attribute_value = self._cached_element.element.get_attribute(attribute_name)
                if not attribute_value:
                    attribute_name = "name"
                    attribute_value = self._cached_element.element.get_attribute("name")
                if attribute_value:
                    path = rootpath.detect()
                    stack = [line for line in traceback.format_stack() if
                             path in line and "core\\" not in line]
                    logging.warning(
                        "Your defined locator: '%s' has %s attribute with value '%s'. Please use it instead of xpath." +
                        "\n\rTraceback: \n\r%s",
                        self._locator, attribute_name, attribute_value, "\n".join(stack))
                self._is_xpath_warning = True

            return self._cached_element.element
        except NoSuchElementException:
            return None

    @staleness_of
    def click(self):
        self.wait_for_enabled()
        self.element.click()

    @staleness_of
    def send_keys(self, *value):
        self.element.send_keys(value)

    @staleness_of
    def clear(self):
        self.element.clear()

    @staleness_of
    def is_displayed(self):
        if self.element is None:
            return False
        return self.element.is_displayed()

    @staleness_of
    def is_enabled(self):
        return self.element.is_enabled()

    @staleness_of
    def is_selected(self):
        return self.element.is_selected()

    def wait_for_visible(self, timeout=None):
        """Wait until element is visible
        """
        wait_until(lambda: self.is_displayed(),
                   "Element '%s' not visible after <TIMEOUT>." % self._locator,
                   timeout)

    def wait_for_invisible(self, timeout=None):
        """ Wait until element is not visible
        """
        wait_until(lambda: not self.is_displayed(),
                   "Element '%s' still visible after <TIMEOUT>." % self._locator,
                   timeout)

    def wait_for_enabled(self, timeout=None):
        """
        Waits until element is enabled.
        """
        wait_until(lambda: self.is_enabled(),
                   "Element '%s' was not enabled in <TIMEOUT>." % self._locator,
                   timeout)

    def wait_for_disabled(self, timeout=None):
        """
        Waits until element is disabled.
        """
        wait_until(lambda: not self.is_enabled(),
                   "Element '%s' was not disabled in <TIMEOUT>." % self._locator,
                   timeout)

    def wait_for_appear(self, timeout=None):
        """Wait until element appears in DOM.
        """
        wait_until(lambda: self.get_element(False) is not None,
                   "Element '%s' did not exit in <TIMEOUT>." % self._locator,
                   timeout)

    def wait_for_disappear(self, timeout=None):
        """Wait until element disappears in DOM.
        """
        wait_until(lambda: self.get_element(False) is None,
                   "Element '%s' did not exit in <TIMEOUT>." % self._locator,
                   timeout)

    def wait_for_text_contains(self, text, timeout=None):
        """Wait until element contains text.
        """
        wait_until(lambda: text in self.text,
                   "Element '%s' did not get text '%s' in <TIMEOUT>." % (self._locator, text),
                   timeout)

    def wait_for_text_not_contains(self, text, timeout=None):
        """Wait until element does not contain text.
        """
        wait_until(lambda: text not in self.text,
                   "Element '%s' still had text '%s' after <TIMEOUT>." % (self._locator, text),
                   timeout)

    # private methods
    @property
    def _driver(self):
        return selenium.get_driver()

    def __parse_locator(self, locator):
        if locator.startswith(('//', '(//')):
            return 'xpath', locator
        index = self.__get_locator_separator_index(locator)
        if index != -1:
            prefix = locator[:index].strip()
            if prefix in self._strategies:
                return prefix, locator[index + 1:].lstrip()
        return 'default', locator

    def __is_xpath(self):
        if self._locator.startswith(('//', '(//')):
            return True
        return False

    def __by(self, prefix):
        if prefix == "class":
            return By.CLASS_NAME
        elif prefix == "css":
            return By.CSS_SELECTOR
        else:
            return prefix

    def __get_locator_separator_index(self, locator):
        if '=' not in locator:
            return locator.find(':')
        if ':' not in locator:
            return locator.find('=')
        return min(locator.find('='), locator.find(':'))

    def __find(self, first_only=True):

        prefix, criteria = self.__parse_locator(self._locator)
        strategy = self._strategies[prefix]
        if self._parent:
            if type(self._parent) is not BaseElement:
                raise ValueError('Parent must be BaseElement but it '
                                 'was {}.'.format(type(self._parent)))
            else:
                parent = self._parent.element
        else:
            parent = self._driver
        return strategy(criteria, parent=parent, first_only=first_only)

    def __find_by_id(self, criteria, parent, first_only=True):
        if first_only:
            return parent.find_element_by_id(criteria)
        return parent.find_elements_by_id(criteria)

    def __find_by_name(self, criteria, parent, first_only=True):
        if first_only:
            return parent.find_element_by_name(criteria)
        return parent.find_elements_by_name(criteria)

    def __find_by_xpath(self, criteria, parent, first_only=True):
        if first_only:
            return parent.find_element_by_xpath(criteria)
        return parent.find_elements_by_xpath(criteria)

    def __find_by_css_selector(self, criteria, parent, first_only=True):
        if first_only:
            return parent.find_element_by_css_selector(criteria)
        return parent.find_elements_by_css_selector(criteria)

    def __find_by_class_name(self, criteria, parent, first_only=True):
        if first_only:
            return parent.find_element_by_class_name(criteria)
        return parent.find_elements_by_class_name(criteria)

    def __find_by_accessibility_id(self, criteria, parent, first_only=True):
        if first_only:
            return parent.find_element_by_accessibility_id(criteria)
        return parent.find_elements_by_accessibility_id(criteria)
