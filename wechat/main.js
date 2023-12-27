import { getWeChatBot } from './wx.js'
import { scheduleBarkTasks } from './bark.js'

const bot = await getWeChatBot()

bot.on('login', async () => {
    await scheduleBarkTasks(bot)
})

if (bot.PROP.uin) {
    bot.restart()
} else {
    bot.start()
}





