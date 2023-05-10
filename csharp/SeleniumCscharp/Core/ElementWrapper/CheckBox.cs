using OpenQA.Selenium;

namespace SeleniumCSharp.Core.ElementWrapper
{
    /// <summary>
    ///     Declare functions for the element of type checkbox.
    /// </summary>
    public class CheckBox : BaseElement
    {
        /// <summary>
        ///     Find a web element of type checkbox using By Xpath locator.
        /// </summary>
        public CheckBox(string locator)
            : base(locator)
        {
        }


        /// <summary>
        ///     Find a web element of type checkbox using By locator.
        /// </summary>
        public CheckBox(By locator)
            : base(locator)
        {
        }

        /// <summary>
        ///     Select an element of type checkbox.
        /// </summary>
        public void Check()
        {
            if (!IsChecked()) Click();
        }

        /// <summary>
        ///     Cancel the selection of the element of type checkbox.
        /// </summary>
        public void Uncheck()
        {
            if (IsChecked()) Click();
        }

        /// <summary>
        ///     Return a Boolean value indicating whether the element of type checkbox is checked or not.
        /// </summary>
        public bool IsChecked()
        {
            return IsSelected();
        }
    }
}