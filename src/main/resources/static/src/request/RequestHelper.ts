export class RequestHelper {
    public async get(url: string, data: {}, successFunc: any, errorFunc: any) {
        let serverUrl: string = url + '?' + new URLSearchParams(data);
        if (!data) {
            serverUrl = serverUrl.replace('?', '');
        }

        await fetch(serverUrl, {
            method: 'GET'
        })
        .then((res) => res.json())
        .then((data) => successFunc(data))
        .catch(errorFunc)
    }

    public async post(url: string, data: {}, successFunc: any, errorFunc: any): Promise<any> {
        await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data),
        })
        .then((res) => res.json())
        .then((data) => successFunc(data))
        .catch(errorFunc)
    }

    public async put(url: string, data: {}, successFunc: any, errorFunc: any): Promise<any> {
        await fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data),
        })
        .then((res) => res.json())
        .then((data) => successFunc(data))
        .catch(errorFunc)
    }

    public async delete(url: string, data: {}, successFunc: any, errorFunc: any): Promise<any> {
        await fetch(url, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data),
        })
        .then((res) => res.json())
        .then((data) => successFunc(data))
        .catch(errorFunc)
    }
}