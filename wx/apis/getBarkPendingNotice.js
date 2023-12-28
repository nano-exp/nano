import { NANO_HOST } from '../global.js'

/**
 * @returns {Promise<{recipient: string, message: object}[]>}
 */
export default async function getBarkPendingNotice() {
    const url = new URL('/api/bark/pending-notice', NANO_HOST)
    const response = await fetch(url)
    return await response.json()
}