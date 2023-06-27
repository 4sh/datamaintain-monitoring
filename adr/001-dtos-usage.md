# ADR001 - DTOs usage

## Status

✅ Accepted 

## Context

Considering that the objects returned by the repository should already be formatted to be returned by the resources, is there a need for DTOs? Couldn't we just use domain objects, that would be returned by the resources?

## Discussed points of views

### How about using DTOs that are the exact copy of the objects returned by the repositories? 

It is redundant to create such objects and there is a need to create mappers (by hand or using a library). Since the services do not add information in the objects, everything is already returned by the repository so it wouldn't be useful for the services to return different objects (DTOs).

### How about not using DTOs at all? 
The repositories already return objects with everything that is needed. Those objects will have to be in the `domain` package to be accessed by the `rest` and the `dao` module. Resources would just return the domain objects. 

The **problem** with this point of view is that, if the domain is updated for some reason, the API will no longer return the same objects. When a V2 object is added, the V1 endpoints could break, because it was not anticipated. That problem may not occur, because this is a small project and for now, we don't see why attributes would be removed from API objects.

Another issue with this point of view is that, since we are using Ktor, the objects returned by routes need to be tagged with `@Serializable`, which requires the `kotlinx-serialization` dependency. We don't want this dependency in our `domain` module.


## Decision

Use DTOs, only in the `rest` module. The `rest` module maps domain objects into DTOs, in the resources. A DTO is versioned, its name is composed of the object domain and "V" + version number. For example, `ProjectV1`.

## Consequences

### Benefits

Our APIs are safer thanks to this change, a given version won't change, even if the domain objects are updated. 

No `kotlix-serialization` dependency in the `domain` module.

### Drawbacks

We will have to write mappers to transform an object domain into its DTO, which should be an exact copy.  