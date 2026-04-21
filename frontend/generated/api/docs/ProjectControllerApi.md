# ProjectControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**delete2**](#delete2) | **DELETE** /api/project/{id} | |
|[**findById2**](#findbyid2) | **GET** /api/project/{id} | |
|[**findByUserId1**](#findbyuserid1) | **GET** /api/project/user/{userId} | |
|[**save2**](#save2) | **POST** /api/project | |
|[**update2**](#update2) | **PUT** /api/project/{id} | |

# **delete2**
> delete2()


### Example

```typescript
import {
    ProjectControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ProjectControllerApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.delete2(
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

# **findById2**
> ProjectDto findById2()


### Example

```typescript
import {
    ProjectControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ProjectControllerApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.findById2(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**number**] |  | defaults to undefined|


### Return type

**ProjectDto**

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

# **findByUserId1**
> Array<ProjectDto> findByUserId1()


### Example

```typescript
import {
    ProjectControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ProjectControllerApi(configuration);

let userId: number; // (default to undefined)

const { status, data } = await apiInstance.findByUserId1(
    userId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **userId** | [**number**] |  | defaults to undefined|


### Return type

**Array<ProjectDto>**

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

# **save2**
> ProjectDto save2(projectDto)


### Example

```typescript
import {
    ProjectControllerApi,
    Configuration,
    ProjectDto
} from './api';

const configuration = new Configuration();
const apiInstance = new ProjectControllerApi(configuration);

let projectDto: ProjectDto; //

const { status, data } = await apiInstance.save2(
    projectDto
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **projectDto** | **ProjectDto**|  | |


### Return type

**ProjectDto**

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

# **update2**
> ProjectDto update2(projectDto)


### Example

```typescript
import {
    ProjectControllerApi,
    Configuration,
    ProjectDto
} from './api';

const configuration = new Configuration();
const apiInstance = new ProjectControllerApi(configuration);

let id: number; // (default to undefined)
let projectDto: ProjectDto; //

const { status, data } = await apiInstance.update2(
    id,
    projectDto
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **projectDto** | **ProjectDto**|  | |
| **id** | [**number**] |  | defaults to undefined|


### Return type

**ProjectDto**

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

