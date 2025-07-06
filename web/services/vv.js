export async function getRandomVv() {
  const response = await fetch('/api/vv/random')
  return await response.json()
}

export async function searchVv({ keyword, pageIndex, pageSize }) {
  const params = new URLSearchParams()
  params.append('keyword', keyword)
  params.append('pageIndex', pageIndex)
  params.append('pageSize', pageSize)
  const response = await fetch('/api/vv/search?' + params.toString())
  const totalCount = response.headers.get('X-Total-Count')
  return {
    list: await response.json(),
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

export async function deleteVv({ id, token }) {
  const form = new FormData()
  form.append('id', id)
  const response = await fetch('/api/vv/delete', {
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
