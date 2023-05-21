export class RequestHelper {
    public async get(url: string, data: {}, successFunc: any, errorFunc: any) {
        const serverUrl: string = url + '?' + new URLSearchParams(data);
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
}