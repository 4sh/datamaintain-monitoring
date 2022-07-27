export interface Searcher<T> {
    search(skip: number, limit: number): T[]
    count(): number
    getCurrentResults(): T[]
}