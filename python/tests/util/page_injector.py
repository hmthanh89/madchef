import pytest
import logging


def get_page(base_page):
    if pytest.test_env:
        logging.debug("Get page for %s" % str(base_page.__name__))
        clazz = [cls for cls in base_page.__subclasses__() if pytest.test_env.lower() in cls.__name__.lower()]
        if len(clazz) == 1:
            return clazz[0]()
        elif len(clazz) == 0:
            raise RuntimeError(
                "Cannot find the '%s' on test environment '%s'" % (str(base_page.__name__), pytest.test_env))
        else:
            raise RuntimeError("More than one page found %s" % ([cls for cls in clazz]))
    return base_page()
