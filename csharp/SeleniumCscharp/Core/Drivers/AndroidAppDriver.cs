using System;
using OpenQA.Selenium.Appium.Android;
using OpenQA.Selenium.Remote;
using SeleniumCSharp.Core.DriverWrapper;

namespace SeleniumCSharp.Core.Drivers
{
    /// <summary>
    ///     Android App
    /// </summary>
    public class AndroidAppDriver : DriverManager
    {
        /// <inheritdoc />
        /// <summary>
        ///     Create Android App Driver
        /// </summary>
        /// <param name="key"></param>
        public override void CreateDriver(string key)
        {
            var pros = GetDriverProperties(key);
            var caps = new DesiredCapabilities();

            SetDriverOptions(caps, pros.GetCapabilities());
            var _androidDriver = new AndroidDriver<AndroidElement>(new Uri(pros.GetRemoteUrl()), caps,
                TimeSpan.FromSeconds(pros.GetTimeSpan()));
            SetWebDriver(key, _androidDriver);
        }
    }
}