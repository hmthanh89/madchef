import uuid
from tests.util import constants
from core.util.helper import get_full_path


def get_file_name(extension='.png'):
    return str(uuid.uuid4()) + extension


def get_screenshot_path(extension='.png'):
    name = get_file_name(extension)
    return get_full_path(constants.SCREENSHOT_FOLDER + name), name
