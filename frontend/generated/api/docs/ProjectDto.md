# ProjectDto


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **number** |  | [optional] [default to undefined]
**title** | **string** |  | [optional] [default to undefined]
**explanation** | **string** |  | [optional] [default to undefined]
**url** | **string** |  | [optional] [default to undefined]
**githubUrl** | **string** |  | [optional] [default to undefined]
**isGithub** | **boolean** |  | [optional] [default to undefined]
**skills** | [**Set&lt;SkillDto&gt;**](SkillDto.md) |  | [optional] [default to undefined]

## Example

```typescript
import { ProjectDto } from './api';

const instance: ProjectDto = {
    id,
    title,
    explanation,
    url,
    githubUrl,
    isGithub,
    skills,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
