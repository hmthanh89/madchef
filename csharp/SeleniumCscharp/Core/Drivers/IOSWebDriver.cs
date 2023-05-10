using System;
using OpenQA.Selenium;
using OpenQA.Selenium.Appium.iOS;
using OpenQA.Selenium.Remote;
using SeleniumCSharp.Core.DriverWrapper;

namespace SeleniumCSharp.Core.Drivers
{
    /// <summary>
    ///     IOS Web
    /// </summary>
    public class IOSWebDriver : DriverManager
    {
        /// <summary>
        ///     Create IOS Web Driver
        /// </summary>
        /// <param name="key"></param>
        public override void CreateDriver(string key)
        {
            var pros = GetDriverProperties(key);
            var caps = new DesiredCapabilities();
            SetDriverOptions(caps, pros.GetCapabilities());
            var _iosDriver = new IOSDriver<IWebElement>(new Uri(pros.GetRemoteUrl()), caps,
                TimeSpan.FromSeconds(pros.GetTimeSpan()));
            SetWebDriver(key, _iosDriver);
        }
    }
}