import logging
from selenium import webdriver

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)


def store_page_source(url: str, out_filename: str, headless: bool = True):
    options = webdriver.FirefoxOptions()
    if headless:
        options.add_argument('--headless')

    browser = webdriver.Firefox(options=options)

    browser.get(url)
    with open(out_filename, "w") as f:
        f.write(browser.page_source)
    browser.close()


if __name__ == "__main__":
    store_page_source(
        url="https://www.amazon.com/dp/B07WZDKMHZ/ref=nosim?tag=qrshare05-20",
        out_filename="out-4.html",
        headless=True
    )
