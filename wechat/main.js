import { getWxBot } from './wx_bot.js'
import MessageHandler from './bark/MessageHandler.js'
import NoticeSender from './bark/NoticeSender.js'
import { sleep } from './schedule.js'

const bot = await getWxBot()

const mh = new MessageHandler(bot)
bot.on('message', async (msg) => {
    await mh.handle(msg)
})

bot.on('login', async () => {
    const noticeSender = new NoticeSender(bot)
    while (bot.state === bot.CONF.STATE.login) {
        await noticeSender.sendMessage()
        await sleep(5000)
    }
})

if (bot.PROP.uin) {
    bot.restart()
} else {
    bot.start()
}





