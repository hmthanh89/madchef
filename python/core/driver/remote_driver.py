from core.driver.base_driver import BaseDriver
from selenium import webdriver


class RemoteDriver(BaseDriver):

    def create_driver(self, config):
        self._driver = webdriver.Remote(command_executor=config.host,
                                        desired_capabilities=config.capabilities)
        return self
