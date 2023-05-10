using System;
using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;
using OpenQA.Selenium.Remote;
using SeleniumCSharp.Core.DriverWrapper;

namespace SeleniumCSharp.Core.Drivers
{
    /// <summary>
    ///     Chrome
    /// </summary>
    public class ChromeBrowser : DriverManager
    {
        /// <summary>
        ///     Create Chrome Driver
        /// </summary>
        /// <param name="key"></param>
        public override void CreateDriver(string key)
        {
            var pros = GetDriverProperties(key);
            var ops = new ChromeOptions();
            ops.AddArguments(pros.GetArguments());
            SetDriverOptions(ops, pros.GetCapabilities());
            IWebDriver _webDriver;
            _webDriver = !pros.IsRemoteMode()
                ? new ChromeDriver(ops)
                : new RemoteWebDriver(new Uri(pros.GetRemoteUrl()), ops);
            SetWebDriver(key, _webDriver);
        }
    }
}