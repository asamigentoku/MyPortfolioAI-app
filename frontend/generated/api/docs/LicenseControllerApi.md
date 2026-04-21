# LicenseControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**delete3**](#delete3) | **DELETE** /api/licenses/{id} | |
|[**findById3**](#findbyid3) | **GET** /api/licenses/{id} | |
|[**findByUserId2**](#findbyuserid2) | **GET** /api/licenses/user/{userId} | |
|[**save3**](#save3) | **POST** /api/licenses | |
|[**update3**](#update3) | **PUT** /api/licenses/{id} | |

# **delete3**
> delete3()


### Example

```typescript
import {
    LicenseControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new LicenseControllerApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.delete3(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**number**] |  | defaults to undefined|


### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **findById3**
> LicenseDto findById3()


### Example

```typescript
import {
    LicenseControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new LicenseControllerApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.findById3(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**number**] |  | defaults to undefined|


### Return type

**LicenseDto**

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

# **findByUserId2**
> Array<LicenseDto> findByUserId2()


### Example

```typescript
import {
    LicenseControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new LicenseControllerApi(configuration);

let userId: number; // (default to undefined)

const { status, data } = await apiInstance.findByUserId2(
    userId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **userId** | [**number**] |  | defaults to undefined|


### Return type

**Array<LicenseDto>**

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

# **save3**
> LicenseDto save3(licenseDto)


### Example

```typescript
import {
    LicenseControllerApi,
    Configuration,
    LicenseDto
} from './api';

const configuration = new Configuration();
const apiInstance = new LicenseControllerApi(configuration);

let licenseDto: LicenseDto; //

const { status, data } = await apiInstance.save3(
    licenseDto
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **licenseDto** | **LicenseDto**|  | |


### Return type

**LicenseDto**

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

# **update3**
> LicenseDto update3(licenseDto)


### Example

```typescript
import {
    LicenseControllerApi,
    Configuration,
    LicenseDto
} from './api';

const configuration = new Configuration();
const apiInstance = new LicenseControllerApi(configuration);

let id: number; // (default to undefined)
let licenseDto: LicenseDto; //

const { status, data } = await apiInstance.update3(
    id,
    licenseDto
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **licenseDto** | **LicenseDto**|  | |
| **id** | [**number**] |  | defaults to undefined|


### Return type

**LicenseDto**

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

