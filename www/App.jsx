import { defineComponent } from 'vue'
import { RouterView } from 'vue-router'
import { NConfigProvider, zhCN, dateZhCN, lightTheme, NMessageProvider } from 'naive-ui'

export default defineComponent({
    render() {
        return (
            <NConfigProvider theme={lightTheme} locale={zhCN} dateLocale={dateZhCN}>
                <NMessageProvider>
                    <RouterView/>
                </NMessageProvider>
            </NConfigProvider>
        )
    },
})
