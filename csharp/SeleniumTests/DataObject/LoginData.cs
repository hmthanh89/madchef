using Newtonsoft.Json;

namespace SeleniumTests.DataObject
{
    public class LoginData
    {
        public string Path => $"\\Resources\\TestData\\{this.GetType().Name}.json";

        [JsonProperty("invalidUser")] public string InvalidUser { get; set; }
        [JsonProperty("invalidPassword")] public string InvalidPassword { get; set; }
        [JsonProperty("validUser")] public string ValidUser { get; set; }
        [JsonProperty("validPassword")] public string ValidPassword { get; set; }
        [JsonProperty("invalidEmailErrorMessage")] public string InvalidEmailErrorMessage { get; set; }
        [JsonProperty("emptyEmailErrorMessage")] public string EmptyEmailErrorMessage { get; set; }
    }
}