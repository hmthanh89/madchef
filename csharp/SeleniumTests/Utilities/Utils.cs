using System;
using NUnit.Framework;
using SeleniumCSharp.Core.Utilities;

namespace SeleniumTests.Utilities
{
    public class Utils
    {
        public static string GeScreenshotFile(TestContext context = null)
        {
            if (context != null)
                return
                    $"{FileUtils.GetBasePath()}Resources\\Screenshots\\{GetFileName("IMG", context)}{Constants.ImgExtension}";
            return
                $"{FileUtils.GetBasePath()}Resources\\Screenshots\\{Guid.NewGuid().ToString()}{Constants.ImgExtension}";
        }

        public static string GetScreenshotFolder()
        {
            return $"{FileUtils.GetBasePath()}Resources\\Screenshots";
        }

        public static string GetLogFile(TestContext context = null)
        {
            if (context != null)
                return
                    $"{FileUtils.GetBasePath()}Resources\\Logs\\{GetFileName("Log", context)}{Constants.LogExtension}";
            return $"{FileUtils.GetBasePath()}Resources\\Logs\\{Guid.NewGuid().ToString()}{Constants.LogExtension}";
        }

        public static string GetLogFolder()
        {
            return $"{FileUtils.GetBasePath()}Resources\\Logs";
        }

        private static string GetFileName(string type, TestContext context)
        {
            var filename =
                $"[Test]_[{type}]_{context.Test.MethodName}_{DateTime.Now.ToString("yy-MM-dd HH.mm.ss")}";

            //If log file name length > 70+ , incorrect tests count published to VSO. Hence limiting log file name to max 70 char.
            if (filename.Length > 70) filename = filename.Substring(0, 70);

            return filename;
        }
    }
}