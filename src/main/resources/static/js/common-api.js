const apiFetchJson = (url, method, body) => {
    let headers = {
        'content-type': 'application/json',
    };

    return apiFetchComm(url, method, headers, body);
}

const apiFetchComm = async (url, method, headers, body) => {
    return fetch(url, {
        method: method,
        headers: headers,
        body: body
    }).then(res => {
        console.log(res);
        if (!res.ok) {
            res.json().then(error => {
                alert(error);
                throw new Error(errMsg);
            })
        }
        return res;
    });
}

const apiFetchExcel = async (url, method, body, filename) => {
    let headers = {
        'content-type': 'application/json',
    };

    return fetch(url, {
        method: method,
        headers: headers,
        responseType: 'blob',
        body: body
    }).then(res => {
        if (!res.ok) {
            res.json().then(error => {
                let errMsg = `[${error.errorType}] ${error.errorMsg}`;
                alert(errMsg);
                throw new Error(errMsg);
            })
        } else {
            res.blob().then(file => {
                const url = URL.createObjectURL(file);
                const link = document.createElement('a');
                link.href = url;
                link.setAttribute('download', filename);
                document.body.appendChild(link);
                link.click();
            });
        }
        return res;
    });
}