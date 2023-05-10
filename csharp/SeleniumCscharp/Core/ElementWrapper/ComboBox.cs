using System.Collections.Generic;
using System.Linq;
using OpenQA.Selenium;
using OpenQA.Selenium.Support.UI;

namespace SeleniumCSharp.Core.ElementWrapper
{
    /// <summary>
    ///     Declare functions for the element of type combobox.
    /// </summary>
    public class ComboBox : BaseElement
    {
        /// <summary>
        ///     Find a web element of type combobox using By locator.
        /// </summary>
        public ComboBox(By locator)
            : base(locator)
        {
        }


        /// <summary>
        ///     Find a web element of type combobox using By Xpath locator.
        /// </summary>
        public ComboBox(string locator)
            : base(locator)
        {
        }

        /// <summary>
        ///     Select an item in the element of type combobox.
        /// </summary>
        private SelectElement Selection()
        {
            var selection = new SelectElement(GetElement());
            return selection;
        }

        /// <summary>
        ///     Select an item in the element of type combobox by its index.
        /// </summary>
        public void SelectByIndex(int index)
        {
            Selection().SelectByIndex(index);
        }

        /// <summary>
        ///     Select an item in the element of type combobox by its inner text.
        /// </summary>
        public void SelectByText(string text)
        {
            Selection().SelectByText(text);
        }

        /// <summary>
        ///     Select an item in the element of type combobox by its value.
        /// </summary>
        public void SelectByValue(string value)
        {
            Selection().SelectByValue(value);
        }

        /// <summary>
        ///     Return the inner text of the selected item in the element of type combobox.
        /// </summary>
        public string GetSelectedOption()
        {
            return Selection().SelectedOption.Text;
        }

        /// <summary>
        ///     Return the inner text of all options in the element of type combobox.
        /// </summary>
        public List<string> GetSelectOptions()
        {
            var elementsList = Selection().Options;

            return elementsList.Select(element => element.Text).ToList();
        }
    }
}