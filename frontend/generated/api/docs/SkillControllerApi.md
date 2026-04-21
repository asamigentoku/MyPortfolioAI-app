# SkillControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**delete1**](#delete1) | **DELETE** /api/skills/{id} | |
|[**findById1**](#findbyid1) | **GET** /api/skills/{id} | |
|[**findByUserId**](#findbyuserid) | **GET** /api/skills/user/{userId} | |
|[**save1**](#save1) | **POST** /api/skills | |
|[**update1**](#update1) | **PUT** /api/skills/{id} | |

# **delete1**
> delete1()


### Example

```typescript
import {
    SkillControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new SkillControllerApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.delete1(
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

# **findById1**
> SkillDto findById1()


### Example

```typescript
import {
    SkillControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new SkillControllerApi(configuration);

let id: number; // (default to undefined)

const { status, data } = await apiInstance.findById1(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**number**] |  | defaults to undefined|


### Return type

**SkillDto**

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

# **findByUserId**
> Array<SkillDto> findByUserId()


### Example

```typescript
import {
    SkillControllerApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new SkillControllerApi(configuration);

let userId: number; // (default to undefined)

const { status, data } = await apiInstance.findByUserId(
    userId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **userId** | [**number**] |  | defaults to undefined|


### Return type

**Array<SkillDto>**

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

# **save1**
> SkillDto save1(skillDto)


### Example

```typescript
import {
    SkillControllerApi,
    Configuration,
    SkillDto
} from './api';

const configuration = new Configuration();
const apiInstance = new SkillControllerApi(configuration);

let skillDto: SkillDto; //

const { status, data } = await apiInstance.save1(
    skillDto
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **skillDto** | **SkillDto**|  | |


### Return type

**SkillDto**

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

# **update1**
> SkillDto update1(skillDto)


### Example

```typescript
import {
    SkillControllerApi,
    Configuration,
    SkillDto
} from './api';

const configuration = new Configuration();
const apiInstance = new SkillControllerApi(configuration);

let id: number; // (default to undefined)
let skillDto: SkillDto; //

const { status, data } = await apiInstance.update1(
    id,
    skillDto
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **skillDto** | **SkillDto**|  | |
| **id** | [**number**] |  | defaults to undefined|


### Return type

**SkillDto**

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

