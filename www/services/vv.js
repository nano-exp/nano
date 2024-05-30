export async function searchVv({ keyword, pageIndex, pageSize }) {
    const p = new URLSearchParams()
    p.append('keyword', keyword)
    p.append('pageIndex', pageIndex)
    p.append('pageSize', pageSize)
    const r = await fetch('/api/vv/search?' + p.toString())
    const totalCount = r.headers.get('X-Total-Count')
    return {
        list: await r.json(),
        totalCount: parseInt(totalCount),
    }
}


export async function createVv({ file, filename, token }) {
    const form = new FormData()
    form.append('file', file)
    form.append('filename', filename)
    const response = await fetch('/api/vv/create', {
        method: 'POST',
        headers: {
            'X-Token': token,
        },
        body: form,
    })
    if (!response.ok) {
        throw new Error(response.statusText || `Error: ${response.status}`)
    }
}
