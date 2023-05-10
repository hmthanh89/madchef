using System.Collections.Generic;
using System.Linq;
using OpenQA.Selenium;
using OpenQA.Selenium.Support.UI;

namespace SeleniumCSharp.Core.ElementWrapper
{
    /// <summary>
    ///     Declare functions for the element of type Button.
    /// </summary>
    public class Button : BaseElement
    {
        /// <summary>
        ///     Find a web element of type Button using By locator.
        /// </summary>
        public Button(By locator)
            : base(locator)
        {
        }


        /// <summary>
        ///     Find a web element of type Button using By Xpath locator.
        /// </summary>
        public Button(string locator)
            : base(locator)
        {
        }
    }
}