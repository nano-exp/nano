import { getCSTString } from '../utils.js'
import applyAckMessage from '../apis/applyAckMessage.js'
import applyAckAllMessage from '../apis/applyAckAllMessage.js'

const ACK_RE = /.*ack (\d+)$/i
const ACK_ALL_RE = /.*ack *all$/i
const AT_RE = /@Nano *$/

/**
 *
 * @param cases {{re:RegExp,fn:Function}[]}
 * @param s {string}
 */
function when(cases = [], s) {
    for (const c of cases) {
        if (c.re.test(s)) {
            c.fn(s)
        }
    }
}

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

    async handleAckAll(message) {
        const bot = this.bot
        const content = message.Content
        const r = await applyAckAllMessage(content)
        if (r) {
            bot.sendMsg(`✅Ack messages done`, message.FromUserName)
        }
    }

    async handleText(message) {
        const content = message.Content
        const dispatches = [{
            when: () => ACK_RE.test(content),
            handle: () => this.handleAck(message)
        }, {
            when: () => ACK_ALL_RE.test(content),
            handle: () => this.handleAckAll(message)
        }, {
            when: () => AT_RE.test(content),
            handle: () => this.handleAt(message)
        }]
        for (const dispatch of dispatches) {
            if (dispatch.when()) {
                await dispatch.handle()
            }
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