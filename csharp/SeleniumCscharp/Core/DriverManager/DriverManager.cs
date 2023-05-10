using System.Collections.Generic;
using System.Diagnostics;
using OpenQA.Selenium;

namespace SeleniumCSharp.Core.DriverWrapper
{
    /// <summary>
    ///     Create Web Driver
    /// </summary>
    public abstract class DriverManager
    {
        /// <summary>
        ///     This is used to set time out for finding web element. Default is 5s
        /// </summary>
        private readonly int _defaultElementTimeOut = 5;

        private readonly Dictionary<string, DriverProperties> _driverProperties =
            new Dictionary<string, DriverProperties>();

        private readonly Dictionary<string, int> _elementTimeOut = new Dictionary<string, int>();

        // This is used to store the WebDriver instance using key. It helps users can open more than 2 WebDriver instances at the same time. For example, 1 for Firefox, 1 for Chrome
        private readonly Dictionary<string, IWebDriver> _webDrivers = new Dictionary<string, IWebDriver>();

        /// <summary>
        ///     Create WebDriver
        /// </summary>
        /// <param name="key"></param>
        public abstract void CreateDriver(string key);

        /// <summary>
        ///     Keep driver properties
        /// </summary>
        /// <param name="key"></param>
        /// <param name="properties"></param>
        public void SetDriverProperties(string key, DriverProperties properties)
        {
            _driverProperties.Add(key, properties);
        }

        /// <summary>
        ///     Get driver properties
        /// </summary>
        /// <param name="key"></param>
        /// <returns></returns>
        public DriverProperties GetDriverProperties(string key)
        {
            return _driverProperties[key];
        }

        /// <summary>
        ///     Keep web driver instance by key
        /// </summary>
        /// <param name="key"></param>
        /// <param name="driver"></param>
        public void SetWebDriver(string key, IWebDriver driver)
        {
            SetElementTimeOut(key, _defaultElementTimeOut);
            _webDrivers.Add(key, driver);
        }

        /// <summary>
        ///     Get web driver instance by key
        /// </summary>
        /// <param name="key"></param>
        /// <returns></returns>
        public IWebDriver GetWebDriver(string key)
        {
            return _webDrivers[key];
        }

        /// <summary>
        ///     Get time out for finding web element
        /// </summary>
        /// <param name="key"></param>
        /// <returns></returns>
        public int GetElementTimeOut(string key)
        {
            return _elementTimeOut[key];
        }

        /// <summary>
        ///     Set time out for finding web element
        /// </summary>
        /// <param name="key"></param>
        /// <param name="seconds"></param>
        public void SetElementTimeOut(string key, int seconds)
        {
            _elementTimeOut.Add(key, seconds);
        }


        /// <summary>
        ///     Add caps for each browser
        /// </summary>
        /// <param name="ops"></param>
        /// <param name="caps"></param>
        protected void SetDriverOptions(dynamic ops, Dictionary<string, object> caps)
        {
            if (caps == null) return;
            var callingMethod = new StackTrace().GetFrame(1).GetMethod().ReflectedType.Name;
            foreach (var item in caps)
            {
                if (callingMethod.ToLower().Contains("android") || callingMethod.ToLower().Contains("ios"))
                {
                    if (!ops.HasCapability(item.Key))
                        ops.SetCapability(item.Key, item.Value);
                }
                else
                {
                    if (ops.ToCapabilities().GetCapability(item.Key) == null)
                        ops.AddAdditionalCapability(item.Key, item.Value, true);
                }
            }
        }

        /// <summary>
        ///     Quit all web driver instance
        /// </summary>
        public void CloseDrivers()
        {
            foreach (var item in _webDrivers.Values) item.Quit();
            //Remote all
            _webDrivers.Clear();
            _driverProperties.Clear();
            _elementTimeOut.Clear();
        }
    }
}