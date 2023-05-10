import inspect
import pytest
import json
from core.util.helper import get_full_path

KEY_MAPPING = {
    "chrome": "default",
    "ie": "default"
}

LOCATOR_FILE = '../../tests/config/element/locators.json'


def get_locator(name=None):
    frame, filename, line_number, function_name, lines, index = inspect.stack()[1]
    page_name = type(frame.f_locals['self']).__name__
    config = pytest.driver_conf
    platform = config.driver if not config.driver == 'mobile' else config.capabilities['platformName']
    if platform in KEY_MAPPING:
        platform = KEY_MAPPING[platform]
    if name is None:
        name = lines[0].split('=')[0].replace('self.', '').strip()
    with open(get_full_path(LOCATOR_FILE)) as f:
        data = json.load(f)
        locator = data[platform.lower()][page_name][name]
    return locator
