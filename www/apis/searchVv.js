export default async function searchVv({ keyword, pageIndex, pageSize }) {
    const p = new URLSearchParams()
    p.append('keyword', keyword)
    p.append('pageIndex', pageIndex)
    p.append('pageSize', pageSize)
    const r = await fetch('/api/vv/search?' + p.toString())
    return r.json()
}
