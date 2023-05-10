using AventStack.ExtentReports;
using AventStack.ExtentReports.Reporter;
using SeleniumCSharp.Core.Utilities;

namespace SeleniumTests.Utilities
{
    public class ExtentManager
    {
        private static ExtentReports extent;
        private static readonly string reportFolder = "ExtentReports";

        public static ExtentReports GetReporter()
        {
            if (extent == null)
            {
                var dir = FileUtils.CreateFolder(FileUtils.GetBasePath(), reportFolder);
                extent = new ExtentReports();
                var html = new ExtentHtmlReporter(dir.FullName + "\\");
                extent.AttachReporter(html);
            }

            return extent;
        }

        public static void FlushReporter()
        {
            extent.Flush();
        }
    }
}