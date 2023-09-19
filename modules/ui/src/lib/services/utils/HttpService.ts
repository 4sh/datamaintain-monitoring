async function sendJson<T>(url: string, method: 'POST' | 'PUT', content: any) {
    const res = await fetch(url, {
        method: method,
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(content)
    });

    return (await res.json()) as T;
}

async function get<T>(url: string) {
    const res = await fetch(url);
    return (await res.json()) as T;
}

async function post<T>(url: string, content: any) {
    return sendJson<T>(url, 'POST', content);
}

async function put<T>(url: string, content: any) {
    return sendJson<T>(url, 'PUT', content);
}

async function remove(url: string) {
    return await fetch(url, {method: 'DELETE'});
}

export const Http = {
    get,
    post,
    put,
    remove
};