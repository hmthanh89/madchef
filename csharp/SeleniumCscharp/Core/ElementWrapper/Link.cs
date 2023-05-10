using System.Collections.Generic;
using System.Linq;
using OpenQA.Selenium;
using OpenQA.Selenium.Support.UI;

namespace SeleniumCSharp.Core.ElementWrapper
{
    /// <summary>
    ///     Declare functions for the element of type Link.
    /// </summary>
    public class Link : BaseElement
    {
        /// <summary>
        ///     Find a web element of type Link using By locator.
        /// </summary>
        public Link(By locator)
            : base(locator)
        {
        }


        /// <summary>
        ///     Find a web element of type Link using By Xpath locator.
        /// </summary>
        public Link(string locator)
            : base(locator)
        {
        }
    }
}