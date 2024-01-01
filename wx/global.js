import path from 'node:path'
import { fileURLToPath } from 'node:url'

const __dirname = path.dirname(fileURLToPath(import.meta.url))

export const WX_DATA_FILENAME = path.join(__dirname, '..', 'nano.wechat.json')

export const NANO_HOST = process.env.NANO_HOST || 'http://localhost:9000'
