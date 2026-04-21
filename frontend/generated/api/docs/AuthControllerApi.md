# AuthControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**loginWithGoogle**](#loginwithgoogle) | **POST** /api/api/auth/google | |

# **loginWithGoogle**
> { [key: string]: string; } loginWithGoogle(requestBody)


### Example

```typescript
import {
    AuthControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new AuthControllerApi(configuration);

let requestBody: { [key: string]: string; }; //

const { status, data } = await apiInstance.loginWithGoogle(
    requestBody
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **requestBody** | **{ [key: string]: string; }**|  | |


### Return type

**{ [key: string]: string; }**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

