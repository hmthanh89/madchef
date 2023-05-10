from tests.testcases.test_base import TestBase
from core.element.base_element import BaseElement
import pytest


class TestAndroid(TestBase):
    
    @pytest.mark.mobile_native
    def test_android_01(self):
        graphics = BaseElement('accessibility_id=Graphics')
        graphics.wait_for_visible()
        graphics.click()
        arcs = BaseElement('accessibility_id=Arcs')
        assert arcs.is_displayed()
