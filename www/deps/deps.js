import { loadJs } from '../common/load.js'

await loadJs(new URL('./emotion-css.umd.min.js', import.meta.url).toString())
await loadJs(new URL('./vue.global.prod.js', import.meta.url).toString())

export const getGlobalModule = (name) => window[name] || {}

export const { reactive, watch, onMounted, createApp, ref, computed } = getGlobalModule('Vue')
export const { css, cx, injectGlobal } = getGlobalModule('emotion')
