using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SeleniumTests.Utilities
{
    [AttributeUsage(AttributeTargets.Field)]
    public class PageObjectAttribute : Attribute
    {
        public PageObjectAttribute() { }
    }
}
