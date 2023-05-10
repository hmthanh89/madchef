from core.element.base_element import BaseElement
from core.element.text_box import TextBox
from core import selenium
from tests.util.locator_loader import get_locator
import allure


class GoogleHomePage:

    def __init__(self):
        self.txt_search = TextBox(get_locator())

    @allure.step("Open google search page")
    def open_google(self):
        selenium.open_url("https://google.com")
        selenium.wait_for_title_contains("Google")

    @allure.step("Search by entering keyword")
    def search(self, key_word):
        self.txt_search.wait_for_visible(10)
        self.txt_search.enter(key_word)

    def get_searched_value(self):
        return self.txt_search.value


class GoogleHomePageQA(GoogleHomePage):

    def __init__(self):
        super().__init__()
        self.search_form = BaseElement("id=searchform")
        self.txt_search = TextBox("name=q1", self.search_form)

    @allure.step("Search by entering keyword")
    def search(self, key_word):
        self.txt_search.wait_for_visible(10)
        self.txt_search.enter(key_word)
