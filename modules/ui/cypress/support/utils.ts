export function generateRandomNumber(): string {
    return Math.random().toString(16).slice(2);
}