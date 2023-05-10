using NUnit.Framework;
using SeleniumCSharp.Core.Utils;
using SeleniumTests.APITests.Dto;
using SeleniumTests.Utilities;
using System;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading.Tasks;

namespace SeleniumTests.APITests
{
    public class ApiBase
    {
        [SetUp]
        public void SetUp()
        {
            Config.SetAPIEnvVariables();
        }

        private async Task<HttpClient> GetAuthenticatedClient(string email, string password)
        {
            var client = new HttpClient
            {
                BaseAddress = new Uri(Config.ApiUrl)
            };

            var creds = new LoginDto
            {
                Email = email,
                Password = password
            };

            var response = await client.PostAsync<LoginDto, string>("account/login", creds);
            var token = response.Dto.Replace("\"", string.Empty);

            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);

            return client;
        }

        protected HttpClient GetUnauthenticatedClient()
        {
            return new HttpClient
            {
                BaseAddress = new Uri(Config.ApiUrl)
            };
        }

        #region Request Uri's
        protected string GetEmployeesUri() => "employees";
        #endregion
    }
}
