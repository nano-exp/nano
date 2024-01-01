import fs from 'node:fs/promises'
import WeChat from 'wechat4u'

// see https://github.com/nodeWechat/wechat4u

async function createWxBot(dataStorePath) {
    try {
        console.info('try load bot data:', dataStorePath)
        const botData = JSON.parse(await fs.readFile(dataStorePath, 'utf-8'))
        return new WeChat(botData)
    } catch (err) {
        return new WeChat()
    }
}

export async function newWxBot(dataStorePath) {
    const bot = await createWxBot(dataStorePath)

    bot.on('uuid', (uuid) => {
        console.info('QR Code', 'https://login.weixin.qq.com/qrcode/' + uuid)
    })

    bot.on('login', async () => {
        console.info('login done')
        await fs.writeFile(dataStorePath, JSON.stringify(bot.botData))
    })

    bot.on('logout', async () => {
        console.info('logout done')
        await fs.unlink(dataStorePath)
    })

    bot.on('error', (err) => {
        if (err.tips) {
            console.info(err.tips)
        } else {
            console.error('error', err)
        }
    })

    return bot
}



