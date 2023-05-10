using System;
using OpenQA.Selenium;
using OpenQA.Selenium.IE;
using OpenQA.Selenium.Remote;
using SeleniumCSharp.Core.DriverWrapper;

namespace SeleniumCSharp.Core.Drivers
{
    /// <summary>
    ///     IE Web Driver
    /// </summary>
    public class IEBrowser : DriverManager
    {
        /// <summary>
        ///     Create IE web driver instance
        /// </summary>
        /// <param name="key"></param>
        public override void CreateDriver(string key)
        {
            var pros = GetDriverProperties(key);
            var ops = new InternetExplorerOptions();
            SetDriverOptions(ops, pros.GetCapabilities());
            IWebDriver _webDriver;
            _webDriver = !pros.IsRemoteMode()
                ? new InternetExplorerDriver(ops)
                : new RemoteWebDriver(new Uri(pros.GetRemoteUrl()), ops);
            SetWebDriver(key, _webDriver);
        }
    }
}