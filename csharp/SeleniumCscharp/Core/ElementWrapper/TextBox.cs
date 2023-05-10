using System.Collections.Generic;
using System.Linq;
using OpenQA.Selenium;
using OpenQA.Selenium.Support.UI;

namespace SeleniumCSharp.Core.ElementWrapper
{
    /// <summary>
    ///     Declare functions for the element of type TextBox.
    /// </summary>
    public class TextBox : BaseElement
    {
        /// <summary>
        ///     Find a web element of type TextBox using By locator.
        /// </summary>
        public TextBox(By locator)
            : base(locator)
        {
        }


        /// <summary>
        ///     Find a web element of type TextBox using By Xpath locator.
        /// </summary>
        public TextBox(string locator)
            : base(locator)
        {
        }
    }
}