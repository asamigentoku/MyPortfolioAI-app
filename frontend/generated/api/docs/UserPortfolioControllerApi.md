# UserPortfolioControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**addData**](#adddata) | **POST** /api/api/portfolio/{userId} | |
|[**getData**](#getdata) | **GET** /api/api/portfolio/{userId} | |

# **addData**
> UserData addData(userData)


### Example

```typescript
import {
    UserPortfolioControllerApi,
    Configuration,
    UserData
} from './api';

const configuration = new Configuration();
const apiInstance = new UserPortfolioControllerApi(configuration);

let userId: string; // (default to undefined)
let userData: UserData; //

const { status, data } = await apiInstance.addData(
    userId,
    userData
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **userData** | **UserData**|  | |
| **userId** | [**string**] |  | defaults to undefined|


### Return type

**UserData**

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

# **getData**
> Array<UserData> getData()


### Example

```typescript
import {
    UserPortfolioControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new UserPortfolioControllerApi(configuration);

let userId: string; // (default to undefined)

const { status, data } = await apiInstance.getData(
    userId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **userId** | [**string**] |  | defaults to undefined|


### Return type

**Array<UserData>**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

