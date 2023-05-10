using FluentAssertions;
using FluentAssertions.Execution;
using NUnit.Framework;
using SeleniumCSharp.Core.Utilities;
using SeleniumTests.DataObject;
using SeleniumTests.PageObject.Login;
using SeleniumTests.Utilities;

namespace SeleniumTests.Tests
{
    [TestFixture]
    [Parallelizable(ParallelScope.Fixtures)]
    public class TrelloLoginTest : TestBase
    {
        [PageObject]
        private TrelloLoginPage trelloLoginPageObject;

        private readonly TrelloLoginPage trelloLoginPage = PageFactory.Get<TrelloLoginPage>();
       
        private readonly LoginData loginData = JsonParser.Get<LoginData>();

        [Test]
        [Category("Smoke")]
        [Category("Login")]
        [Description("Login with invalid user")]
        public void Login_InvalidUser()
        {
            Log.Info("1. Login with invalid user");
            trelloLoginPage.Login(loginData.InvalidUser);
            Log.Info("2. Verify that error message should display");
            trelloLoginPage.GetErrorMessage().Should().Be(loginData.InvalidEmailErrorMessage, "Error message doesn't display properly");
        }

        [Test]
        [Category("Smoke")]
        [Category("Login")]
        [Description("Login with empty user")]
        public void Login_EmptyUser()
        {
            Log.Info("1. Login with empty user");
            trelloLoginPageObject.Login(null, loginData.ValidPassword);
            Log.Info("2. Verify that error message should display");
            var testString = "ACDDDDDDB";

            using (new AssertionScope())
            {
                testString.Should().StartWith("A").And.EndWith("B").And.Contain("C").And.HaveLength(9);
                trelloLoginPage.GetErrorMessage().Should().Be(loginData.EmptyEmailErrorMessage, "Error message doesn't display properly");
                7.Should().Be(6);
                7.Should().Be(5);
                7.Should().Be(7);
            }
        }
    }
}