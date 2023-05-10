using System.IO;
using System.Linq;

namespace SeleniumCSharp.Core.Utilities
{
    /// <summary>
    ///     This class used to read the file .
    /// </summary>
    public class FileReader
    {
        /// <summary>
        ///     read file .
        /// </summary>
        public static string ReadFile(string filePath)
        {
            var lines = File.ReadAllLines(filePath);
            return lines.Aggregate("", (current, line) => current + line);
        }
    }
}