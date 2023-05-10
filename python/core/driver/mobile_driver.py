from core.driver.base_driver import BaseDriver
from appium import webdriver
from core.util.helper import get_full_path


class MobileDriver(BaseDriver):

    def create_driver(self, config):
        if 'app' in config.capabilities:
            app = config.capabilities['app']
            config.capabilities['app'] = get_full_path(app)
        self._driver = webdriver.Remote(command_executor=config.host,
                                        desired_capabilities=config.capabilities, direct_connection=True)
        return self
