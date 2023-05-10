using System;
using System.Collections.Generic;
using System.Drawing;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using OpenQA.Selenium;
using OpenQA.Selenium.Interactions;
using OpenQA.Selenium.Support.UI;
using SeleniumCSharp.Core.DriverWrapper;
using ExpectedConditions = SeleniumExtras.WaitHelpers.ExpectedConditions;

namespace SeleniumCSharp.Core.ElementWrapper
{
    /// <summary>
    ///     Declare some basic functions of the element.
    /// </summary>
    public class BaseElement
    {
        private By _byLocator;
        private readonly string _dynamicLocator;
        private Element _element;
        private string _locator;

        /// <summary>
        ///     Define the locator for web element
        /// </summary>
        public BaseElement(string locator)
        {
            _locator = locator;
            _dynamicLocator = locator;
            _byLocator = getByLocator();
        }

        /// <summary>
        ///     Define the locator for web element
        /// </summary>
        /// <param name="element"></param>
        public BaseElement(IWebElement element)
        {
            KeepElement(element);
        }

        /// <summary>
        ///     Find web element using By locator.
        /// </summary>
        public BaseElement(By locator)
        {
            _byLocator = locator;
        }

        private By getByLocator()
        {
            var body = Regex.Replace(_locator, "[\\w\\s]*=(.*)", "$1").Trim();
            var type = Regex.Replace(_locator, "([\\w\\s]*)=.*", "$1").Trim();
            switch (type)
            {
                case "css":
                    return By.CssSelector(body);
                case "id":
                    return By.Id(body);
                case "link":
                    return By.LinkText(body);
                case "xpath":
                    return By.XPath(body);
                case "text":
                    return By.XPath(string.Format("//*[contains(text(), '%s')]", body));
                case "name":
                    return By.Name(body);
                default:
                    return By.XPath(_locator);
            }
        }

        /// <summary>
        ///     Cache element for next working
        /// </summary>
        /// <param name="element"></param>
        private void KeepElement(IWebElement element)
        {
            _element = new Element();
            _element.WebElement = element;
            _element.DriverKey = DriverUtils.GetKey();
        }

        /// <summary>
        ///     Return the locator of the element.
        /// </summary>
        public By GetLocator()
        {
            return _byLocator;
        }

        /// <summary>
        ///     Formats the generic element locator definition
        /// </summary>
        /// <param name="parameters"></param>
        /// <example>
        ///     How we can replace parts of defined locator:
        ///     <code>
        /// private BaseElement menuLink = new BaseElement("//*[@title='{0}' and @ms.title='{1}']");
        /// menuLink.Format("info","news").Text;
        /// </code>
        /// </example>
        public BaseElement Format(params object[] parameters)
        {
            _locator = string.Format(_dynamicLocator, parameters);
            _byLocator = getByLocator();
            return this;
        }

        /// <summary>
        ///     Return the current web element.
        /// </summary>
        public IWebElement GetElement()
        {
            try
            {
                // Don't need to find element again if we do it before              
                if (_element?.WebElement != null && _element.DriverKey.Equals(DriverUtils.GetKey()))
                {
                    // Check an element is no longer attached to the DOM.
                    var enabled = _element.WebElement.Enabled;
                    return _element.WebElement;
                }

                var wait = new WebDriverWait(DriverUtils.GetDriver(),
                    TimeSpan.FromSeconds(DriverUtils.GetElementTimeOut()));
                var element = wait.Until(ExpectedConditions.ElementIsVisible(_byLocator));
                KeepElement(element);
                return element;
            }
            catch (StaleElementReferenceException)
            {
                Console.WriteLine("Element: '{0}' is no longer attached to the DOM", _locator);
                _element = null;
                return GetElement();
            }
        }

        /// <summary>
        ///     Get List of web element
        /// </summary>
        /// <returns></returns>
        public List<IWebElement> GetElements()
        {
            var wait = new WebDriverWait(DriverUtils.GetDriver(),
                TimeSpan.FromSeconds(DriverUtils.GetElementTimeOut()));
            return new List<IWebElement>(wait.Until(ExpectedConditions.VisibilityOfAllElementsLocatedBy(_byLocator)));
        }

        /// <summary>
        ///     Return a List of BaseElement which have the same By locator.
        /// </summary>
        public List<BaseElement> GetListElements()
        {
            var elements = new List<BaseElement>();
            var lst = GetElements();
            Parallel.ForEach(lst, x => { elements.Add(new BaseElement(x)); });

            return elements;
        }

        /// <summary>
        ///     Click on the element.
        /// </summary>
        public void Click()
        {
            GetElement().Click();
        }

        /// <summary>
        ///     Click on the element using JavaScript executor.
        /// </summary>
        public void ClickByJs()
        {
            var js = "arguments[0].click();";
            DriverUtils.ExecuteScript(js, GetElement());
        }


        /// <summary>
        ///     Click on the element using the X and Y coordinate of the element.
        /// </summary>
        public void Click(int x, int y)
        {
            var action = new Actions(DriverUtils.GetDriver());
            action.MoveToElement(GetElement(), x, y).Click().Build().Perform();
        }

        /// <summary>
        ///     Click on the element twice.
        /// </summary>
        public void DoubleClick()
        {
            var actions = new Actions(DriverUtils.GetDriver());
            actions.DoubleClick(GetElement()).Build().Perform();
        }

