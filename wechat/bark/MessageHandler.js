import applyAckMessage from '../apis/applyAckMessage.js'
import { getCSTString } from '../utils.js'

const ACK_RE = /.*ack (\d+)$/i
const AT_RE = /@Nano *$/

export default class MessageHandler {
    constructor(bot) {
        this.bot = bot
    }

    async handleAt(message) {
        const bot = this.bot
        bot.sendMsg(`🟢Nano bot works fine`, message.FromUserName)
    }

    async handleAck(message) {
        const bot = this.bot
        const content = message.Content
        const g = ACK_RE.exec(content)
        const id = g[1]
        const r = await applyAckMessage(id, content)
        if (r) {
            const m = JSON.parse(r)
            bot.sendMsg(`✅Message [${id}] acked at \n${getCSTString(new Date(m.ackTime))}`, message.FromUserName)
        } else {
            bot.sendMsg(`❌Message [${id}] not found`, message.FromUserName)
        }
    }

    async handleText(message) {
        const content = message.Content
        if (ACK_RE.test(content)) {
            await this.handleAck(message)
        } else if (AT_RE.test(content)) {
            await this.handleAt(message)
        }

    }

    async handle(message) {
        const bot = this.bot
        if (message.isSendBySelf) {
            // Message discarded because its outgoing
            return
        }
        if (message.CreateTime + 2 * 60 < new Date().getTime() / 1000) {
            // Message discarded because its TOO OLD(than 2 minutes)
            return
        }

        switch (message.MsgType) {
            case bot.CONF.MSGTYPE_TEXT:
                await this.handleText(message)
                break
            default:
                break
        }
    }
}