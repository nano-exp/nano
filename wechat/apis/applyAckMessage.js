import { NANO_HOST } from '../global.js'

export default async function applyAckMessage(id, comment) {
    const url = new URL(`/api/bark/ack-message/${id}`, NANO_HOST)
    const response = await fetch(url, {
        method: 'POST',
        body: comment,
        headers: {
            'Content-Type': 'text/plain',
        },
    })
    return response.json()
}