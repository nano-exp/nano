export default async function applyAckMessage(id) {
    const response = await fetch(`/api/bark/ack-message/${id}`, {
        method: 'POST',
    })
    return response.json()
}