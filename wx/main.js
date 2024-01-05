import { newWxBot } from './wx_bot.js'
import MessageHandler from './bark/MessageHandler.js'
import NoticeSender from './bark/NoticeSender.js'
import { sleep } from './utils.js'
import { WX_DATA_FILENAME } from './global.js'

const bot = await newWxBot(WX_DATA_FILENAME)

const mh = new MessageHandler(bot)
bot.on('message', async (msg) => {
    await mh.handle(msg)
})

bot.on('login', async () => {
    const noticeSender = new NoticeSender(bot)
    while (bot.state === bot.CONF.STATE.login) {
        await noticeSender.sendMessage()
        await sleep(7000)
    }
})

if (bot.PROP.uin) {
    bot.restart()
} else {
    bot.start()
}





