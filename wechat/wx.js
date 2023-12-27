import WeChat from 'wechat4u'
import fs from 'fs/promises'
import { MessageHandler } from './message.js'
import { WX_DATA_FILENAME } from './global.js'

// see https://github.com/nodeWechat/wechat4u/blob/master/run-core.js

async function createWeChatBot() {
    try {
        console.info('try load bot data:', WX_DATA_FILENAME)
        const botData = JSON.parse(await fs.readFile(WX_DATA_FILENAME, 'utf-8'))
        return new WeChat(botData)
    } catch (err) {
        return new WeChat()
    }
}

export async function getWeChatBot() {
    const bot = await createWeChatBot()

    bot.on('uuid', (uuid) => {
        console.info('QR Code', 'https://login.weixin.qq.com/qrcode/' + uuid)
    })

    bot.on('login', async () => {
        console.info('login done')
        await fs.writeFile(WX_DATA_FILENAME, JSON.stringify(bot.botData))
    })

    bot.on('logout', async () => {
        console.info('logout done')
        await fs.unlink(WX_DATA_FILENAME)
    })

    bot.on('error', (err) => {
        if (err.tips) {
            console.info(err.tips)
        } else {
            console.error('error', err)
        }
    })

    const mh = new MessageHandler(bot)
    bot.on('message', async (msg) => {
        await mh.handle(msg)
    })

    return bot
}



