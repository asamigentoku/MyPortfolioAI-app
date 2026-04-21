# CareerControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**delete4**](#delete4) | **DELETE** /api/careers/{id} | |
|[**findById4**](#findbyid4) | **GET** /api/careers/{id} | |
|[**findByUserId3**](#findbyuserid3) | **GET** /api/careers/user/{userId} | |
|[**save4**](#save4) | **POST** /api/careers | |
|[**update4**](#update4) | **PUT** /api/careers/{id} | |

# **delete4**
> delete4()


### Example

```typescript
import {
    CareerControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new CareerControllerApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.delete4(
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

# **findById4**
> CareerDto findById4()


### Example

```typescript
import {
    CareerControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new CareerControllerApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.findById4(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**number**] |  | defaults to undefined|


### Return type

**CareerDto**

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

# **findByUserId3**
> Array<CareerDto> findByUserId3()


### Example

```typescript
import {
    CareerControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new CareerControllerApi(configuration);

let userId: number; // (default to undefined)

const { status, data } = await apiInstance.findByUserId3(
    userId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **userId** | [**number**] |  | defaults to undefined|


### Return type

**Array<CareerDto>**

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

# **save4**
> CareerDto save4(careerDto)


### Example

```typescript
import {
    CareerControllerApi,
    Configuration,
    CareerDto
} from './api';

const configuration = new Configuration();
const apiInstance = new CareerControllerApi(configuration);

let careerDto: CareerDto; //

const { status, data } = await apiInstance.save4(
    careerDto
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **careerDto** | **CareerDto**|  | |


### Return type

**CareerDto**

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

# **update4**
> CareerDto update4(careerDto)


### Example

```typescript
import {
    CareerControllerApi,
    Configuration,
    CareerDto
} from './api';

const configuration = new Configuration();
const apiInstance = new CareerControllerApi(configuration);

let id: number; // (default to undefined)
let careerDto: CareerDto; //

const { status, data } = await apiInstance.update4(
    id,
    careerDto
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **careerDto** | **CareerDto**|  | |
| **id** | [**number**] |  | defaults to undefined|


### Return type

**CareerDto**

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

