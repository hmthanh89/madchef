import functools
from selenium.common.exceptions import StaleElementReferenceException


def staleness_of(f):
    @functools.wraps(f)
    def wrapper(self, *args, **kwargs):
        try:
            return f(self, *args, **kwargs)
        except StaleElementReferenceException:
            self._element = None
            return f(self, *args, **kwargs)

    return wrapper
