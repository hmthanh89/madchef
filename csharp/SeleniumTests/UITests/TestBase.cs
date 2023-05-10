using System;
using AventStack.ExtentReports;
using AventStack.ExtentReports.MarkupUtils;
using NUnit.Framework;
using NUnit.Framework.Interfaces;
using SeleniumCSharp.Core.DriverWrapper;
using SeleniumCSharp.Core.Helpers;
using SeleniumCSharp.Core.Utilities;
using SeleniumTests.Utilities;
using static NUnit.Framework.TestContext;

namespace SeleniumTests.Tests
{
    [TestFixture]
    public class TestBase
    {

        [SetUp]
        public void SetUp()
        {
            Config.SetUIEnvVariables();
            //Create extent test                  
            extentTest = ExtentTestManager.StartTest(TestContextInfo.TestName, TestContextInfo.Description,
                TestContextInfo.Categories);

            Log = new Logger(Utils.GetLogFile(TestContextInfo.CurrentContext), TestContextInfo.CurrentContext,
                extentTest);
            Log.StartTest();

            // Delete old log files
            FileUtils.DeleteFilesOlderThan(Constants.LogRetentionDays, Utils.GetLogFolder(), Constants.LogExtension);
            FileUtils.DeleteFilesOlderThan(Constants.LogRetentionDays, Utils.GetScreenshotFolder(),
                Constants.ImgExtension);

            //Create new web driver
            DriverUtils.CreateDriver(new DriverProperties(Config.ConfigFilePath,
                Config.Driver));
            DriverUtils.Maximize();
            DriverUtils.GoToUrl(Config.Url);

            //Initial page objects
            PageFactory.InitialPageObjects(this);
        }

        [TearDown]
        public void TearDown()
        {
            try
            {
                if (TestContextInfo.Result.Outcome.Status != TestStatus.Passed)
                {
                    var screenshotPath = Utils.GeScreenshotFile(TestContextInfo.CurrentContext);
                    DriverUtils.TakeScreenshot(screenshotPath);
                    AddTestAttachment(screenshotPath);
                    Log.GetBrowserLog();

                    //Add screenshot for extents report                    
                    extentTest.Fail(MarkupHelper.CreateCodeBlock(TestContextInfo.Result.Message));
                    extentTest.AddScreenCaptureFromPath(screenshotPath);
                }

                AddTestAttachment(Log.LogPath);
                ExtentTestManager.EndTest();
                DriverUtils.CloseDrivers();
            }
            catch (Exception e)
            {
                Log.Error(e);
                extentTest.Error(e);
            }

            Log.EndTest();
        }

        protected Logger Log { get; set; }
        private ExtentTest extentTest;
    }
}