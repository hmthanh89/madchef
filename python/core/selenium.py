from core.driver.factory_driver import FactoryDriver
from core.util.wait_helper import wait_until


def get_driver():
    return FactoryDriver.get_instance().get_current_driver()


def get_title():
    return get_driver().title


def is_valid():
    return True if get_driver() is not None else False


def start_driver(config, key="default"):
    FactoryDriver.get_instance().start_driver(config, key)
    if config.maximize:
        maximize_window()


def maximize_window():
    if is_valid():
        get_driver().maximize_window()


def close_browser():
    if is_valid():
        get_driver().close()


def switch_to_driver(driver_key="default"):
    FactoryDriver.get_instance().switch_to_driver(driver_key)


def open_url(url):
    if is_valid():
        get_driver().get(url)


def quit_all():
    FactoryDriver.get_instance().quit_all()


def select_main_window():
    if is_valid():
        handles = get_driver().window_handles
        get_driver().switch_to.window(handles[0])


def close():
    get_driver().close()
    FactoryDriver.get_instance().delete_driver()


def wait_for_title_contains(value, timeout=None):    
    wait_until(lambda: value in get_title(), "Title '%s' did not display after after <TIMEOUT>." % value, timeout)


def get_browser_name():
    return get_driver().capabilities['browserName']


def get_browser_version():    
    return get_driver().capabilities['version']


def save_screenshot(file):
    """
        :Usage:
            selenium.save_screenshot('screenshots/example.png')
    """
    get_driver().save_screenshot(file)
