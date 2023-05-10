using System.Collections.Generic;
using NUnit.Framework;
using static NUnit.Framework.TestContext;

namespace SeleniumTests.Utilities
{
    /// <summary>
    ///     TestContextInfo
    /// </summary>
    public class TestContextInfo
    {
        /// <summary>
        ///     Gets current test content
        /// </summary>
        public static TestContext CurrentContext => TestContext.CurrentContext;

        /// <summary>
        ///     Gets current test
        /// </summary>
        public static TestAdapter CurrentTest => TestContext.CurrentContext.Test;

        /// <summary>
        ///     Gets description of test
        /// </summary>
        public static string Description
        {
            get
            {
                var pro = CurrentTest.Properties["Description"] as IList<object>;
                if (pro != null && pro.Count > 0)
                    return pro[0].ToString();
                return string.Empty;
            }
        }

        /// <summary>
        ///     Gets categories of test
        /// </summary>
        public static string[] Categories
        {
            get
            {
                var pro = CurrentTest.Properties["Category"] as IList<object>;
                if (pro != null)
                {
                    var categories = new string[pro.Count];
                    pro.CopyTo(categories, 0);
                    return categories;
                }

                return null;
            }
        }

        /// <summary>
        ///     Gets current method name of test
        /// </summary>
        public static string TestName => CurrentTest.MethodName;

        /// <summary>
        ///     Gets current test result
        /// </summary>
        public static ResultAdapter Result => CurrentContext.Result;
    }
}