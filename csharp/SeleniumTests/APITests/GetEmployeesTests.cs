using NUnit.Framework;
using SeleniumCSharp.Core.Utils;
using SeleniumTests.APITests.Dto;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Threading.Tasks;

namespace SeleniumTests.APITests
{
    [TestFixture]
    public class GetEmployeesTests : ApiBase
    {
        [Test]
        public async Task Employee_GetEmployees()
        {
            var client = GetUnauthenticatedClient();
            var response = await client.GetAsync<IList<EmployeesResponse>>(GetEmployeesUri());
            Assert.AreEqual(HttpStatusCode.OK, response.StatusCode);
            Assert.IsTrue(response.Dto.Any());
        }
    }
}
