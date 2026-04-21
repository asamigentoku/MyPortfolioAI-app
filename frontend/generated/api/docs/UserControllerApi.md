# UserControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**_delete**](#_delete) | **DELETE** /api/users/{id} | |
|[**findById**](#findbyid) | **GET** /api/users/{id} | |
|[**save**](#save) | **POST** /api/users/create | |
|[**update**](#update) | **PUT** /api/users/{id} | |

# **_delete**
> _delete()


### Example

```typescript
import {
    UserControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new UserControllerApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance._delete(
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

# **findById**
> UserDto findById()


### Example

```typescript
import {
    UserControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new UserControllerApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.findById(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**number**] |  | defaults to undefined|


### Return type

**UserDto**

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

# **save**
> UserDto save(userDto)


### Example

```typescript
import {
    UserControllerApi,
    Configuration,
    UserDto
} from './api';

const configuration = new Configuration();
const apiInstance = new UserControllerApi(configuration);

let userDto: UserDto; //

const { status, data } = await apiInstance.save(
    userDto
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **userDto** | **UserDto**|  | |


### Return type

**UserDto**

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

# **update**
> UserDto update(userDto)


### Example

```typescript
import {
    UserControllerApi,
    Configuration,
    UserDto
} from './api';

const configuration = new Configuration();
const apiInstance = new UserControllerApi(configuration);

let id: number; // (default to undefined)
let userDto: UserDto; //

const { status, data } = await apiInstance.update(
    id,
    userDto
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **userDto** | **UserDto**|  | |
| **id** | [**number**] |  | defaults to undefined|


### Return type

**UserDto**

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

