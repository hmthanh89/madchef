using System;
using System.Globalization;
using System.IO;
using AventStack.ExtentReports;
using AventStack.ExtentReports.MarkupUtils;
using NUnit.Framework;
using OpenQA.Selenium;
using SeleniumCSharp.Core.DriverWrapper;
using SeleniumCSharp.Core.Utilities;

namespace SeleniumCSharp.Core.Helpers
{
    /// <summary>
    ///     Used for logging
    /// </summary>
    public class Logger
    {
        /// <summary>
        ///     Constructor
        /// </summary>
        /// <param name="testContext"></param>
        public Logger(TestContext testContext)
        {
            Context = testContext;
            LogPath =
                $"{FileUtils.GetBasePath()}\\Resources\\Logs\\[LOG]_{testContext.Test.Name}_{DateTime.Now.ToString("yyyy-MM-dd_HH.mm.ss")}.log";
        }

        /// <summary>
        ///     Constructor
        /// </summary>
        /// <param name="logPath"></param>
        /// <param name="test"></param>
        /// <param name="testContext"></param>
        public Logger(string logPath, TestContext testContext = null, ExtentTest test = null)
        {
            LogPath = logPath;
            ExtentTest = test;
            Context = testContext;
        }

        /// <summary>
        ///     Current TestContext
        /// </summary>
        public TestContext Context { get; set; }

        /// <summary>
        ///     Current Test of ExtentReport
        /// </summary>
        public ExtentTest ExtentTest { get; set; }

        /// <summary>
        ///     Log location
        /// </summary>
        public string LogPath { get; set; }


        /// <summary>
        ///     Log info
        /// </summary>
        /// <param name="message"></param>
        public void Info(string message)
        {
            Info(message, true);
        }

        /// <summary>
        ///     Log for staring test
        /// </summary>
        public void StartTest()
        {
            Info($"Starting test {Context.Test.MethodName}", false);
        }

        /// <summary>
        ///     Log info
        /// </summary>
        /// <param name="message"></param>
        /// <param name="extentLog"></param>
        public void Info(string message, bool extentLog = true)
        {
            var filestream = File.AppendText(LogPath);
            filestream.WriteLine($"{DateTime.Now.ToString(CultureInfo.InvariantCulture)} | INFO | {message}");
            Console.WriteLine($"{DateTime.Now.ToString(CultureInfo.InvariantCulture)} | INFO | {message}");
            filestream.Close();
            if (ExtentTest != null && extentLog) ExtentTest.Info(message);
        }

        /// <summary>
        ///     Log for ending test
        /// </summary>
        public void EndTest()
        {
            Info($"Result - {Context.Test.MethodName} {Context.Result.Outcome}", false);
        }

        /// <summary>
        ///     Log warning
        /// </summary>
        /// <param name="message"></param>
        public void Warning(string message)
        {
            var filestream = File.AppendText(LogPath);
            filestream.WriteLine($"{DateTime.Now.ToString(CultureInfo.InvariantCulture)} | WARNING | {message}");
            filestream.Close();
            if (ExtentTest != null) ExtentTest.Warning(message);
        }

        /// <summary>
        ///     Log error
        /// </summary>
        /// <param name="e"></param>
        public void Error(Exception e)
        {
            var filestream = File.AppendText(LogPath);
            var timeStamp = DateTime.Now.ToString(CultureInfo.InvariantCulture);

            filestream.WriteLine($"{timeStamp} | ERROR | {e.Message}");
            Console.WriteLine($"{timeStamp} | ERROR | {e.Message}");
            filestream.WriteLine(e.ToString());
            Console.WriteLine(e.ToString());
            if (e.InnerException != null) filestream.WriteLine("Inner Exception: " + e.InnerException.Message);
            filestream.Close();
            ExtentTest?.Error(e.InnerException.Message);
        }

        /// <summary>
        ///     Get browser logs and print
        /// </summary>
        public void GetBrowserLog()
        {
            var logs = DriverUtils.GetBrowserLog();
            if (logs != null)
            {
                foreach (var log in logs)
                {
                    if (log.Level != LogLevel.Severe) continue;
                    Info($"[URL]: {DriverUtils.CurrentUrl()}", false);
                    Info($"[BROWSER LOG]: {log.Message}", false);
                    ExtentTest?.Info(MarkupHelper.CreateCodeBlock(log.Message));
                }
            }
        }
    }
}