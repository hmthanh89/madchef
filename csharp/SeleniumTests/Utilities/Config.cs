using System;
using NUnit.Framework;
using SeleniumCSharp.Core.Utilities;

namespace SeleniumTests.Utilities
{
    public class Config
    {
        public static string ConfigFilePath;
        public static string Driver; // can be chrome,ie,firefox
        public static string Env; // can be QA, DEV, DEMO, PREPROD
        public static string Url;
        public static string ApiUrl;

        //Setting variables via test context
        public static void SetUIEnvVariables()
        {
            ConfigFilePath = FileUtils.GetBasePath() + GetString("ConfigPath");
            Driver = GetString("Driver");
            Env = GetString("Env");
            ApiUrl = string.Format(GetString("ApiUrl"), Env);
            Url = GetString("Url");
        }

        public static void SetAPIEnvVariables()
        {
            ApiUrl = string.Format(GetString("ApiUrl"), Env);
        }

        public static string GetString(string property)
        {
            if (TestContext.Parameters == null)
                throw new ArgumentException(
                    "Property does not exist, does not have a value, or a test setting is not selected");
            return TestContext.Parameters[property];
        }
    }
}