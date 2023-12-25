export  default async function getMessage(id) {
    const response = await fetch(`/api/bark/message/${id}`)
    return  await response.json()
}