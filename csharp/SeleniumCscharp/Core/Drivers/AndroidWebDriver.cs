using System;
using OpenQA.Selenium;
using OpenQA.Selenium.Appium.Android;
using OpenQA.Selenium.Appium.Properties;
using OpenQA.Selenium.Remote;
using SeleniumCSharp.Core.DriverWrapper;

namespace SeleniumCSharp.Core.Drivers
{
    /// <inheritdoc />
    /// <summary>
    ///     Android Web
    /// </summary>
    public class AndroidWebDriver : DriverManager
    {
        /// <summary>
        ///     Create Android Web Driver
        /// </summary>
        /// <param name="key"></param>
        public override void CreateDriver(string key)
        {
            var pros = GetDriverProperties(key);
            var caps = new DesiredCapabilities();
            SetDriverOptions(caps, pros.GetCapabilities());
            var _androidDriver = new AndroidDriver<IWebElement>(new Uri(pros.GetRemoteUrl()), caps,
                TimeSpan.FromSeconds(pros.GetTimeSpan()));
            SetWebDriver(key, _androidDriver);
        }
    }
}