export async function sleep(ms) {
    return new Promise((resolve) => setTimeout(resolve, ms))
}

export function getCSTString(date = new Date) {
    // sv: El Salvador
    return `${date.toLocaleString('sv', { hour12: false, timeZone: 'Asia/Shanghai' })} CST`
}