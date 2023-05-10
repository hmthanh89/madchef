using SeleniumTests.PageObject.Login;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace SeleniumTests.Utilities
{

    public class PageFactory

    {

        public static T Get<T>()
        {
            var type = typeof(T);
            String fullName = type.FullName + Config.Env;
            type = Type.GetType(fullName);
            return (T)Activator.CreateInstance(type);
        }


        public static void InitialPageObjects(object obj)
        {
            FieldInfo[] fields = obj.GetType().GetFields(BindingFlags.NonPublic | BindingFlags.Instance).Where(x => x.GetCustomAttributes(typeof(PageObjectAttribute), true).Length > 0).ToArray();
            foreach (var field in fields)
            {
                string fullName = field.FieldType.FullName + Config.Env;
                field.SetValue(obj, Activator.CreateInstance(Type.GetType(fullName)));
            }                    
        }
    }
}
