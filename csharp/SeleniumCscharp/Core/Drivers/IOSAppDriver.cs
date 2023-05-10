using System;
using OpenQA.Selenium.Appium.iOS;
using OpenQA.Selenium.Remote;
using SeleniumCSharp.Core.DriverWrapper;

namespace SeleniumCSharp.Core.Drivers
{
    /// <summary>
    ///     IOS App
    /// </summary>
    public class IOSAppDriver : DriverManager
    {
        /// <inheritdoc />
        /// <summary>
        ///     Create IOS App Driver
        /// </summary>
        /// <param name="key"></param>
        public override void CreateDriver(string key)
        {
            var pros = GetDriverProperties(key);
            var caps = new DesiredCapabilities();

            SetDriverOptions(caps, pros.GetCapabilities());
            var _iosDriver = new IOSDriver<IOSElement>(new Uri(pros.GetRemoteUrl()), caps,
                TimeSpan.FromSeconds(pros.GetTimeSpan()));
            SetWebDriver(key, _iosDriver);
        }
    }
}