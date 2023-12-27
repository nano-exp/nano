import path from 'path'
import { fileURLToPath } from 'url'

const __dirname = path.dirname(fileURLToPath(import.meta.url))

export const WX_DATA_FILENAME = path.join(__dirname, '..', 'nano.wechat.json')

export const NANO_HOST = process.env.NANO_HOST || 'http://localhost:9000'
