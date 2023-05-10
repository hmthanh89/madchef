from core.driver.driver_constant import CHROME, FIREFOX, REMOTE, MOBILE, IE
from core.driver.chrome_driver import ChromeDriver
from core.driver.firefox_driver import FirefoxDriver
from core.driver.remote_driver import RemoteDriver
from core.driver.mobile_driver import MobileDriver
from core.driver.ie_driver import IEDriver
from core.config import config
import logging


class FactoryDriver:
    factory_driver = None

    @classmethod
    def get_instance(cls):
        if cls.factory_driver is None:
            cls.factory_driver = FactoryDriver()
        return cls.factory_driver

    def __init__(self):
        self._driver_manager = {}
        self._current_driver = None
        self._current_key = "default"
        self._driver_mapping = {
            CHROME: ChromeDriver(),
            FIREFOX: FirefoxDriver(),
            IE: IEDriver(),
            REMOTE: RemoteDriver(),
            MOBILE: MobileDriver()
        }

    def start_driver(self, cfc, key="default"):
        if self.__is_key_existed(key):
            if config.override_driver():
                logging.warning(
                    "Your driver '%s' is already existed. Will close the current and start new one. "
                    "If you want to make sure there is not existed driver, "
                    "change the value for 'selenpy_override_driver' environment variable to false" % key)
                try:
                    self.quit(key)
                except Exception as e:
                    logging.warning("Cannot quit your driver", e)
            else:
                raise RuntimeError("Your driver '%s' is already existed. Please close it and try again" % key)
        self._current_driver = self._driver_mapping[cfc.driver].create_driver(cfc)
        self._driver_manager[key] = self._current_driver
        self._current_key = key

    def get_current_driver(self):
        if self._current_driver:
            return self._current_driver.get_webdriver()
        return None

    def get_driver(self, key):
        if self.__is_key_existed(key):
            return self._driver_manager[key].get_webdriver()
        raise RuntimeError("Cannot find '%s'. Please make sure that it has been created" % key)

    def switch_to_driver(self, key):
        if self.__is_key_existed(key):
            self._current_key = key
            self._current_driver = self._driver_manager[key]
        else:
            raise RuntimeError("Cannot find '%s'. Please make sure that it has been created" % key)

    def quit_all(self):
        for key in self._driver_manager:
            try:
                self._driver_manager[key].get_webdriver().quit()
            except Exception as e:
                logging.warning("Error occurs when quitting driver", e)
        self._driver_manager.clear()

    def remove_driver(self, key):
        if self.__is_key_existed(key):
            del self._driver_manager[key]

    def quit(self, key):
        # Close driver by key
        self.get_driver(key).quit()

        # Remove key from _driver_manager
        self.remove_driver(key)

    def __is_key_existed(self, key):
        return key in self._driver_manager
