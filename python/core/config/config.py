from core.config import constants
from core.util.helper import get_env


def poll_during_waits():
    return float(get_env(constants.SELENPY_POLL_DURING_WAITS,
                         constants.SELENPY_POLL_DURING_DEFAULT_WAITS))


def timeout():
    return int(get_env(constants.SELENPY_TIMEOUT, constants.SELENPY_DEFAULT_TIMEOUT))


def override_driver():
    value = get_env(constants.SELENPY_OVERRIDE_DRIVER, constants.SELENPY_OVERRIDE_DRIVER_DEFAULT)
    return __convert_to_boolean(value)


def element_caching():
    value = get_env(constants.SELENPY_ELEMENT_CACHING, constants.SELENPY_ELEMENT_CACHING_DEFAULT)
    return __convert_to_boolean(value)


def xpath_warning():
    value = get_env(constants.SELENPY_XPATH_WARNING, constants.SELENPY_XPATH_WARNING_DEFAULT)
    return __convert_to_boolean(value)


def __convert_to_boolean(value):
    if type(value) is bool:
        return value
    if value and value.strip().lower() == 'true':
        return True
    return False
