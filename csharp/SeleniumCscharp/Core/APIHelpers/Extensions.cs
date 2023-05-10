using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace SeleniumCSharp.Core.Utils
{
    public static class Extensions
    {
        public static async Task<ApiResponse<TResponseDto>> PostAsync<TRequestDto, TResponseDto>(this HttpClient client, string requestUri, TRequestDto dto)
        {
            var json = JsonConvert.SerializeObject(dto);
            var httpContent = new StringContent(json, Encoding.UTF8, "application/json");

            var httpResponse = await client.PostAsync(requestUri, httpContent);
            var apiResponse = await ApiResponse<TResponseDto>.Map(httpResponse);

            return apiResponse;
        }

        public static async Task<ApiResponse<TDto>> GetAsync<TDto>(this HttpClient client, string requestUri)
        {
            var httpResponse = await client.GetAsync(requestUri);
            var apiResponse = await ApiResponse<TDto>.Map(httpResponse);

            return apiResponse;
        }

        public static async Task<ApiResponse<TResponseDto>> PostAsync<TResponseDto>(this HttpClient client, string requestUri, object dto)
        {
            var json = JsonConvert.SerializeObject(dto);
            var httpContent = new StringContent(json, Encoding.UTF8, "application/json");

            var httpResponse = await client.PostAsync(requestUri, httpContent);
            var apiResponse = await ApiResponse<TResponseDto>.Map(httpResponse);

            return apiResponse;
        }
    }
}