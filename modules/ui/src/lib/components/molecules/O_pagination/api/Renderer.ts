export interface Renderer<T> {
    render(items: T[]): void
}