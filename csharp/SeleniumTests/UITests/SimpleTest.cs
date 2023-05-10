using System;
using FluentAssertions;
using NUnit.Framework;
using NUnit.Framework.Internal;
using SeleniumCSharp.Core.DriverWrapper;
using SeleniumCSharp.Core.ElementWrapper;
using SeleniumCSharp.Core.Helpers;
using SeleniumTests.Utilities;

namespace SeleniumTests.Tests
{
    [TestFixture]
    public class SimpleTest : TestBase
    {
        [Test]
        public void TestDriverKey()
        {
            Log.Info("1. Navigate to google");
            DriverUtils.GoToUrl("https://www.google.com");
            Log.Info("2. Search a text");
            var txtSearch = new BaseElement("name={0}");
            txtSearch.Format("q").SendKeys("Hello Selenium");
            WaitHelper.WaitUntil(() => txtSearch.Format("q").Value.Equals("444"), TimeSpan.FromSeconds(4),
                TimeSpan.FromSeconds(2));

            var properties = new DriverProperties(Constants.CONFIG_FILE_PATH, "local-chrome");

            Log.Info("3. Create a new driver");
            DriverUtils.CreateDriver(properties, "new chrome");

            DriverUtils.GoToUrl("https://www.seleniumhq.org/");

            DriverUtils.SwitchDriverTo();

            txtSearch.Format("q").SendKeys("Hello LogiGear");
            Assert.IsTrue(true, "Log this if false 1");
            bool abc = false;
            abc.Should().BeTrue("I don't know");
        }
    }
}