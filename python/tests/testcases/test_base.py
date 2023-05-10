import pytest
from core import selenium


class TestBase:

    @pytest.fixture(scope="module", autouse=True)
    def setup(self):
        selenium.start_driver(pytest.driver_conf)

        # Close all browsers when tests have been finished
        yield
        selenium.quit_all()
