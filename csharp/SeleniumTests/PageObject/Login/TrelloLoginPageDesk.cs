using SeleniumCSharp.Core.ElementWrapper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SeleniumTests.PageObject.Login
{
    class TrelloLoginPageDesk : TrelloLoginPage
    {
        protected override Label ErrorMessageLabel => new Label("css=#error>.error-message");

        public override void Login(string userName, string password)
        {
            Console.WriteLine("Login on desktop mode");
            if (userName != null) EmailTextBox.SendKeys(userName);

            if (password != null) PasswordTextBox.SendKeys(password);
            LoginButton.Click();
        }       
    }
}
