export interface Searcher<T> {
    search(skip: number, limit: number): Promise<T[]>
    count(): Promise<number>
    getCurrentResults(): T[]
}