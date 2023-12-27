export default async function applyAckMessage(id) {
    const response = await fetch(`/api/bark/ack-message/${id}`, {
        method: 'POST',
        body: navigator.userAgent,
        headers: {
            'Content-Type': 'plain/text',
        },
    })
    return response.json()
}