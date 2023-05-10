using System;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace SeleniumCSharp.Core.Utils
{
    public class ApiResponse<TDto>
    {
        public TDto Dto { get; set; }
        public HttpStatusCode StatusCode { get; private set; }
        public Uri LocationHeader { get; private set; }

        public static async Task<ApiResponse<TDto>> Map(HttpResponseMessage httpResponseMessage)
        {
            var content = await httpResponseMessage.Content.ReadAsStringAsync();

            return new ApiResponse<TDto>
            {
                Dto = JsonConvert.DeserializeObject<TDto>(content),
                StatusCode = httpResponseMessage.StatusCode,
                LocationHeader = httpResponseMessage.Headers?.Location
            };
        }
    }
}