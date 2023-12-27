import { sleep } from './schedule.js'
import getBarkPendingNotice from './apis/getBarkPendingNotice.js'

async function sendMessage(bot) {
    try {
        const pendingNoticeList = await getBarkPendingNotice()
        if (pendingNoticeList.length) {
            const contact = await bot.getContact()
            const contactMap = new Map
            contact.MemberList.forEach((it) => contactMap.set(it.NickName, it))
            for (const it of pendingNoticeList) {
                const c = contactMap.get(it.recipient)
                if (c) {
                    bot.sendMsg(`⚠️ID: ${it.message.id}\n${it.message.payload}`, c.UserName)
                }
            }
        }
    } catch (err) {
        console.error('sendMessage error', err)
    }
}

export async function scheduleBarkTasks(bot) {
    while (bot.state === bot.CONF.STATE.login) {
        await sendMessage(bot)
        await sleep(5000)
    }
}