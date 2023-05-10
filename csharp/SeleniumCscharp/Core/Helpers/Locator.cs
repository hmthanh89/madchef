using System;
using System.Diagnostics;
using Newtonsoft.Json.Linq;
using SeleniumCSharp.Core.Utilities;
using System.Collections.Generic;

namespace SeleniumCSharp.Core.Helpers
{
    /// <summary>
    ///     SelectorLoader
    /// </summary>
    public class Locator
    {
        private static readonly Lazy<Locator> lazy = new Lazy<Locator>(() => new Locator());

        /// <summary>
        /// Set mapping for page. For example, you have 2 pages (LoginPageA, LoginPageB) and have only one LoginPage (define in json locator file) and you want to use the locator of LoginPage for A and B. 
        /// Usage: 
        /// setMap("LoginPageA","LoginPage");
        /// setMap("LoginPageB","LoginPage")
        /// </summary>

        public void setMap(string key, string value)
        {
            maps.Add(key, value);
        }

        private Dictionary<string, string> maps;
        private JObject selectors;

        private Locator()
        {
            maps = new Dictionary<string, string>();
        }

        /// <summary>
        ///     Get LoaderSelector instance
        /// </summary>
        public static Locator Instance => lazy.Value;


        /// <summary>
        ///     Load json selector file
        /// </summary>
        /// <param name="path"></param>
        /// <returns></returns>
        public void Load(string path = @"\Resources\Selector.json")
        {
            if (selectors == null) selectors = JsonParser.CreateJsonObjectFromFile(FileUtils.GetBasePath() + path);
        }

        /// <summary>
        ///     Get selector of web element from json file
        /// </summary>
        /// <param name="key"></param>
        /// <param name="pageName"></param>
        /// <returns></returns>
        public string Get(string key, string pageName = null)
        {
            if (!string.IsNullOrEmpty(pageName)) return (string)selectors[pageName][key];
            var stackTrace = new StackTrace();
            var method = stackTrace.GetFrame(1).GetMethod();
            pageName = method.ReflectedType.Name;
            if (maps.ContainsKey(pageName))
                pageName = maps[pageName];
            return (string)selectors[pageName][key];
        }
    }
}