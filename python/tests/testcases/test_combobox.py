from core import selenium
from core.element.iframe import IFrame
from core.element.combo_box import ComboBox
from .test_base import TestBase
import pytest
import allure


class TestComboBox(TestBase):
    result = IFrame("id=%s")
    car = ComboBox("//select")

    @pytest.mark.smoke
    @pytest.mark.mobile_browser
    @allure.tag("smoke")
    @allure.feature('r1', 'smoke')
    @allure.testcase('http://my.tms.org/browse/TESTCASE-2', 'TESTCASE-2')
    @allure.issue('http://my.tms.org/browse/ISSUE-2', 'BUG-2')
    def test_combobox(self):
        selenium.open_url("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_select")
        self.result.format("iframeResult")
        self.result.select()
        self.car.select_by_value("audi")
        assert self.car.first_selected_text == "Audi1"
        self.result.deselect()

    def test_combobox_2(self):
        selenium.open_url("https://www.w3schools.com")
        assert 1 == 1
