using OpenQA.Selenium;
using SeleniumCSharp.Core.DriverWrapper;

namespace SeleniumCSharp.Core.ElementWrapper
{
    /// <summary>
    ///     Declare functions for the element.
    /// </summary>
    public class Element
    {
        /// <summary>
        ///     Gets or Sets IWebElement
        /// </summary>
        public IWebElement WebElement { get; set; }

        /// <summary>
        ///     Gets or Sets Current Driver Key
        /// </summary>
        public Key DriverKey { get; set; }
    }
}