using SeleniumCSharp.Core.ElementWrapper;
using SeleniumCSharp.Core.Helpers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SeleniumTests.PageObject.Login
{
    class TrelloLoginPageMobile : TrelloLoginPage
    {
        protected override Label ErrorMessageLabel => new Label(Locator.Instance.Get("ErrorMessageLabel"));

        private new Button LoginButton => new Button(Locator.Instance.Get("LoginButton"));

        private new TextBox EmailTextBox => new TextBox(Locator.Instance.Get("EmailTextBox"));
        private new TextBox PasswordTextBox => new TextBox(Locator.Instance.Get("PasswordTextBox"));

        public override void Login(string userName, string password)
        {
            Console.WriteLine("Login on mobile mode");
            if (userName != null) EmailTextBox.SendKeys(userName);

            if (password != null) PasswordTextBox.SendKeys(password);
            LoginButton.Click();
        }       
    }
}
