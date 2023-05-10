using System;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;
using OpenQA.Selenium.Remote;
using SeleniumCSharp.Core.DriverWrapper;

namespace SeleniumCSharp.Core.Drivers
{
    /// <summary>
    ///     Firefox
    /// </summary>
    public class FireFoxBrowser : DriverManager
    {
        /// <summary>
        ///     Create firefox web driver instances
        /// </summary>
        /// <param name="key"></param>
        public override void CreateDriver(string key)
        {
            var pros = GetDriverProperties(key);
            var ops = new FirefoxOptions();
            ops.AddArguments(pros.GetArguments());
            SetDriverOptions(ops, pros.GetCapabilities());
            IWebDriver _webDriver;
            _webDriver = !pros.IsRemoteMode()
                ? new FirefoxDriver(ops)
                : new RemoteWebDriver(new Uri(pros.GetRemoteUrl()), ops);
            SetWebDriver(key, _webDriver);
        }
    }
}