import json
from core.config import constants
from core.util.helper import get_full_path


class DriverConfig(object):

    @staticmethod
    def get_driver_config(driver_key, config_file):
        with open(get_full_path(config_file)) as f:
            data = json.load(f)
            item = data[driver_key]
            config = DriverConfig(driver_key,
                                  item[constants.REMOTE_HOST_KEY],
                                  item[constants.DRIVER_KEY],
                                  item[constants.CAPABILITIES_KEY],
                                  item[constants.MAXIMIZE_KEY])
        return config

    def __init__(self, driver_key, host, driver, capabilities, maximize=False):
        self.driver_key = driver_key
        self._host = host
        self._driver = driver
        self._capabilities = capabilities
        self._maximize = maximize

    @property
    def host(self):
        return self._host

    @host.setter
    def host(self, value):
        self._host = value

    @property
    def driver(self):
        return self._driver

    @driver.setter
    def driver(self, value):
        self._driver = value

    @property
    def capabilities(self):
        return self._capabilities

    @capabilities.setter
    def capabilities(self, value):
        self._capabilities = value

    @property
    def maximize(self):
        return self._maximize

    @maximize.setter
    def maximize(self, value):
        self._maximize = value
