import { NANO_HOST } from '../global.js'

export default async function applyAckAllMessage(comment) {
    const url = new URL(`/api/bark/ack-all-message`, NANO_HOST)
    const response = await fetch(url, {
        method: 'POST',
        body: comment,
        headers: {
            'Content-Type': 'text/plain',
        },
    })
    return response.text()
}