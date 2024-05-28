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
