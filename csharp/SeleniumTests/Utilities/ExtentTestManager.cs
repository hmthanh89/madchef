using System.Collections.Generic;
using System.Linq;
using System.Threading;
using AventStack.ExtentReports;

namespace SeleniumTests.Utilities
{
    public class ExtentTestManager
    {
        private static readonly Dictionary<long, ExtentTest> extentTestMap = new Dictionary<long, ExtentTest>();
        private static readonly ExtentReports extent = ExtentManager.GetReporter();

        public static ExtentTest GetTest()
        {
            return extentTestMap[Thread.CurrentThread.ManagedThreadId];
        }

        public static void EndTest()
        {
            extentTestMap.Remove(Thread.CurrentThread.ManagedThreadId);
        }

        public static ExtentTest StartTest(string testName, string desc = "", params string[] category)
        {
            var test = extent.CreateTest(testName, desc);
            if (category != null && category.Any()) test.AssignCategory(category);
            extentTestMap.Add(Thread.CurrentThread.ManagedThreadId, test);
            return test;
        }
    }
}