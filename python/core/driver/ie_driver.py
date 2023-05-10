from core.driver.base_driver import BaseDriver
from selenium import webdriver
from webdriver_manager.microsoft import IEDriverManager
from webdriver_manager import utils


class IEDriver(BaseDriver):

    def create_driver(self, config):
        options = webdriver.IeOptions()
        self._driver = webdriver.Ie(executable_path=IEDriverManager(os_type=utils.os_name() + '32').install(),
                                    options=options,
                                    desired_capabilities=config.capabilities)
        return self
