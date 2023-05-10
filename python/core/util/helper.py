import os


def get_env(key, default=None):
    try:
        value = os.environ.get(key, default)
        if not value:
            return default
        return value
    except KeyError:
        return None


def get_full_path(path):
    return os.path.abspath(os.path.join(os.path.dirname(__file__), path))


def get_project_path():
    return os.path.abspath(__file__)
