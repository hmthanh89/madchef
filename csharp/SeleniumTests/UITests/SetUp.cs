using NUnit.Framework;
using SeleniumCSharp.Core.Helpers;
using SeleniumTests.Utilities;

namespace SeleniumTests.Tests
{
    [SetUpFixture]
    public class SetUp
    {
        [OneTimeSetUp]
        public void BeforeAll()
        {
            Config.SetUIEnvVariables();
            ExtentManager.GetReporter();

            //Re-map the interface
            Locator.Instance.Load("\\Resources\\Selector.json");
            Locator.Instance.setMap("TrelloLoginPageDesk", "TrelloLoginPage");
            Locator.Instance.setMap("TrelloLoginPageMobile", "TrelloLoginPage");
        }

        [OneTimeTearDown]
        public void AfterAll()
        {
            ExtentManager.FlushReporter();
        }
    }
}