export class RequestHelper {
    public async get(url: string, data: {}) {
        const serverUrl: string = url + new URLSearchParams(data);
        await fetch(url, {
            method: 'GET'
        })
        .then((res) => {
            console.log(res);
        })
        .then((err) => {
            console.error(err);
        });
    }

    public async post(url: string, data: {}, successFunc: any, errorFunc: any): Promise<any> {
        await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data),
        })
        .then(successFunc)
        .then(errorFunc)
    }
}