        /// <summary>
        ///     Send keystrokes to the active web.
        /// </summary>
        public void SendKeys(string keys)
        {
            var element = GetElement();
            element.Clear();
            element.SendKeys(keys);
        }

        /// <summary>
        ///     Hit the enter key to submit.
        /// </summary>
        public void Submit()
        {
            GetElement().Submit();
        }

        /// <summary>
        ///     Return the inner Text of the element.
        /// </summary>
        public string GetText()
        {
            return GetElement().Text;
        }

        /// <summary>
        ///     Return the value of the specific attribute for the element.
        /// </summary>
        public string GetAttribute(string attributeName)
        {
            return GetElement().GetAttribute(attributeName);
        }


        /// <summary>
        ///     Return the value of the element.
        /// </summary>
        public string GetValue()
        {
            return GetAttribute("value");
        }

        /// <summary>
        ///     Return the Size object containing the height and width of the element.
        /// </summary>
        public Size GetSize()
        {
            return GetElement().Size;
        }

        /// <summary>
        ///     Return the width of the element.
        /// </summary>
        public int GetWidth()
        {
            return GetSize().Width;
        }

        /// <summary>
        ///     Return the height of the element.
        /// </summary>
        public int GetHeight()
        {
            return GetSize().Height;
        }

        /// <summary>
        ///     Return a Point object containing the coordinates of the upper-left corner of the element
        ///     relative to the upper-left corner of the page.
        /// </summary>
        public Point GetLocation()
        {
            return GetElement().Location;
        }

        /// <summary>
        ///     Return the x coordinate of the element.
        /// </summary>
        public int GetPointX()
        {
            return GetLocation().X;
        }

        /// <summary>
        ///     Return the y coordinate of the element.
        /// </summary>
        public int GetPointY()
        {
            return GetLocation().Y;
        }

        /// <summary>
        ///     Return the value of a CSS property of the element.
        /// </summary>
        public string GetCssValue(string propertyName)
        {
            return GetElement().GetCssValue(propertyName);
        }

        /// <summary>
        ///     Return the tag name of the element.
        /// </summary>
        public string GetTagName()
        {
            return GetElement().TagName;
        }

        /// <summary>
        ///     Set a value for the element.
        /// </summary>
        public void SetValue(string value)
        {
            try
            {
                var js = string.Format("arguments[0].value='{0}';", value);
                DriverUtils.ExecuteScript(js, GetElement());
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        /// <summary>
        ///     Set a value for the specific attribute of the element.
        /// </summary>
        public void SetAttribute(string attributeName, string value)
        {
            try
            {
                var js = string.Format("arguments[0].setAttribute({0}, {1});", attributeName, value);
                DriverUtils.ExecuteScript(js, GetElement());
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        /// <summary>
        ///     Returns a Boolean value indicating whether the element is displayed or not.
        /// </summary>
        public bool IsDisplayed()
        {
            try
            {
                return GetElement().Displayed;
            }
            catch (Exception ex)
            {
                if (ex is TimeoutException || ex is NoSuchElementException || ex is ElementNotVisibleException)
                    return false;
                throw;
            }
        }

        /// <summary>
        ///     Returns a Boolean value indicating whether the element is displayed or not.
        /// </summary>
        /// <param name="timeout"></param>
        /// <returns></returns>
        public bool IsDisplayed(int timeout)
        {
            throw new NotImplementedException();
        }

        /// <summary>
        ///     Returns a Boolean value indicating whether the element is enabled or not.
        /// </summary>
        public bool IsEnabled()
        {
            return GetElement().Enabled;
        }

        /// <summary>
        ///     Wait until element is visible
        /// </summary>
        /// <param name="timeout"></param>
        public void WaitForVisible(int timeout)
        {
            var wait = new WebDriverWait(DriverUtils.GetDriver(), TimeSpan.FromSeconds(timeout));
            var element = wait.Until(ExpectedConditions.ElementIsVisible(_byLocator));
        }

        /// <summary>
        ///     Wait until element is visible
        /// </summary>
        public void WaitForVisible()
        {
            var wait = new WebDriverWait(DriverUtils.GetDriver(),
                TimeSpan.FromSeconds(DriverUtils.GetElementTimeOut()));
            var element = wait.Until(ExpectedConditions.ElementIsVisible(_byLocator));
        }

        /// <summary>
        ///     Returns a Boolean value indicating whether the element is selected or not.
        /// </summary>
        public bool IsSelected()
        {
            return GetElement().Selected;
        }

        /// <summary>
        ///     Drags the element and drops it to the target element.
        /// </summary>
        public void DragTo(BaseElement targetElement)
        {
            var action = new Actions(DriverUtils.GetDriver());
            action.DragAndDrop(GetElement(), targetElement.GetElement());
            action.Build().Perform();
        }

        /// <summary>
        ///     Find the element and move mouse to the middle of it.
        /// </summary>
        public void MoveToElement()
        {
            var action = new Actions(DriverUtils.GetDriver());
            action.MoveToElement(GetElement()).Build().Perform();
        }

        /// <summary>
        ///     Focus on the element.
        /// </summary>
        public void Focus()
        {
            var js = "arguments[0].focus();";
            DriverUtils.ExecuteScript(js, GetElement());
        }

        /// <summary>
        ///     Scroll the page till the element is found.
        /// </summary>
        public void ScrollToView()
        {
            var js = "arguments[0].scrollIntoView(true);";
            DriverUtils.ExecuteScript(js, GetElement());
        }
    }
}