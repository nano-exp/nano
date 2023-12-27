import getBarkPendingNotice from '../apis/getBarkPendingNotice.js'

export default class NoticeSender {

    constructor(bot) {
        this.bot = bot
    }

    async getPendingNotice() {
        try {
            return await getBarkPendingNotice()
        } catch (err) {
            console.error('getBarkPendingNotice error:', err.message)
            return []
        }
    }

    async getContactMap() {
        const bot = this.bot
        const contact = await bot.getContact()
        const contactMap = new Map
        for (const it of contact.MemberList) {
            contactMap.set(it.NickName, it)
        }
        return contactMap
    }

    buildWxMessage(notice) {
        return `⚠️ID: ${notice.message.id}\n${notice.message.payload}`
    }

    async sendMessage() {
        const bot = this.bot
        const noticeList = await this.getPendingNotice()
        if (!noticeList.length) {
            return
        }
        const contactMap = await this.getContactMap()
        for (const it of noticeList) {
            const c = contactMap.get(it.recipient)
            if (c) {
                bot.sendMsg(this.buildWxMessage(it), c.UserName)
            }
        }
    }
}