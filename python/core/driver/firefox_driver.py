from core.driver.base_driver import BaseDriver
from selenium import webdriver
from webdriver_manager.firefox import GeckoDriverManager


class FirefoxDriver(BaseDriver):

    def create_driver(self, config):
        options = webdriver.FirefoxOptions()
        self._driver = webdriver.Firefox(executable_path=GeckoDriverManager().install(),
                                         options=options,
                                         desired_capabilities=config.capabilities)
        return self
