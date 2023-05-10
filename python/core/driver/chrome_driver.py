from core.driver.base_driver import BaseDriver
from selenium import webdriver
from webdriver_manager.chrome import ChromeDriverManager


class ChromeDriver(BaseDriver):

    def create_driver(self, config):
        options = webdriver.ChromeOptions()
        self._driver = webdriver.Chrome(executable_path=ChromeDriverManager().install(),
                                        options=options,
                                        desired_capabilities=config.capabilities)
        return self
