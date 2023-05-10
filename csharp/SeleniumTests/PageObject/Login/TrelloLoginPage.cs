using SeleniumCSharp.Core.ElementWrapper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SeleniumTests.PageObject.Login
{
    abstract class TrelloLoginPage
    {
        protected Button LoginButton => new Button("id=login");
       
        protected TextBox EmailTextBox => new TextBox("name=user");
        protected TextBox PasswordTextBox => new TextBox("name=password");

        protected abstract Label ErrorMessageLabel { get; }

        public abstract void Login(string userName = null, String password = null);

        public string GetErrorMessage()
        {
            ErrorMessageLabel.WaitForVisible();
            return ErrorMessageLabel.GetText();
        }
    }
}
