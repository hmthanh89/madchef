from abc import abstractmethod


class BaseDriver():

    def __init__(self):
        self._driver = None

    @abstractmethod
    def create_driver(self, config):
        pass

    def get_webdriver(self):
        return self._driver

    def quit(self):
        self._driver.quit()
